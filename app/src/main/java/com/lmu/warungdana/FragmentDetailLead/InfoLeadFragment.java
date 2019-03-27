package com.lmu.warungdana.FragmentDetailLead;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListAddressAdapter;
import com.lmu.warungdana.Adapter.ListPhoneAdapter;
import com.lmu.warungdana.Adapter.ListUnitAdapter;
import com.lmu.warungdana.AddContactActivity;
import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.DetailLeadActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.CheckContact;
import com.lmu.warungdana.Response.CheckNIK;
import com.lmu.warungdana.Response.DetailLead;
import com.lmu.warungdana.Response.ListAddress;
import com.lmu.warungdana.Response.ListPhone;
import com.lmu.warungdana.Response.ListUnit;
import com.lmu.warungdana.Response.RespListAddress;
import com.lmu.warungdana.Response.RespListPhone;
import com.lmu.warungdana.Response.RespListUnit;
import com.lmu.warungdana.Response.RespPost;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoLeadFragment extends Fragment {
    private ApiEndPoint mApiService;
    private Integer idLead;
    private TextView status, job, source, owner, convert;
    private RecyclerView rvPhone, rvAddress, rvUnitUfi;
    Context context;
    private String firstName, lastName, mainPhone, mobilePhone, pekerjaan, sumber,
            addressCat, address, prov, kab, kec, kel;
    private Integer idJob, idCatAddress, idAlamat, idUser;
    LinearLayout llJob;
    SharedPrefManager sharedPrefManager;

    public InfoLeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        DetailLeadActivity activity = (DetailLeadActivity) context;
        idLead = activity.idLead;
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_info_lead, container, false);
        rvPhone = view.findViewById(R.id.rvPhone);
        rvAddress = view.findViewById(R.id.rvAddress);
        mApiService = UtilsApi.getAPIService();
        status = view.findViewById(R.id.tvStatus);
        job = view.findViewById(R.id.tvJob);
        source = view.findViewById(R.id.tvSource);
        owner = view.findViewById(R.id.tvOwner);
        convert = view.findViewById(R.id.convert);
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        rvUnitUfi = view.findViewById(R.id.rvUnitUfi);
        llJob = view.findViewById(R.id.llJob);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mApiService.leadDetail(idLead).enqueue(new Callback<DetailLead>() {
            @Override
            public void onResponse(Call<DetailLead> call, Response<DetailLead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        status.setText(response.body().getLeadMstStatusStatus());

                        if (response.body().getMstJobJob() != null) {
                            job.setText(response.body().getMstJobJob());
                        } else {
                            llJob.setVisibility(View.GONE);
                        }

                        source.setText(response.body().getMstDataSourceDatasource());
                        owner.setText(response.body().getCmsUsersName());

                        firstName = response.body().getFirstName();
                        lastName = response.body().getLastName();
                        if (response.body().getIdMstJob() != null) {
                            idJob = response.body().getIdMstJob();
                        }
                        if (response.body().getIdMstDataSource() != null) {
                            sumber = response.body().getMstDataSourceDatasource();
                        }
                        pekerjaan = response.body().getMstJobJob();

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailLead> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        mApiService.listPhoneLead(idLead).enqueue(new Callback<RespListPhone>() {
            @Override
            public void onResponse(Call<RespListPhone> call, Response<RespListPhone> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rvPhone.setLayoutManager(layoutManager);
                    List<ListPhone> listLeads = response.body().getData();
                    rvPhone.setAdapter(new ListPhoneAdapter(getContext(), listLeads));
                    if (!listLeads.isEmpty()) {
                        mainPhone = response.body().getData().get(0).getNumber();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespListPhone> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.listUnit(idLead).enqueue(new Callback<RespListUnit>() {
            @Override
            public void onResponse(Call<RespListUnit> call, Response<RespListUnit> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rvUnitUfi.setLayoutManager(layoutManager);
                    List<ListUnit> listLeads = response.body().getData();
                    rvUnitUfi.setAdapter(new ListUnitAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListUnit> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.listAddressLead(idLead).enqueue(new Callback<RespListAddress>() {
            @Override
            public void onResponse(Call<RespListAddress> call, Response<RespListAddress> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
                    rvAddress.setLayoutManager(layoutManager1);
                    List<ListAddress> listAddresses = response.body().getData();
                    rvAddress.setAdapter(new ListAddressAdapter(getContext(), listAddresses));
                    if (listAddresses != null && !listAddresses.isEmpty()) {
                        idAlamat = response.body().getData().get(0).getIdMstAddress();
                        addressCat = response.body().getData().get(0).getMstCategoryAddressCategory();
                        address = response.body().getData().get(0).getAddress();
                        prov = response.body().getData().get(0).getMstAddressProvinsi();
                        kab = response.body().getData().get(0).getMstAddressKabupaten();
                        kec = response.body().getData().get(0).getMstAddressKecamatan();
                        kel = response.body().getData().get(0).getMstAddressKelurahan();

                    }
                }
            }

            @Override
            public void onFailure(Call<RespListAddress> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View view = inflater.inflate(R.layout.dialog_add_contact, null);
                final MaterialEditText nik = view.findViewById(R.id.NIK);
                alert.setTitle("Add New Contact");
                alert.setView(view);
                alert.setPositiveButton("Check", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (nik.length() < 16) {
                            Toast.makeText(context, "NIK kurang dari 16 Angka!", Toast.LENGTH_LONG).show();
                        } else {
                            mApiService.checkNIK(nik.getText().toString()).enqueue(new Callback<CheckNIK>() {
                                @Override
                                public void onResponse(Call<CheckNIK> call, Response<CheckNIK> response) {
                                    if (response.body().getApiStatus() == 0) {

                                        Intent intent = new Intent(context, AddContactActivity.class);
                                        intent.putExtra("nik", nik.getText().toString());
                                        intent.putExtra("firstName", firstName);
                                        intent.putExtra("lastName", lastName);
                                        intent.putExtra("mainPhone", mainPhone);
                                        intent.putExtra("idJob", pekerjaan);
                                        if (address != null) {
                                            intent.putExtra("idMstAddress", idAlamat);
                                            intent.putExtra("idAddressCat", addressCat);
                                            intent.putExtra("alamat", address);
                                            intent.putExtra("provinsi", prov);
                                            intent.putExtra("kabupaten", kab);
                                            intent.putExtra("kecamatan", kec);
                                            intent.putExtra("kelurahan", kel);
                                        }
                                        intent.putExtra("idSource", sumber);
                                        intent.putExtra("convertSource", 1);
                                        intent.putExtra("idConvert", idLead);
                                        startActivity(intent);

                                    } else {
                                        final Integer idContact = response.body().getId();
                                        mApiService.CheckContact(idContact, sharedPrefManager.getSpId()).enqueue(new Callback<CheckContact>() {
                                            @Override
                                            public void onResponse(Call<CheckContact> call, Response<CheckContact> response) {
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
                                                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
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
                                            }

                                            @Override
                                            public void onFailure(Call<CheckContact> call, Throwable t) {
                                                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckNIK> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });

    }

}
