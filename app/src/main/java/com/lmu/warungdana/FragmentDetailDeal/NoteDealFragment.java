package com.lmu.warungdana.FragmentDetailDeal;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListNoteAdapter;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.DetailDealActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListNote;
import com.lmu.warungdana.Response.RespListNote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDealFragment extends Fragment {
    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idOrder;
    private Context context;


    public NoteDealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recycler_view, container, false);
        DetailDealActivity activity = (DetailDealActivity) getActivity();
        idOrder = activity.idOrder;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.listNoteOrder(idOrder).enqueue(new Callback<RespListNote>() {
            @Override
            public void onResponse(Call<RespListNote> call, Response<RespListNote> response) {
                if (response.isSuccessful()){
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListNote> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListNoteAdapter(getContext(),listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListNote> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
