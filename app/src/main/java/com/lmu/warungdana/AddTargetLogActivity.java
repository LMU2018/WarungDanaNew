package com.lmu.warungdana;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Response.DetailTarget;
import com.lmu.warungdana.Response.ListLogDesc;
import com.lmu.warungdana.Response.ListLogStatus;
import com.lmu.warungdana.Response.RespListLogDesc;
import com.lmu.warungdana.Response.RespListLogStatus;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTargetLogActivity extends AppCompatActivity {
    private Integer idData, idSource;
    private ApiEndPoint mApiService;
    private TextView nama, status, tanggal;
    private Context context;
    private Spinner spLogStatus, spLogDesc;
    List<ListLogStatus> listLogStatuses;
    List<ListLogDesc> listLogDescs;
    private Integer idDesc, idLog, idLead, idUser;
    private Calendar calendar;
    private ImageView imgList;
    private Button btnCheck;
    private String tglPilih, statusLead, callDuration;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    boolean connected = false;
    Integer idLogStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_add_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_delete_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Batal menambah Log ?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        onBackPressed();
                    }
                });

                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        sharedPrefManager = new SharedPrefManager(context);
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        idData = getIntent().getIntExtra("idData", 0);
        getDetailDataLead();
        nama = findViewById(R.id.tvItemNama);
        status = findViewById(R.id.tvItemDeskripsi);
        spLogStatus = findViewById(R.id.spLogStatus);
        spLogDesc = findViewById(R.id.spLogDesc);
        tanggal = findViewById(R.id.tvTangal);
        calendar = Calendar.getInstance();
        imgList = findViewById(R.id.imgList);
        btnCheck = findViewById(R.id.btnCheck);
        loading = new ProgressDialog(AddTargetLogActivity.this);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 0);
        }

        getData();
        listener();

    }

    private void getData() {

        mApiService.logStatus().enqueue(new Callback<RespListLogStatus>() {
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
                                Integer idStatus = listLogStatuses.get(position).getId();
                                idLogStatus = idStatus;
                                mApiService.logDesc(idStatus).enqueue(new Callback<RespListLogDesc>() {
                                    @Override
                                    public void onResponse(Call<RespListLogDesc> call, Response<RespListLogDesc> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body().getApiStatus() != 0) {
                                                listLogDescs = response.body().getData();
                                                List<String> list = new ArrayList<>();
                                                for (int i = 0; i < listLogDescs.size(); i++) {
                                                    list.add(listLogDescs.get(i).getDescription());
                                                }
                                                ArrayAdapter<String> adapterSource = new ArrayAdapter<>(context, R.layout.spinner_item_log, list);
                                                spLogDesc.setAdapter(adapterSource);
                                                spLogDesc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        idDesc = listLogDescs.get(position).getId();
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
                                    public void onFailure(Call<RespListLogDesc> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
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

    private void listener() {

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(context, listener,
//                        calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,listener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1);
                datePickerDialog.show();
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
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else {
                    connected = false;
                }

                if (connected == true) {
                    getCallDuration();
                    if (TextUtils.isEmpty(tanggal.getText())) {
                        tanggal.setError("Wajib Diisi");
                        return;
                    } else {
//                        loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                        loading.setMessage("Harap Tunggu...");
                        loading.setCancelable(false);
                        loading.show();

                        if (idLogStatus == 6 || idLogStatus == 7 || idLogStatus == 8 || idLogStatus == 9){

                            callDuration = "0";
                        }

//                        mApiService.targetLog(idData, callDuration, idDesc, tglPilih, idUser).enqueue(new Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                mApiService.userLogCreate(3, idData, "create", idUser).enqueue(new Callback<ResponseBody>() {
//                                    @Override
//                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                        //kode baru pengambilan target detail
//
//                                        if (response.isSuccessful()){
//
//                                            mApiService.targetDetail(idData).enqueue(new Callback<DetailTarget>() {
//                                                @Override
//                                                public void onResponse(Call<DetailTarget> call, Response<DetailTarget> response) {
//                                                    if (response.isSuccessful()) {
//                                                        if (response.body().getApiStatus() != 0) {
//                                                            int idTargetStatus, idLogStatus, idTarget;
//                                                            idTargetStatus = response.body().getIdTargetMstStatus();
//                                                            idLogStatus = response.body().getIdMstLogStatus();
//                                                            idTarget = response.body().getId();
//
//                                                            if (idTargetStatus == 1 & idLogStatus == 1) {
//                                                                mApiService.targetUpdateStatus(idTarget, 2, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else if (idTargetStatus == 1 & idLogStatus == 2) {
//                                                                mApiService.targetUpdateStatus(idTarget, 5, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else if (idTargetStatus == 2 & idLogStatus == 1) {
//                                                                mApiService.targetUpdateStatus(idTarget, 2, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else if (idTargetStatus == 2 & idLogStatus == 2) {
//                                                                mApiService.targetUpdateStatus(idTarget, 5, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else if (idTargetStatus == 3 & idLogStatus == 1) {
//                                                                mApiService.targetUpdateStatus(idTarget, 2, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else if (idTargetStatus == 3 & idLogStatus == 2) {
//                                                                mApiService.targetUpdateStatus(idTarget, 5, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else if (idTargetStatus == 5 & idLogStatus == 1) {
//                                                                mApiService.targetUpdateStatus(idTarget, 2, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else if (idTargetStatus == 5 & idLogStatus == 2) {
//                                                                mApiService.targetUpdateStatus(idTarget, 5, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//                                                            } else {
//                                                                mApiService.targetUpdateStatus(idTarget, 3, idUser).enqueue(new Callback<ResponseBody>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                                                        loading.dismiss();
//                                                                        finish();
//                                                                        Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();
//
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                        loading.dismiss();
//                                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//
//                                                        } else {
//                                                            loading.dismiss();
//                                                            Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    } else {
//                                                        loading.dismiss();
//                                                        Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<DetailTarget> call, Throwable t) {
//                                                    loading.dismiss();
//                                                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                        }
//
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                        loading.dismiss();
//                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                loading.dismiss();
//                                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
//                            }
//                        });


                        mApiService.targetLogCreate(idData,callDuration,idDesc,tglPilih,idUser,3,idData,"create").enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                if (response.isSuccessful()){

                                    loading.dismiss();
                                    finish();
                                    Toast.makeText(context, "Berhasil menambah log call!", Toast.LENGTH_LONG).show();


                                }else{

                                    loading.dismiss();
                                    Toast.makeText(context, "Gagal menambah log call ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                loading.dismiss();
                                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    //kode lama pengambilan target detail



                } else {
                    Toast toast = Toast.makeText(context, "Periksa Koneksi Anda", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void getCallDuration() {
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        managedCursor.moveToLast();
        String phNumber = managedCursor.getString(number);
        String callType = managedCursor.getString(type);
        String callDate = managedCursor.getString(date);
        Date callDayTime = new Date(Long.valueOf(callDate));
        callDuration = managedCursor.getString(duration);
        String dir = null;
        int dircode = Integer.parseInt(callType);

        switch (dircode) {
            case CallLog.Calls.OUTGOING_TYPE:
                dir = "OUTGOING";
                break;

            case CallLog.Calls.INCOMING_TYPE:
                dir = "INCOMING";
                break;

            case CallLog.Calls.MISSED_TYPE:
                dir = "MISSED";
                break;
        }
        managedCursor.close();

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
