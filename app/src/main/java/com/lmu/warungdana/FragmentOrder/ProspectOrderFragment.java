package com.lmu.warungdana.FragmentOrder;


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

import com.lmu.warungdana.Adapter.ListOrderAdapter;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListOrder;
import com.lmu.warungdana.Response.RespListOrder;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProspectOrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    Integer idUser;
    private Context context;

    ArrayList<ListOrder> listOrders;
    ListOrderAdapter listOrderAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;
    LinearLayout iconKosong;

    public ProspectOrderFragment() {
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
        listOrders = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        /*progress.setVisibility(View.VISIBLE);
        firstLoad();*/

        listOrderAdapter = new ListOrderAdapter(getContext(), listOrders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listOrderAdapter);
        iconKosong = view.findViewById(R.id.iconKosong);


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
    public void onStart() {
        super.onStart();
        listOrders.clear();
        progress.setVisibility(View.VISIBLE);
        firstLoad();
/*
        mApiService.listOrder(idUser, 2, null).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListOrder> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListOrderAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.listOrderPagg(idUser, 2, null, limit, 0).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListOrder> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String firstName, lastName, status, model, createdAt;
                            Integer id, idContact, plafond, idUnit;
                            id = list.get(i).getId();
                            idContact = list.get(i).getIdContact();
                            firstName = list.get(i).getContactFirstName();
                            lastName = list.get(i).getContactLastName();
                            status = list.get(i).getOrderMstStatusStatus();
                            plafond = list.get(i).getPlafond();
                            idUnit = list.get(i).getIdMstUnit();
                            model = list.get(i).getModel();
                            createdAt = list.get(i).getCreatedAt();

                            listOrders.add(new ListOrder(id, idContact, firstName, lastName, status, plafond, idUnit, model, createdAt));

                        }

                        listOrderAdapter.notifyDataSetChanged();

                        if (listOrders.size() >= 1) {
                            iconKosong.setVisibility(LinearLayout.INVISIBLE);
                        } else {
                            iconKosong.setVisibility(LinearLayout.VISIBLE);
                        }

                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void loadMore() {
        itShouldLoadMore = false;
        mApiService.listOrderPagg(idUser, 2, null, limit, offset).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListOrder> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String firstName, lastName, status, model, createdAt;
                            Integer id, idContact, plafond, idUnit;
                            id = list.get(i).getId();
                            idContact = list.get(i).getIdContact();
                            firstName = list.get(i).getContactFirstName();
                            lastName = list.get(i).getContactLastName();
                            status = list.get(i).getOrderMstStatusStatus();
                            plafond = list.get(i).getPlafond();
                            idUnit = list.get(i).getIdMstUnit();
                            model = list.get(i).getModel();
                            createdAt = list.get(i).getCreatedAt();

                            listOrders.add(new ListOrder(id, idContact, firstName, lastName, status, plafond, idUnit, model, createdAt));

                        }

                        listOrderAdapter.notifyDataSetChanged();
                        int index = listOrders.size();
                        offset = index;
                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

}
