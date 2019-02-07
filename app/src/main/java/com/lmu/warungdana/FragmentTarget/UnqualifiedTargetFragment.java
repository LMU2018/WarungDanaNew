package com.lmu.warungdana.FragmentTarget;


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

import com.lmu.warungdana.Adapter.ListTargetAdapter;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListTarget;
import com.lmu.warungdana.Response.RespListTarget;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class UnqualifiedTargetFragment extends Fragment implements Comparator<ListTarget> {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    Integer idUser;
    private Context context;
    ArrayList<ListTarget> listTargets;
    ListTargetAdapter listTargetAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;
    LinearLayout iconKosong;

    public UnqualifiedTargetFragment() {
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
        listTargets = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        listTargetAdapter = new ListTargetAdapter(getContext(), listTargets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listTargetAdapter);
        iconKosong = view.findViewById(R.id.iconKosong);

        /*progress.setVisibility(View.VISIBLE);
        firstLoad();*/

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {

                        if (itShouldLoadMore) {
                            progress.setVisibility(View.VISIBLE);
//                            loadMore();

                            if ((listTargetAdapter.num)*20 < listTargets.size()){
                                listTargetAdapter.num = listTargetAdapter.num + 1;
                                listTargetAdapter.notifyDataSetChanged();
                            }
                            progress.setVisibility(View.INVISIBLE);
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
        listTargets.clear();
        progress.setVisibility(View.VISIBLE);
        firstLoad();
//        mApiService.listTarget(idUser, 5).enqueue(new Callback<RespListTarget>() {
//            @Override
//            public void onResponse(Call<RespListTarget> call, Response<RespListTarget> response) {
//                if (response.isSuccessful()) {
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                    recyclerView.setLayoutManager(layoutManager);
//                    List<ListTarget> listLeads = response.body().getData();
//                    recyclerView.setAdapter(new ListTargetAdapter(getContext(), listLeads));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespListTarget> call, Throwable t) {
//                Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.listTargetPagg(idUser, 5).enqueue(new Callback<RespListTarget>() {
            @Override
            public void onResponse(Call<RespListTarget> call, Response<RespListTarget> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListTarget> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String category, firstName, lastName, recall, description, status, revisit, visitStatus,updated_by , created_at_log;
                            Integer id, idTargetMstStatus, idMstLogDesc, idMstLogStatus, idMstVisumStatus;
                            category = list.get(i).getCategory();
                            firstName = list.get(i).getFirstName();
                            lastName = list.get(i).getLastName();
                            recall = list.get(i).getRecall();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();
                            revisit = list.get(i).getRevisit();
                            visitStatus = list.get(i).getVisitStatus();
                            id = list.get(i).getId();
                            idTargetMstStatus = list.get(i).getIdTargetMstStatus();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            idMstVisumStatus = list.get(i).getIdMstVisumStatus();
                            updated_by = list.get(i).getUpdated_by();
                            created_at_log = list.get(i).getCreated_at_target_log();
                            listTargets.add(new ListTarget(id, idTargetMstStatus, category, firstName, lastName, updated_by,recall, idMstLogDesc,
                                    idMstLogStatus, description, status, idMstVisumStatus, revisit, visitStatus,created_at_log));
                        }

                        Collections.sort(listTargets,Collections.reverseOrder(UnqualifiedTargetFragment.this));
                        listTargetAdapter.notifyDataSetChanged();

                        if (listTargets.size() >= 1) {
                            iconKosong.setVisibility(LinearLayout.INVISIBLE);
                        } else {
                            iconKosong.setVisibility(LinearLayout.VISIBLE);
                        }

                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListTarget> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int compare(ListTarget listTarget, ListTarget t1) {
        return listTarget.getUpdated_by().compareTo(t1.getUpdated_by());
    }

//    private void loadMore() {
//        itShouldLoadMore = false;
//        mApiService.listTargetPagg(idUser, 5, limit, offset).enqueue(new Callback<RespListTarget>() {
//            @Override
//            public void onResponse(Call<RespListTarget> call, Response<RespListTarget> response) {
//                if (response.isSuccessful()) {
//                    itShouldLoadMore = true;
//                    if (response.body().getData() != null) {
//                        List<ListTarget> list = response.body().getData();
//                        for (int i = 0; i < list.size(); i++) {
//                            String category, firstName, lastName, recall, description, status, revisit, visitStatus;
//                            Integer id, idTargetMstStatus, idMstLogDesc, idMstLogStatus, idMstVisumStatus;
//                            category = list.get(i).getCategory();
//                            firstName = list.get(i).getFirstName();
//                            lastName = list.get(i).getLastName();
//                            recall = list.get(i).getRecall();
//                            description = list.get(i).getDescription();
//                            status = list.get(i).getStatus();
//                            revisit = list.get(i).getRevisit();
//                            visitStatus = list.get(i).getVisitStatus();
//                            id = list.get(i).getId();
//                            idTargetMstStatus = list.get(i).getIdTargetMstStatus();
//                            idMstLogDesc = list.get(i).getIdMstLogDesc();
//                            idMstLogStatus = list.get(i).getIdMstLogStatus();
//                            idMstVisumStatus = list.get(i).getIdMstVisumStatus();
//                            listTargets.add(new ListTarget(id, idTargetMstStatus, category, firstName, lastName, recall, idMstLogDesc,
//                                    idMstLogStatus, description, status, idMstVisumStatus, revisit, visitStatus));
//                        }
//                        listTargetAdapter.notifyDataSetChanged();
//
//                        int index = listTargets.size();
//                        Log.d(TAG, "indexOff: " + index);
//                        offset = index;
//                        Log.d(TAG, "offset: " + offset);
//                        progress.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespListTarget> call, Throwable t) {
//                itShouldLoadMore = true;
//                progress.setVisibility(View.GONE);
//            }
//        });
//
//    }

}
