package com.lmu.warungdananew.FragmentDetailLead;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lmu.warungdananew.Adapter.ListLogAdapter;
import com.lmu.warungdananew.DetailLeadActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListLog;
import com.lmu.warungdananew.Response.RespListLog;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogLeadFragment extends Fragment {
    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idLead;
    private Context context;

    public LogLeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_lead, container, false);
        DetailLeadActivity activity = (DetailLeadActivity) getActivity();
        idLead = activity.idLead;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.listLog(idLead).enqueue(new Callback<RespListLog>() {
            @Override
            public void onResponse(Call<RespListLog> call, Response<RespListLog> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListLog> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListLogAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListLog> call, Throwable t) {
                Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
