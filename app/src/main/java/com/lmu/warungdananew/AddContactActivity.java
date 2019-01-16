package com.lmu.warungdananew;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdananew.Helper.NumberTextWatcher;
import com.lmu.warungdananew.Response.ListAlamat;
import com.lmu.warungdananew.Response.ListCategoryAddress;
import com.lmu.warungdananew.Response.ListDataSource;
import com.lmu.warungdananew.Response.ListEmployee;
import com.lmu.warungdananew.Response.ListJob;
import com.lmu.warungdananew.Response.ListMarital;
import com.lmu.warungdananew.Response.ListPlace;
import com.lmu.warungdananew.Response.ListReligion;
import com.lmu.warungdananew.Response.RespListCategoryAddress;
import com.lmu.warungdananew.Response.RespListDataSource;
import com.lmu.warungdananew.Response.RespListEmployee;
import com.lmu.warungdananew.Response.RespListJob;
import com.lmu.warungdananew.Response.RespListMarital;
import com.lmu.warungdananew.Response.RespListPlace;
import com.lmu.warungdananew.Response.RespListReligion;
import com.lmu.warungdananew.Response.RespPost;
import com.lmu.warungdananew.SQLite.DatabaseHelper;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Api.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView nik, birthdate, city, state, postalcode;
    private EditText firstname, lastname, birthplace, mainphone, mobilephone, address, rt, rw, mother, tanggungan, perusahaan, jabatan, lamakerja, pendapatan, pengeluaran;
    private AutoCompleteTextView kelurahan;
    private Spinner religion, marital, job, source, addcategory, rumah, karyawan, prov, kec, kab, kel;
    Context context;
    List<ListCategoryAddress> listCategoryAddresses;
    List<ListAlamat> listAlamats;
    List<ListJob> listJobs;
    List<ListDataSource> listDataSources;
    List<ListReligion> listReligions;
    List<ListMarital> listMaritals;
    List<ListPlace> listPlaces;
    List<ListEmployee> listEmployees;
    private Button btnCheck;
    private Integer idReligion, idmarital, idjob, idContact, idDataSource, idJobs, idContactIntent, idAddressIntent,
            idAddContact, idContactUpdate, idMainPhone, idMobilePhone;
    private CheckBox cbemployeeDetail, cbemployee;
    private ApiEndPoint mApiService;
    private LinearLayout detailAlamat, employeeDetail, employee;
    private String nikIntent, firstName, lastName, birthPlace, birthDate, strGender, strReligion, strMarital,
            idAddressCat, strRt, strRw, provinsi, kabupaten, kecamatan, strKel,
            mainPhone, mobilePhone, jalan, tglPilih, gender, strProv, strKab, strKec,
            strMother, strFamily, stsRumah, strPerusahaan, stsKaryawan, strJabatan, strLamaKerja, strIncome, strOutlay;

    private Integer idJob, idSource, idAddress, idCategory, idCatAddress, convertSource, idConvert;
    ProgressDialog loading;
    private String pekerjaan, sumber, sEmail = null, sMother = null, sTanggungan = null,
            sPerusahaan = null, sJabatan = null, sLamakerja = null, sPendapatan = null, sPengeluaran = null;
    private Integer iRumah = null, iKaryawan = null, idUser;
    private Calendar calendar;
    private RadioGroup rgGender;
    private RadioButton rbGender, rbLaki, rbPerempuan;
    SharedPrefManager sharedPrefManager;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_add_contact);

        idContactIntent = getIntent().getIntExtra("idContact", 0);

        toolbar = findViewById(R.id.toolbar);
        if (idContactIntent != 0) {
            toolbar.setTitle("Edit Contact");
        } else {
            toolbar.setTitle("Tambah Contact");
        }
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                if (idContactIntent != 0) {
                    alert.setTitle("Batal Edit Contact ?");
                } else {
                    alert.setTitle("Batal Menambah Contact ?");
                }
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        AddContactActivity.super.onBackPressed();
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

        databaseHelper = new DatabaseHelper(this);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        idUser = sharedPrefManager.getSpId();
        calendar = Calendar.getInstance();

        nik = findViewById(R.id.tvNIK);
        firstname = findViewById(R.id.tvFirstName);
        lastname = findViewById(R.id.tvLastName);
        birthplace = findViewById(R.id.tvBirthPlace);
        birthdate = findViewById(R.id.tvBirthDate);
        rgGender = findViewById(R.id.rgGender);
        rbLaki = findViewById(R.id.rbLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        religion = findViewById(R.id.tvReligion);
        marital = findViewById(R.id.tvMarital);
        job = findViewById(R.id.tvJob);

        source = findViewById(R.id.tvSource);
        mainphone = findViewById(R.id.tvMainPhone);
        mobilephone = findViewById(R.id.tvMobileNumber);
        addcategory = findViewById(R.id.tvAddressCategory);
        address = findViewById(R.id.tvAddress);
        rt = findViewById(R.id.tvRT);
        rw = findViewById(R.id.tvRW);
        prov = findViewById(R.id.tvProvinsi);
        kab = findViewById(R.id.tvKabupaten);
        kec = findViewById(R.id.tvKecamatan);
        kel = findViewById(R.id.tvKel);
        postalcode = findViewById(R.id.tvPostalCode);

        mother = findViewById(R.id.tvMother);
        tanggungan = findViewById(R.id.tvTanggungan);
        rumah = findViewById(R.id.tvStatusRumah);

        perusahaan = findViewById(R.id.tvPerusahaan);
        karyawan = findViewById(R.id.tvStatusKaryawan);
        jabatan = findViewById(R.id.tvJabatan);
        lamakerja = findViewById(R.id.tvJamKerja);
        pendapatan = findViewById(R.id.tvPendapatan);
        pengeluaran = findViewById(R.id.tvPengeluaran);

//        kelurahan = findViewById(R.id.tvKelurahan);
        city = findViewById(R.id.tvCity);
        state = findViewById(R.id.tvState);
        detailAlamat = findViewById(R.id.detailAlamat);
//        detailAlamat.setVisibility(View.GONE);

        nik.setText(getIntent().getStringExtra("nik"));
        btnCheck = findViewById(R.id.btnCheck);
        employeeDetail = findViewById(R.id.employeeDetail);
        employee = findViewById(R.id.employee);


        idContactUpdate = getIntent().getIntExtra("idContactUpdate", 0);
        nikIntent = getIntent().getStringExtra("nik");
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        birthPlace = getIntent().getStringExtra("birthPlace");
        birthDate = getIntent().getStringExtra("birthDate");
        strGender = getIntent().getStringExtra("gender");
        strReligion = getIntent().getStringExtra("religion");
        strMarital = getIntent().getStringExtra("marital");
        pekerjaan = getIntent().getStringExtra("idJob");
        sumber = getIntent().getStringExtra("idSource");

        idAddressIntent = getIntent().getIntExtra("idAddress", 0);
        idAddressCat = getIntent().getStringExtra("idAddressCat");
        idMainPhone = getIntent().getIntExtra("idMainPhone", 0);
        mainPhone = getIntent().getStringExtra("mainPhone");
        idMobilePhone = getIntent().getIntExtra("idMobilePhone", 0);
        mobilePhone = getIntent().getStringExtra("mobilePhone");
        jalan = getIntent().getStringExtra("alamat");
        strRt = getIntent().getStringExtra("rt");
        strRw = getIntent().getStringExtra("rw");
        provinsi = getIntent().getStringExtra("provinsi");
        kabupaten = getIntent().getStringExtra("kabupaten");
        kecamatan = getIntent().getStringExtra("kecamatan");
        strKel = getIntent().getStringExtra("kelurahan");
        idAddContact = getIntent().getIntExtra("idAddContact", 0);
        strMother = getIntent().getStringExtra("mother");
        strFamily = getIntent().getStringExtra("tanggungan");
        stsRumah = getIntent().getStringExtra("stsRumah");
        strPerusahaan = getIntent().getStringExtra("perusahaan");
        stsKaryawan = getIntent().getStringExtra("stsKaryawan");
        strJabatan = getIntent().getStringExtra("jabatan");
        strLamaKerja = getIntent().getStringExtra("lamakerja");
        strIncome = getIntent().getStringExtra("pendapatan");
        strOutlay = getIntent().getStringExtra("pengeluaran");


        convertSource = getIntent().getIntExtra("convertSource", 0);
        idConvert = getIntent().getIntExtra("idConvert", 0);


        firstname.setText(firstName);
        lastname.setText(lastName);
        birthplace.setText(birthPlace);
        birthdate.setText(birthDate);
        if (strGender != null && strGender.equalsIgnoreCase("L")) {
            rbLaki.setChecked(true);
        } else if (strGender != null && strGender.equalsIgnoreCase("P")) {
            rbPerempuan.setChecked(true);
        }

        mainphone.setText(mainPhone);
        mobilephone.setText(mobilePhone);
        address.setText(jalan);
        rt.setText(strRt);
        rw.setText(strRw);

        mother.setText(strMother);
        tanggungan.setText(strFamily);
        perusahaan.setText(strPerusahaan);
        jabatan.setText(strJabatan);
        lamakerja.setText(strLamaKerja);
        pendapatan.setText(strIncome);
        pengeluaran.setText(strOutlay);

        pendapatan.addTextChangedListener(new NumberTextWatcher(pendapatan));
        pengeluaran.addTextChangedListener(new NumberTextWatcher(pengeluaran));

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        initSpinnerAddressCat();
        initSpinnerJob();
        initSpinnerSource();
        initSpinnerReligion();
        initSpinnerMarital();
        initSpinnerPlace();
        initSpinnerEmployee();
        initProv();

    }

    @Override
    protected void onResume() {
        super.onResume();

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, listener,
                        1980,
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }

            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    birthdate.setText(dateFormat.format(calendar.getTime()));
                    tglPilih = dateFormat1.format(calendar.getTime());
                }
            };

        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = rgGender.getCheckedRadioButtonId();
                rbGender = findViewById(id);
                if (rbGender.getText().toString().equals("Laki-Laki")) {
                    gender = "L";
                } else {
                    gender = "P";
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstname.getText())) {
                    firstname.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(lastname.getText())) {
                    lastname.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(birthplace.getText())) {
                    birthplace.setError("Wajib Diisi !");
                    return;
                } else if (TextUtils.isEmpty(birthdate.getText())) {
                    birthdate.setError("Wajib Diisi !");
                    return;
                } else if (rgGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Pilih Jenis Kelamin", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mainphone.getText())) {
                    mainphone.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(mobilephone.getText())) {
                    mobilephone.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(address.getText())) {
                    address.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(rt.getText())) {
                    rt.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(rw.getText())) {
                    rw.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(mother.getText())) {
                    mother.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(tanggungan.getText())) {
                    tanggungan.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(perusahaan.getText())) {
                    perusahaan.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(jabatan.getText())) {
                    jabatan.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(lamakerja.getText())) {
                    lamakerja.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(pendapatan.getText())) {
                    pendapatan.setError("Wajib Diisi !");
                } else if (TextUtils.isEmpty(pengeluaran.getText())) {
                    pengeluaran.setError("Wajib Diisi !");
                } else {
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
                    contactCreate();
                }
            }
        });

    }

    private void contactCreate() {

        sMother = mother.getText().toString();
        sTanggungan = tanggungan.getText().toString();
        sPerusahaan = perusahaan.getText().toString();
        sJabatan = jabatan.getText().toString();
        sLamakerja = lamakerja.getText().toString();
        sPendapatan = pendapatan.getText().toString();
        sPengeluaran = pengeluaran.getText().toString();

        if (idContactIntent != 0) {

            mApiService.contactUpdate(idContactUpdate, nik.getText().toString(), firstname.getText().toString(), lastname.getText().toString(),
                    birthplace.getText().toString(), tglPilih, gender, idReligion, idmarital, idjob, idSource, idUser)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            mApiService.contactPhoneUpdate(idMainPhone, idContactIntent, mainphone.getText().toString(), "Y", idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });

                            mApiService.contactPhoneUpdate(idMobilePhone, idContactIntent, mobilephone.getText().toString(), "Y", idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });

                            mApiService.contactAddressUpdate(idAddressIntent, idContactIntent, address.getText().toString(), rt.getText().toString(), rw.getText().toString(), idAddress, idCategory, idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });

                            mApiService.contactAdditionalUpdate(idAddContact, idContact, mother.getText().toString(), Integer.parseInt(tanggungan.getText().toString()), iRumah, sPerusahaan, iKaryawan, sJabatan, Integer.parseInt(lamakerja.getText().toString()),
                                    Integer.parseInt(pendapatan.getText().toString().replaceAll(",", "").replaceAll("\\.", "")), Integer.parseInt(pengeluaran.getText().toString().replaceAll(",", "").replaceAll("\\.", "")),
                                    idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });

                            loading.dismiss();
//                            Toast.makeText(context, "MAINTENANCE", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(context, DetailContactActivity.class);
                            intent.putExtra("idContact", idContactIntent);
                            context.startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                        }
                    });


        } else {

            mApiService.contactCreate(nik.getText().toString(), firstname.getText().toString(), lastname.getText().toString(),
                    birthplace.getText().toString(), tglPilih,
                    gender, idReligion, idmarital, idjob, idSource, idUser).enqueue(new Callback<RespPost>() {
                @Override
                public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                    if (response.isSuccessful()) {
                        idContact = response.body().getId();

                        mApiService.contactCollabCreate(idUser, idContact).enqueue(new Callback<RespPost>() {
                            @Override
                            public void onResponse(Call<RespPost> call, Response<RespPost> response) {

                            }

                            @Override
                            public void onFailure(Call<RespPost> call, Throwable t) {
                                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                            }
                        });

                        mApiService.contactPhoneCreate(idContact, mainphone.getText().toString(), "Y", idUser).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                            }
                        });

                        if (mobilephone.getText() != null) {
                            mApiService.contactPhoneCreate(idContact, mobilephone.getText().toString(), "Y", idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        mApiService.contactAddressCreate(idContact, address.getText().toString(), rt.getText().toString(), rw.getText().toString(), idAddress, idCategory, idUser).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                            }
                        });

                        mApiService.contactAdditionalCreate(idContact, mother.getText().toString(), Integer.parseInt(tanggungan.getText().toString()), sPerusahaan, sJabatan, Integer.parseInt(lamakerja.getText().toString()),
                                Integer.parseInt(pendapatan.getText().toString().replaceAll(",", "").replaceAll("\\.", "")), Integer.parseInt(pengeluaran.getText().toString().replaceAll(",", "").replaceAll("\\.", "")), iRumah, iKaryawan, idUser)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        if (convertSource == 1) {
                            mApiService.leadUpdateStatus(idConvert, 5, idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            mApiService.targetUpdateStatus(idConvert, 6, idUser).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        mApiService.userLogCreate(4, idContact, "create", idUser).enqueue(new Callback<ResponseBody>() {
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
                        Intent intent = new Intent(context, DetailContactActivity.class);
                        intent.putExtra("idContact", idContact);
                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RespPost> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void initSpinnerReligion() {
        mApiService.religion().enqueue(new Callback<RespListReligion>() {
            @Override
            public void onResponse(Call<RespListReligion> call, Response<RespListReligion> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        listReligions = response.body().getData();
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < listReligions.size(); i++) {
                            list.add(listReligions.get(i).getAgama());
                        }
                        ArrayAdapter<String> adapterReligion = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        religion.setAdapter(adapterReligion);

                        if (strReligion != null) {
                            int intReligion = adapterReligion.getPosition(strReligion);
                            religion.setSelection(intReligion);
                        }

                        religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idReligion = listReligions.get(position).getId();
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
            public void onFailure(Call<RespListReligion> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerMarital() {
        mApiService.marital().enqueue(new Callback<RespListMarital>() {
            @Override
            public void onResponse(Call<RespListMarital> call, Response<RespListMarital> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listMaritals = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listMaritals.size(); i++) {
                            list.add(listMaritals.get(i).getStatus());
                        }
                        ArrayAdapter<String> adapterMarital = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        marital.setAdapter(adapterMarital);

                        if (strMarital != null) {
                            int intMarital = adapterMarital.getPosition(strMarital);
                            marital.setSelection(intMarital);
                        }

                        marital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idmarital = listMaritals.get(position).getId();
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
            public void onFailure(Call<RespListMarital> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
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

                        if (pekerjaan != null) {
                            int intJob = adapterJob.getPosition(pekerjaan);
                            job.setSelection(intJob);
                        }

                        job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idjob = listJobs.get(position).getId();
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
            public void onFailure(Call<RespListJob> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
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

                        if (sumber != null) {
                            int intSource = adapterSource.getPosition(sumber);
                            source.setSelection(intSource);
                        }

                        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idSource = listDataSources.get(position).getId();
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
            public void onFailure(Call<RespListDataSource> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerAddressCat() {
        mApiService.categoryadress().enqueue(new Callback<RespListCategoryAddress>() {
            @Override
            public void onResponse(Call<RespListCategoryAddress> call, Response<RespListCategoryAddress> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listCategoryAddresses = response.body().getData();
                        List<String> listKategori = new ArrayList<>();
                        for (int i = 0; i < listCategoryAddresses.size(); i++) {
                            listKategori.add(listCategoryAddresses.get(i).getCategory());
                        }
                        ArrayAdapter<String> adapterKategori = new ArrayAdapter<>(context, R.layout.spinner_item, listKategori);
                        addcategory.setAdapter(adapterKategori);

                        if (idAddressCat != null) {
                            int intKategori = adapterKategori.getPosition(idAddressCat);
                            addcategory.setSelection(intKategori);
                        }

                        addcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idCategory = listCategoryAddresses.get(position).getId();
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
            public void onFailure(Call<RespListCategoryAddress> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
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

        if (provinsi != null) {
            int intProv = adapter.getPosition(provinsi);
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

        if (kabupaten != null) {
            int intKab = adapter.getPosition(kabupaten);
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

        if (kecamatan != null) {
            int intKec = adapter.getPosition(kecamatan);
            kec.setSelection(intKec);
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

        if (strKel != null) {
            int intKel = adapter.getPosition(strKel);
            kel.setSelection(intKel);
        }

        kel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idAddress = listAlamats.get(position).getId();
                postalcode.setText(listAlamats.get(position).getKodepos());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerPlace() {
        mApiService.place().enqueue(new Callback<RespListPlace>() {
            @Override
            public void onResponse(Call<RespListPlace> call, Response<RespListPlace> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listPlaces = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listPlaces.size(); i++) {
                            list.add(listPlaces.get(i).getStatus());
                        }
                        ArrayAdapter<String> adapterPlace = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        rumah.setAdapter(adapterPlace);

                        if (stsRumah != null) {
                            int intPlace = adapterPlace.getPosition(stsRumah);
                            rumah.setSelection(intPlace);
                        }

                        rumah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                iRumah = listPlaces.get(position).getId();
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
            public void onFailure(Call<RespListPlace> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerEmployee() {
        mApiService.employee().enqueue(new Callback<RespListEmployee>() {
            @Override
            public void onResponse(Call<RespListEmployee> call, Response<RespListEmployee> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listEmployees = response.body().getData();
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < listEmployees.size(); i++) {
                            list.add(listEmployees.get(i).getStatus());
                        }
                        ArrayAdapter<String> adapterKaryawan = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        karyawan.setAdapter(adapterKaryawan);

                        if (stsKaryawan != null) {
                            int intPlace = adapterKaryawan.getPosition(stsKaryawan);
                            karyawan.setSelection(intPlace);
                        }

                        karyawan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                iKaryawan = listEmployees.get(position).getId();
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
            public void onFailure(Call<RespListEmployee> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void initActvKelurahan() {
        mApiService.alamat("", "","","").enqueue(new Callback<RespListAlamat>() {
            @Override
            public void onResponse(Call<RespListAlamat> call, Response<RespListAlamat> response) {
                if (response.isSuccessful()) {
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
                            mApiService.alamat(kelurahan, kecamatan,"","").enqueue(new Callback<RespListAlamat>() {
                                @Override
                                public void onResponse(Call<RespListAlamat> call, Response<RespListAlamat> response) {
                                    listAlamats = response.body().getData();
                                    detailAlamat.setVisibility(View.VISIBLE);
                                    city.setText(listAlamats.get(0).getKabupaten());
                                    state.setText(listAlamats.get(0).getProvinsi());
                                    postalcode.setText(listAlamats.get(0).getKodepos());
                                    idAddress = listAlamats.get(0).getId();
                                }

                                @Override
                                public void onFailure(Call<RespListAlamat> call, Throwable t) {
                                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RespListAlamat> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}
