package com.lmu.warungdananew;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.kcode.lib.UpdateWrapper;
import com.kcode.lib.bean.VersionModel;
import com.kcode.lib.net.CheckUpdateTask;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Helper.BottomNavigationViewHelper;
import com.lmu.warungdananew.Response.DetailUpdateVersion;
import com.lmu.warungdananew.Response.RespDeviceID;
import com.lmu.warungdananew.Response.RespPost;
import com.lmu.warungdananew.SQLite.DatabaseHelper;
import com.lmu.warungdananew.Utils.UtilsConnected;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.UtilsApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    View view;

    // Test sqlite
    DatabaseHelper databaseHelper;
    private ApiEndPoint mApiService;
    Context context;
    private String strLink;
    private Integer intVersion = 0, xmlVersion;
    private FirebaseRemoteConfig remoteConfig = null;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String android_id = "";
    int uId = 0;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        checkUpdateVersion();
        uId = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
//        FirebaseApp.initializeApp(this);
        remoteConfig = FirebaseRemoteConfig.getInstance();
        boolean isConn = UtilsConnected.isNetworkConnected(context);
//        SyncAlamat();
        databaseHelper = new DatabaseHelper(this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlVersion = Integer.parseInt(getResources().getString(R.string.version));

//        Log.d("insert","inserting data");
//        databaseHelper.addUserLog(new UserLogModel(1,1,1,"create"));
//        databaseHelper.addUserLog(new UserLogModel(2,2,2,"berak"));
//
//
//        Log.d("read","baca data");
//        List<UserLogModel> logModels = databaseHelper.getUserLog();
//        for (UserLogModel b:logModels){
////
//            Log.d("data", "ID :"+b.getId()+" | ID_USER :"+b.getCreated_at()+" | PENULIS:"+b.getJenis());
//        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);


        loadFragment(new HomeFragment());

        checkAndRequestPermissions();

        if (isConn) {
            updateVersion();
            /*Toast.makeText(context, "Ada Jaringan", Toast.LENGTH_SHORT).show();*/
        } else {
            Toast.makeText(context, "Periksa Koneksi", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkUpdateVersion() {

//        String url = "http://192.168.139.59/warnadev/public/uploads/1/app-debug.apk";
//
//        DownloadApk downloadApk = new DownloadApk(HomeActivity.this);
//
//        downloadApk.startDownloadingApk(url);

        UpdateWrapper updateWrapper = new UpdateWrapper.Builder(getApplicationContext())
                .setCustomsActivity(DialogUpdateVesion.class)
                //set interval Time
                .setTime(1000)
                //set notification icon
                .setNotificationIcon(R.mipmap.ic_launcher_2)
                //set update file url
                .setUrl("http://192.168.139.59/warnadev/public/api/check_version")
                //set customs activity
                //set showToast. default is true
                .setIsShowToast(false)
                //add callback ,return new version info
                .setCallback(new CheckUpdateTask.Callback() {
                    @Override
                    public void callBack(VersionModel versionModel) {

                    }
                })
                .build();

        updateWrapper.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isConn = UtilsConnected.isNetworkConnected(context);
        if (isConn) {

            checkDeviceID();
            updateVersion();
            checkUpdateVersion();

            checkLogin();

            remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(true)
                    .build());

            HashMap<String, Object> defaults = new HashMap<>();
            defaults.put("is_force_update", false);
            remoteConfig.setDefaults(defaults);

            final Task<Void> fetch = remoteConfig.fetch(0);
            fetch.addOnCompleteListener(this, new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        remoteConfig.activateFetched();
                        if (intVersion != 0) {

                            if (remoteConfig.getBoolean("is_force_update") && !intVersion.equals(xmlVersion)) {

                                showDialogUpdate();

                            }
                        }

                    } else {
                        Toast.makeText(context, "Fetch Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(context, "Periksa Koneksi", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkDeviceID() {

        mApiService.checkAndroidID(uId,android_id).enqueue(new Callback<RespDeviceID>() {
            @Override
            public void onResponse(Call<RespDeviceID> call, Response<RespDeviceID> response) {

                if (response.isSuccessful()){

                    if (response.body().getApiStatus() == 1){


                    }else{

                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setCancelable(false);
                        builder.setMessage("Aplikasi mendeteksi akun anda login lebih dari 1 device , silahkan login kembali")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        builder.show();

                    }
                }

            }

            @Override
            public void onFailure(Call<RespDeviceID> call, Throwable t) {

            }
        });
    }

    private void checkLogin() {

        Log.d("Android ID = ",android_id);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showDialogUpdate() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, Update your app to the newest version.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse(strLink);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No, Thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void SyncAlamat() {

//        TestAdapter mDbHelper = new TestAdapter(context);
//        mDbHelper.createDatabase();
//        mDbHelper.open();
//
//        Cursor testdata = mDbHelper.getTestData();
//
//        mDbHelper.close();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_lead:
                    fragment = new LeadFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_target:
                    fragment = new TargetFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_contact:
                    fragment = new ContactFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_order:
                    fragment = new DealFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean checkAndRequestPermissions() {
        int telpon = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storageRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (telpon != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (storageRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void updateVersion() {

        mApiService.updateVersion(1).enqueue(new Callback<DetailUpdateVersion>() {
            @Override
            public void onResponse(Call<DetailUpdateVersion> call, Response<DetailUpdateVersion> response) {

                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        intVersion = response.body().getVersion();
                        strLink = response.body().getLink();

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailUpdateVersion> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
