package com.lmu.warungdana;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Response.ListAlamat;
import com.lmu.warungdana.Response.ListCategoryAddress;
import com.lmu.warungdana.Response.ListDataSource;
import com.lmu.warungdana.Response.ListJob;
import com.lmu.warungdana.Response.ListUnitUfi;
import com.lmu.warungdana.Response.RespListCategoryAddress;
import com.lmu.warungdana.Response.RespListDataSource;
import com.lmu.warungdana.Response.RespListJob;
import com.lmu.warungdana.Response.RespListUnitList;
import com.lmu.warungdana.Response.RespListUnitUfi;
import com.lmu.warungdana.Response.RespPost;
import com.lmu.warungdana.SQLite.DatabaseHelper;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLeadActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner job, source, model, status, kab, kec, kel, year, prov, addressCat, merk;
    private ApiEndPoint mApiService;
    private TextView postalcode, tvnohp, tvOwner;
    List<ListCategoryAddress> listCategoryAddress;
    List<ListAlamat> listAlamats;
    List<ListJob> listJobs;
    List<ListDataSource> listDataSources;
    List<ListUnitUfi> listUnitUfis;
    List<ListUnitUfi> listUnitUfiYear;
    Context context;
    LinearLayout detailUFI, detailAddress;
    String strMerk, strYear, nohp, strProv, strKab, strKec,
            strFirstName, strLastName, strSource, strJob,
            strMainPhone, strStatus,
            strAddressCat, strAddress, strProvIntent, strKabIntent, strKecIntent, strKelIntent,
            strMerkIntent, strModelIntent, strNopol, strPajak, strOwner;
    Integer idUnitUfi, idProduct, tahunKend, idAddress, idUnitIntent;
    private RadioGroup rgPajak, rgPemilik;
    private RadioButton rbPajak, rbPemilik, rbPajakY, rbPajakN, rbOwnerS, rbOwnerOL;
    private EditText firstName, lastName, address, nopol;
    private Button btnCheck,btnCheck2;
    ProgressDialog loading;
    private Switch switchAddress, switchProduct;
    SharedPrefManager sharedPrefManager;
    DatabaseHelper databaseHelper;
    private Integer idUser, idAlamat, idAlamatKat, idOutlet, idJob,
            idDataSource, idLead, idStatus, idLeadIntent, idBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_add_lead);
        databaseHelper = new DatabaseHelper(this);

        idLeadIntent = getIntent().getIntExtra("idLead", 0);
        toolbar = findViewById(R.id.toolbar);
        if (idLeadIntent != 0) {
            toolbar.setTitle("Edit Lead");
        } else {
            toolbar.setTitle("Tambah Lead");
        }
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                if (idLeadIntent != 0) {
                    alert.setTitle("Batal Edit Lead ?");
                } else {
                    alert.setTitle("Batal Menambah Lead ?");
                }
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        AddLeadActivity.super.onBackPressed();
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

        mApiService = UtilsApi.getAPIService();
        idProduct = null;
        idJob = null;
        idDataSource = null;
        idAlamat = null;
        idAlamatKat = null;
        idUnitUfi = null;

        sharedPrefManager = new SharedPrefManager(this);
        idUser = sharedPrefManager.getSpId();
        idOutlet = sharedPrefManager.getSpOutletId();
        idBranch = sharedPrefManager.getSpBranchId();

        btnCheck = findViewById(R.id.btnCheck);
        btnCheck2 = findViewById(R.id.btnCheck2);

        firstName = findViewById(R.id.tvFirstName);
        lastName = findViewById(R.id.tvLastName);
        job = findViewById(R.id.tvJob);
        source = findViewById(R.id.tvSource);

        tvnohp = findViewById(R.id.tvMainPhone);
        nohp = getIntent().getStringExtra("noHP");

        addressCat = findViewById(R.id.tvAddressCategory);
        address = findViewById(R.id.tvAddress);
        prov = findViewById(R.id.tvProvinsi);
        kab = findViewById(R.id.tvKabupaten);
        kec = findViewById(R.id.tvKecamatan);
        kel = findViewById(R.id.tvKel);
        postalcode = findViewById(R.id.tvPostalCode);

        merk = findViewById(R.id.tvMerk);
        year = findViewById(R.id.tvYear);
        model = findViewById(R.id.tvModel);
        nopol = findViewById(R.id.tvNopol);
        rgPajak = findViewById(R.id.rgPajak);
        rgPemilik = findViewById(R.id.rgOwner);
        rbPajakY = findViewById(R.id.radioPajak1);
        rbPajakN = findViewById(R.id.radioPajak2);
        rbOwnerS = findViewById(R.id.radioOwner1);
        rbOwnerOL = findViewById(R.id.radioOwner2);

        status = findViewById(R.id.tvStatus);
        tvOwner = findViewById(R.id.tvOwner);
        tvOwner.setText(sharedPrefManager.getSPName());

        detailUFI = findViewById(R.id.detailUFI);
        detailUFI.setVisibility(View.GONE);

        detailAddress = findViewById(R.id.detailAddress);
        detailAddress.setVisibility(View.GONE);

        switchProduct = findViewById(R.id.switchProduct);
        switchProduct.setChecked(false);
        switchAddress = findViewById(R.id.switchAddress);
        switchAddress.setChecked(false);

        strFirstName = getIntent().getStringExtra("firstName");
        strLastName = getIntent().getStringExtra("lastName");
        strJob = getIntent().getStringExtra("job");
        strSource = getIntent().getStringExtra("source");
        strMainPhone = getIntent().getStringExtra("mainPhone");
        strStatus = getIntent().getStringExtra("status");
        idAddress = getIntent().getIntExtra("idAlamat", 0);
        strAddressCat = getIntent().getStringExtra("kategoriAlamat");
        strAddress = getIntent().getStringExtra("alamat");
        strProvIntent = getIntent().getStringExtra("provinsi");
        strKabIntent = getIntent().getStringExtra("kabupaten");
        strKecIntent = getIntent().getStringExtra("kecamatan");
        strKelIntent = getIntent().getStringExtra("kelurahan");
        idUnitIntent = getIntent().getIntExtra("idUnit", 0);
        strMerkIntent = getIntent().getStringExtra("merk");
        tahunKend = getIntent().getIntExtra("tahun", 0);
        strModelIntent = getIntent().getStringExtra("model");
        strNopol = getIntent().getStringExtra("nopol");
        strPajak = getIntent().getStringExtra("pajak");
        strOwner = getIntent().getStringExtra("owner");

        firstName.setText(strFirstName);
        lastName.setText(strLastName);
        if (strMainPhone != null) {
            tvnohp.setText(strMainPhone);
        } else {
            tvnohp.setText(nohp);
        }
        address.setText(strAddress);
        nopol.setText(strNopol);

        if (strPajak != null && strPajak.equalsIgnoreCase("Y")) {
            rbPajakY.setChecked(true);
        } else if (strPajak != null && strPajak.equalsIgnoreCase("N")) {
            rbPajakN.setChecked(true);
        }

        if (strOwner != null && strOwner.equalsIgnoreCase("Sendiri")) {
            rbOwnerS.setChecked(true);
        } else if (strOwner != null && strOwner.equalsIgnoreCase("Orang Lain")) {
            rbOwnerOL.setChecked(true);
        }

        if (strProvIntent != null) {
            switchAddress.setChecked(true);
            detailAddress.setVisibility(View.VISIBLE);
        }

        if (strMerkIntent != null) {
            switchProduct.setChecked(true);
            detailUFI.setVisibility(View.VISIBLE);
        }

        initProv();
        initSpinnerMerk();
        initSpinnerAddressCat();
        initSpinnerJob();
        initSpinnerSource();
        getIdStatus();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getIdStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();

        switchProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // true,do the task
                    detailUFI.setVisibility(View.VISIBLE);
                    idProduct = 4;
                } else {
                    detailUFI.setVisibility(View.GONE);
                }

            }
        });

        switchAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    detailAddress.setVisibility(View.VISIBLE);
                } else {
                    detailAddress.setVisibility(View.GONE);
                }

            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idPajak = rgPajak.getCheckedRadioButtonId();
                int idPemilik = rgPemilik.getCheckedRadioButtonId();
                rbPajak = findViewById(idPajak);
                rbPemilik = findViewById(idPemilik);
                if (TextUtils.isEmpty(firstName.getText())) {
                    firstName.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(lastName.getText())) {
                    lastName.setError("Wajib Diisi !");
                } else if (idDataSource == null) {
                    Toast toast = Toast.makeText(context, "Pilih Sumber Data", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchAddress.isChecked() && idAlamatKat == null) {
                    Toast toast = Toast.makeText(context, "Pilih Kategori Alamat", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchAddress.isChecked() && TextUtils.isEmpty(address.getText())) {
                    address.setError("Wajib Diisi !");
                } else if (switchAddress.isChecked() && idAlamat == null) {
                    Toast toast = Toast.makeText(context, "Lengkapi Alamat", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchProduct.isChecked() && idUnitUfi == null) {
                    Toast toast = Toast.makeText(context, "Lengkapi Produk", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchProduct.isChecked() && TextUtils.isEmpty(nopol.getText())) {
                    nopol.setError("Wajib Diisi !");
                } else if (switchProduct.isChecked() && rgPajak.getCheckedRadioButtonId() == -1) {
                    Toast toast = Toast.makeText(context, "Pilih Pajak STNK", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchProduct.isChecked() && rgPemilik.getCheckedRadioButtonId() == -1) {
                    Toast toast = Toast.makeText(context, "Pilih Pemilik Kendaraan", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                    createLead();
                }

            }
        });
        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idPajak = rgPajak.getCheckedRadioButtonId();
                int idPemilik = rgPemilik.getCheckedRadioButtonId();
                rbPajak = findViewById(idPajak);
                rbPemilik = findViewById(idPemilik);
                if (TextUtils.isEmpty(firstName.getText())) {
                    firstName.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(lastName.getText())) {
                    lastName.setError("Wajib Diisi !");
                } else if (idDataSource == null) {
                    Toast toast = Toast.makeText(context, "Pilih Sumber Data", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchAddress.isChecked() && idAlamatKat == null) {
                    Toast toast = Toast.makeText(context, "Pilih Kategori Alamat", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchAddress.isChecked() && TextUtils.isEmpty(address.getText())) {
                    address.setError("Wajib Diisi !");
                } else if (switchAddress.isChecked() && idAlamat == null) {
                    Toast toast = Toast.makeText(context, "Lengkapi Alamat", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchProduct.isChecked() && idUnitUfi == null) {
                    Toast toast = Toast.makeText(context, "Lengkapi Produk", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchProduct.isChecked() && TextUtils.isEmpty(nopol.getText())) {
                    nopol.setError("Wajib Diisi !");
                } else if (switchProduct.isChecked() && rgPajak.getCheckedRadioButtonId() == -1) {
                    Toast toast = Toast.makeText(context, "Pilih Pajak STNK", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (switchProduct.isChecked() && rgPemilik.getCheckedRadioButtonId() == -1) {
                    Toast toast = Toast.makeText(context, "Pilih Pemilik Kendaraan", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                    createLead();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void createLead() {
        if (idLeadIntent != 0) {
//            Toast.makeText(context, "ID Unit "+idUnitIntent, Toast.LENGTH_SHORT).show();
            mApiService.leadUpdate(idLeadIntent, firstName.getText().toString(), lastName.getText().toString(), idJob, idDataSource, idStatus, idUser).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (switchAddress.isChecked()) {
                        if (idAddress != 0) {
                            mApiService.leadAddressUpdate(idAddress, idLeadIntent, address.getText().toString(), idAlamat, idAlamatKat, idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            mApiService.leadAddressCreate(idLeadIntent, address.getText().toString(), idUser, idAlamat, idAlamatKat).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    if (switchProduct.isChecked()) {
                        if (idUnitIntent != 0) {
                            String taxStatus;
                            if (rbPajak.getText().toString().equals("Hidup")) {
                                taxStatus = "Y";
                            } else {
                                taxStatus = "N";
                            }
                            mApiService.leadProductDetailUpdate(idUnitIntent, idUnitUfi, nopol.getText().toString(), taxStatus, rbPemilik.getText().toString(), idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            mApiService.leadProductCreate(idLeadIntent, idProduct, idUser).enqueue(new Callback<RespPost>() {
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

                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                            }
                                        });
//
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
                    }

                    loading.dismiss();
                    finish();
                    Intent intent = new Intent(context, DetailLeadActivity.class);
                    intent.putExtra("idLead", idLeadIntent);
                    context.startActivity(intent);
//                    Toast.makeText(context, "Persiapan Update", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            mApiService.leadCreate(idOutlet, idDataSource, idUser, firstName.getText().toString(), lastName.getText().toString(), idJob, idStatus).enqueue(new Callback<RespPost>() {
                @Override
                public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                    idLead = response.body().getId();
                    mApiService.leadPhoneCreate(idLead, nohp, "Y", idUser).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                        }
                    });

                    if (switchAddress.isChecked()) {
                        mApiService.leadAddressCreate(idLead, address.getText().toString(), idUser, idAlamat, idAlamatKat).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (switchProduct.isChecked()) {
                        mApiService.leadProductCreate(idLead, idProduct, idUser).enqueue(new Callback<RespPost>() {
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

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                        }
                                    });

//
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
                    mApiService.userLogCreate(2, idLead, "create", idUser).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                        }
                    });

                    loading.dismiss();
                    finish();
                    Intent intent = new Intent(context, DetailLeadActivity.class);
                    intent.putExtra("idLead", idLead);
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(Call<RespPost> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void initSpinnerJob() {
        mApiService.job().enqueue(new Callback<RespListJob>() {
            @Override
            public void onResponse(Call<RespListJob> call, Response<RespListJob> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listJobs = response.body().getData();
                        final List<String> listJob = new ArrayList<>();
                        for (int i = 0; i < listJobs.size(); i++) {
                            listJob.add(listJobs.get(i).getJob());
                        }
                        ArrayAdapter<String> adapterJob = new ArrayAdapter<>(context, R.layout.spinner_item, listJob);
                        job.setAdapter(adapterJob);

                        if (strJob != null) {
                            int intJob = adapterJob.getPosition(strJob);
                            job.setSelection(intJob);
                        }

                        job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idJob = listJobs.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListJob> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerSource() {
        mApiService.dataSource().enqueue(new Callback<RespListDataSource>() {
            @Override
            public void onResponse(Call<RespListDataSource> call, Response<RespListDataSource> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listDataSources = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listDataSources.size(); i++) {
                            list.add(listDataSources.get(i).getDatasource());
                        }
                        ArrayAdapter<String> adapterSource = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        source.setAdapter(adapterSource);

                        if (strSource != null) {
                            int intSource = adapterSource.getPosition(strSource);
                            source.setSelection(intSource);
                        }

                        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idDataSource = listDataSources.get(position).getId();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListDataSource> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerAddressCat() {
        mApiService.categoryadress().enqueue(new Callback<RespListCategoryAddress>() {
            @Override
            public void onResponse(Call<RespListCategoryAddress> call, Response<RespListCategoryAddress> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listCategoryAddress = response.body().getData();
                        List<String> listKategori = new ArrayList<>();
                        for (int i = 0; i < listCategoryAddress.size(); i++) {
                            listKategori.add(listCategoryAddress.get(i).getCategory());
                        }
                        ArrayAdapter<String> adapterKategori = new ArrayAdapter<>(context, R.layout.spinner_item, listKategori);
                        addressCat.setAdapter(adapterKategori);

                        if (strAddressCat != null) {
                            int intKategori = adapterKategori.getPosition(strAddressCat);
                            addressCat.setSelection(intKategori);
                        }

                        addressCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idAlamatKat = listCategoryAddress.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListCategoryAddress> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initProv() {

        listAlamats = databaseHelper.getProv();
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < listAlamats.size(); i++) {
            list.add(listAlamats.get(i).getProvinsi());
        }
        Set<String> setWithUniqueValues = new HashSet<>(list);
        List<String> listWithUniqueValues = new ArrayList<>(setWithUniqueValues);
        Collections.sort(listWithUniqueValues, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, listWithUniqueValues);
        prov.setAdapter(adapter);

        if (strProvIntent != null) {
            int intProv = adapter.getPosition(strProvIntent);
            prov.setSelection(intProv);
        }

        prov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strProv = prov.getSelectedItem().toString();
                initKab();
//                                Toast.makeText(context, "Provinsi" + strProv, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initKab() {
        listAlamats = databaseHelper.getAlamat(" Where provinsi='" + strProv + "'");
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < listAlamats.size(); i++) {
            list.add(listAlamats.get(i).getKabupaten());
        }
        Set<String> setWithUniqueValues = new HashSet<>(list);
        List<String> listWithUniqueValues = new ArrayList<>(setWithUniqueValues);
        Collections.sort(listWithUniqueValues, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, listWithUniqueValues);
        kab.setAdapter(adapter);

        if (strKabIntent != null) {
            int intKab = adapter.getPosition(strKabIntent);
            kab.setSelection(intKab);
        }

        kab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strKab = kab.getSelectedItem().toString();
                initKec();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initKec() {
        listAlamats = databaseHelper.getAlamat(" Where kabupaten='" + strKab + "'");
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < listAlamats.size(); i++) {
            list.add(listAlamats.get(i).getKecamatan());
        }
        Set<String> setWithUniqueValues = new HashSet<>(list);
        List<String> listWithUniqueValues = new ArrayList<>(setWithUniqueValues);
        Collections.sort(listWithUniqueValues, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, listWithUniqueValues);
        kec.setAdapter(adapter);

        if (strKecIntent != null) {
            int intProv = adapter.getPosition(strKecIntent);
            kec.setSelection(intProv);
        }

        kec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strKec = kec.getSelectedItem().toString();
                initKel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initKel() {
        listAlamats = databaseHelper.getAlamat(" Where kecamatan='" + strKec + "'");
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < listAlamats.size(); i++) {
            list.add(listAlamats.get(i).getKelurahan());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
        kel.setAdapter(adapter);

        if (strKelIntent != null) {
            int intKel = adapter.getPosition(strKelIntent);
            kel.setSelection(intKel);
        }

        kel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idAlamat = listAlamats.get(position).getId();
//                                detailAlamat.setVisibility(View.VISIBLE);
                postalcode.setText(listAlamats.get(position).getKodepos());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerMerk() {
        mApiService.unitUfi(idBranch, null, null).enqueue(new Callback<RespListUnitUfi>() {
            @Override
            public void onResponse(Call<RespListUnitUfi> call, Response<RespListUnitUfi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listUnitUfis = response.body().getData();
                        listUnitUfiYear = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listUnitUfis.size(); i++) {
                            list.add(listUnitUfis.get(i).getMerk());
                        }
                        final Set<String> setWithUniqueValues = new HashSet<>(list);
                        List<String> listWithUniqueValues = new ArrayList<>(setWithUniqueValues);
                        Collections.sort(listWithUniqueValues, new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return o1.compareTo(o2);
                            }
                        });
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, listWithUniqueValues);
                        merk.setAdapter(adapter);

                        if (strMerkIntent != null) {
                            int intMerk = adapter.getPosition(strMerkIntent);
                            merk.setSelection(intMerk);
                        }

                        merk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strMerk = merk.getSelectedItem().toString();
                                initSpinnerYear();
                                initSpinnerModel();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListUnitUfi> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerYear() {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listUnitUfiYear.size(); i++) {
            list.add(listUnitUfiYear.get(i).getYear());
        }
        Set<Integer> setWithUniqueValues = new HashSet<>(list);
        List<Integer> listWithUniqueValues = new ArrayList<>(setWithUniqueValues);
        Collections.sort(listWithUniqueValues, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, listWithUniqueValues);
        year.setAdapter(adapter);

        if (tahunKend != null) {
            int intYear = adapter.getPosition(tahunKend);
            year.setSelection(intYear);
        }

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strYear = year.getSelectedItem().toString();
//                Toast.makeText(context, "Year " + strYear, Toast.LENGTH_SHORT).show();
                idUnitUfi = null;
                initSpinnerModel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSpinnerModel() {
        mApiService.unitList(idBranch, strMerk, strYear).enqueue(new Callback<RespListUnitList>() {
            @Override
            public void onResponse(Call<RespListUnitList> call, Response<RespListUnitList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listUnitUfis = response.body().getData();
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < listUnitUfis.size(); i++) {
                            list.add(listUnitUfis.get(i).getModel());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, list);
                        model.setAdapter(adapter);

                        if (strModelIntent != null) {
                            int intModel = adapter.getPosition(strModelIntent);
                            model.setSelection(intModel);
                        }

                        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idUnitUfi = listUnitUfis.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListUnitList> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIdStatus() {

        String[] statuslead;
        if (idLeadIntent != 0) {
            statuslead = new String[]{"New", "Working", "Hot", "Unqualified"};
        } else {
            statuslead = getResources().getStringArray(R.array.mst_status);
        }

        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(context, R.layout.spinner_item, statuslead);
        status.setAdapter(adapterStatus);
        /*adapterStatus.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);*/

        if (strStatus != null) {
            int intStatus = adapterStatus.getPosition(strStatus);
            status.setSelection(intStatus);
        }

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (status.getSelectedItem().toString()) {
                    case "New":
                        idStatus = 1;
                        break;
                    case "Hot":
                        idStatus = 2;
                        break;
                    case "Working":
                        idStatus = 3;
                        break;
                    case "Unqualified":
                        idStatus = 4;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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

        /*initSpinnerMerk();
        initSpinnerYear();
        initSpinnerModel();*/

    /*public void switch_clicked(View v) {

        if (switchProduct.isChecked()) {
            // true,do the task
            detailUFI.setVisibility(View.VISIBLE);
            idProduct = 4;
        } else {
            detailUFI.setVisibility(View.GONE);
        }

        if (switchAddress.isChecked()) {
            detailAddress.setVisibility(View.VISIBLE);
        } else {
            detailAddress.setVisibility(View.GONE);
        }

    }*/

    /*private void initActvKelurahan() {
        mApiService.alamat("", "").enqueue(new Callback<RespListAlamat>() {
            @Override
            public void onResponse(Call<RespListAlamat> call, Response<RespListAlamat> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listAlamats = response.body().getData();
                        final List<String> list = new ArrayList<>();

                        for (int i = 0; i < listAlamats.size(); i++) {
                            list.add(listAlamats.get(i).getKelurahan() + ", " + listAlamats.get(i).getKecamatan() + ", " + listAlamats.get(i).getProvinsi());
                        }

                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        kelurahan.setAdapter(adapter);
                        kelurahan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String current = adapter.getItem(position);
                                StringTokenizer separated = new StringTokenizer(current, ",");
                                String kelurahan = separated.nextToken();
                                String kecamatan = separated.nextToken().trim();
                                mApiService.alamat(kelurahan, kecamatan).enqueue(new Callback<RespListAlamat>() {
                                    @Override
                                    public void onResponse(Call<RespListAlamat> call, Response<RespListAlamat> response) {
                                        listAlamats = response.body().getData();
                                        detailAlamat.setVisibility(View.VISIBLE);
                                        city.setText(listAlamats.get(0).getKabupaten());
                                        state.setText(listAlamats.get(0).getProvinsi());
                                        postalcode.setText(listAlamats.get(0).getKodepos());
                                        idAlamat = listAlamats.get(0).getId();
                                    }

                                    @Override
                                    public void onFailure(Call<RespListAlamat> call, Throwable t) {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListAlamat> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*private void initSpinnerModel() {
        mApiService.unitUfi(null, strMerk, strYear).enqueue(new Callback<RespListUnitUfi>() {
            @Override
            public void onResponse(Call<RespListUnitUfi> call, Response<RespListUnitUfi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listUnitUfis = response.body().getData();
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < listUnitUfis.size(); i++) {
                            list.add(listUnitUfis.get(i).getModel());
                        }
                    *//*Collections.sort(list, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });*//*
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        model.setAdapter(adapter);
                        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idUnitUfi = listUnitUfis.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListUnitUfi> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
