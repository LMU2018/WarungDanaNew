package com.lmu.warungdananew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdananew.Adapter.ListReportJadwalAdapter;
import com.lmu.warungdananew.Adapter.ListUserJadwalAdapter;
import com.lmu.warungdananew.Response.DetailJadwal;
import com.lmu.warungdananew.Response.ListReportJadwal;
import com.lmu.warungdananew.Response.ListUserJadwal;
import com.lmu.warungdananew.Response.RespListReportJadwal;
import com.lmu.warungdananew.Response.RespListUserJadwal;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.UtilsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailJadwalActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView StartDate, EndDate, startHour, endHour, address, note, direction;
    private RecyclerView attendees, report;
    private LinearLayout kotakNote;
    private ApiEndPoint mApiService;
    public Integer idJadwal;
    private Context context;
    private Button btnCheck;
    String type, alamat, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        StartDate = findViewById(R.id.tvStartDate);
        startHour = findViewById(R.id.tvStartHour);
        EndDate = findViewById(R.id.tvEndDate);
        endHour = findViewById(R.id.tvEndHour);
        address = findViewById(R.id.tvItemAddress);
        note = findViewById(R.id.tvNote);
        attendees = findViewById(R.id.rvAttendees);
        report = findViewById(R.id.rvReport);
        kotakNote = findViewById(R.id.kotakNote);
        direction = findViewById(R.id.tvDirection);
        idJadwal = getIntent().getIntExtra("idJadwal", 0);
        type = getIntent().getStringExtra("nama");
        toolbar.setTitle(type);
        mApiService = UtilsApi.getAPIService();
        btnCheck = findViewById(R.id.btnCheck);
        getDetail();
        getUser();
        getReport();


    }

    private void getReport() {
        mApiService.listReportJadwal(idJadwal).enqueue(new Callback<RespListReportJadwal>() {
            @Override
            public void onResponse(Call<RespListReportJadwal> call, Response<RespListReportJadwal> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        report.setLayoutManager(layoutManager);
                        List<ListReportJadwal> listLeads = response.body().getData();
                        report.setAdapter(new ListReportJadwalAdapter(context, listLeads));
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListReportJadwal> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddJadwalReportActivity.class);
                intent.putExtra("idJadwal",idJadwal);
                intent.putExtra("type",type);
                intent.putExtra("lokasi",alamat);
                intent.putExtra("tanggal",tanggal);
                context.startActivity(intent);
            }
        });*/
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapsNav = Uri.parse("google.navigation:q=" + alamat + "&avoid=t");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapsNav);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    private void getUser() {
        mApiService.listUserJadwal(idJadwal).enqueue(new Callback<RespListUserJadwal>() {
            @Override
            public void onResponse(Call<RespListUserJadwal> call, Response<RespListUserJadwal> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        attendees.setLayoutManager(layoutManager);
                        List<ListUserJadwal> listLeads = response.body().getData();
                        attendees.setAdapter(new ListUserJadwalAdapter(context, listLeads));
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListUserJadwal> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetail() {
        mApiService.jadwalDetail(idJadwal).enqueue(new Callback<DetailJadwal>() {
            @Override
            public void onResponse(Call<DetailJadwal> call, Response<DetailJadwal> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        StartDate.setText(convertTime(response.body().getStartDate()));
                        EndDate.setText(convertTime(response.body().getEndDate()));
                        startHour.setText(response.body().getStarted());
                        endHour.setText(response.body().getEnded());
                        alamat = response.body().getActivityLocation();
                        tanggal = convertTime(response.body().getStartDate());
                        address.setText(alamat);
                        if (response.body().getNote() != null) {
                            note.setText(response.body().getNote());
                        } else {
                            kotakNote.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailJadwal> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("EEE, dd MMM");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }
}
