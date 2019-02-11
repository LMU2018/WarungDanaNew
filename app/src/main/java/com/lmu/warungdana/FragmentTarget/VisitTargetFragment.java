package com.lmu.warungdana.FragmentTarget;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lmu.warungdana.Adapter.ListTargetVisitAdapter;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListTargetVisit;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.Response.RespListLead;
import com.lmu.warungdana.Response.RespListTarget;
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
public class VisitTargetFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    Integer idUser;
    private Context context;
    ArrayList<ListTargetVisit> listTargets;
    ListTargetVisitAdapter listTargetAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = false;
    LinearLayout iconKosong;
    ArrayList<Integer> saveFirst;
    List<ListTargetVisit> list = null;
    int index = 0;

    public VisitTargetFragment() {
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
        listTargets = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        iconKosong = view.findViewById(R.id.iconKosong);

        listTargetAdapter = new ListTargetVisitAdapter(getContext(), listTargets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listTargetAdapter);

        progress.setVisibility(View.VISIBLE);
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

//                        if (itShouldLoadMore) {
//                            progress.setVisibility(View.VISIBLE);
//                            loadMore();
////                            if ((listTargetAdapter.num)*20 < listTargets.size()){
////                                listTargetAdapter.num = listTargetAdapter.num + 1;
////                                listTargetAdapter.notifyDataSetChanged();
////                            }
//
//                            progress.setVisibility(View.INVISIBLE);
//                        }
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

//        mApiService.listTarget(idUser, 4).enqueue(new Callback<RespListTarget>() {
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


        if (listTargets.size() >= 1){

            recyclerView.setAdapter(null);
        }else{

            recyclerView.setAdapter(listTargetAdapter);

        }
        saveFirst.clear();
        itShouldLoadMore = false;
       mApiService.listVisitTarget(idUser,limit,0).enqueue(new Callback<RespListTargetVisit>() {
            @Override
            public void onResponse(Call<RespListTargetVisit> call, Response<RespListTargetVisit> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {
                        recyclerView.setAdapter(listTargetAdapter);

                        List<ListTargetVisit> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String created_at, category, firstName, lastName, recall, description, status, revisit, visitStatus = "",created_at_target_visum,cms_username,cmsNpm,created_at_target_log,targetMastStatus;
                            Integer id, idTargetMstStatus, idMstLogDesc, idMstLogStatus, idMstVisumStatus,updated_by;
                            category = list.get(i).getCategory();
                            firstName = list.get(i).getFirstName();
                            lastName = list.get(i).getLastName();
                            recall = list.get(i).getRecall();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();
                            revisit = list.get(i).getRevisit();
                            visitStatus = list.get(i).getVisitStatus();
                            updated_by = list.get(i).getUpdatedBy();
                            cms_username = list.get(i).getCmsUsersName();
                            cmsNpm = list.get(i).getCmsUsersNpm();
                            created_at_target_log = list.get(i).getCreatedAtTargetLog();
//                            Log.d("Visit Status",visitStatus);
                            id = list.get(i).getId();
                            idTargetMstStatus = list.get(i).getIdTargetMstStatus();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            idMstVisumStatus = list.get(i).getIdMstVisumStatus();
                            created_at_target_visum = list.get(i).getCreatedAt();
                            targetMastStatus = list.get(i).getTargetMstStatusStatus();
                            created_at = list.get(i).getCreatedAt();


//                            if (visitStatus != null){
//
//
//
//                            }else{
//
//                            }

                            saveFirst.add(id);

                            listTargets.add(new ListTargetVisit(id,idTargetMstStatus,targetMastStatus,category,firstName,lastName,cmsNpm,cms_username,updated_by,created_at,recall,idMstLogDesc,idMstLogStatus,
                                    description,status,idMstVisumStatus,revisit,visitStatus,created_at_target_visum,created_at_target_log));





                        }

//                        Collections.sort(listTargets,Collections.reverseOrder(VisitTargetFragment.this));
                        listTargetAdapter.notifyDataSetChanged();

                        if (listTargets.size() >= 1) {
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
            public void onFailure(Call<RespListTargetVisit> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });

    }

    private void loadMore() {
        itShouldLoadMore = false;
        mApiService.listVisitTarget(idUser, limit,offset).enqueue(new Callback<RespListTargetVisit>() {
            @Override
            public void onResponse(Call<RespListTargetVisit> call, Response<RespListTargetVisit> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                       list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {

//                            String category, firstName, lastName, recall, description, status, revisit, visitStatus = "",created_at_target_visum;
//                            Integer id, idTargetMstStatus, idMstLogDesc, idMstLogStatus, idMstVisumStatus;
//                            category = list.get(i).getCategory();
//                            firstName = list.get(i).getFirstName();
//                            lastName = list.get(i).getLastName();
//                            recall = list.get(i).getRecall();
//                            description = list.get(i).getDescription();
//                            status = list.get(i).getStatus();
//                            revisit = list.get(i).getRevisit();
//                            visitStatus = list.get(i).getVisitStatus();
//
////                            Log.d("Visit Status",visitStatus);
//                            id = list.get(i).getId();
//                            idTargetMstStatus = list.get(i).getIdTargetMstStatus();
//                            idMstLogDesc = list.get(i).getIdMstLogDesc();
//                            idMstLogStatus = list.get(i).getIdMstLogStatus();
//                            idMstVisumStatus = list.get(i).getIdMstVisumStatus();
//                            created_at_target_visum = list.get(i).getCreated_at_target_visum();
//
//
////                            if (visitStatus != null){
////
////                                saveFirst.add(id);
////
////                                listTargets.add(new ListTargetVisit(id, idTargetMstStatus, category, firstName, lastName, recall, idMstLogDesc,
////                                        idMstLogStatus, description, status, idMstVisumStatus, revisit, visitStatus,created_at_target_visum));
////
////                            }else{
////
////                            }
//
//
//                            if (visitStatus != null){
//
//                                listTargets.add(new ListTargetVisit(id, idTargetMstStatus, category, firstName, lastName, recall, idMstLogDesc,
//                                        idMstLogStatus, description, status, idMstVisumStatus, revisit, visitStatus,created_at_target_visum));
//
////                                boolean cek = false;
////
////                                for (int a = 0; a < saveFirst.size(); a++){
////
////                                    if (saveFirst.get(a) == id){
////
////                                        cek = true;
////
////                                        break;
////                                    }
////                                }
////
////                                if (!cek){
////
////                                    saveFirst.add(id);
////
//////                                    listTargets.add(new ListTarget(id, idTargetMstStatus, category, firstName, lastName, recall, idMstLogDesc,
//////                                            idMstLogStatus, description, status, idMstVisumStatus, revisit, visitStatus));
//
////
////
////                                }
//
//
//                            }else{
//
//                            }

//                            String category, firstName, lastName, recall, description, status, revisit, visitStatus = "",created_at_target_visum,cms_username,cmsNpm,created_at_target_log;
//                            Integer id, idTargetMstStatus, idMstLogDesc, idMstLogStatus, idMstVisumStatus,updated_by;
//                            category = list.get(i).getTargetCategory();
//                            firstName = list.get(i).getTargetFirstName();
//                            lastName = list.get(i).getTargetLastName();
//                            recall = list.get(i).getRecall();
//                            description = list.get(i).getDescription();
//                            status = list.get(i).getStatus();
//                            revisit = list.get(i).getRevisit();
//                            visitStatus = list.get(i).getVisitStatus();
//                            updated_by = list.get(i).getTargetUpdatedBy();
//                            cms_username = list.get(i).getCmsUsersName();
//                            cmsNpm = list.get(i).getCmsUsersNpm();
//                            created_at_target_log = list.get(i).getCreatedAtTargetLog();
////                            Log.d("Visit Status",visitStatus);
//                            id = list.get(i).getIdTarget();
//                            idTargetMstStatus = list.get(i).getIdTargetMstStatus();
//                            idMstLogDesc = list.get(i).getIdMstLogDesc();
//                            idMstLogStatus = list.get(i).getIdMstLogStatus();
//                            idMstVisumStatus = list.get(i).getIdMstVisumStatus();
//                            created_at_target_visum = list.get(i).getCreatedAt();
//
//
//                            if (visitStatus != null){
//
//                                saveFirst.add(id);
//
//                                listTargets.add(new ListTargetVisit(id,category,firstName,lastName,updated_by,idMstVisumStatus,revisit,cms_username,cmsNpm,idMstLogDesc,
//                                        recall,idMstLogStatus,description,status,visitStatus,created_at_target_visum,created_at_target_log,idTargetMstStatus));
//
//                            }else{
//
//                            }

                            String created_at, category, firstName, lastName, recall, description, status, revisit, visitStatus = "",created_at_target_visum,cms_username,cmsNpm,created_at_target_log,targetMastStatus;
                            Integer id, idTargetMstStatus, idMstLogDesc, idMstLogStatus, idMstVisumStatus,updated_by;
                            category = list.get(i).getCategory();
                            firstName = list.get(i).getFirstName();
                            lastName = list.get(i).getLastName();
                            recall = list.get(i).getRecall();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();
                            revisit = list.get(i).getRevisit();
                            visitStatus = list.get(i).getVisitStatus();
                            updated_by = list.get(i).getUpdatedBy();
                            cms_username = list.get(i).getCmsUsersName();
                            cmsNpm = list.get(i).getCmsUsersNpm();
                            created_at_target_log = list.get(i).getCreatedAtTargetLog();
//                            Log.d("Visit Status",visitStatus);
                            id = list.get(i).getId();
                            idTargetMstStatus = list.get(i).getIdTargetMstStatus();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            idMstVisumStatus = list.get(i).getIdMstVisumStatus();
                            created_at_target_visum = list.get(i).getCreatedAt();
                            targetMastStatus = list.get(i).getTargetMstStatusStatus();
                            created_at = list.get(i).getCreatedAt();


//                            if (visitStatus != null){
//
//
//
//                            }else{
//
//                            }

                            saveFirst.add(id);

                            listTargets.add(new ListTargetVisit(id,idTargetMstStatus,targetMastStatus,category,firstName,lastName,cmsNpm,cms_username,updated_by,created_at,recall,idMstLogDesc,idMstLogStatus,
                                    description,status,idMstVisumStatus,revisit,visitStatus,created_at_target_visum,created_at_target_log));

                        }
                        listTargetAdapter.notifyDataSetChanged();

                        index = index +list.size();
//                        Log.d(TAG, "indexOff: " + index);
                        offset = index;
//                        Log.d(TAG, "offset: " + offset);
                        progress.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(Call<RespListTargetVisit> call, Throwable t) {
                itShouldLoadMore = true;
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
//        firstLoad();
    }

//    @Override
//    public int compare(ListTargetVisit listTarget, ListTargetVisit t1) {
//        return listTarget.getCreated_at_target_visum().compareTo(t1.getCreated_at_target_visum());
//    }
}
