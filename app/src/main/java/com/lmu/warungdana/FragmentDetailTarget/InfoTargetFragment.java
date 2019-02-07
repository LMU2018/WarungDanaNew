package com.lmu.warungdana.FragmentDetailTarget;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.AddContactActivity;
import com.lmu.warungdana.AddTargetLogActivity;
import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.DetailTargetActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.CheckContact;
import com.lmu.warungdana.Response.CheckNIK;
import com.lmu.warungdana.Response.DetailTarget;
import com.lmu.warungdana.Response.RespPost;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoTargetFragment extends Fragment {
    private ApiEndPoint mApiService;
    private Integer idTarget, idUser;
    private TextView status, job, source, owner, category, priority, contract, nopol, nama, prov1, prov2, address, convert, direction;
    private RelativeLayout phone, phone2, layoutAddress;
    Context context;
    private String firstName, lastName, mainPhone, jalan;
    SharedPrefManager sharedPrefManager;


    public InfoTargetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_target, container, false);
        DetailTargetActivity activity = (DetailTargetActivity) getActivity();
        context = getContext();
        idTarget = activity.idTarget;
        status = view.findViewById(R.id.tvStatus);
        prov1 = view.findViewById(R.id.tvNoHP1);
        prov2 = view.findViewById(R.id.tvNoHP2);
        category = view.findViewById(R.id.tvCategory);
        priority = view.findViewById(R.id.tvPriority);
        job = view.findViewById(R.id.tvJob);
        job = view.findViewById(R.id.tvJob);
        address = view.findViewById(R.id.tvItemAddress);
        contract = view.findViewById(R.id.tvContract);
        nopol = view.findViewById(R.id.tvNopol);
        source = view.findViewById(R.id.tvSource);
        owner = view.findViewById(R.id.tvOwner);
        mApiService = UtilsApi.getAPIService();
        phone2 = view.findViewById(R.id.layoutPhone2);
        phone = view.findViewById(R.id.layoutPhone);
        convert = view.findViewById(R.id.convert);
        direction = view.findViewById(R.id.tvDirection);
        layoutAddress = view.findViewById(R.id.layoutAddress);
        sharedPrefManager = new SharedPrefManager(getContext());
        idUser = sharedPrefManager.getSpId();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.targetDetail(idTarget).enqueue(new Callback<DetailTarget>() {
            @Override
            public void onResponse(Call<DetailTarget> call, final Response<DetailTarget> response) {
                if (response.isSuccessful()) {

                    mainPhone = response.body().getHp1();
                    firstName = response.body().getFirstName();
                    lastName = response.body().getLastName();
                    jalan = response.body().getAddress();


                    if (response.body().getTargetMstStatusStatus() != null) {
                        status.setText(response.body().getTargetMstStatusStatus());
                    } else {
                        status.setText("Empty");
                    }

                    if (response.body().getProvider1() != null) {
                        prov1.setText(response.body().getProvider1());
                    } else {
                        phone.setVisibility(View.GONE);
                    }

                    if (response.body().getProvider2() != null) {
                        prov2.setText(response.body().getProvider2());
                    } else {
                        phone2.setVisibility(View.GONE);
                    }

                    if (response.body().getCategory() != null) {
                        category.setText(response.body().getCategory());
                    } else {
                        category.setText("Empty");
                    }

                    if (response.body().getPriority() != null) {
                        priority.setText(response.body().getPriority());
                    } else {
                        priority.setText("Empty");
                    }

                    if (response.body().getJob() != null) {
                        job.setText(response.body().getJob());
                    } else {
                        job.setText("Empty");
                    }

                    if (response.body().getAddress() != null) {
                        address.setText(response.body().getAddress());
                    } else {
                        layoutAddress.setVisibility(View.GONE);
                    }

                    if (response.body().getNoContract() != null) {
                        contract.setText(response.body().getNoContract());
                    } else {
                        contract.setText("Empty");
                    }

                    if (response.body().getNopol() != null) {
                        nopol.setText(response.body().getNopol());
                    } else {
                        nopol.setText("Empty");
                    }

                    if (response.body().getMstDataSourceDatasource() != null) {
                        source.setText(response.body().getMstDataSourceDatasource());
                    } else {
                        source.setText("Empty");
                    }

                    if (response.body().getCmsUsersName() != null) {
                        owner.setText(response.body().getCmsUsersName());
                    } else {
                        owner.setText("Empty");
                    }

                    phone2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle(response.body().getProvider2());
                            String[] animals = {"Call with phone"};
                            builder.setItems(animals, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:

                                            Intent intent = new Intent(context, AddTargetLogActivity.class);
                                            intent.putExtra("idData", response.body().getId());
                                            intent.putExtra("idSource", 2);
                                            context.startActivity(intent);

                                            Intent caa = new Intent(Intent.ACTION_CALL);
                                            String telp = "tel:" + response.body().getHp2();
                                            caa.setData(Uri.parse(telp));
                                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                return;
                                            }
                                            context.startActivity(caa);
                                    }
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });

                    phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle(response.body().getProvider1());
                            String[] animals = {"Call with phone"};
                            builder.setItems(animals, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:

                                            Intent intent = new Intent(context, AddTargetLogActivity.class);
                                            intent.putExtra("idData", response.body().getId());
                                            intent.putExtra("idSource", 2);
                                            context.startActivity(intent);

                                            Intent caa = new Intent(Intent.ACTION_CALL);
                                            String telp = "tel:" + response.body().getHp1();
                                            caa.setData(Uri.parse(telp));
                                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                return;
                                            }
                                            context.startActivity(caa);
                                    }
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });

                    direction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri mapsNav = Uri.parse("google.navigation:q=" + response.body().getAddress() + ", " + response.body().getKabupaten() + "&avoid=t");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapsNav);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            context.startActivity(mapIntent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<DetailTarget> call, Throwable t) {
                Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_add_contact, null);
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
                                        intent.putExtra("jalan", jalan);
                                        intent.putExtra("mainPhone", mainPhone);
                                        intent.putExtra("idJob", job.getText().toString());
                                        intent.putExtra("idSource", source.getText().toString());
                                        intent.putExtra("convertSource", 2);
                                        intent.putExtra("idConvert", idTarget);
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
                                                                    Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckNIK> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
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
