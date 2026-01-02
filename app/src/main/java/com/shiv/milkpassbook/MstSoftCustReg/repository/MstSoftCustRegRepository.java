package com.shiv.milkpassbook.MstSoftCustReg.repository;

import androidx.lifecycle.LiveData;

import com.shiv.milkpassbook.MstSoftCustReg.database.MstSoftCustRegDao;
import com.shiv.milkpassbook.MstSoftCustReg.database.MstSoftCustRegEntity;
import com.shiv.milkpassbook.MstSoftCustReg.model.MstSoftCustRegResponse;
import com.shiv.milkpassbook.retrofit.MstSoftCustRegApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import retrofit2.Response;
import android.util.Log;

public class MstSoftCustRegRepository {
    private MstSoftCustRegApi api;
    private MstSoftCustRegDao dao;

    public MstSoftCustRegRepository(MstSoftCustRegApi api, MstSoftCustRegDao dao) {
        this.api = api;
        this.dao = dao;
    }

    // Add LiveData support
    public LiveData<List<MstSoftCustRegEntity>> getAllSoftCustomers() {
        return dao.getAll();
    }

    // Improved refresh method with callback
    public void refreshData(Callback<Boolean> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Response<MstSoftCustRegResponse> response = api.getAllSoftCustReg().execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<MstSoftCustRegEntity> newEntities = new ArrayList<>();
                    for (MstSoftCustRegResponse.MstSoftCustRegData data : response.body().data) {
                        newEntities.add(new MstSoftCustRegEntity(
                                data.SoftCustCode,
                                data.Mob,
                                data.PurDate,
                                data.RenwDate
                        ));
                    }

                    // Get current data from database
                    List<MstSoftCustRegEntity> currentEntities = dao.getAllSync();

                    // Check if data has changed
                    if (!currentEntities.equals(newEntities)) {
                        dao.deleteAll();
                        dao.insertAll(newEntities);
                        Log.d("DB_DEBUG", "Data updated - inserted " + newEntities.size() + " records");
                        callback.onSuccess(true);
                    } else {
                        Log.d("DB_DEBUG", "No changes in data - skipping update");
                        callback.onSuccess(false);
                    }
                } else {
                    Log.e("API_ERROR", "Response not successful. Code: " + response.code());
                    callback.onError(new Exception("API call failed with code: " + response.code()));
                }
            } catch (Exception e) {
                Log.e("REFRESH_ERROR", "Error refreshing data", e);
                callback.onError(e);
            }
        });
    }

    // Original refresh method maintained for backward compatibility
    public void refreshData() {
        refreshData(new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                Log.d("REFRESH", "Data refreshed successfully");
            }

            @Override
            public void onError(Exception e) {
                Log.e("REFRESH", "Error in refresh: " + e.getMessage());
            }
        });
    }

    public MstSoftCustRegEntity getBySoftCustCode(String softCustCode) {
        return dao.getBySoftCustCode(softCustCode);
    }

    public boolean checkRenewalDate(String softCustCode) {
        MstSoftCustRegEntity entity = dao.getBySoftCustCode(softCustCode);
        if (entity != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String currentDate = sdf.format(new Date());
            return currentDate.compareTo(entity.RenwDate) <= 0;
        }
        return false;
    }

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }

    // Additional debug methods
    public int getRecordCount() {
        return dao.getCount();
    }

    public List<MstSoftCustRegEntity> getSampleRecords() {
        return dao.getSampleRecords();
    }
}