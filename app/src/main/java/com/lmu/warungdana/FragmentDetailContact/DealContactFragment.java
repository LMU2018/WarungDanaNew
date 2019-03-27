package com.lmu.warungdana.FragmentDetailContact;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListOrderContactAdapter;
import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.DetailContact;
import com.lmu.warungdana.Response.ListOrder;
import com.lmu.warungdana.Response.RespListOrder;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealContactFragment extends Fragment {
    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idContact, idUser;
    private TextView wonSum, potentSum;
    SharedPrefManager sharedPrefManager;
    private Context context;


    public DealContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deal_contact, container, false);
        DetailContactActivity activity = (DetailContactActivity) getActivity();
        context =  getContext();
        idContact = activity.idContact;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        wonSum = view.findViewById(R.id.wonDealSum);
        potentSum = view.findViewById(R.id.potentialDealSum);
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.detailContact(idContact).enqueue(new Callback<DetailContact>() {
            @Override
            public void onResponse(Call<DetailContact> call, Response<DetailContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getWonOrder() != null) {
                        wonSum.setText(response.body().getWonOrder().toString());
                        potentSum.setText(response.body().getPotentialOrder().toString());
                    }

                }
            }

            @Override
            public void onFailure(Call<DetailContact> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.listOrder(idUser, null, idContact).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListOrder> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListOrderContactAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
