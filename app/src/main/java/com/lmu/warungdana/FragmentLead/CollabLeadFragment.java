package com.lmu.warungdana.FragmentLead;


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

import com.lmu.warungdana.Adapter.ListLeadCollabAdapter;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListLeadCollab;
import com.lmu.warungdana.Response.RespListLeadCollab;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollabLeadFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    private Integer idUser;
    SharedPrefManager sharedPrefManager;

    ArrayList<ListLeadCollab> listLeadCollabs;
    ListLeadCollabAdapter listLeadCollabAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;
    LinearLayout iconKosong;

    public CollabLeadFragment() {
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

        limit = Integer.parseInt(getResources().getString(R.string.limit));
        listLeadCollabs = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        iconKosong = view.findViewById(R.id.iconKosong);

        /*progress.setVisibility(View.VISIBLE);
        firstLoad();*/

        listLeadCollabAdapter = new ListLeadCollabAdapter(getContext(), listLeadCollabs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listLeadCollabAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
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
    public void onResume() {
        super.onResume();
        listLeadCollabs.clear();
        listLeadCollabAdapter.notifyDataSetChanged();
        progress.setVisibility(View.VISIBLE);
        firstLoad();
    }

    @Override
    public void onStart() {
        super.onStart();
//        listLeadCollabs.clear();
//        progress.setVisibility(View.VISIBLE);
//        firstLoad();
/*
        mApiService.listLeadCollab(idUser).enqueue(new Callback<RespListLeadCollab>() {
            @Override
            public void onResponse(Call<RespListLeadCollab> call, Response<RespListLeadCollab> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListLeadCollab> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListLeadCollabAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListLeadCollab> call, Throwable t) {

            }
        });
*/

    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.listLeadCollab(idUser, limit, 0).enqueue(new Callback<RespListLeadCollab>() {
            @Override
            public void onResponse(Call<RespListLeadCollab> call, Response<RespListLeadCollab> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListLeadCollab> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String leadFirstName, leadLastName, cmsUserName, recall, description, status;
                            Integer id, idLead, idMstLogDesc, idMstLogStatus;
                            id = list.get(i).getId();
                            idLead = list.get(i).getIdLead();
                            leadFirstName = list.get(i).getLeadFirstName();
                            leadLastName = list.get(i).getLeadLastName();
                            cmsUserName = list.get(i).getCmsUsersName();
                            recall = list.get(i).getRecall();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();

                            listLeadCollabs.add(new ListLeadCollab(id, idLead, leadFirstName, leadLastName, cmsUserName, recall, idMstLogDesc, idMstLogStatus, description, status));
                        }

                        listLeadCollabAdapter.notifyDataSetChanged();

                        if (listLeadCollabs.size() >= 1) {
                            iconKosong.setVisibility(LinearLayout.INVISIBLE);
                        } else {
                            iconKosong.setVisibility(LinearLayout.VISIBLE);
                        }

                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListLeadCollab> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void loadMore() {
        itShouldLoadMore = false;
        mApiService.listLeadCollab(idUser, limit, offset).enqueue(new Callback<RespListLeadCollab>() {
            @Override
            public void onResponse(Call<RespListLeadCollab> call, Response<RespListLeadCollab> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListLeadCollab> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String leadFirstName, leadLastName, cmsUserName, recall, description, status;
                            Integer id, idLead, idMstLogDesc, idMstLogStatus;
                            id = list.get(i).getId();
                            idLead = list.get(i).getIdLead();
                            leadFirstName = list.get(i).getLeadFirstName();
                            leadLastName = list.get(i).getLeadLastName();
                            cmsUserName = list.get(i).getCmsUsersName();
                            recall = list.get(i).getRecall();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();

                            listLeadCollabs.add(new ListLeadCollab(id, idLead, leadFirstName, leadLastName, cmsUserName, recall, idMstLogDesc, idMstLogStatus, description, status));
                        }

                        listLeadCollabAdapter.notifyDataSetChanged();
                        int index = listLeadCollabs.size();
                        offset = index;
                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListLeadCollab> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

}
