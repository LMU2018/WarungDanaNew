package com.lmu.warungdananew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Api.UtilsApi;
import com.lmu.warungdananew.Response.Login;

import org.w3c.dom.Text;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPassword extends AppCompatActivity {
    EditText passLama, passBaru, rePass;
    Button btnUbahPass;
    Context context;
    SharedPrefManager sharedPrefManager;
    ApiEndPoint mApiService;
    String npmUser;
    Integer idUpdate;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        passLama = findViewById(R.id.passwordLama);
        passBaru = findViewById(R.id.passwordBaru);
        rePass = findViewById(R.id.rePassword);
        btnUbahPass = findViewById(R.id.btnUbahPassword);
        context = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService();
        npmUser = sharedPrefManager.getSPNpm();


    }

    @Override
    protected void onStart() {
        super.onStart();
        btnUbahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(passLama.getText())) {
                    passLama.setError("Wajib Diisi !!!");
                } else if (TextUtils.isEmpty(passBaru.getText())) {
                    passBaru.setError("Wajib Diisi !!!");
                } else if (TextUtils.isEmpty(rePass.getText())) {
                    rePass.setError("Wajib Diisi !!!");
                } else if (passBaru.getText().toString().length() < 6) {
                    Toast.makeText(context, "Password Baru Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
                } else if (!passBaru.getText().toString().equalsIgnoreCase(rePass.getText().toString())) {
                    Toast.makeText(context, "Password Baru Tidak Sesuai", Toast.LENGTH_SHORT).show();
                } else {
                    loading = ProgressDialog.show(context, null, "Harap Tunggu...", true, false);
                    ubahPass();
                }
            }
        });
    }

    private void ubahPass() {
        mApiService.loginrequest(npmUser, passLama.getText().toString()).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        idUpdate = response.body().getId();
                        mApiService.ubahPasword(idUpdate, rePass.getText().toString()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                loading.dismiss();
                                Toast.makeText(context, "Password Berhasil Diubah", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                            }
                        });

                    } else {
                        loading.dismiss();
                        Toast.makeText(context, "Password Lama Salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

}
