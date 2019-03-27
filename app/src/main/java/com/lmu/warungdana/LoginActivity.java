package com.lmu.warungdana;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.AlarmManager.BootCompletedIntentReceiver;
import com.lmu.warungdana.Response.DetailUserAgreement;
import com.lmu.warungdana.Response.Login;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.Response.RespPost;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText npm, password;
    Button login;
    TextView link, version;
    CheckBox eula;
    ProgressDialog loading;
    Context mContext;
    ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    ArrayList<String> spAlamat;
    String userAgent;
    Integer uId, time;
    String android_id, nama, roleName, npmX, outletName;
    int roleId, outletId, branchId;
    ImageView bcLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        npm = findViewById(R.id.loginNpm);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.btnLogin);
        eula = findViewById(R.id.cbEula);
        link = findViewById(R.id.llink);
        bcLogin = findViewById(R.id.bcLogin);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        spAlamat = new ArrayList<>();
        userAgent = System.getProperty("http.agent");
        version = findViewById(R.id.tvVersionX);

        version.setText("Warung Dana Mobile versi " + BuildConfig.VERSION_NAME);
        loading = new ProgressDialog(this);
        loading.setMessage("Masuk ...");
//                    loading.setIndeterminate(true);
        loading.setCancelable(false);

        bcLogin.animate().scaleX(3).scaleY(3).setDuration(30000).start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        link.setText(Html.fromHtml("<a href=http://warungdana.com/eula/> Syarat dan Ketentuan"));
        link.setMovementMethod(LinkMovementMethod.getInstance());
        /*checkAndRequestPermissions();*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(npm.getText())) {
                    npm.setError("Wajib Diisi !!!");
                } else if (TextUtils.isEmpty(password.getText())) {
                    password.setError("Wajib Diisi !!!");
                } else if (!eula.isChecked()) {
                    Toast toast = Toast.makeText(mContext, "Ceklist Syarat dan Ketentuan", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    /*Calendar calendar = Calendar.getInstance();
                    time = calendar.get(Calendar.MINUTE);
                    Toast.makeText(mContext, "Time " + time, Toast.LENGTH_SHORT).show();*/
                    loading.show();
                    callAutoLogout();
                    requestlogin();
                }
            }
        });
        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    private void requestlogin() {
        mApiService.loginawal(npm.getText().toString(), password.getText().toString(),android_id)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getApiStatus() == 1) {
                                if (response.body().getStatus() == null || response.body().getStatus().equalsIgnoreCase("N")) {
                                    loading.dismiss();
                                    Toast.makeText(mContext, "Username Anda Tidak Aktif !!!", Toast.LENGTH_SHORT).show();
                                } else {

                                    Integer userId = response.body().getId();
                                    uId = userId;
                                    eula();
                                    userLogs();
                                    nama = response.body().getName();
                                    roleName = response.body().getCmsPrivilegesName();
                                    roleId = response.body().getIdCmsPrivileges();
                                    npmX = response.body().getNpm();
                                    outletId = response.body().getIdMstOutlet();
                                    branchId = response.body().getIdMstBranch();
                                    outletName = response.body().getMstOutletOutletName();
                                    Log.d("User ID", "" + uId);
//                                    updateAndroidID();

                                    loading.dismiss();
                                    sharedPrefManager.saveSPInt(SharedPrefManager.SP_ID, uId);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAME, nama);
                                    sharedPrefManager.saveSPInt(SharedPrefManager.SP_ROLES, roleId);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NPM, npmX);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLES_NAMES, roleName);
                                    sharedPrefManager.saveSPInt(SharedPrefManager.SP_OUTLET_ID, outletId);
                                    sharedPrefManager.saveSPInt(SharedPrefManager.SP_BRANCH_ID, branchId);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_OUTLET_NAME, outletName);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                                    startActivity(new Intent(mContext, HomeActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                }

                            } else {
                                loading.dismiss();
                                Toast.makeText(mContext, "Username atau Password anda Salah !!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            loading.dismiss();
                            Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void eula() {

        mApiService.eulaCheck(uId).enqueue(new Callback<DetailUserAgreement>() {
            @Override
            public void onResponse(Call<DetailUserAgreement> call, Response<DetailUserAgreement> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() == 0) {

                        mApiService.eulaCreate("Y", uId).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } else {
                    Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailUserAgreement> call, Throwable t) {
                Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userLogs() {

        mApiService.userLogs(userAgent, "Login", uId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callAutoLogout() {
        Intent alaramIntent = new Intent(LoginActivity.this, BootCompletedIntentReceiver.class);
        alaramIntent.setAction("LogOutAction");
        Log.e("MethodCall", "AutoLogOutCall");
        alaramIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alaramIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        Log.e("Logout", "Auto Logout set at..!" + calendar.getTime());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

//    private void updateAndroidID() {
//
//        mApiService.updateAndroidID(uId, android_id).enqueue(new Callback<RespPost>() {
//            @Override
//            public void onResponse(Call<RespPost> call, Response<RespPost> response) {
//
//                if (response.isSuccessful()) {
//
//                    Log.d("Api Status", "" + response.body().getApiStatus());
//                    Log.d("Message", "" + response.body().getApiMessage());
//                    Log.d("Android ID", "" + android_id);
//
//                    if (response.body().getApiStatus() == 1) {
//
//                        loading.dismiss();
//                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_ID, uId);
//                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAME, nama);
//                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_ROLES, roleId);
//                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NPM, npmX);
//                        sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLES_NAMES, roleName);
//                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_OUTLET_ID, outletId);
//                        sharedPrefManager.saveSPInt(SharedPrefManager.SP_BRANCH_ID, branchId);
//                        sharedPrefManager.saveSPString(SharedPrefManager.SP_OUTLET_NAME, outletName);
//                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
//
//                        startActivity(new Intent(mContext, HomeActivity.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        finish();
//
//                    } else {
//
//                        loading.dismiss();
//                        Toast.makeText(mContext, "Gagal login", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                } else {
//
//                    loading.dismiss();
//                    Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespPost> call, Throwable t) {
//
//                loading.dismiss();
//                Toast.makeText(mContext, "Not Respond", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

}