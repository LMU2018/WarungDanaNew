package com.lmu.warungdana;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListContactOrderAdapter;
import com.lmu.warungdana.Response.CheckContact;
import com.lmu.warungdana.Response.CheckNIK;
import com.lmu.warungdana.Response.ListContact;
import com.lmu.warungdana.Response.RespListContact;
import com.lmu.warungdana.Response.RespPost;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ApiEndPoint mApiService;
    private Context context;
    Integer idUser;
    SharedPrefManager sharedPrefManager;

    ArrayList<ListContact> listContactOrders;
    ListContactOrderAdapter listContactOrderAdapter;
    ProgressBar progress;
    private Integer offset = 15, limit;
    private boolean itShouldLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Contact");
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        context = this;
        limit = Integer.parseInt(getResources().getString(R.string.limit));
        listContactOrders = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(context);
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        progress = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

       /* progress.setVisibility(View.VISIBLE);
        firstLoad();*/

        listContactOrderAdapter = new ListContactOrderAdapter(context, listContactOrders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listContactOrderAdapter);

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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View gas = inflater.inflate(R.layout.dialog_add_contact, null);
                final MaterialEditText nik = gas.findViewById(R.id.NIK);
                alert.setTitle("Tambah Contact Baru");
                alert.setView(gas);

                alert.setPositiveButton("Check", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (nik.length() < 16) {
                            Toast.makeText(context, "NIK kurang dari 16 Angka!", Toast.LENGTH_LONG).show();
                        } else {
                            mApiService.checkNIK(nik.getText().toString()).enqueue(new Callback<CheckNIK>() {
                                @Override
                                public void onResponse(Call<CheckNIK> call, Response<CheckNIK> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getApiStatus() == 0) {
                                            Intent intent = new Intent(context, AddContactActivity.class);
                                            intent.putExtra("nik", nik.getText().toString());
                                            startActivity(intent);
                                        } else {
                                            final Integer idContact = response.body().getId();
                                            mApiService.CheckContact(idContact, sharedPrefManager.getSpId()).enqueue(new Callback<CheckContact>() {
                                                @Override
                                                public void onResponse(Call<CheckContact> call, Response<CheckContact> response) {
                                                    if (response.isSuccessful()) {
                                                        if (response.body().getApiStatus() == 0) {
                                                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                                            alert.setMessage("Contact sudah ada. Apakah and ingin menambahkan ke Contact yang sama?");
                                                            alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    mApiService.contactCollabCreate(sharedPrefManager.getSpId(), idContact).enqueue(new Callback<RespPost>() {
                                                                        @Override
                                                                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                                                                            Intent intent = new Intent(context, DetailContactActivity.class);
                                                                            intent.putExtra("idContact", idContact);
                                                                            context.startActivity(intent);
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<RespPost> call, Throwable t) {
                                                                            Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                            alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            });
                                                            alert.show();
                                                        } else {
                                                            Toast.makeText(context, "Data Contact sudah ada !", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(context, DetailContactActivity.class);
                                                            intent.putExtra("idContact", idContact);
                                                            context.startActivity(intent);
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<CheckContact> call, Throwable t) {
                                                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    } else {
                                        Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckNIK> call, Throwable t) {
                                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }
                });

                alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        listContactOrders.clear();
        progress.setVisibility(View.VISIBLE);
        firstLoad();
/*
        mApiService.listContact(idUser).enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(layoutManager);
                        List<ListContact> listLeads = response.body().getData();
                        recyclerView.setAdapter(new ListContactOrderAdapter(context, listLeads));
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListContact> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    private void firstLoad() {
        itShouldLoadMore = false;
        mApiService.listContactPagg(idUser, limit, 0).enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListContact> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String contactFirstName, contactLastName, contactGender, status;
                            Integer id, idContact, idOrderMstStatus;
                            id = list.get(i).getId();
                            idContact = list.get(i).getIdContact();
                            contactFirstName = list.get(i).getContactFirstName();
                            contactLastName = list.get(i).getContactLastName();
                            contactGender = list.get(i).getContactGender();
                            idOrderMstStatus = list.get(i).getIdOrderMstStatus();
                            status = list.get(i).getStatus();

                            listContactOrders.add(new ListContact(id, idContact, contactFirstName, contactLastName, contactGender, idOrderMstStatus, status));
                        }
                        listContactOrderAdapter.notifyDataSetChanged();
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
        mApiService.listContactPagg(idUser, limit, offset).enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    itShouldLoadMore = true;
                    if (response.body().getData() != null) {
                        List<ListContact> list = response.body().getData();
                        for (int i = 0; i < list.size(); i++) {
                            String contactFirstName, contactLastName, contactGender, status;
                            Integer id, idContact, idOrderMstStatus;
                            id = list.get(i).getId();
                            idContact = list.get(i).getIdContact();
                            contactFirstName = list.get(i).getContactFirstName();
                            contactLastName = list.get(i).getContactLastName();
                            contactGender = list.get(i).getContactGender();
                            idOrderMstStatus = list.get(i).getIdOrderMstStatus();
                            status = list.get(i).getStatus();

                            listContactOrders.add(new ListContact(id, idContact, contactFirstName, contactLastName, contactGender, idOrderMstStatus, status));
                        }
                        listContactOrderAdapter.notifyDataSetChanged();
                        int index = listContactOrders.size();
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
