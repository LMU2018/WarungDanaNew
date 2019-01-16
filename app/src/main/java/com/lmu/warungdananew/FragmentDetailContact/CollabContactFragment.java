package com.lmu.warungdananew.FragmentDetailContact;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lmu.warungdananew.Adapter.ListContactCollabAdapter;
import com.lmu.warungdananew.DetailContactActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListContactCollaborate;
import com.lmu.warungdananew.Response.RespListContactCollaborate;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollabContactFragment extends Fragment {
    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idContact;
    private Context context;


    public CollabContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collab_contact, container, false);
        DetailContactActivity activity = (DetailContactActivity) getActivity();
        idContact = activity.idContact;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.listContactCollab(idContact).enqueue(new Callback<RespListContactCollaborate>() {
            @Override
            public void onResponse(Call<RespListContactCollaborate> call, Response<RespListContactCollaborate> response) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                List<ListContactCollaborate> listLeads = response.body().getData();
                recyclerView.setAdapter(new ListContactCollabAdapter(getContext(), listLeads));
            }

            @Override
            public void onFailure(Call<RespListContactCollaborate> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
