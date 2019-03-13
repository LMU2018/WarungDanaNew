package com.lmu.warungdana.FragmentDetailDeal;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.DetailDealActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.DetailOrder;
import com.lmu.warungdana.Response.DetailOrderLoan;
import com.lmu.warungdana.Response.DetailOrderSurety;
import com.lmu.warungdana.Response.DetailProductUFI;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.Response.OrderReasonDetail;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoDealFragment extends Fragment {
    private ApiEndPoint mApiService;
    private Integer idOrder;
    private TextView nopol, tahun, type, vehiclecode, otr, merk, pajak, pemilik;
    private TextView plafond, dp, angsuran, tenor, keperluan, penjamin, ttl , otr_taksasi , nomor_taksasi;
    private TextView status, waktuPooling, sumberOrder, statusKonsumen, alasan;
    private LinearLayout llStatusKonsumen, llAlasan;
    NumberFormat formatter = new DecimalFormat("#,###");
    LinearLayout viewOTRTaksasi , viewNomorTaksasi;
    private Context context;

    public InfoDealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_deal, container, false);
        DetailDealActivity activity = (DetailDealActivity) getActivity();
        idOrder = activity.idOrder;

        llStatusKonsumen = view.findViewById(R.id.llStatusKonsumen);
        llStatusKonsumen.setVisibility(View.GONE);
        llAlasan = view.findViewById(R.id.llAlasan);
        llAlasan.setVisibility(View.GONE);

        status = view.findViewById(R.id.tvStatus);
        alasan = view.findViewById(R.id.tvAlasan);
        waktuPooling = view.findViewById(R.id.tvTanggal);
        sumberOrder = view.findViewById(R.id.tvSumber);
        statusKonsumen = view.findViewById(R.id.tvStatusKonsumen);

        nopol = view.findViewById(R.id.tvNopol);
        tahun = view.findViewById(R.id.tvTahun);
        type = view.findViewById(R.id.tvType);
        vehiclecode = view.findViewById(R.id.tvVehicleCode);
        otr = view.findViewById(R.id.tvOtr);
        merk = view.findViewById(R.id.tvMerk);
        pajak = view.findViewById(R.id.tvStatusPajak);
        pemilik = view.findViewById(R.id.tvNamaPemilik);

        plafond = view.findViewById(R.id.tvPlafond);
        dp = view.findViewById(R.id.tvDP);
        angsuran = view.findViewById(R.id.tvAngsuran);
        tenor = view.findViewById(R.id.tvTenor);
        keperluan = view.findViewById(R.id.tvKeperluan);

        penjamin = view.findViewById(R.id.tvNamaPenjamin);
        ttl = view.findViewById(R.id.tvTTLPenjamin);

        otr_taksasi = view.findViewById(R.id.tvOTRTaksasi);
        nomor_taksasi = view.findViewById(R.id.tvNomorTaksasi);

        viewNomorTaksasi = view.findViewById(R.id.viewNomorTaksasi);
       viewOTRTaksasi = view.findViewById(R.id.viewOTRTaksasi);



        mApiService = UtilsApi.getAPIService();

        getDetailOrder();
        getDetailProductUFI();
        getDetailSurety();
//        getDetailLoan();
        return view;
    }

    private String convertTime2(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate2 = format1.format(date);
        return convertedDate2;
    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMMM yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }

    private void getDetailOrder() {

        mApiService.orderDetail(idOrder).enqueue(new Callback<DetailOrder>() {
            @Override
            public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                if (response.isSuccessful()) {


                    if (response.body().getOrderMstStatusStatus() != null) {
                        status.setText(response.body().getOrderMstStatusStatus().toUpperCase());
                        if (response.body().getOrderMstStatusStatus().equals("Cancel")){
                            llAlasan.setVisibility(View.VISIBLE);
                            mApiService.order_reason(response.body().getIdOrderMstReason()).enqueue(new Callback<OrderReasonDetail>() {
                                @Override
                                public void onResponse(Call<OrderReasonDetail> call, Response<OrderReasonDetail> response) {
                                    if (response.isSuccessful()){
                                        alasan.setText(response.body().getReason());
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderReasonDetail> call, Throwable t) {

                                }
                            });
                        }
                        else if(response.body().getOrderMstStatusStatus().equals("Reject")){
                            llAlasan.setVisibility(View.VISIBLE);
                            mApiService.order_reason(response.body().getIdOrderMstReason()).enqueue(new Callback<OrderReasonDetail>() {
                                @Override
                                public void onResponse(Call<OrderReasonDetail> call, Response<OrderReasonDetail> response) {
                                    if (response.isSuccessful()){
                                        alasan.setText(response.body().getReason());
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderReasonDetail> call, Throwable t) {

                                }
                            });
                        }
                    } else {
                        status.setText("Empty");
                    }

                    if (response.body().getCreatedAt() != null) {
                        waktuPooling.setText(convertTime2(response.body().getCreatedAt()));
                    } else {
                        waktuPooling.setText("Empty");
                    }

                    if (response.body().getMstDataSourceDatasource() != null) {
                        sumberOrder.setText(response.body().getMstDataSourceDatasource().toUpperCase());
                    } else {
                        sumberOrder.setText("Empty");
                    }

                } else {
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailOrder> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDetailProductUFI() {

        mApiService.productUfiDetail(idOrder).enqueue(new Callback<DetailProductUFI>() {
            @Override
            public void onResponse(Call<DetailProductUFI> call, Response<DetailProductUFI> response) {
                if (response.isSuccessful()) {

                    if (response.body().getNopol() != null) {
                        nopol.setText(response.body().getNopol());
                    } else {
                        nopol.setText("Empty");
                    }

                    if (response.body().getMstUnitYear() != null) {
                        tahun.setText(response.body().getMstUnitYear().toString());
                    } else {
                        tahun.setText("Empty");
                    }

                    if (response.body().getMstUnitModel() != null) {
                        type.setText(response.body().getMstUnitModel());
                    } else {
                        type.setText("Empty");
                    }

                    if (response.body().getMstUnitKodeUnit() != null) {
                        vehiclecode.setText(response.body().getMstUnitKodeUnit());
                    } else {
                        vehiclecode.setText("Empty");
                    }

                    if (response.body().getMstUnitOtr() != null) {
                        otr.setText("IDR " + formatter.format(response.body().getMstUnitOtr()));
                    } else {
                        otr.setText("Empty");
                    }

                    if (response.body().getMstUnitMerk() != null) {
                        merk.setText(response.body().getMstUnitMerk());
                    } else {
                        merk.setText("Empty");
                    }

                    if (response.body().getTaxStatus() != null) {
                        if (response.body().getTaxStatus().equalsIgnoreCase("Y")) {
                            pajak.setText("Hidup");
                        } else {
                            pajak.setText("Mati");
                        }
                    } else {
                        pajak.setText("Empty");
                    }

                    if (response.body().getOwner() != null) {
                        pemilik.setText(response.body().getOwner());
                    } else {
                        pemilik.setText("Empty");
                    }

                    if (response.body().getOtr_taksasi()!=null){
                        otr_taksasi.setText("IDR " + formatter.format(response.body().getOtr_taksasi()));
                    }else{

                        viewOTRTaksasi.setVisibility(LinearLayout.GONE);
                    }

                    if (response.body().getNomor_taksasi()!=null){
                        nomor_taksasi.setText(response.body().getNomor_taksasi());
                    }else{

                       viewNomorTaksasi.setVisibility(LinearLayout.GONE);
                    }

                    getDetailLoan();

                } else {
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailProductUFI> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailLoan() {
        mApiService.orderLoanDetail(idOrder).enqueue(new Callback<DetailOrderLoan>() {
            @Override
            public void onResponse(Call<DetailOrderLoan> call, Response<DetailOrderLoan> response) {
                if (response.isSuccessful()) {

                    if (response.body().getOtr_custom() != null){

                        otr.setText("IDR " + formatter.format(response.body().getOtr_custom()));
                    }


                    if (response.body().getPlafond() != null) {
                        plafond.setText("IDR " + formatter.format(response.body().getPlafond()));
                    } else {
                        plafond.setText("Empty");
                    }

                    if (response.body().getDownPayment() != null) {
                        dp.setText("IDR " + formatter.format(response.body().getDownPayment()));
                    } else {
                        dp.setText("Empty");
                    }

                    if (response.body().getInstallment() != null) {
                        angsuran.setText("IDR " + formatter.format(response.body().getInstallment()));
                    } else {
                        angsuran.setText("Empty");
                    }

                    if (response.body().getTenor() != null) {
                        tenor.setText(response.body().getTenor().toString() + " " + "Bulan");
                    } else {
                        tenor.setText("Empty");
                    }

                    if (response.body().getNeed() != null) {
                        keperluan.setText(response.body().getNeed().toString());
                    } else {
                        keperluan.setText("Empty");
                    }

                } else {
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailOrderLoan> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailSurety() {
        mApiService.orderSuretyDetail(idOrder).enqueue(new Callback<DetailOrderSurety>() {
            @Override
            public void onResponse(Call<DetailOrderSurety> call, Response<DetailOrderSurety> response) {
                if (response.isSuccessful()) {

                    if (response.body().getName() != null) {
                        penjamin.setText(response.body().getName());
                    } else {
                        penjamin.setText("Empty");

                    }
                    if (response.body().getBirthPlace() != null & response.body().getBirthDate() != null) {
                        ttl.setText(response.body().getBirthPlace() + ", " + convertTime(response.body().getBirthDate()));
                    } else if (response.body().getBirthPlace() != null & response.body().getBirthDate() == null) {
                        ttl.setText(response.body().getBirthPlace() + ", " + "Empty");
                    } else if (response.body().getBirthPlace() == null & response.body().getBirthDate() != null) {
                        ttl.setText("Empty" + ", " + convertTime(response.body().getBirthDate()));
                    } else {
                        ttl.setText("Empty");
                    }
                } else {
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailOrderSurety> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
