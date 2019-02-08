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
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListLead;
import com.lmu.warungdana.Response.RespListLead;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnqualifiedLeadFragment extends Fragment implements Comparator<ListLead> {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    Integer idUser;
    SharedPrefManager sharedPrefManager;
    private Context context;

    ArrayList<ListLead> listLeads;
    ListLeadAdapter listLeadAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;
    LinearLayout iconKosong;

    public UnqualifiedLeadFragment() {
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
        listLeads = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        iconKosong = view.findViewById(R.id.iconKosong);

        /*progress.setVisibility(View.VISIBLE);
        firstLoad();*/

        listLeadAdapter = new ListLeadAdapter(getContext(), listLeads);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listLeadAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

//                    if (itShouldLoadMore) {
//                        progress.setVisibility(View.VISIBLE);
////                            loadMore();
//                        if ((listLeadAdapter.num)*20 < listLeads.size()){
//                            listLeadAdapter.num = listLeadAdapter.num + 1;
//                            listLeadAdapter.notifyDataSetChanged();
//                        }
//
//                        progress.setVisibility(View.INVISIBLE);
//                    }
                    if (itShouldLoadMore) {
                        progress.setVisibility(View.VISIBLE);
                        loadMore();

                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listLeads.clear();
        progress.setVisibility(View.VISIBLE);
        firstLoad();
/*
        mApiService.listLead(idUser, 4).enqueue(new Callback<RespListLead>() {
            @Override
            public void onResponse(Call<RespListLead> call, Response<RespListLead> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListLead> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListLeadAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListLead> call, Throwable t) {
                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
*/

    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.listLeadPagg(idUser, 4,limit,0).enqueue(new Callback<RespListLead>() {
            @Override
            public void onResponse(Call<RespListLead> call, Response<RespListLead> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListLead> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String firstName, lastName, recall, description, status,created_at;
                            Integer id, idLeadMstStatus, idMstLogDesc, idMstLogStatus, favorite;
                            id = list.get(i).getId();
                            firstName = list.get(i).getFirstName();
                            lastName = list.get(i).getLastName();
                            idLeadMstStatus = list.get(i).getIdLeadMstStatus();
                            recall = list.get(i).getRecall();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();
                            favorite = list.get(i).getFavorite();
                            created_at = list.get(i).getCreated_at_lead();

                            listLeads.add(new ListLead(id, firstName, lastName, idLeadMstStatus, recall, idMstLogDesc, idMstLogStatus, description, status, favorite,created_at));
                        }

                        Collections.sort(listLeads,Collections.reverseOrder(UnqualifiedLeadFragment.this));
                        listLeadAdapter.notifyDataSetChanged();

                        if (listLeads.size() >= 1) {
                            iconKosong.setVisibility(LinearLayout.INVISIBLE);
                        } else {
                            iconKosong.setVisibility(LinearLayout.VISIBLE);
                        }

                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListLead> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int compare(ListLead listLead, ListLead t1) {
        return listLead.getCreated_at_lead().compareTo(t1.getCreated_at_lead());
    }

    private void loadMore() {
        itShouldLoadMore = false;
        mApiService.listLeadPagg(idUser, 4, limit, offset).enqueue(new Callback<RespListLead>() {
            @Override
            public void onResponse(Call<RespListLead> call, Response<RespListLead> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListLead> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String firstName, lastName, recall, description, status,created_at;
                            Integer id, idLeadMstStatus, idMstLogDesc, idMstLogStatus, favorite;
                            id = list.get(i).getId();
                            firstName = list.get(i).getFirstName();
                            lastName = list.get(i).getLastName();
                            idLeadMstStatus = list.get(i).getIdLeadMstStatus();
                            recall = list.get(i).getRecall();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();
                            favorite = list.get(i).getFavorite();
                            created_at = list.get(i).getCreated_at_lead();

                            listLeads.add(new ListLead(id, firstName, lastName, idLeadMstStatus, recall, idMstLogDesc, idMstLogStatus, description, status, favorite,created_at));
                        }

                        listLeadAdapter.notifyDataSetChanged();
                        int index = listLeads.size();
                        offset = index;
                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListLead> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

}
