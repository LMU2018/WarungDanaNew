package com.lmu.warungdananew;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdananew.Response.DetailTarget;
import com.lmu.warungdananew.Response.ListLogDesc;
import com.lmu.warungdananew.Response.ListLogStatus;
import com.lmu.warungdananew.Response.RespListLogStatus;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Api.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVisumActivity extends AppCompatActivity {
    private Integer idData, idSource, idUser;
    private ApiEndPoint mApiService;
    private TextView nama, status, tanggal;
    private Context context;
    private Spinner spLogStatus;
    List<ListLogStatus> listLogStatuses;
    List<ListLogDesc> listLogDescs;
    private Integer idDesc, idLog, idLead, idStatus;
    private Calendar calendar;
    private ImageView imgList;
    private Button btnCheck;
    private String tglPilih, statusLead;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_add_visum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_delete_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mApiService = UtilsApi.getAPIService();
        idData = getIntent().getIntExtra("idData", 0);
        getDetailDataLead();
        nama = findViewById(R.id.tvItemNama);
        status = findViewById(R.id.tvItemDeskripsi);
        spLogStatus = findViewById(R.id.spLogStatus);
        sharedPrefManager = new SharedPrefManager(this);
        idUser = sharedPrefManager.getSpId();
        tanggal = findViewById(R.id.tvTangal);
        calendar = Calendar.getInstance();
        imgList = findViewById(R.id.imgList);
        btnCheck = findViewById(R.id.btnCheck);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mApiService.visumStatus().enqueue(new Callback<RespListLogStatus>() {
            @Override
            public void onResponse(Call<RespListLogStatus> call, Response<RespListLogStatus> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listLogStatuses = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listLogStatuses.size(); i++) {
                            list.add(listLogStatuses.get(i).getStatus());
                        }
                        ArrayAdapter<String> adapterSource = new ArrayAdapter<>(context, R.layout.spinner_item_log, list);
                        spLogStatus.setAdapter(adapterSource);
                        spLogStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idStatus = listLogStatuses.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListLogStatus> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, listener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }

            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    tanggal.setText(dateFormat.format(calendar.getTime()));
                    tglPilih = dateFormat1.format(calendar.getTime());
                }
            };
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tanggal.getText())) {
                    tanggal.setError("Wajib Diisi");
                    return;
                } else {
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                    mApiService.targetVisumCreate(idData, idUser, tglPilih, idStatus).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            finish();
                            loading.dismiss();
                            Toast.makeText(context, "Berhasil menambah visum!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            loading.dismiss();
                            Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getDetailDataLead() {
        mApiService.targetDetail(idData).enqueue(new Callback<DetailTarget>() {
            @Override
            public void onResponse(Call<DetailTarget> call, Response<DetailTarget> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        if (response.body().getLastName() == null) {
                            nama.setText(response.body().getFirstName());
                        } else {
                            nama.setText(response.body().getFirstName() + " " + response.body().getLastName());
                        }
                        if (response.body().getDescription() != null) {
                            status.setText(response.body().getDescription());
                        } else {
                            status.setText("Belum di Follow Up");
                        }
                        statusLead = response.body().getTargetMstStatusStatus();
                        if (response.body().getIdTargetMstStatus() != null) {
                            idLead = 0;
                        } else {
                            idLead = response.body().getIdTargetMstStatus();
                        }
                        if (response.body().getIdMstLogDesc() != null) {
                            idLead = 0;
                        } else {
                            idLog = response.body().getIdMstLogDesc();
                        }

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailTarget> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
