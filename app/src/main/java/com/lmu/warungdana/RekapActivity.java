package com.lmu.warungdana;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Response.CounterBrosur;
import com.lmu.warungdana.Response.KpiCfa;
import com.lmu.warungdana.Response.RespCounterBrosur;
import com.lmu.warungdana.Response.RespCounterLead;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.Response.RespPerformaIndicator;
import com.lmu.warungdana.Response.RespRekapActivity;

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

public class RekapActivity extends AppCompatActivity {
    private ApiEndPoint mApiService;
    private android.support.v7.widget.Toolbar toolbar;
    private TextView newdb, tele, brosur, order, booking, namaOutlet, orderOutlet, bookingOutlet;
    HorizontalCalendar horizontalCalendar;
    View view;
    SharedPrefManager sharedPrefManager;
    String selectedDate;
    Context mContext;
    TextView nama_cfa,outlet_cfa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap);
        mContext = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Rekap");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);
        namaOutlet = findViewById(R.id.namaOutlet);
        namaOutlet.setText(sharedPrefManager.getSPOutletName());
        tele = findViewById(R.id.teleSum);
        brosur = findViewById(R.id.brosurSum);
        newdb = findViewById(R.id.newDBSum);
        order = findViewById(R.id.orderSum);
        booking = findViewById(R.id.bookingSum);
        orderOutlet = findViewById(R.id.jumlahOrder);
        bookingOutlet = findViewById(R.id.jumlahBooking);


        nama_cfa = findViewById(R.id.nama_cfa);
        outlet_cfa = findViewById(R.id.outlet_cfa);
        nama_cfa.setText(sharedPrefManager.getSPName());
        outlet_cfa.setText(sharedPrefManager.getSPOutletName());

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -1);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .mode(HorizontalCalendar.Mode.MONTHS)
                .configure()
                .formatMiddleText("MMM")
                .sizeMiddleText(18)
                .formatBottomText("yyyy")
                .showTopText(false)
                .showBottomText(true)
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();
//        selectedDate = String.valueOf(defaultSelectedDate.get(Calendar.YEAR))+"-"+String.valueOf(defaultSelectedDate.get(Calendar.MONTH)+1);


        String month = String.valueOf(defaultSelectedDate.get(Calendar.MONTH)+1);

        if (!month.equals("10") || !month.equals("11") ||!month.equals("12") || !month.equals("0")){

            month = "0"+month;
        }

        selectedDate = String.valueOf(defaultSelectedDate.get(Calendar.YEAR))+"-"+month;
        Log.d("selectDate",selectedDate);
        getKPISKRG();
        listener();

    }

    private void listener() {

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String month = String.valueOf(date.get(Calendar.MONTH)+1);

                if (!month.equals("10") || !month.equals("11") ||!month.equals("12") || !month.equals("0")){

                    month = "0"+month;
                }

                selectedDate = String.valueOf(date.get(Calendar.YEAR))+"-"+month;
                Log.d("selectDateClick",selectedDate);
                getKPISKRG();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("M");
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

        mApiService.rekapActivity(sharedPrefManager.getSpId(),selectedDate,sharedPrefManager.getSpOutletId()).enqueue(new Callback<RespRekapActivity>() {
            @Override
            public void onResponse(Call<RespRekapActivity> call, Response<RespRekapActivity> response) {

                if (response.isSuccessful()){

                    if (response.body().getApiStatus() == 1){

                        if (response.body().getCountBrosur() != null){

                            brosur.setText(""+response.body().getCountBrosur());
                        }else{

                            brosur.setText("0");
                        }


                        tele.setText(""+response.body().getCountTele());
                        newdb.setText(""+response.body().getCountNewDB());
                        order.setText(""+response.body().getCountOrder());
                        booking.setText(""+response.body().getCountBooking());
                        orderOutlet.setText(""+response.body().getCountOrderOutlet());
                        bookingOutlet.setText(""+response.body().getCountBookingOutlet());



                    }
                }
            }

            @Override
            public void onFailure(Call<RespRekapActivity> call, Throwable t) {

            }
        });

//        final Calendar calendar = Calendar.getInstance();
//        final List<KpiCfa> data = new ArrayList<KpiCfa>();
//        Date d1 = null, d2 = null;
//
//        mApiService.leadCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getApiStatus() != 0) {
//                        String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//                        List<String> gas = new ArrayList<String>();
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
//                        }
//                        int count = 0;
//                        for (String cfa : gas) {
//                            if (cfa.equals(selectedDate)) {
//                                count++;
//                            }
//                        }
//                        newdb.setText(String.valueOf(count));
//                    } else {
//                        Toast.makeText(mContext, "Checking", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Not Responding", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//                Toast.makeText(mContext, "Koneksi Bermasalah !", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mApiService.targetCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getApiStatus() != 0) {
//                        String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//                        List<String> gas = new ArrayList<String>();
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
//                        }
//                        int count = 0;
//                        for (String cfa : gas) {
//                            if (cfa.equals(selectedDate)) {
//                                count++;
//                            }
//
//                        }
//                        tele.setText(String.valueOf(count));
//
//                    } else {
//                        Toast.makeText(mContext, "Checking", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Not Responding", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mApiService.orderCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getApiStatus() != 0) {
//                        String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//                        List<String> gas = new ArrayList<String>();
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
//                        }
//                        int count = 0;
//                        for (String cfa : gas) {
//                            if (cfa.equals(selectedDate)) {
//                                count++;
//                            }
//
//                        }
//                        order.setText(String.valueOf(count));
//
//                    } else {
//                        Toast.makeText(mContext, "Checking", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Not Responding", Toast.LENGTH_LONG).show();
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mApiService.orderCounterOutlet(sharedPrefManager.getSpOutletId()).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getApiStatus() != 0) {
//                        List<String> gas = new ArrayList<String>();
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
//                        }
//                        int count = 0;
//                        for (String cfa : gas) {
//                            if (cfa.equals(selectedDate)) {
//                                count++;
//                            }
//
//                        }
//                        orderOutlet.setText(String.valueOf(count));
//                    } else {
//                        Toast.makeText(mContext, "Checking", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Not Responding", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mApiService.bookingCounterOutlet(sharedPrefManager.getSpOutletId()).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getApiStatus() != 0) {
//                        String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//                        List<String> gas = new ArrayList<String>();
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
//                        }
//                        int count = 0;
//                        for (String cfa : gas) {
//                            if (cfa.equals(selectedDate)) {
//                                count++;
//                            }
//
//                        }
//                        bookingOutlet.setText(String.valueOf(count));
//
//                    } else {
//                        Toast.makeText(mContext, "Checking", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Not Responding", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mApiService.bookingCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterLead>() {
//            @Override
//            public void onResponse(Call<RespCounterLead> call, Response<RespCounterLead> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getApiStatus() != 0) {
//                        String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//                        List<String> gas = new ArrayList<String>();
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//                            gas.add(convertTime(response.body().getData().get(i).getCreatedAt()));
//                        }
//                        int count = 0;
//                        for (String cfa : gas) {
//                            if (cfa.equals(selectedDate)) {
//                                count++;
//                            }
//
//                        }
//                        booking.setText(String.valueOf(count));
//
//                    } else {
//                        Toast.makeText(mContext, "Checking", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Not Responding", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterLead> call, Throwable t) {
//                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mApiService.brosurCounter(sharedPrefManager.getSpId()).enqueue(new Callback<RespCounterBrosur>() {
//            @Override
//            public void onResponse(Call<RespCounterBrosur> call, Response<RespCounterBrosur> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getApiStatus() != 0) {
//                        String d3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//                        List<CounterBrosur> list = response.body().getData();
//                        List<CounterBrosur> baru = new ArrayList<>();
//                        List<Integer> jumbros = new ArrayList<>();
//                        Integer tot = 0;
//                        for (CounterBrosur p : list) {
//                            if (convertTime(p.getCreatedAt()).equals(selectedDate)) {
//                                baru.add(p);
//                            }
//                        }
//                        for (int i = 0; i < baru.size(); i++) {
//                            if (baru.get(i).getBrosur() != null) {
//                                jumbros.add(baru.get(i).getBrosur());
//                            }
//
//                        }
//                        for (int i = 0; i < jumbros.size(); i++) {
//                            tot += jumbros.get(i);
//
//                        }
//                        brosur.setText(String.valueOf(tot));
//
//                    } else {
//                        Toast.makeText(mContext, "Checking", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(mContext, "Not Responding", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespCounterBrosur> call, Throwable t) {
//                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
//            }
//        });

    }

}
