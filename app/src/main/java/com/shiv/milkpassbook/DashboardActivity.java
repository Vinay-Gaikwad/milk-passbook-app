package com.shiv.milkpassbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shiv.milkpassbook.softcustdatabase.MstSoftCustDatabase;
import com.shiv.milkpassbook.softcustdatabase.MstSoftCustEntity;
import com.shiv.milkpassbook.softcustretrofit.MstSoftCustApiService;
import com.shiv.milkpassbook.softcustretrofit.MstSoftCustResponse;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.shiv.milkpassbook.MstSoftCustReg.database.MstSoftCustRegDao;
import com.shiv.milkpassbook.MstSoftCustReg.database.MstSoftCustRegDatabase;
import com.shiv.milkpassbook.MstSoftCustReg.database.MstSoftCustRegEntity;
import com.shiv.milkpassbook.MstSoftCustReg.repository.MstSoftCustRegRepository;
import com.shiv.milkpassbook.api.validate_session.RetrofitClientSession;
import com.shiv.milkpassbook.api.validate_session.SessionApi;
import com.shiv.milkpassbook.api.validate_session.SessionRequest;
import com.shiv.milkpassbook.api.validate_session.SessionResponse;

import com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkDatabase;
import com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkEntity;
import com.shiv.milkpassbook.lngdatabase.MstLngSubDatabase;
import com.shiv.milkpassbook.lngdatabase.MstLngSubEntity;

import com.shiv.milkpassbook.mstlngsubretrofit.MstLngSubApiService;
import com.shiv.milkpassbook.mstlngsubretrofit.MstLngSubResponse;
import com.shiv.milkpassbook.mstlngsubretrofit.RetrofitClient;
import com.shiv.milkpassbook.cmntrandatabase.MstCmnTrnDatabase;
import com.shiv.milkpassbook.cmntrandatabase.MstCmnTrnEntity;
import com.shiv.milkpassbook.mstcmntrnretrofit.MstCmnTrnApiService;
import com.shiv.milkpassbook.mstcmntrnretrofit.MstCmnTrnResponse;
import com.shiv.milkpassbook.mstcmntrnretrofit.RetrofitClientCmnTrn;
import com.shiv.milkpassbook.deddatabase.MstDedDatabase;
import com.shiv.milkpassbook.deddatabase.MstDedEntity;
import com.shiv.milkpassbook.mstdedretrofit.MstDedApiService;
import com.shiv.milkpassbook.mstdedretrofit.MstDedResponse;
import com.shiv.milkpassbook.mstdedretrofit.RetrofitClientDed;
import com.shiv.milkpassbook.mstparty.AppDatabase;
import com.shiv.milkpassbook.mstparty.MstParty;

import com.shiv.milkpassbook.softcustretrofit.RetrofitClientSoftCust;
import com.shiv.milkpassbook.tempmilkbilldatabase.TempMilkBillDatabase;
import com.shiv.milkpassbook.tempmilkbilldatabase.TempMilkBillEntity;
import com.shiv.milkpassbook.milkbillretrofit.TempMilkBillApiService;
import com.shiv.milkpassbook.milkbillretrofit.TempMilkBillResponse;
import com.shiv.milkpassbook.milkbillretrofit.RetrofitClientMilkBill;
import com.shiv.milkpassbook.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private static final long REFRESH_INTERVAL = 1000; // 1 second
    private LinearLayout btnDaily, btnPeriodic;
    private LinearLayout layoutDailyContainer, layoutPeriodicContainer;
    private TextView tvName, tvNumber, tvDairyName;
    private String loggedInMobileNumber;
    private ProgressBar loadingSpinner;
    private String softCustCode;
    private String deviceId;
    private String firmName = "";
    private RadioGroup radioGroupMilkType;
    private RadioButton radioCow, radioBuffalo;
    private int selectedMilkType = 1; // Changed from 2 to 1 for Buffalo default

    private Handler handler = new Handler();
    private Runnable sessionCheckRunnable;
    private MstSoftCustRegRepository mstSoftCustRegRepository;
    private Handler renewalHandler = new Handler();
    private Handler refreshHandler = new Handler();
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        loggedInMobileNumber = getIntent().getStringExtra("MOBILE_NUMBER");
        softCustCode = getIntent().getStringExtra("SOFT_CUST_CODE");
        deviceId = android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        if (loggedInMobileNumber == null || softCustCode == null) {
            Log.e("DashboardActivity", "Missing intent extras: SoftCustCode or Mobile is null");
            finish();
            return;
        }

        // Initialize MstSoftCustRegRepository
        MstSoftCustRegDao dao = MstSoftCustRegDatabase.getInstance(this).mstSoftCustRegDao();
        mstSoftCustRegRepository = new MstSoftCustRegRepository(
                ApiClient.getApiService(),
                dao
        );

        // Set up database observers
        setupDatabaseObservers();

        // Save Mobile and SoftCustCode to SharedPreferences for session check
        getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .edit()
                .putString("SoftCustCode", softCustCode)
                .putString("Mobile", loggedInMobileNumber)
                .apply();

        // Initialize views
        btnDaily = findViewById(R.id.btnDaily);
        btnPeriodic = findViewById(R.id.btnPeriodic);
        layoutDailyContainer = findViewById(R.id.layoutDailyBills);
        layoutPeriodicContainer = findViewById(R.id.layoutPeriodicBills);
        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvNumber);
        tvDairyName = findViewById(R.id.tvDairyName);
        loadingSpinner = findViewById(R.id.loadingSpinner);
        radioGroupMilkType = findViewById(R.id.radioGroupMilkType);
        radioCow = findViewById(R.id.radioCow);
        radioBuffalo = findViewById(R.id.radioBuffalo);

        // Load saved milk type (or default to cow)
        SharedPreferences prefs = getSharedPreferences("MilkAppPrefs", MODE_PRIVATE);
        selectedMilkType = prefs.getInt("selectedMilkType", 1); // Default: buff (1)
        updateRadioButtonSelection();

        // Set up milk type radio group listener
        radioGroupMilkType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioCow) {
                selectedMilkType = 2;
            } else if (checkedId == R.id.radioBuffalo) {
                selectedMilkType = 1;
            }
            // Save selection
            prefs.edit().putInt("selectedMilkType", selectedMilkType).apply();
            // Refresh bills
            loadSampleDailyBills();
        });

        // Menu button setup
        ImageView btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(DashboardActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.dashboard_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_logout) {
                    new AlertDialog.Builder(DashboardActivity.this, R.style.MyAlertDialogStyle)
                            .setTitle("Confirm Logout")
                            .setMessage("Are you sure you want to logout?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                getSharedPreferences("MilkAppPrefs", MODE_PRIVATE)
                                        .edit()
                                        .clear()
                                        .apply();
                                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            })
                            .setNegativeButton("No", null)
                            .show();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        boolean openPeriodic = getIntent().getBooleanExtra("OPEN_PERIODIC", false);
        if (openPeriodic) {
            showPeriodicBills();
        } else {
            showDailyBills();
        }

        btnDaily.setOnClickListener(v -> showDailyBills());
        btnPeriodic.setOnClickListener(v -> showPeriodicBills());

        loadSampleDailyBills();
        loadSamplePeriodicBills();

        loadUserProfileWithRetry(5);
        fetchAndStoreLanguageData();
        fetchAndStoreCmnTrnData();
        fetchAndStoreDeductionData();
        fetchAndStoreTempMilkBillData();
        fetchAndStoreInsertTempTrnMilkData();
        fetchAndStoreSoftCustData();

        startSessionCheck();
        checkRenewalDate();

        // Initial data refresh
        refreshData();

        // Start periodic refresh
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
                    Log.d("Dashboard", "Data refreshed successfully");
                    // Check renewal date after each refresh
                    checkRenewalDate();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("Dashboard", "Error refreshing data: " + e.getMessage());
            }
        });
    }

    private void setupDatabaseObservers() {
        mstSoftCustRegRepository.getAllSoftCustomers().observe(this, new Observer<List<MstSoftCustRegEntity>>() {
            @Override
            public void onChanged(List<MstSoftCustRegEntity> entities) {
                Log.d("DB_OBSERVER", "Data changed. Count: " + (entities != null ? entities.size() : 0));
                if (entities != null && !entities.isEmpty()) {
                    // Update UI or do something with the data
                    for (MstSoftCustRegEntity entity : entities) {
                        Log.d("DB_ITEM", "Code: " + entity.SoftCustCode + ", Mob: " + entity.Mob);
                    }
                } else {
                    Log.d("DB_OBSERVER", "No data in database");
                }
            }
        });
    }

    private void refreshData() {
        mstSoftCustRegRepository.refreshData(new MstSoftCustRegRepository.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                runOnUiThread(() -> {
                    Toast.makeText(DashboardActivity.this,
                            "Data refreshed successfully", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(DashboardActivity.this,
                            "Error refreshing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void updateRadioButtonSelection() {
        if (selectedMilkType == 2) {
            radioCow.setChecked(true);
        } else {
            radioBuffalo.setChecked(true);
        }
    }

    private void startSessionCheck() {
        sessionCheckRunnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String softCustCode = preferences.getString("SoftCustCode", "");
                String mob = preferences.getString("Mobile", "");
                String deviceId = android.provider.Settings.Secure.getString(getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);

                Log.d("SessionCheck", "SoftCustCode: " + softCustCode + ", Mobile: " + mob + ", DeviceId: " + deviceId);

                SessionRequest request = new SessionRequest(softCustCode, deviceId, mob);
                SessionApi api = RetrofitClientSession.getApiService();

                api.validate(request).enqueue(new Callback<SessionResponse>() {
                    @Override
                    public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            SessionResponse sr = response.body();
                            Log.d("SessionCheck", "Response status: " + sr.isStatus() + ", message: " + sr.getMessage());
                            if (!sr.isStatus()) {
                                Toast.makeText(DashboardActivity.this, sr.getMessage(), Toast.LENGTH_LONG).show();
                                logoutAndRedirect();
                            }
                        } else {
                            Log.e("SessionCheck", "Session validation failed. Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<SessionResponse> call, Throwable t) {
                        Log.e("SessionCheck", "API call failed: " + t.getMessage());
                    }
                });

                handler.postDelayed(this, 2000);
            }
        };

        handler.post(sessionCheckRunnable);
    }

    private void validateSession() {
        SharedPreferences preferences = getSharedPreferences("MilkAppPrefs", MODE_PRIVATE);
        String savedSoftCustCode = preferences.getString("SoftCustCode", "");
        String savedMobile = preferences.getString("Mobile", "");
        String currentDeviceId = deviceId;

        Log.d("SessionCheck", "SoftCustCode: " + savedSoftCustCode + ", DeviceId: " + currentDeviceId + ", Mobile: " + savedMobile);

        SessionApi apiService = RetrofitClientSession.getApiService();
        SessionRequest request = new SessionRequest(savedSoftCustCode, currentDeviceId, savedMobile);

        apiService.validate(request).enqueue(new Callback<SessionResponse>() {
            @Override
            public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SessionResponse sessionResponse = response.body();
                    if (!sessionResponse.isStatus()) {
                        Toast.makeText(DashboardActivity.this, sessionResponse.getMessage(), Toast.LENGTH_LONG).show();
                        logoutAndRedirect();
                    } else {
                        Log.d("SessionCheck", "Session validated.");
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "Server error during session check.", Toast.LENGTH_SHORT).show();
                    Log.d("SessionCheck", "HTTP Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SessionResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DashboardActivity.this, "Session API failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutAndRedirect() {
        getSharedPreferences("MilkAppPrefs", MODE_PRIVATE).edit()
                .clear()
                .apply();
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void checkRenewalDate() {
        new Thread(() -> {
            MstSoftCustRegEntity entity = mstSoftCustRegRepository.getBySoftCustCode(softCustCode);
            if (entity != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                if (currentDate.compareTo(entity.RenwDate) > 0) {
                    runOnUiThread(() -> {
                        Toast.makeText(DashboardActivity.this,
                                "Your subscription has expired. Please renew.",
                                Toast.LENGTH_LONG).show();
                        logoutAndRedirect();
                    });
                }
            }
        }).start();
    }

    private void refreshSoftCustRegData() {
        refreshData();
    }

    private void updateData() {
        new Thread(() -> {
            try {
                refreshSoftCustRegData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadUserProfileWithRetry(int attemptsLeft) {
        new Thread(() -> {
            try {
                AppDatabase appDb = AppDatabase.getInstance(this);
                MstParty party = appDb.mstPartyDao().getPartyByMobile(loggedInMobileNumber);

                if (party != null) {
                    MstSoftCustDatabase softCustDb = MstSoftCustDatabase.getInstance(this);
                    MstSoftCustEntity softCust = softCustDb.mstSoftCustDao().getBySoftCustCode(party.getSoftCustCode());

                    if (softCust != null) {
                        runOnUiThread(() -> {
                            tvName.setText(party.getPartyNameOth());
                            tvNumber.setText("Code: " + party.getPartyCode());
                            tvDairyName.setText("Dairy: " + softCust.getSoftCustName());
                            firmName = softCust.getSoftCustName();
                        });
                    } else if (attemptsLeft > 0) {
                        new android.os.Handler(getMainLooper()).postDelayed(() ->
                                        loadUserProfileWithRetry(attemptsLeft - 1),
                                500);
                    } else {
                        runOnUiThread(() -> {
                            tvName.setText(party.getPartyNameOth());
                            tvNumber.setText("Code: " + party.getPartyCode());
                            tvDairyName.setText("Dairy: Not available");
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Error loading profile", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void fetchAndStoreLanguageData() {
        MstLngSubApiService apiService = RetrofitClient.getApiService();
        apiService.getLanguageSubs().enqueue(new Callback<MstLngSubResponse>() {
            @Override
            public void onResponse(Call<MstLngSubResponse> call, Response<MstLngSubResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<MstLngSubEntity> apiList = response.body().getData();

                    new Thread(() -> {
                        MstLngSubDatabase db = MstLngSubDatabase.getInstance(DashboardActivity.this);
                        List<MstLngSubEntity> localList = db.mstLngSubDao().getAll();

                        if (localList == null || localList.size() != apiList.size()) {
                            db.mstLngSubDao().deleteAll();
                            db.mstLngSubDao().insertAll(apiList);
                            Log.d("LanguageDB", "Inserted " + apiList.size() + " rows into lng_sub_db");
                        } else {
                            Log.d("LanguageDB", "No change in API data. Skipped update.");
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<MstLngSubResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchAndStoreCmnTrnData() {
        MstCmnTrnApiService apiService = RetrofitClientCmnTrn.getApiService();
        apiService.getCmnTrnList().enqueue(new Callback<MstCmnTrnResponse>() {
            @Override
            public void onResponse(Call<MstCmnTrnResponse> call, Response<MstCmnTrnResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<MstCmnTrnEntity> apiList = response.body().getData();

                    new Thread(() -> {
                        MstCmnTrnDatabase db = MstCmnTrnDatabase.getInstance(DashboardActivity.this);
                        List<MstCmnTrnEntity> localList = db.mstCmnTrnDao().getAll();

                        if (localList == null || localList.size() != apiList.size()) {
                            db.mstCmnTrnDao().deleteAll();
                            db.mstCmnTrnDao().insertAll(apiList);
                            Log.d("CmnTrnDB", "Inserted " + apiList.size() + " rows into cmn_trn_db");
                        } else {
                            Log.d("CmnTrnDB", "No change in API data. Skipped update.");
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<MstCmnTrnResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchAndStoreDeductionData() {
        MstDedApiService apiService = RetrofitClientDed.getApiService();
        apiService.getDeductions().enqueue(new Callback<MstDedResponse>() {
            @Override
            public void onResponse(Call<MstDedResponse> call, Response<MstDedResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<MstDedEntity> apiList = response.body().getData();

                    new Thread(() -> {
                        MstDedDatabase db = MstDedDatabase.getInstance(DashboardActivity.this);
                        List<MstDedEntity> localList = db.mstDedDao().getAll();

                        if (localList == null || localList.size() != apiList.size()) {
                            db.mstDedDao().deleteAll();
                            db.mstDedDao().insertAll(apiList);
                            Log.d("DeductionDB", "Inserted " + apiList.size() + " rows into mst_ded_db");
                        } else {
                            Log.d("DeductionDB", "No change in API data. Skipped update.");
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<MstDedResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchAndStoreTempMilkBillData() {
        TempMilkBillApiService apiService = RetrofitClientMilkBill.getApiService();
        apiService.getMilkBillData().enqueue(new Callback<TempMilkBillResponse>() {
            @Override
            public void onResponse(Call<TempMilkBillResponse> call, Response<TempMilkBillResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<TempMilkBillEntity> apiList = response.body().getData();

                    new Thread(() -> {
                        TempMilkBillDatabase db = TempMilkBillDatabase.getInstance(DashboardActivity.this);
                        List<TempMilkBillEntity> localList = db.tempMilkBillDao().getAll();

                        if (localList == null || localList.size() != apiList.size()) {
                            db.tempMilkBillDao().deleteAll();
                            db.tempMilkBillDao().insertAll(apiList);
                            Log.d("MilkBillDB", "Inserted " + apiList.size() + " rows.");
                        } else {
                            Log.d("MilkBillDB", "No changes found. Skipping update.");
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<TempMilkBillResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchAndStoreInsertTempTrnMilkData() {
        com.shiv.milkpassbook.inserttempmilkretrofit.InsertTempTrnMilkApiService apiService =
                com.shiv.milkpassbook.inserttempmilkretrofit.RetrofitClientTempMilkTrn.getApiService();

        apiService.getMilkTrnList().enqueue(new retrofit2.Callback<com.shiv.milkpassbook.inserttempmilkretrofit.InsertTempTrnMilkResponse>() {
            @Override
            public void onResponse(Call<com.shiv.milkpassbook.inserttempmilkretrofit.InsertTempTrnMilkResponse> call,
                                   Response<com.shiv.milkpassbook.inserttempmilkretrofit.InsertTempTrnMilkResponse> response) {

                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkEntity> apiList = response.body().getData();

                    new Thread(() -> {
                        com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkDatabase db =
                                com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkDatabase.getInstance(DashboardActivity.this);
                        List<com.shiv.milkpassbook.inserttempmilkdatabase.InsertTempTrnMilkEntity> localList =
                                db.insertTempTrnMilkDao().getAll();

                        if (localList == null || localList.size() != apiList.size()) {
                            db.insertTempTrnMilkDao().deleteAll();
                            db.insertTempTrnMilkDao().insertAll(apiList);
                            Log.d("InsertMilkTrnDB", "Inserted " + apiList.size() + " rows.");
                        } else {
                            Log.d("InsertMilkTrnDB", "No changes. Skipping update.");
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<com.shiv.milkpassbook.inserttempmilkretrofit.InsertTempTrnMilkResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchAndStoreSoftCustData() {
        MstSoftCustApiService apiService = RetrofitClientSoftCust.getApiService();
        Call<MstSoftCustResponse> call = apiService.getSoftCustList();

        call.enqueue(new Callback<MstSoftCustResponse>() {
            @Override
            public void onResponse(Call<MstSoftCustResponse> call, Response<MstSoftCustResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<MstSoftCustEntity> apiList = response.body().getData();

                    new Thread(() -> {
                        MstSoftCustDatabase db = MstSoftCustDatabase.getInstance(DashboardActivity.this);
                        List<MstSoftCustEntity> localList = db.mstSoftCustDao().getAll();

                        boolean shouldUpdate = false;

                        if (localList == null || localList.size() != apiList.size()) {
                            shouldUpdate = true;
                        } else {
                            for (int i = 0; i < apiList.size(); i++) {
                                if (!apiList.get(i).equals(localList.get(i))) {
                                    shouldUpdate = true;
                                    break;
                                }
                            }
                        }

                        if (shouldUpdate) {
                            db.runInTransaction(() -> {
                                db.mstSoftCustDao().deleteAll();
                                db.mstSoftCustDao().insertAll(apiList);
                            });
                            Log.d("SoftCustDB", "Updated with " + apiList.size() + " new records.");
                        } else {
                            Log.d("SoftCustDB", "No changes found. Skipping update.");
                        }
                    }).start();
                } else {
                    Log.e("SoftCustAPI", "Invalid response or status=false: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MstSoftCustResponse> call, Throwable t) {
                Log.e("SoftCustAPI", "API call failed", t);
            }
        });
    }

    private void showDailyBills() {
        btnDaily.setBackgroundResource(R.drawable.toggle_selected_background);
        ((TextView) btnDaily.getChildAt(0)).setTextColor(Color.BLACK);

        btnPeriodic.setBackgroundColor(Color.TRANSPARENT);
        ((TextView) btnPeriodic.getChildAt(0)).setTextColor(Color.WHITE);

        layoutDailyContainer.setVisibility(View.VISIBLE);
        layoutPeriodicContainer.setVisibility(View.GONE);
        radioGroupMilkType.setVisibility(View.VISIBLE);
    }

    private void showPeriodicBills() {
        btnPeriodic.setBackgroundResource(R.drawable.toggle_selected_background);
        ((TextView) btnPeriodic.getChildAt(0)).setTextColor(Color.BLACK);

        btnDaily.setBackgroundColor(Color.TRANSPARENT);
        ((TextView) btnDaily.getChildAt(0)).setTextColor(Color.WHITE);

        layoutDailyContainer.setVisibility(View.GONE);
        layoutPeriodicContainer.setVisibility(View.VISIBLE);
        radioGroupMilkType.setVisibility(View.GONE);
    }

    private void loadSampleDailyBills() {
        layoutDailyContainer.removeAllViews();
        layoutPeriodicContainer.removeAllViews();

        loadingSpinner.setVisibility(View.VISIBLE);
        layoutDailyContainer.setVisibility(View.GONE);
        layoutPeriodicContainer.setVisibility(View.GONE);

        new android.os.Handler().postDelayed(() -> {
            new Thread(() -> {
                try {
                    AppDatabase appDb = AppDatabase.getInstance(this);
                    MstParty party = appDb.mstPartyDao().getPartyByMobile(loggedInMobileNumber);

                    if (party == null) {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                            loadingSpinner.setVisibility(View.GONE);
                        });
                        return;
                    }

                    InsertTempTrnMilkDatabase milkDb = InsertTempTrnMilkDatabase.getInstance(this);
                    List<InsertTempTrnMilkEntity> milkTransactions = milkDb.insertTempTrnMilkDao()
                            .getByPartyAndSoftCustAndMilkType(
                                    party.getPartyCode(),
                                    party.getSoftCustCode(),
                                    selectedMilkType
                            );

                    runOnUiThread(() -> {
                        loadingSpinner.setVisibility(View.GONE);
                        layoutDailyContainer.setVisibility(View.VISIBLE);
                        layoutPeriodicContainer.setVisibility(View.GONE);

                        if (milkTransactions == null || milkTransactions.isEmpty()) {
                            layoutDailyContainer.removeAllViews();
                            TextView emptyText = new TextView(DashboardActivity.this);
                            emptyText.setText("डेटा सापडला नाही");
                            emptyText.setTextSize(18);
                            emptyText.setTextColor(Color.WHITE);
                            emptyText.setGravity(Gravity.CENTER);
                            emptyText.setPadding(0, 32, 0, 0);
                            emptyText.setTypeface(null, Typeface.BOLD);
                            layoutDailyContainer.addView(emptyText);
                            return;
                        }

                        // Create a map to group transactions by date and then by shift (morning/evening)
                        Map<String, Map<Integer, List<InsertTempTrnMilkEntity>>> groupedTransactions = new HashMap<>();
                        for (InsertTempTrnMilkEntity transaction : milkTransactions) {
                            String date = transaction.getTrDate().split(" ")[0];
                            int shiftCode = transaction.getMeCode(); // 1=Morning, 2=Evening

                            if (!groupedTransactions.containsKey(date)) {
                                groupedTransactions.put(date, new HashMap<>());
                            }

                            if (!groupedTransactions.get(date).containsKey(shiftCode)) {
                                groupedTransactions.get(date).put(shiftCode, new ArrayList<>());
                            }

                            groupedTransactions.get(date).get(shiftCode).add(transaction);
                        }

                        // Sort dates in descending order (newest first)
                        List<String> sortedDates = new ArrayList<>(groupedTransactions.keySet());
                        Collections.sort(sortedDates, Collections.reverseOrder());

                        for (String date : sortedDates) {
                            TextView dateHeader = new TextView(this);
                            dateHeader.setText(date);
                            dateHeader.setTextSize(16);
                            dateHeader.setTypeface(null, Typeface.BOLD);
                            dateHeader.setTextColor(Color.WHITE);
                            dateHeader.setPadding(16, 32, 16, 8);
                            layoutDailyContainer.addView(dateHeader);

                            // Get shifts for this date
                            Map<Integer, List<InsertTempTrnMilkEntity>> shifts = groupedTransactions.get(date);

                            // Process evening shift first (shift code 2)
                            if (shifts.containsKey(2)) {
                                List<InsertTempTrnMilkEntity> eveningTransactions = shifts.get(2);
                                Collections.sort(eveningTransactions, (t1, t2) -> {
                                    String time1 = t1.getTrDate().split(" ")[1];
                                    String time2 = t2.getTrDate().split(" ")[1];
                                    return time2.compareTo(time1); // Descending order
                                });

                                for (InsertTempTrnMilkEntity transaction : eveningTransactions) {
                                    addBillCard(date, "संध्याकाळ", transaction, party);
                                }
                            }

                            // Then process morning shift (shift code 1)
                            if (shifts.containsKey(1)) {
                                List<InsertTempTrnMilkEntity> morningTransactions = shifts.get(1);
                                Collections.sort(morningTransactions, (t1, t2) -> {
                                    String time1 = t1.getTrDate().split(" ")[1];
                                    String time2 = t2.getTrDate().split(" ")[1];
                                    return time2.compareTo(time1); // Descending order
                                });

                                for (InsertTempTrnMilkEntity transaction : morningTransactions) {
                                    addBillCard(date, "सकाळ", transaction, party);
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Error loading milk transactions", Toast.LENGTH_SHORT).show();
                        loadingSpinner.setVisibility(View.GONE);
                    });
                }
            }).start();
        }, 2000);
    }

    private void addBillCard(String date, String shift, InsertTempTrnMilkEntity transaction, MstParty party) {
        View billCard = getLayoutInflater().inflate(R.layout.item_bill_card, layoutDailyContainer, false);

        // Remove unused views
        billCard.findViewById(R.id.tvMilkType).setVisibility(View.GONE);
        billCard.findViewById(R.id.tvCustomerNo).setVisibility(View.GONE);
        billCard.findViewById(R.id.tvDate).setVisibility(View.GONE);
        billCard.findViewById(R.id.tvLiter).setVisibility(View.GONE);

        // Update card views in new order
        ((TextView) billCard.findViewById(R.id.tvCustomer)).setText("शिफ्ट: " + shift);

        // Combine liter with fat, SNF and rate in one line (now moved before fat)
        ((TextView) billCard.findViewById(R.id.tvShift)).setText(
                "लिटर: " + String.format("%.2f", transaction.getQty()) +
                        " | फॅट: " + String.format("%.1f", transaction.getFat()) +
                        " | SNF: " + String.format("%.1f", transaction.getSnf()) +
                        " | दर: ₹" + String.format("%.2f", transaction.getRate())
        );

        // Show total amount
        ((TextView) billCard.findViewById(R.id.tvTotal)).setText("रक्कम: ₹" + transaction.getAmt());

        if (transaction.getMlkTypeCode() == 1) {
            billCard.setBackgroundResource(R.drawable.buffalo_card_bg);
        } else {
            billCard.setBackgroundResource(R.drawable.cow_card_bg);
        }

        billCard.setOnClickListener(view -> {
            Intent intent = new Intent(this, BillDetailActivity.class);
            intent.putExtra("firmName", firmName);
            intent.putExtra("partyName", party.getPartyNameOth());
            intent.putExtra("partyCode", party.getPartyCode());
            intent.putExtra("date", date);
            intent.putExtra("time", transaction.getTrDate().split(" ")[1]);
            intent.putExtra("shift", shift);
            intent.putExtra("milkType", transaction.getMlkTypeCode() == 1 ? "म्हैस" : "गाय");
            intent.putExtra("liter", String.format("%.2f", transaction.getQty()));
            intent.putExtra("fat", String.format("%.1f", transaction.getFat()));
            intent.putExtra("snf", String.format("%.1f", transaction.getSnf()));
            intent.putExtra("rate", String.format("%.2f", transaction.getRate()));
            intent.putExtra("total", String.format("%.2f", transaction.getAmt()));
            startActivity(intent);
        });

        layoutDailyContainer.addView(billCard);
    }

    private void loadSamplePeriodicBills() {
        layoutPeriodicContainer.removeAllViews();
        layoutDailyContainer.removeAllViews();

        loadingSpinner.setVisibility(View.VISIBLE);
        layoutPeriodicContainer.setVisibility(View.GONE);
        layoutDailyContainer.setVisibility(View.GONE);

        new android.os.Handler().postDelayed(() -> {
            loadingSpinner.setVisibility(View.GONE);
            layoutPeriodicContainer.setVisibility(View.VISIBLE);
            layoutDailyContainer.setVisibility(View.GONE);

            TextView dateHeader = new TextView(this);
            dateHeader.setText("2025-06-15 ते 2025-06-20");
            dateHeader.setTextSize(16);
            dateHeader.setTypeface(null, Typeface.BOLD);
            dateHeader.setTextColor(Color.WHITE);
            dateHeader.setPadding(16, 32, 16, 8);
            layoutPeriodicContainer.addView(dateHeader);

            View billCard = getLayoutInflater().inflate(R.layout.item_bill_card, layoutPeriodicContainer, false);
            ((TextView) billCard.findViewById(R.id.tvCustomer)).setText("ग्राहकाचे नाव: सुरेश पाटील");
            ((TextView) billCard.findViewById(R.id.tvCustomerNo)).setText("ग्राहक क्रमांक: 8");
            ((TextView) billCard.findViewById(R.id.tvMilkType)).setText("दूध प्रकार: म्हैस");

            ((TextView) billCard.findViewById(R.id.tvShift)).setText("शिफ्ट: सकाळ + संध्याकाळ");
            ((TextView) billCard.findViewById(R.id.tvLiter)).setText("लिटर: 10.00");
            ((TextView) billCard.findViewById(R.id.tvTotal)).setText("एकूण रक्कम: ₹284.5");

            layoutPeriodicContainer.addView(billCard);
        }, 2000);
    }

    static class BillModel {
        String name, number, milkType, shift, liter, totalAmount;

        BillModel(String name, String number, String milkType, String shift, String liter, String totalAmount) {
            this.name = name;
            this.number = number;
            this.milkType = milkType;
            this.shift = shift;
            this.liter = liter;
            this.totalAmount = totalAmount;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sessionCheckRunnable);
        renewalHandler.removeCallbacksAndMessages(null);
        refreshHandler.removeCallbacks(refreshRunnable);
    }
}