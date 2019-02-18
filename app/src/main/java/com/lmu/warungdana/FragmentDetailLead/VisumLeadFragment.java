package com.lmu.warungdana.FragmentDetailLead;


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

import com.lmu.warungdana.Adapter.ListVisumAdapter;
import com.lmu.warungdana.Adapter.ListVisumLeadAdapter;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.DetailLeadActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListVisumLead;
import com.lmu.warungdana.Response.ListVisumLead;
import com.lmu.warungdana.Response.RespListLeadVisum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisumLeadFragment extends Fragment {

    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idTarget;
    private Context context;
    ArrayList<ListVisumLead> listVisums;
    SharedPrefManager sharedPrefManager;
    List<ListVisumLead> listLeadsx = null;


    public VisumLeadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        DetailLeadActivity activity = (DetailLeadActivity) getActivity();
        idTarget = activity.idLead;

        Log.d("idTarget",""+idTarget);
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        listVisums = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        if (listVisums.size() >= 1){

            listVisums.clear();
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        mApiService.listLeadVisum(idTarget).enqueue(new Callback<RespListLeadVisum>() {
            @Override
            public void onResponse(Call<RespListLeadVisum> call, Response<RespListLeadVisum> response) {

                if (response.isSuccessful()){

                    if (response.body().getData()!=null) {
                        listLeadsx = response.body().getData();


                        for (int i = 0; i < listLeadsx.size() ; i++){

                            int idCMS = listLeadsx.get(i).getId_cms_users();
                            int spID = sharedPrefManager.getSpId();

                            if (idCMS == spID){

                                Integer id , id_cms_users;
                                String targetFirstName , mstVisumStatus , revisit ,cmsUsername , createdAt , photo;
                                Object targetLastName;

                                id = listLeadsx.get(i).getId();
                                id_cms_users = listLeadsx.get(i).getId_cms_users();
                                targetFirstName = listLeadsx.get(i).getLeadFirstName();
                                targetLastName = listLeadsx.get(i).getLeadLastName();
                                mstVisumStatus = listLeadsx.get(i).getMstVisumStatusStatus();
                                revisit = listLeadsx.get(i).getRevisit();
                                cmsUsername = listLeadsx.get(i).getCmsUsersName();
                                createdAt = listLeadsx.get(i).getCreatedAt();
                                photo = listLeadsx.get(i).getPhoto();

                                listVisums.add(new ListVisumLead(id,targetFirstName,targetLastName,mstVisumStatus,revisit,id_cms_users,cmsUsername,createdAt,photo));
                            }
                        }




                        recyclerView.setAdapter(new ListVisumLeadAdapter(getContext(), listVisums));
                    }
                }

            }

            @Override
            public void onFailure(Call<RespListLeadVisum> call, Throwable t) {
                Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
