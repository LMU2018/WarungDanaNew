package com.lmu.warungdana.FragmentContact;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lmu.warungdana.Adapter.ListContactAdapter;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListContact;
import com.lmu.warungdana.Response.RespListContact;
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
public class CurrentContactFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    Integer iduser;
    SharedPrefManager sharedPrefManager;
    private Context context;

    ArrayList<ListContact> listContacts;
    ListContactAdapter listContactAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;

    public CurrentContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        listContacts = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        iduser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        /*progress.setVisibility(View.VISIBLE);
        firstLoad();*/

        listContactAdapter = new ListContactAdapter(getContext(), listContacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listContactAdapter);

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
        listContacts.clear();
        progress.setVisibility(View.VISIBLE);
        firstLoad();
/*
        mApiService.listContact(iduser).enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListContact> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListContactAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListContact> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.listContactPagg(iduser, limit, 0).enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListContact> list = response.body().getData();
//                        for (int i = 0; i < list.size(); i++) {
//                            String contactFirstName, contactLastName, contactGender, status;
//                            Integer id, idContact, idOrderMstStatus;
//                            id = list.get(i).getId();
//                            idContact = list.get(i).getIdContact();
//                            contactFirstName = list.get(i).getContactFirstName();
//                            contactLastName = list.get(i).getContactLastName();
//                            contactGender = list.get(i).getContactGender();
//                            idOrderMstStatus = list.get(i).getIdOrderMstStatus();
//                            status = list.get(i).getStatus();
//
//                            listContacts.add(new ListContact(id, idContact, contactFirstName, contactLastName, contactGender, idOrderMstStatus, status));
//                        }
                        listContacts.addAll(list);
                        listContactAdapter.notifyDataSetChanged();
                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListContact> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void loadMore() {
        itShouldLoadMore = false;
        mApiService.listContactPagg(iduser, limit, offset).enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListContact> list = response.body().getData();
//                        for (int i = 0; i < list.size(); i++) {
//                            String contactFirstName, contactLastName, contactGender, status;
//                            Integer id, idContact, idOrderMstStatus;
//                            id = list.get(i).getId();
//                            idContact = list.get(i).getIdContact();
//                            contactFirstName = list.get(i).getContactFirstName();
//                            contactLastName = list.get(i).getContactLastName();
//                            contactGender = list.get(i).getContactGender();
//                            idOrderMstStatus = list.get(i).getIdOrderMstStatus();
//                            status = list.get(i).getStatus();
//
//                            listContacts.add(new ListContact(id, idContact, contactFirstName, contactLastName, contactGender, idOrderMstStatus, status));
//                        }
                        listContacts.addAll(list);
                        listContactAdapter.notifyDataSetChanged();
                        int index = listContacts.size();
                        offset = index;
                        progress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListContact> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });

    }

}
