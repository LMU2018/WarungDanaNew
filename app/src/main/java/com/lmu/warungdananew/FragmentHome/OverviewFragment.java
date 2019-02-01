package com.lmu.warungdananew.FragmentHome;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdananew.Adapter.ListJadwalAdapter;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.RekapActivity;
import com.lmu.warungdananew.Response.CounterBrosur;
import com.lmu.warungdananew.Response.KpiCfa;
import com.lmu.warungdananew.Response.ListJadwal;
import com.lmu.warungdananew.Response.RespCounterBrosur;
import com.lmu.warungdananew.Response.RespCounterLead;
import com.lmu.warungdananew.Response.RespListJadwal;
import com.lmu.warungdananew.Utils.UtilsConnected;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Api.UtilsApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    String greeting = null;
    private TextView tvGreeting, tvTanggal, tvSKRG, tvKMRN, tvSEMUA, tvKPIDeskripsi, newdb, tele, brosur, order, booking, tvRekap;
    HorizontalCalendar horizontalCalendar;
    private Button btnGoToday;
    private RecyclerView rvJadwalAktivitas;
    Context mContext;
    public String selectedDate;
    private ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    private LinearLayout jadwalAda, jadwalKosong;
    boolean isConn;


    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        mContext = getContext();
        mApiService = UtilsApi.getAPIService();
        isConn = UtilsConnected.isNetworkConnected(getContext());
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvTanggal = view.findViewById(R.id.tvTanggal);
        sharedPrefManager = new SharedPrefManager(mContext);
        tvRekap = view.findViewById(R.id.tvRekap);
        tvSKRG = view.findViewById(R.id.tvSKRG);
        tvKMRN = view.findViewById(R.id.tvKMRN);
        tvSEMUA = view.findViewById(R.id.tvSEMUA);
        tvKPIDeskripsi = view.findViewById(R.id.tvKPIDeskripsi);
        tvKPIDeskripsi.setText("Bulan Ini");
        final int accentColor = getResources().getColor(R.color.colorAccent);
        tvSKRG.setTextColor(accentColor);
        tvSEMUA.setTextColor(Color.DKGRAY);
        tvKMRN.setTextColor(Color.DKGRAY);
        tele = view.findViewById(R.id.teleSum);
        brosur = view.findViewById(R.id.brosurSum);
        newdb = view.findViewById(R.id.newDBSum);
        order = view.findViewById(R.id.orderSum);
        booking = view.findViewById(R.id.bookingSum);
        btnGoToday = view.findViewById(R.id.btnGoToday);
        rvJadwalAktivitas = view.findViewById(R.id.rvJadwalAktivitas);
        jadwalKosong = view.findViewById(R.id.jadwalKosong);
        jadwalAda = view.findViewById(R.id.jadwalAda);
        jadwalAda.setVisibility(View.GONE);
        jadwalKosong.setVisibility(View.GONE);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .sizeMiddleText(20)
                .showBottomText(isHidden())
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();

        inisiasiSalam();
        selectedDate = android.text.format.DateFormat.format("yyyy-MM-dd", defaultSelectedDate).toString();

        if (isConn) {

            mApiService.listJadwal(selectedDate, sharedPrefManager.getSpId()).enqueue(new Callback<RespListJadwal>() {
                @Override
                public void onResponse(Call<RespListJadwal> call, Response<RespListJadwal> response) {
                    if (response.isSuccessful()) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        rvJadwalAktivitas.setLayoutManager(layoutManager);
                        List<ListJadwal> listLeads = response.body().getData();
                        if (listLeads != null) {
                            rvJadwalAktivitas.setAdapter(new ListJadwalAdapter(getContext(), listLeads));
                            jadwalAda.setVisibility(View.VISIBLE);
                            jadwalKosong.setVisibility(View.GONE);
                        } else {
//                        rvJadwalAktivitas.setAdapter(new ListJadwalAdapter(getContext(), listLeads));
                            jadwalAda.setVisibility(View.GONE);
                            jadwalKosong.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getContext(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RespListJadwal> call, Throwable t) {
                    Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isConn) {
            getKPISKRG();
            jadwalAktivitas();
        } else {
            Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        reportKPI();

        tvRekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RekapActivity.class));
            }
        });


        btnGoToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalCalendar.goToday(false);
            }
        });

    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }

    private void getKPISKRG() {
        final Calendar calendar = Calendar.getInstance();
        final List<KpiCfa> data = new ArrayList<KpiCfa>();
        Date d1 = null, d2 = null;

        mApiService.brosurCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterBrosur>() {
            @Override
            public void onResponse(Call<RespCounterBrosur> call, Response<RespCounterBrosur> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    d3 = tahun+"-"+d3;
                    List<CounterBrosur> list = response.body().getData();
                    List<CounterBrosur> baru = new ArrayList<>();
                    List<Integer> jumbros = new ArrayList<>();
                    Integer tot = 0;

                    if (response.body().getData() != null) {
                        for (CounterBrosur p : list) {
                            if (convertTime(p.getCreatedAt()).equals(d3)) {
                                baru.add(p);
                            }
                        }
                        for (int i = 0; i < baru.size(); i++) {
                            if (baru.get(i).getBrosur() != null) {
                                jumbros.add(baru.get(i).getBrosur());
                            }

                        }
                        for (int i = 0; i < jumbros.size(); i++) {
                            tot += jumbros.get(i);

                        }
                        brosur.setText(String.valueOf(tot));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterBrosur> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.targetCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    d3 = tahun+"-"+d3;

                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        tele.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.leadCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    d3 = tahun+"-"+d3;

                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        newdb.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.orderCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    d3 = tahun+"-"+d3;
                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        order.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.bookingCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    d3 = tahun+"-"+d3;

                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        booking.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getKPIKMRIN() {
        final Calendar calendar = Calendar.getInstance();
        final List<KpiCfa> data = new ArrayList<KpiCfa>();
        Date d1 = null, d2 = null;

        mApiService.brosurCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterBrosur>() {
            @Override
            public void onResponse(Call<RespCounterBrosur> call, Response<RespCounterBrosur> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH)+1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    int blnKemarin = Integer.parseInt(d3) - 1;

                    d3 = String.valueOf(blnKemarin);
                    if (d3.equals("0")){
                        d3 = "12";
                        tahun = String.valueOf(Integer.parseInt(tahun)-1);
                    }

                    d3 = ""+tahun+"-"+d3;

                    Log.d("Bulan , Tahun",d3+" "+tahun);

                    List<CounterBrosur> list = response.body().getData();
                    List<CounterBrosur> baru = new ArrayList<>();
                    List<Integer> jumbros = new ArrayList<>();
                    Integer tot = 0;
                    if (response.body().getData() != null) {
                        for (CounterBrosur p : list) {

                            Log.d("Convert , Hasil",convertTime(p.getCreatedAt())+"="+d3);
                            if (convertTime(p.getCreatedAt()).equals(d3)) {
                                baru.add(p);
                            }
                        }
                        for (int i = 0; i < baru.size(); i++) {
                            if (baru.get(i).getBrosur() != null) {
                                jumbros.add(baru.get(i).getBrosur());
                            }

                        }
                        for (int i = 0; i < jumbros.size(); i++) {
                            tot += jumbros.get(i);
                            brosur.setText(String.valueOf(tot));
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterBrosur> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.targetCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH)+1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    int blnKemarin = Integer.parseInt(d3) - 1;

                    d3 = String.valueOf(blnKemarin);
                    if (d3.equals("0")){
                        d3 = "12";
                        tahun = String.valueOf(Integer.parseInt(tahun)-1);
                    }

                    d3 = ""+tahun+"-"+d3;

                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                            Log.d("Convert , Hasil",convertTime(response.body().getData().get(i).getCreatedAt())+"="+d3);
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        tele.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.leadCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH)+1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    int blnKemarin = Integer.parseInt(d3) - 1;

                    d3 = String.valueOf(blnKemarin);
                    if (d3.equals("0")){
                        d3 = "12";
                        tahun = String.valueOf(Integer.parseInt(tahun)-1);
                    }

                    d3 = ""+tahun+"-"+d3;

                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        newdb.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.orderCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH)+1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    int blnKemarin = Integer.parseInt(d3) - 1;

                    d3 = String.valueOf(blnKemarin);
                    if (d3.equals("0")){
                        d3 = "12";
                        tahun = String.valueOf(Integer.parseInt(tahun)-1);
                    }

                    d3 = ""+tahun+"-"+d3;

                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        order.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.bookingCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH)+1);
                    String tahun = String.valueOf(calendar.get(Calendar.YEAR));

                    int blnKemarin = Integer.parseInt(d3) - 1;

                    d3 = String.valueOf(blnKemarin);
                    if (d3.equals("0")){
                        d3 = "12";
                        tahun = String.valueOf(Integer.parseInt(tahun)-1);
                    }

                    d3 = ""+tahun+"-"+d3;

                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        int count = 0;
                        for (String cfa : gas) {
                            if (cfa.equals(d3)) {
                                count++;
                            }

                        }
                        booking.setText(String.valueOf(count));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getKPITOTAL() {
        final Calendar calendar = Calendar.getInstance();
        final List<KpiCfa> data = new ArrayList<KpiCfa>();
        Date d1 = null, d2 = null;

        mApiService.brosurCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterBrosur>() {
            @Override
            public void onResponse(Call<RespCounterBrosur> call, Response<RespCounterBrosur> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH));
                    List<CounterBrosur> list = response.body().getData();
                    List<CounterBrosur> baru = new ArrayList<>();
                    List<Integer> jumbros = new ArrayList<>();
                    Integer tot = 0;
                    if (response.body().getData() != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getBrosur() != null) {
                                jumbros.add(list.get(i).getBrosur());
                            }

                        }
                        for (int i = 0; i < jumbros.size(); i++) {
                            tot += jumbros.get(i);
                            brosur.setText(String.valueOf(tot));
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<RespCounterBrosur> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.targetCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH));
                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }

                        tele.setText(String.valueOf(gas.size()));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.leadCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH));
                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        newdb.setText(String.valueOf(gas.size()));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.orderCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH));
                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }

                        order.setText(String.valueOf(gas.size()));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.bookingCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
            @Override
            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
                if (response.isSuccessful()) {
                    String d3 = String.valueOf(calendar.get(Calendar.MONTH));
                    List<String> gas = new ArrayList<String>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
                        }
                        booking.setText(String.valueOf(gas.size()));

                    }
                }
            }

            @Override
            public void onFailure(Call<RespCounterLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jadwalAktivitas() {

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                selectedDate = android.text.format.DateFormat.format("yyyy-MM-dd", date).toString();

                mApiService.listJadwal(selectedDate, sharedPrefManager.getSpId()).enqueue(new Callback<RespListJadwal>() {
                    @Override
                    public void onResponse(Call<RespListJadwal> call, Response<RespListJadwal> response) {
                        if (response.isSuccessful()) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            rvJadwalAktivitas.setLayoutManager(layoutManager);
                            List<ListJadwal> listLeads = response.body().getData();
                            if (listLeads != null) {
                                rvJadwalAktivitas.setAdapter(new ListJadwalAdapter(getContext(), listLeads));
                                jadwalAda.setVisibility(View.VISIBLE);
                                jadwalKosong.setVisibility(View.GONE);
                            } else {
                                rvJadwalAktivitas.setAdapter(new ListJadwalAdapter(getContext(), listLeads));
                                jadwalAda.setVisibility(View.GONE);
                                jadwalKosong.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<RespListJadwal> call, Throwable t) {
                        Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void reportKPI() {
        final int accentColor = getResources().getColor(R.color.colorAccent);
        tvSKRG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSKRG.setTextColor(accentColor);
                tvKMRN.setTextColor(Color.DKGRAY);
                tvSEMUA.setTextColor(Color.DKGRAY);
                tvKPIDeskripsi.setText("Bulan Ini");
                if (isConn) {
                    getKPISKRG();
                } else {
                    Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvKMRN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvKMRN.setTextColor(accentColor);
                tvSKRG.setTextColor(Color.DKGRAY);
                tvSEMUA.setTextColor(Color.DKGRAY);
                tvKPIDeskripsi.setText("Bulan Kemarin");
                if (isConn) {
                    getKPIKMRIN();
                } else {
                    Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSEMUA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSEMUA.setTextColor(accentColor);
                tvSKRG.setTextColor(Color.DKGRAY);
                tvKMRN.setTextColor(Color.DKGRAY);
                tvKPIDeskripsi.setText("Total");
                if (isConn) {
                    getKPITOTAL();
                } else {
                    Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void inisiasiSalam() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd MMM, yyyy");
        String hariini = df.format(Calendar.getInstance().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 12 && hour < 17) {
            greeting = "Selamat Siang";
        } else if (hour >= 17 && hour < 21) {
            greeting = "Selamat Sore";
        } else if (hour >= 21 && hour < 24) {
            greeting = "Selamat Malam";
        } else {
            greeting = "Selamat Pagi";
        }
        tvGreeting.setText(greeting + ", " + sharedPrefManager.getSPName());
        tvTanggal.setText(hariini);
    }

}
