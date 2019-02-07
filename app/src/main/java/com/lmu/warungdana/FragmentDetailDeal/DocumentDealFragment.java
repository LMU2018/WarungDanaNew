package com.lmu.warungdana.FragmentDetailDeal;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lmu.warungdana.Adapter.ListOrderDocumentAdapter;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.DetailDealActivity;
import com.lmu.warungdana.DocumentActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListOrderDocument;
import com.lmu.warungdana.Response.RespOrderDocument;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentDealFragment extends Fragment implements ListOrderDocumentAdapter.OnReloadListener {

    FloatingActionButton btnTambahFoto;
    int idOrder,id_cms_users;
    SharedPrefManager sharedPreferences;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ApiEndPoint mApiService;
    List<ListOrderDocument> listOrderDocumentList;
    ListOrderDocumentAdapter listOrderDocumentAdapter;
    LinearLayout iconKosong;
    ProgressBar progressBar;


    public DocumentDealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_document_deal, container, false);

        DetailDealActivity activity = (DetailDealActivity) getActivity();
        mApiService = UtilsApi.getAPIService();

        sharedPreferences = new SharedPrefManager(getActivity());

        idOrder = activity.idOrder;
        Log.d("idOrder",""+idOrder);
        id_cms_users = sharedPreferences.getSpId();


        btnTambahFoto = view.findViewById(R.id.btnTambahFoto);
        recyclerView = view.findViewById(R.id.recyclerView);
        iconKosong = view.findViewById(R.id.iconKosong);
        progressBar = view.findViewById(R.id.progressBar);


        getOrderDocList();

        btnTambahFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent uploadFoto = new Intent(getActivity(), DocumentActivity.class);
                uploadFoto.putExtra("idOrder",idOrder);
                uploadFoto.putExtra("id_cms_users",id_cms_users);
                uploadFoto.putExtra("mode","tambah");
                startActivityForResult(uploadFoto,2);
            }
        });
        return view;
    }

    public void getOrderDocList() {

        iconKosong.setVisibility(LinearLayout.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(null);

        mApiService.orderPhotoListing(idOrder).enqueue(new Callback<RespOrderDocument>() {
            @Override
            public void onResponse(Call<RespOrderDocument> call, Response<RespOrderDocument> response) {

                if (response.isSuccessful()){




                    if (response.body() != null){

                        listOrderDocumentList = response.body().getData();

                        layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);


                        listOrderDocumentAdapter = new ListOrderDocumentAdapter(listOrderDocumentList,getActivity());
                        recyclerView.setAdapter(listOrderDocumentAdapter);

                        setCallBack();

                        if (listOrderDocumentList.size() >=1 ){

                            iconKosong.setVisibility(LinearLayout.INVISIBLE);

                        }else{

                            iconKosong.setVisibility(LinearLayout.VISIBLE);
                        }


                    }
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RespOrderDocument> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void setCallBack() {

        listOrderDocumentAdapter.setOnReloadListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2){

            getOrderDocList();
        }
    }

    @Override
    public void OnReload() {

        getOrderDocList();
    }
}
