package com.lmu.warungdana.FragmentHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListUserLogAdapter;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListUserLog;
import com.lmu.warungdana.Response.RespListUserLog;
import com.lmu.warungdana.Utils.UtilsConnected;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    private Integer idUser;
    SharedPrefManager sharedPrefManager;
    boolean isConn;

    ArrayList<ListUserLog> listUserLogs;
    ListUserLogAdapter listUserLogAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;
    TextView baba;
    View bibi;

    public ActivityFragment() {
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
        listUserLogs = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        isConn = UtilsConnected.isNetworkConnected(getContext());

        listUserLogAdapter = new ListUserLogAdapter(getContext(), listUserLogs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listUserLogAdapter);

        baba = view.findViewById(R.id.baba);
        bibi = view.findViewById(R.id.a);
        baba.setVisibility(TextView.VISIBLE);
        bibi.setVisibility(View.VISIBLE);

       /* if (isConn) {
            progress.setVisibility(View.VISIBLE);
            firstLoad();
        } else {
            Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_LONG).show();
        }*/


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        if (itShouldLoadMore) {
                            if (isConn) {
                                progress.setVisibility(View.VISIBLE);
                                loadMore();
                            } else {
                                Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_LONG).show();
                            }
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
        if (isConn) {
            listUserLogs.clear();
            progress.setVisibility(View.VISIBLE);
            firstLoad();
        } else {
            Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_LONG).show();
        }

        /*if (isConn) {

            mApiService.userLogListing(idUser, 15).enqueue(new Callback<RespListUserLog>() {
                @Override
                public void onResponse(Call<RespListUserLog> call, Response<RespListUserLog> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getApiStatus() != 0) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            List<ListUserLog> listLeads = response.body().getData();
                            recyclerView.setAdapter(new ListUserLogAdapter(getContext(), listLeads));
                        }
                    } else {
                        Toast.makeText(getContext(), "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RespListUserLog> call, Throwable t) {
                    Toast.makeText(getContext(), "Not Responding", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            Toast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_LONG).show();
        }*/
    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.userLogListing(idUser, limit, 0).enqueue(new Callback<RespListUserLog>() {
            @Override
            public void onResponse(Call<RespListUserLog> call, Response<RespListUserLog> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListUserLog> list = response.body().getData();
//                        for (int i = 0; i < list.size(); i++) {
//                            String cmsUsersName, jenis, createdAt;
//                            Integer id, idModul, idData;
//                            id = list.get(i).getId();
//                            cmsUsersName = list.get(i).getCmsUsersName();
//                            idModul = list.get(i).getIdModul();
//                            idData = list.get(i).getIdData();
//                            jenis = list.get(i).getJenis();
//                            createdAt = list.get(i).getCreatedAt();
//
//                            listUserLogs.add(new ListUserLog(id, cmsUsersName, idModul, idData, jenis, createdAt));
//                        }

                        listUserLogs.addAll(list);

                        listUserLogAdapter.notifyDataSetChanged();
                        progress.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<RespListUserLog> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });

    }

    private void loadMore() {
        itShouldLoadMore = false;
        mApiService.userLogListing(idUser, limit, offset).enqueue(new Callback<RespListUserLog>() {
            @Override
            public void onResponse(Call<RespListUserLog> call, Response<RespListUserLog> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListUserLog> list = response.body().getData();
//                        for (int i = 0; i < list.size(); i++) {
//                            String cmsUsersName, jenis, createdAt;
//                            Integer id, idModul, idData;
//                            id = list.get(i).getId();
//                            cmsUsersName = list.get(i).getCmsUsersName();
//                            idModul = list.get(i).getIdModul();
//                            idData = list.get(i).getIdData();
//                            jenis = list.get(i).getJenis();
//                            createdAt = list.get(i).getCreatedAt();
//
//                            listUserLogs.add(new ListUserLog(id, cmsUsersName, idModul, idData, jenis, createdAt));
//                        }
                        listUserLogs.addAll(list);

                        listUserLogAdapter.notifyDataSetChanged();
                        int index = listUserLogs.size();
                        offset = index;
                        progress.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<RespListUserLog> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });
    }

}
