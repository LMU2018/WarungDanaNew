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

import com.lmu.warungdananew.Adapter.ListVisumAdapter;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.DetailTargetActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListVisum;
import com.lmu.warungdananew.Response.RespListVisum;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisumTargetFragment extends Fragment {
    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idTarget;
    private Context context;
    ArrayList<ListVisum> listVisums;
    SharedPrefManager sharedPrefManager;


    public VisumTargetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visum_target, container, false);
        DetailTargetActivity activity = (DetailTargetActivity) getActivity();
        idTarget = activity.idTarget;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        listVisums = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.listVisum(idTarget).enqueue(new Callback<RespListVisum>() {
            @Override
            public void onResponse(Call<RespListVisum> call, Response<RespListVisum> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListVisum> listLeads = response.body().getData();

                    for (int i = 0; i < listLeads.size() ; i++){

                        int idCMS = listLeads.get(i).getId_cms_users();
                        int spID = sharedPrefManager.getSpId();

                        if (idCMS == spID){

                            Integer id , id_cms_users;
                            String targetFirstName , mstVisumStatus , revisit ,cmsUsername , createdAt , photo;
                            Object targetLastName;

                            id = listLeads.get(i).getId();
                            id_cms_users = listLeads.get(i).getId_cms_users();
                            targetFirstName = listLeads.get(i).getTargetFirstName();
                            targetLastName = listLeads.get(i).getTargetLastName();
                            mstVisumStatus = listLeads.get(i).getMstVisumStatusStatus();
                            revisit = listLeads.get(i).getRevisit();
                            cmsUsername = listLeads.get(i).getCmsUsersName();
                            createdAt = listLeads.get(i).getCreatedAt();
                            photo = listLeads.get(i).getPhoto();

                            listVisums.add(new ListVisum(id,targetFirstName,targetLastName,mstVisumStatus,revisit,id_cms_users,cmsUsername,createdAt,photo));
                        }
                    }
                    recyclerView.setAdapter(new ListVisumAdapter(getContext(), listVisums));
                }
            }

            @Override
            public void onFailure(Call<RespListVisum> call, Throwable t) {
                Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
