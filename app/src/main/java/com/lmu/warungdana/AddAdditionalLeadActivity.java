package com.lmu.warungdana;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.lmu.warungdana.Response.ListUnitUfi;
import com.lmu.warungdana.Response.RespPost;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdditionalLeadActivity extends AppCompatActivity {
    private LinearLayout addPhone, addAddress, addNote, addUnit;
    private ApiEndPoint mApiService;
    private Context context;
    private android.support.v7.widget.Toolbar toolbar;
    private Button btnCheck,btnCheck2;
    private Integer idIndicator, idModul, idData, idUser, idUnitUfi;
    private EditText note, nopol;
    private RadioGroup rgPajak, rgPemilik;
    private RadioButton rbPajak, rbPemilik;
    private Spinner merk, year, model;
    private String strMerk, strYear;
    List<ListUnitUfi> listUnitUfis;
    private MaterialEditText phone;
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_additional_lead);
        context = this;
        mApiService = UtilsApi.getAPIService();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        sharedPrefManager = new SharedPrefManager(this);
        idUser = sharedPrefManager.getSpId();
        merk = findViewById(R.id.tvMerk);
        year = findViewById(R.id.tvYear);
        model = findViewById(R.id.tvModel);
        rgPajak = findViewById(R.id.rgPajak);
        rgPemilik = findViewById(R.id.rgOwner);
        nopol = findViewById(R.id.tvNopol);
        btnCheck = findViewById(R.id.btnCheck);
        btnCheck2 = findViewById(R.id.btnCheck2);
        addAddress = findViewById(R.id.addAddress);
        addPhone = findViewById(R.id.addPhone);
        addNote = findViewById(R.id.addNote);
        addUnit = findViewById(R.id.addUnit);
        addUnit.setVisibility(View.GONE);
        addAddress.setVisibility(View.GONE);
        addPhone.setVisibility(View.GONE);
        addNote.setVisibility(View.GONE);
        note = findViewById(R.id.etNote);
        phone = findViewById(R.id.tvMobileNumber);

        idIndicator = getIntent().getIntExtra("idIndicator", 0);
        idModul = getIntent().getIntExtra("idModul", 0);
        idData = getIntent().getIntExtra("idData", 0);

        getIndicator();
        if (idModul == 1) {
            fungsiLead();
        } else if (idModul == 2) {
            fungsiTarget();
        } else if (idModul == 3) {
            fungsiContact();
        } else if (idModul == 4) {
            fungsiDeal();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    private void fungsiContact() {
        if (idIndicator == 1) {
            final Editable isiNote = note.getText();
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Tambah Catatan ?");
                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mApiService.contactNoteCreate(idData, idUser, isiNote.toString()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    finish();
                                    Toast.makeText(context, "Berhasil Menambah Note!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });
                    alert.show();
                }
            });
            btnCheck2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Tambah Catatan ?");
                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mApiService.contactNoteCreate(idData, idUser, isiNote.toString()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    finish();
                                    Toast.makeText(context, "Berhasil Menambah Note!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });
                    alert.show();
                }
            });
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Batal tambah catatan ?");
                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });

                    alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });
                    alert.show();
                }
            });
        }
    }

    private void fungsiDeal() {

    }

    private void fungsiLead() {

        if (idIndicator == 1) {
            final Editable isiNote = note.getText();
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Tambah Catatan ?");
                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mApiService.leadNoteCreate(idData, idUser, isiNote.toString()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    finish();
                                    Toast.makeText(context, "Berhasil Menambah Note !", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();

                }
            });
            btnCheck2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Tambah Catatan ?");
                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mApiService.leadNoteCreate(idData, idUser, isiNote.toString()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    finish();
                                    Toast.makeText(context, "Berhasil Menambah Note !", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();

                }
            });
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Batal tambah catatan ?");
                    alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });

                    alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();
                }
            });
        } else if (idIndicator == 4) {
            /*strMerk = merk.getSelectedItem().toString();
            strYear = year.getSelectedItem().toString();
            merk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    strMerk = merk.getSelectedItem().toString();
                    strYear = year.getSelectedItem().toString();
                    initSpinnerModel();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    strMerk = merk.getSelectedItem().toString();
                    strYear = year.getSelectedItem().toString();
                    initSpinnerModel();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idPajak = rgPajak.getCheckedRadioButtonId();
                    int idPemilik = rgPemilik.getCheckedRadioButtonId();
                    rbPajak = findViewById(idPajak);
                    rbPemilik = findViewById(idPemilik);
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                    mApiService.leadProductCreate(idData, 4, idUser).enqueue(new Callback<RespPost>() {
                        @Override
                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                            if (response.isSuccessful()) {
                                Integer idhasil = response.body().getId();
                                String taxStatus;
                                if (rbPajak.getText().toString().equals("Hidup")) {
                                    taxStatus = "Y";
                                } else {
                                    taxStatus = "N";
                                }
                                mApiService.leadProductDetailCreate(idhasil, idUnitUfi, idUser, nopol.getText().toString(), taxStatus,
                                        rbPemilik.getText().toString()).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        loading.dismiss();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        loading.dismiss();
                                        Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespPost> call, Throwable t) {
                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            btnCheck2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idPajak = rgPajak.getCheckedRadioButtonId();
                    int idPemilik = rgPemilik.getCheckedRadioButtonId();
                    rbPajak = findViewById(idPajak);
                    rbPemilik = findViewById(idPemilik);
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                    mApiService.leadProductCreate(idData, 4, idUser).enqueue(new Callback<RespPost>() {
                        @Override
                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                            if (response.isSuccessful()) {
                                Integer idhasil = response.body().getId();
                                String taxStatus;
                                if (rbPajak.getText().toString().equals("Hidup")) {
                                    taxStatus = "Y";
                                } else {
                                    taxStatus = "N";
                                }
                                mApiService.leadProductDetailCreate(idhasil, idUnitUfi, idUser, nopol.getText().toString(), taxStatus,
                                        rbPemilik.getText().toString()).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        loading.dismiss();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        loading.dismiss();
                                        Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespPost> call, Throwable t) {
                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Cancel Add Data ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });
                    alert.show();
                }
            });
        }
    }

    private void fungsiTarget() {
        if (idIndicator == 1) {
            final Editable isiNote = note.getText();
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Add Note ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mApiService.targetNoteCreate(idData, idUser, isiNote.toString()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    finish();
                                    Toast.makeText(context, "Berhasil Menambah Note!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();

                }
            });
            btnCheck2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Add Note ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mApiService.targetNoteCreate(idData, 1, isiNote.toString()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    finish();
                                    Toast.makeText(context, "Berhasil Menambah Note!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();

                }
            });
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Cancel Add Note ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();
                }
            });
        }
    }

    private void getIndicator() {

        if (idIndicator == 1) {
            addNote.setVisibility(View.VISIBLE);
            toolbar.setTitle("Tambah Catatan");
        }
        if (idIndicator == 2) {
            addPhone.setVisibility(View.VISIBLE);
            toolbar.setTitle("Add Phone Number");
        }
        if (idIndicator == 3) {
            addAddress.setVisibility(View.VISIBLE);
            toolbar.setTitle("Add Address");
        }
        if (idIndicator == 4) {
            addUnit.setVisibility(View.VISIBLE);
            toolbar.setTitle("Tambah Unit");
        }
    }

}
