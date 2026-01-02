package com.shiv.milkpassbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shiv.milkpassbook.api.login_party.LoginApi;
import com.shiv.milkpassbook.api.login_party.LoginRequest;
import com.shiv.milkpassbook.api.login_party.LoginResponse;
import com.shiv.milkpassbook.loginretrofit.MstPartyRepository;
import com.shiv.milkpassbook.MstSoftCustReg.database.MstSoftCustRegDatabase;
import com.shiv.milkpassbook.MstSoftCustReg.repository.MstSoftCustRegRepository;
import com.shiv.milkpassbook.retrofit.MstSoftCustRegApi;
import com.shiv.milkpassbook.util.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final long REFRESH_INTERVAL = 1000; // 1 second
    private EditText etSoftCustCode, etMob;
    private Button btnLogin;
    private MstPartyRepository mstPartyRepository;
    private MstSoftCustRegDatabase mstSoftCustRegDatabase;
    private MstSoftCustRegRepository mstSoftCustRegRepository;
    private Handler refreshHandler = new Handler();
    private Runnable refreshRunnable;
    private String softCustCode, cleanMob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etSoftCustCode = findViewById(R.id.etSoftCustNumber);
        etMob = findViewById(R.id.etMob);
        btnLogin = findViewById(R.id.btnLogin);

        mstPartyRepository = new MstPartyRepository(this);
        mstSoftCustRegDatabase = MstSoftCustRegDatabase.getInstance(this);
        mstSoftCustRegRepository = new MstSoftCustRegRepository(
                RetrofitClient.getInstance().create(MstSoftCustRegApi.class),
                mstSoftCustRegDatabase.mstSoftCustRegDao()
        );

        btnLogin.setOnClickListener(v -> validateAndLogin());

        // Start periodic refresh when activity is created
        startPeriodicRefresh();
    }

    private void startPeriodicRefresh() {
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                checkForUpdates();
                refreshHandler.postDelayed(this, REFRESH_INTERVAL);
            }
        };
        refreshHandler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
    }

    private void checkForUpdates() {
        mstSoftCustRegRepository.refreshData(new MstSoftCustRegRepository.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    Log.d("LoginActivity", "MstSoftCustReg data updated successfully");
                } else {
                    Log.d("LoginActivity", "No changes in MstSoftCustReg data");
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("LoginActivity", "Error refreshing MstSoftCustReg data: " + e.getMessage());
            }
        });
    }

    private void validateAndLogin() {
        softCustCode = etSoftCustCode.getText().toString().trim();
        String mob = etMob.getText().toString().trim();

        if (softCustCode.isEmpty() || mob.isEmpty()) {
            Toast.makeText(this, "Please enter both SoftCustCode and Mobile", Toast.LENGTH_SHORT).show();
            return;
        }

        cleanMob = mob.replaceAll("[^0-9]", "");
        if (cleanMob.length() > 10) {
            cleanMob = cleanMob.substring(cleanMob.length() - 10);
        }

        // Check renewal date first
        checkRenewalDateAndProceed();
    }

    private void checkRenewalDateAndProceed() {
        new Thread(() -> {
            String renewalDateStr = mstSoftCustRegDatabase.mstSoftCustRegDao().getRenewalDate(softCustCode);
            if (renewalDateStr != null && !renewalDateStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date renewalDate = sdf.parse(renewalDateStr);
                    Date currentDate = new Date();

                    if (renewalDate.before(currentDate)) {
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this,
                                    "Your subscription has expired. Please renew to continue.",
                                    Toast.LENGTH_LONG).show();
                        });
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            // Proceed with login if renewal date is valid
            runOnUiThread(this::performFinalLogin);
        }).start();
    }

    private void performFinalLogin() {
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("LOGIN_REQUEST", "softCustCode=" + softCustCode + ", mob=" + cleanMob + ", deviceId=" + deviceId);

        LoginRequest request = new LoginRequest(softCustCode, cleanMob, deviceId);

        LoginApi apiService = RetrofitClient.getInstance().create(LoginApi.class);
        Call<LoginResponse> call = apiService.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LOGIN_RESPONSE", new Gson().toJson(response.body()));
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isStatus()) {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // Save login session
                        SharedPreferences prefs = getSharedPreferences("MilkAppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("mobileNumber", cleanMob);
                        editor.putString("softCustCode", softCustCode);
                        editor.apply();

                        // Proceed to Dashboard after saving local data
                        mstPartyRepository.fetchAndStoreMstParties(() ->
                                runOnUiThread(() -> openDashboard(cleanMob, softCustCode))
                        );
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("LOGIN_RESPONSE_ERROR", "Raw: " + response.toString());
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("LOGIN_RESPONSE_ERROR", "Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openDashboard(String mobile, String softCustCode) {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        intent.putExtra("MOBILE_NUMBER", mobile);
        intent.putExtra("SOFT_CUST_CODE", softCustCode);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the periodic refresh when activity is destroyed
        refreshHandler.removeCallbacks(refreshRunnable);
    }
}