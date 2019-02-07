package com.lmu.warungdana.BottomSheet;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lmu.warungdana.AddContactActivity;
import com.lmu.warungdana.AddLeadActivity;
import com.lmu.warungdana.AddOrderActivity;
import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.CheckContact;
import com.lmu.warungdana.Response.CheckNIK;
import com.lmu.warungdana.Response.CheckNewDB;
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
public class BottomSheetHome extends BottomSheetDialogFragment {
    private LinearLayout bsLead, bsContact, bsDeal;
    Context context;
    private ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;


    public BottomSheetHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_home, container, false);
        bsLead = view.findViewById(R.id.bsLead);
        bsContact = view.findViewById(R.id.bsContact);
        bsDeal = view.findViewById(R.id.bsDeal);
        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);
        mApiService = UtilsApi.getAPIService();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bsLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_add_lead, null);
                final MaterialEditText nohp = view.findViewById(R.id.phoneNumber);

                alert.setTitle("Tambah Lead Baru");
                alert.setView(view);
                alert.setPositiveButton("Check", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String nope;
                        if (nohp.length() < 10) {
                            Toast.makeText(context, "Nomor HP kurang dari 10 Angka!", Toast.LENGTH_LONG).show();
                        } else {
                            nope = nohp.getText().toString();
                            mApiService.checkNewDB(nope).enqueue(new Callback<CheckNewDB>() {
                                @Override
                                public void onResponse(Call<CheckNewDB> call, Response<CheckNewDB> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getApiStatus() == 0) {
                                            Intent intent = new Intent(context, AddLeadActivity.class);
                                            intent.putExtra("noHP", nohp.getText().toString());
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(context, "Data Sudah Ada!", Toast.LENGTH_LONG).show();
                                            BottomSheetLeadAvail bottomSheet = new BottomSheetLeadAvail();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("idLead", response.body().getIdLead());
                                            bottomSheet.setArguments(bundle);
                                            bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
                                            dismiss();
                                        }
                                    } else {
                                        Toast.makeText(context, "Not Responding", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckNewDB> call, Throwable t) {
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

        bsContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_add_contact, null);
                final MaterialEditText nik = view.findViewById(R.id.NIK);
                alert.setTitle("Tambah Contact Baru");
                alert.setView(view);

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

        bsDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddOrderActivity.class));
            }
        });
    }

}
