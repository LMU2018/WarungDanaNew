package com.lmu.warungdananew;

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

import com.lmu.warungdananew.Response.DetailLead;
import com.lmu.warungdananew.Response.ListLogDesc;
import com.lmu.warungdananew.Response.ListLogStatus;
import com.lmu.warungdananew.Response.RespListLogDesc;
import com.lmu.warungdananew.Response.RespListLogStatus;
import com.lmu.warungdananew.SQLite.DatabaseHelper;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Api.UtilsApi;

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

public class AddLeadLogActivity extends AppCompatActivity {
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
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        databaseHelper = new DatabaseHelper(this);
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

        sharedPrefManager = new SharedPrefManager(this);
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

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 0);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
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
                                    public void onFailure
                                            (Call<RespListLogDesc> call, Throwable t) {
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

    @Override
    protected void onResume() {
        super.onResume();

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                        loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                        mApiService.leadLog(idData, callDuration, idDesc, tglPilih, idUser).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                Log.d("insert","inserting data");
//                                databaseHelper.addUserLog(new UserLogModel(idUser,2,idData,"call"));

                                mApiService.userLogCreate(2, idData, "call", idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    mApiService.leadDetail(idData).enqueue(new Callback<DetailLead>() {
                        @Override
                        public void onResponse(Call<DetailLead> call, Response<DetailLead> response) {
                            Integer idLeadStatus, idLogStatus, idLead;
                            idLead = response.body().getId();
                            idLeadStatus = response.body().getIdLeadMstStatus();
                            idLogStatus = response.body().getIdMstLogStatus();

                            if (idLeadStatus == 1 && idLogStatus == 1) {
                                mApiService.leadUpdateStatus(idLead, 2, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (idLeadStatus == 1 && idLogStatus == 2) {
                                mApiService.leadUpdateStatus(idLead, 4, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (idLeadStatus == 2 && idLogStatus == 1) {
                                mApiService.leadUpdateStatus(idLead, 2, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (idLeadStatus == 2 && idLogStatus == 2) {
                                mApiService.leadUpdateStatus(idLead, 4, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (idLeadStatus == 3 && idLogStatus == 1) {
                                mApiService.leadUpdateStatus(idLead, 2, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (idLeadStatus == 3 && idLogStatus == 2) {
                                mApiService.leadUpdateStatus(idLead, 4, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (idLeadStatus == 4 && idLogStatus == 1) {
                                mApiService.leadUpdateStatus(idLead, 2, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (idLeadStatus == 4 && idLogStatus == 2) {
                                mApiService.leadUpdateStatus(idLead, 4, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                mApiService.leadUpdateStatus(idLead, 3, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            finish();
                            loading.dismiss();
                            Toast.makeText(context, "Berhasil menambah log call !", Toast.LENGTH_LONG).show();

                        }


                        @Override
                        public void onFailure(Call<DetailLead> call, Throwable t) {
                            loading.dismiss();
                            Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast toast = Toast.makeText(context, "Periksa Koneksi Anda", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

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
        mApiService.leadDetail(idData).enqueue(new Callback<DetailLead>() {
            @Override
            public void onResponse(Call<DetailLead> call, Response<DetailLead> response) {
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
                        statusLead = response.body().getLeadMstStatusStatus();
                        if (response.body().getIdLeadMstStatus() != null) {
                            idLead = 0;
                        } else {
                            idLead = response.body().getIdLeadMstStatus();
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
            public void onFailure(Call<DetailLead> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
