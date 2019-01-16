package com.lmu.warungdananew.FragmentDetailTarget;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lmu.warungdananew.Adapter.ListLogTargetAdapter;
import com.lmu.warungdananew.DetailTargetActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListLogTarget;
import com.lmu.warungdananew.Response.RespListLogTarget;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogTargetFragment extends Fragment {
    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idTarget;
    private Context context;


    public LogTargetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_log_target, container, false);
        DetailTargetActivity activity = (DetailTargetActivity) getActivity();
        idTarget = activity.idTarget;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.listLogTarget(idTarget).enqueue(new Callback<RespListLogTarget>() {
            @Override
            public void onResponse(Call<RespListLogTarget> call, Response<RespListLogTarget> response) {
                if (response.isSuccessful()){
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListLogTarget> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListLogTargetAdapter(getContext(),listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListLogTarget> call, Throwable t) {
                Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
