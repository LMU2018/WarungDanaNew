package com.lmu.warungdana.BottomSheet;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.DetailLeadActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.DetailLead;
import com.lmu.warungdana.Response.RespPost;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetLeadAvail extends BottomSheetDialogFragment {
    TextView nama,outlet,tvStatus,tvJob,tvSource,tvOwner;
    ApiEndPoint mApiService;
    Integer idLead;
    Button lewati,collab;
    SharedPrefManager sharedPrefManager;
    Context context;


    public BottomSheetLeadAvail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_lead_ada, container, false);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(getContext());
        idLead = getArguments().getInt("idLead");
        nama = view.findViewById(R.id.nama);
        outlet = view.findViewById(R.id.outlet);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvJob = view.findViewById(R.id.tvJob);
        tvSource = view.findViewById(R.id.tvSource);
        tvOwner = view.findViewById(R.id.tvOwner);
        lewati = view.findViewById(R.id.lewati);
        collab = view.findViewById(R.id.collab);
        context = getContext();

        mApiService.leadDetail(idLead).enqueue(new Callback<DetailLead>() {
            @Override
            public void onResponse(Call<DetailLead> call, Response<DetailLead> response) {
                if (response.isSuccessful()){
                    if (response.body().getLastName()!=null){
                        nama.setText(response.body().getFirstName()+" "+response.body().getLastName());
                    }else {
                        nama.setText(response.body().getFirstName());
                    }
                    tvStatus.setText(response.body().getLeadMstStatusStatus());
                    tvJob.setText(response.body().getMstJobJob());
                    tvOwner.setText(response.body().getCmsUsersName());
                    tvSource.setText(response.body().getMstDataSourceDatasource());
                    outlet.setText(response.body().getMstOutletOutletName());
                }
            }

            @Override
            public void onFailure(Call<DetailLead> call, Throwable t) {
                Toast.makeText(context,"Fail",Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        lewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        collab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog loading = ProgressDialog.show(context, null, "Harap Tunggu...", true, false);
                mApiService.leadCollabCheck(sharedPrefManager.getSpId(),idLead).enqueue(new Callback<RespPost>() {
                    @Override
                    public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                        if (response.body().getApiStatus()==0){
                            mApiService.lead_collab_create(idLead,sharedPrefManager.getSpId()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()){
                                        loading.dismiss();
                                        dismiss();
                                        Intent intent = new Intent(context, DetailLeadActivity.class);
                                        intent.putExtra("idLead", idLead);
                                        context.startActivity(intent);

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    loading.dismiss();
                                    Toast.makeText(context,"Periksa koneksi anda !",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            loading.dismiss();
                            Toast.makeText(getContext(),"Anda sudah collab dengan data ini !",Toast.LENGTH_SHORT).show();
                            dismiss();
                            Intent intent = new Intent(context, DetailLeadActivity.class);
                            intent.putExtra("idLead", idLead);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<RespPost> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(context,"Periksa koneksi anda !",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
