package com.lmu.warungdana.FragmentLead;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lmu.warungdana.Adapter.ListLeadAdapter;
import com.lmu.warungdana.Adapter.ListLeadVisitAdapter;
import com.lmu.warungdana.Adapter.ListTargetVisitAdapter;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListLead;
import com.lmu.warungdana.Response.ListLeadVisit;
import com.lmu.warungdana.Response.ListTargetVisit;
import com.lmu.warungdana.Response.RespListLeadVisit;
import com.lmu.warungdana.Response.RespListTargetVisit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitLeadFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    Integer idUser;
    private Context context;

    ArrayList<ListLeadVisit> listLeads;
    ListLeadVisitAdapter listLeadAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;
    LinearLayout iconKosong;
    ArrayList<Integer> saveFirst;

    public VisitLeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_data, container, false);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.pull);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onAttach(getContext());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        saveFirst = new ArrayList<>();
        limit = Integer.parseInt(getResources().getString(R.string.limit));
        listLeads = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        iconKosong = view.findViewById(R.id.iconKosong);

        listLeadAdapter = new ListLeadVisitAdapter(getContext(), listLeads);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listLeadAdapter);

        progress.setVisibility(View.VISIBLE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {

//                        if (itShouldLoadMore) {
//                            progress.setVisibility(View.VISIBLE);
////                            loadMore();
//
//                            if ((listLeadAdapter.num)*20 < listLeads.size()){
//                                listLeadAdapter.num = listLeadAdapter.num + 1;
//                                listLeadAdapter.notifyDataSetChanged();
//                            }
//                            progress.setVisibility(View.INVISIBLE);
//                        }
                        if (itShouldLoadMore) {
                            progress.setVisibility(View.VISIBLE);
                            loadMore();

                        }
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        listLeads.clear();
//        listLeadAdapter.notifyDataSetChanged();
//        progress.setVisibility(View.VISIBLE);
//        firstLoad();

    }

    private void firstLoad() {

        if (listLeads.size() >= 1){

            recyclerView.setAdapter(null);
        }else{

            recyclerView.setAdapter(listLeadAdapter);

        }
        saveFirst.clear();
        itShouldLoadMore = false;
        mApiService.listLeadVisit(idUser,limit,0).enqueue(new Callback<RespListLeadVisit>() {
            @Override
            public void onResponse(Call<RespListLeadVisit> call, Response<RespListLeadVisit> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {
                        recyclerView.setAdapter(listLeadAdapter);

                        List<ListLeadVisit> list = response.body().getData();
//                        for (int i = 0; i < list.size(); i++) {
//
//                            Integer id , idLeadMstStatus,idMstLogDesc,idMstLogStatus,favorite;
//                            String firstName , lastName , recall , description , status , created_at_lead , visitStatus , created_at_lead_visum;
//
//                            id = list.get(i).getId();
//                            idLeadMstStatus = list.get(i).getIdLeadMstStatus();
//                            idMstLogDesc = list.get(i).getIdMstLogDesc();
//                            idMstLogStatus = list.get(i).getIdMstLogStatus();
//                            favorite = list.get(i).getFavorite();
//
//                            firstName = list.get(i).getFirstName();
//                            lastName = list.get(i).getLastName();
//                            recall = list.get(i).getRecall();
//                            description = list.get(i).getDescription();
//                            status = list.get(i).getStatus();
//                            created_at_lead = list.get(i).getCreated_at_lead();
//                            visitStatus = list.get(i).getVisit_status();
//                            created_at_lead_visum = list.get(i).getCreated_at_lead_visum();
//
//                            saveFirst.add(id);
//
//                            listLeads.add(new ListLeadVisit(id, firstName, lastName, idLeadMstStatus, recall, idMstLogDesc,
//                                        idMstLogStatus, description, status, favorite, created_at_lead, visitStatus,created_at_lead_visum));
//
//
//
//
//                        }

                        listLeads.addAll(list);


                        listLeadAdapter.notifyDataSetChanged();

                        if (listLeads.size() >= 1) {
                            iconKosong.setVisibility(LinearLayout.INVISIBLE);
                        } else {
                            iconKosong.setVisibility(LinearLayout.VISIBLE);
                        }

                        itShouldLoadMore = true;

                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListLeadVisit> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });

    }

    private void loadMore() {
        itShouldLoadMore = false;
        mApiService.listLeadVisit(idUser, limit,offset).enqueue(new Callback<RespListLeadVisit>() {
            @Override
            public void onResponse(Call<RespListLeadVisit> call, Response<RespListLeadVisit> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListLeadVisit> list = response.body().getData();
//                        for (int i = 0; i < list.size(); i++) {
//                            Integer id , idLeadMstStatus,idMstLogDesc,idMstLogStatus,favorite;
//                            String firstName , lastName , recall , description , status , created_at_lead , visitStatus , created_at_lead_visum;
//
//                            id = list.get(i).getId();
//                            idLeadMstStatus = list.get(i).getIdLeadMstStatus();
//                            idMstLogDesc = list.get(i).getIdMstLogDesc();
//                            idMstLogStatus = list.get(i).getIdMstLogStatus();
//                            favorite = list.get(i).getFavorite();
//
//                            firstName = list.get(i).getFirstName();
//                            lastName = list.get(i).getLastName();
//                            recall = list.get(i).getRecall();
//                            description = list.get(i).getDescription();
//                            status = list.get(i).getStatus();
//                            created_at_lead = list.get(i).getCreated_at_lead();
//                            visitStatus = list.get(i).getVisit_status();
//                            created_at_lead_visum = list.get(i).getCreated_at_lead_visum();
//
//                            saveFirst.add(id);
//
//                            listLeads.add(new ListLeadVisit(id, firstName, lastName, idLeadMstStatus, recall, idMstLogDesc,
//                                    idMstLogStatus, description, status, favorite, created_at_lead, visitStatus,created_at_lead_visum));
//
//
//
//                        }
                        listLeads.addAll(list);
                        listLeadAdapter.notifyDataSetChanged();

                        int index = listLeads.size();
//                        Log.d(TAG, "indexOff: " + index);
                        offset = index;
//                        Log.d(TAG, "offset: " + offset);
                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListLeadVisit> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
//        firstLoad();
        listLeads.clear();
        listLeadAdapter.notifyDataSetChanged();
        progress.setVisibility(View.VISIBLE);
        firstLoad();
    }

}
