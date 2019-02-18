package com.lmu.warungdana.FragmentDetailTarget;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListLogTargetAdapter;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.DetailTargetActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListLogTarget;
import com.lmu.warungdana.Response.RespListLogTarget;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
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
    SharedPrefManager sharedPrefManager;
    private Context context;
    ArrayList<ListLogTarget> listTargets;
    Activity mActivity;


    public LogTargetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recycler_view, container, false);
        DetailTargetActivity activity = (DetailTargetActivity) getActivity();
        mActivity = getActivity();
        idTarget = activity.idTarget;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        sharedPrefManager = new SharedPrefManager(getContext());
        listTargets = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.listLogTarget(idTarget,sharedPrefManager.getSpId()).enqueue(new Callback<RespListLogTarget>() {
            @Override
            public void onResponse(Call<RespListLogTarget> call, Response<RespListLogTarget> response) {
                if (response.isSuccessful()){
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListLogTarget> listLeads = response.body().getData();

//                    for (int i = 0 ; i< listLeads.size(); i++ ){
//
//                        int idCMS = listLeads.get(i).getId_cms_users();
//                        int spID = sharedPrefManager.getSpId();
//
//                        if (idCMS == spID){
//
//                            Integer id;
//                            String targetFirstName;
//                            String targetLastName;
//                            Integer duration;
//                            Integer idMstLogDesc;
//                            String mstLogDescDescription;
//                            String recall;
//                            Integer id_cms_users;
//                            String cmsUsersName;
//                            String createdAt;
//                            Integer idMstLogStatus;
//                            String status;
//
//                            id = listLeads.get(i).getId();
//                            targetFirstName = listLeads.get(i).getTargetFirstName();
//                            targetLastName = (String) listLeads.get(i).getTargetLastName();
//                            duration = listLeads.get(i).getDuration();
//                            idMstLogDesc = listLeads.get(i).getIdMstLogDesc();
//                            mstLogDescDescription = listLeads.get(i).getMstLogDescDescription();
//                            recall = listLeads.get(i).getRecall();
//                            id_cms_users = listLeads.get(i).getId_cms_users();
//                            cmsUsersName = listLeads.get(i).getCmsUsersName();
//
//                            Log.d("Username" , ""+cmsUsersName);
//                            createdAt = listLeads.get(i).getCreatedAt();
//                            idMstLogStatus = listLeads.get(i).getIdMstLogStatus();
//                            status = listLeads.get(i).getStatus();
//
//                            listTargets.add(new ListLogTarget(id,
//                                    targetFirstName,
//                                    targetLastName,
//                                    duration,
//                                    idMstLogDesc,
//                                    mstLogDescDescription,
//                                    recall,
//                                    id_cms_users,
//                                    cmsUsersName,
//                                    createdAt,
//                                    idMstLogStatus,
//                                    status));
//
//
//                        }
//                    }

                    listTargets.addAll(listLeads);

                    recyclerView.setAdapter(new ListLogTargetAdapter(getContext(),listTargets));

                }
            }

            @Override
            public void onFailure(Call<RespListLogTarget> call, Throwable t) {
                Toast.makeText(mActivity, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
