package com.lmu.warungdana;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lmu.warungdana.Helper.NumberTextWatcher;
import com.lmu.warungdana.Response.ListCabangFif;
import com.lmu.warungdana.Response.ListDataSource;
import com.lmu.warungdana.Response.ListEmployee;
import com.lmu.warungdana.Response.ListJob;
import com.lmu.warungdana.Response.ListNeed;
import com.lmu.warungdana.Response.ListOrder;
import com.lmu.warungdana.Response.ListUnitUfi;
import com.lmu.warungdana.Response.RespListCabangFif;
import com.lmu.warungdana.Response.RespListDataSource;
import com.lmu.warungdana.Response.RespListEmployee;
import com.lmu.warungdana.Response.RespListJob;
import com.lmu.warungdana.Response.RespListNeed;
import com.lmu.warungdana.Response.RespListOrder;
import com.lmu.warungdana.Response.RespListUnitList;
import com.lmu.warungdana.Response.RespListUnitUfi;
import com.lmu.warungdana.Response.RespPost;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.CamcorderProfile.get;

public class AddOrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvContact, tvBirthdate, tvTanggalSurvei, tvNamaOutlet, tvDP;
    private EditText tvNopol, tvNoka, tvNosin, tvNoBPKB, tvAlamatBPKB, tvOTR, tvPlafond,
            tvAngsuran, tvTenor, tvSuretyName, tvSuretyBirthPlace, tvPerusahaan, tvLamaKerja, tvJabatan, tvPenghasilan, tvPengeluaran, otrTaksasi,nomorTaksasi;
    private Spinner spStatus, spMerk, spYear, spModel, tvNeed, source, spJob, tvStatusKonsumen, spCabangFif, spPosFif, spStsAlamat;
    private Context context;
    private ApiEndPoint mApiService;
    ProgressDialog loading;
    List<ListUnitUfi> listUnitUfis;
    List<ListDataSource> listDataSources;
    List<ListOrder> listOrder;
    List<ListJob> listJobs;
    List<ListEmployee> listEmployees;
    List<ListCabangFif> listCabangFif;
    private Integer idContact, idUser, idOrder, idProduct, idStatus, idUnit, idOutlet, idDataSource, count, idJob, idStatusPenjamin, idCabangFif, idBranch;
    private String nmrOrder, namaContact, strYear, strNeed, strMerk, tglPilih, strNopol, strNoka,
            strNosin, strNoBPKB, strAlamatBPKB, strOTR, strPlafond, strDP, strAngsuran, strTenor,
            strSuretyName, strSuretyBirthPlace, strSuretyBirthDate, tglSurvei = null, jamsurvei, strStatKons,
            tglJamSurvei, tglSurveiPost, strCabangFif, strStatusAlamat;
    private String perusahaan, jabatan = null;
    private Integer lamaKerja, pendapatan, pengeluaran = null, jumlah = 0;
    private RadioGroup rgTax, rgOwner;
    private RadioButton rbTax, rbOwner;
    private Button btnCheck, btnCheck2;
    SharedPrefManager sharedPrefManager;
    private Calendar calendar;
    NumberFormat formatter = new DecimalFormat("#,###");
    NestedScrollView scrollView;
    TextView nomorTaksasiWajib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Order");
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Batal menambah Deal ?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        AddOrderActivity.super.onBackPressed();
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
        context = this;
        sharedPrefManager = new SharedPrefManager(this);
        calendar = Calendar.getInstance();
        idUser = sharedPrefManager.getSpId();
        idOutlet = sharedPrefManager.getSpOutletId();
        idBranch = sharedPrefManager.getSpBranchId();

        tvContact = findViewById(R.id.tvContact);
        tvStatusKonsumen = findViewById(R.id.tvStatusKonsumen);
        source = findViewById(R.id.tvSource);
        tvNamaOutlet = findViewById(R.id.tvNamaOutlet);
        spCabangFif = findViewById(R.id.tvCabangFIF);
        spPosFif = findViewById(R.id.tvPosFIF);
        tvTanggalSurvei = findViewById(R.id.tvTanggalSurvei);
        spStsAlamat = findViewById(R.id.tvStatusAlamat);

        spMerk = findViewById(R.id.tvMerk);
        spYear = findViewById(R.id.tvYear);
        spModel = findViewById(R.id.tvModel);
        tvNopol = findViewById(R.id.tvNopol);
        rgTax = findViewById(R.id.rgPajak);
        rgOwner = findViewById(R.id.rgOwner);

        tvOTR = findViewById(R.id.tvOTR);
        tvPlafond = findViewById(R.id.tvPlafond);
        tvDP = findViewById(R.id.tvDP);
        tvAngsuran = findViewById(R.id.tvAngsuran);
        tvTenor = findViewById(R.id.tvTenor);
        tvNeed = findViewById(R.id.tvKebutuhan);

        tvSuretyName = findViewById(R.id.tvName);
        tvSuretyBirthPlace = findViewById(R.id.tvBirthPlace);
        tvBirthdate = findViewById(R.id.tvBirthDate);
        spJob = findViewById(R.id.tvJob);
        tvPerusahaan = findViewById(R.id.tvPerusahaan);
        spStatus = findViewById(R.id.tvStatusPasangan);
        tvJabatan = findViewById(R.id.tvJabatan);
        tvLamaKerja = findViewById(R.id.tvLamaKerja);
        tvPenghasilan = findViewById(R.id.tvPendapatan);
        tvPengeluaran = findViewById(R.id.tvPengeluaran);

        btnCheck = findViewById(R.id.btnCheck);
        btnCheck2 = findViewById(R.id.btnCheck2);
        scrollView = findViewById(R.id.scrollView);

        otrTaksasi = findViewById(R.id.tvOTRTaksasi);
        nomorTaksasi = findViewById(R.id.tvNomorTaksasi);
        nomorTaksasiWajib = findViewById(R.id.tvNomorTaksasiWajib);

        tvNamaOutlet.setText(sharedPrefManager.getSPOutletName());

        tvPlafond.addTextChangedListener(autoTextWatcher);
        tvOTR.addTextChangedListener(autoTextWatcher);

        tvPlafond.addTextChangedListener(new NumberTextWatcher(tvPlafond));
        tvOTR.addTextChangedListener(new NumberTextWatcher(tvOTR));
        tvAngsuran.addTextChangedListener(new NumberTextWatcher(tvAngsuran));
        tvPenghasilan.addTextChangedListener(new NumberTextWatcher(tvPenghasilan));
        tvPengeluaran.addTextChangedListener(new NumberTextWatcher(tvPengeluaran));
        otrTaksasi.addTextChangedListener(new NumberTextWatcher(otrTaksasi));
        nomorTaksasi.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(20)});

        nomorTaksasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               nomorTaksasiWajib.setText("Nomor Taksasi ("+nomorTaksasi.length()+"/20)");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listener();

    }

    private void listener() {

        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ContactOrderActivity.class));
                finish();
            }
        });

        idContact = getIntent().getIntExtra("idContact", 0);
        namaContact = getIntent().getStringExtra("nameContact");
        tvContact.setText(namaContact);
        //strMerk = spMerk.getSelectedItem().toString();
        //strYear = spYear.getSelectedItem().toString();
        tvBirthdate.setOnClickListener(new View.OnClickListener() {
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
                    tvBirthdate.setText(dateFormat.format(calendar.getTime()));
                    tglPilih = dateFormat1.format(calendar.getTime());
                }
            };

        });
        tvTanggalSurvei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog
                        (context, listener2,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
            }

            DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                    final SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    tglSurvei = dateFormat.format(calendar.getTime());
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            jamsurvei = new SimpleDateFormat("HH:mm:ss", Locale.US).format(calendar.getTime());
                            tvTanggalSurvei.setText(tglSurvei + " " + jamsurvei);
                            Log.d("tglsurvei" + tglSurvei, "");

                            tglSurveiPost = dateFormat1.format(calendar.getTime());
                            tglJamSurvei = tglSurveiPost + " " + jamsurvei;
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            };
        });

        initSpinnerNeed();
        initSpinnerModel();
        initSpinnerSource();
        initSpinnerMerk();
        initSpinnerYear();
        initSpinnerJob();
        initSpinnerEmployee();
        initSpinnerStatusKonsumen();
        initSpinnerStatusAlamat();
        initSpinnerCabangFif();
        countOrder();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countOrder();
                int idPajak = rgTax.getCheckedRadioButtonId();
                int idPemilik = rgOwner.getCheckedRadioButtonId();
                rbTax = findViewById(idPajak);
                rbOwner = findViewById(idPemilik);

                perusahaan = tvPerusahaan.getText().toString();
                jabatan = tvJabatan.getText().toString();

                if (TextUtils.isEmpty(tvPerusahaan.getText())) {
                    lamaKerja = 0;
                } else {
                    lamaKerja = Integer.parseInt(tvLamaKerja.getText().toString());
                }
                if (TextUtils.isEmpty(tvPenghasilan.getText())) {
                    pendapatan = 0;
                } else {
                    pendapatan = Integer.parseInt(tvPenghasilan.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));

                }
                if (TextUtils.isEmpty(tvPengeluaran.getText())) {
                    pengeluaran = 0;
                } else {
                    pengeluaran = Integer.parseInt(tvPengeluaran.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));
                }

                Calendar clndr = Calendar.getInstance(TimeZone.getDefault());
                int year = clndr.get(Calendar.YEAR);
                int month = clndr.get(Calendar.MONTH) + 1;
                String idCnt = String.valueOf(count);
                String idMonth = String.valueOf(month);
                String urut, urutMonth;

                if (idCnt.length() == 1) {
                    urut = "00000" + count;
                } else if (idCnt.length() == 2) {
                    urut = "0000" + count;
                } else if (idCnt.length() == 3) {
                    urut = "000" + count;
                } else if (idCnt.length() == 4) {
                    urut = "00" + count;
                } else if (idCnt.length() == 5) {
                    urut = "0" + count;
                } else {
                    urut = "" + count;
                }

                if (idMonth.length() == 1) {
                    urutMonth = "0" + month;
                } else {
                    urutMonth = "" + month;
                }

                nmrOrder = year + "" + urutMonth + "OR" + urut;
//                Toast.makeText(context, "COBA " + nmrOrder, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(tvContact.getText())) {
                    tvContact.setError("Wajib Diisi !");

                    return;
                } else if (tglSurvei == null) {
//                    tvTanggalSurvei.setError("Wajib Diisi !");
                    Toast.makeText(getApplicationContext(), "Isi Jadwal Survei !", Toast.LENGTH_LONG).show();

                    return;
                } else if (TextUtils.isEmpty(tvNopol.getText())) {
                    tvNopol.setError("Wajib Diisi !");

                    return;
                } else if (rgTax.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Pajak STNK Harus diisi !", Toast.LENGTH_LONG).show();
                } else if (rgOwner.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Pemilik Kend. Harus diisi !", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(otrTaksasi.getText())
                        && !TextUtils.isEmpty(nomorTaksasi.getText()) ){

                    otrTaksasi.setError("Jika Nomor Taksasi diisi , OTR Taksasi wajib diisi");

                    return;

                }else if (!TextUtils.isEmpty(otrTaksasi.getText())
                        && TextUtils.isEmpty(nomorTaksasi.getText()) ){

                    nomorTaksasi.setError("Jika OTR Taksasi diisi , Nomor Taksasi wajib diisi");

                    return;

                }else if (TextUtils.isEmpty(tvOTR.getText())) {
                    tvOTR.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvPlafond.getText())) {
                    tvPlafond.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvDP.getText())) {
                    Toast.makeText(getApplicationContext(), "DP Kosong cek Plafond Pinjaman", Toast.LENGTH_LONG).show();

                    return;
                } else if (TextUtils.isEmpty(tvAngsuran.getText())) {
                    tvAngsuran.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvTenor.getText())) {
                    tvTenor.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvSuretyName.getText())) {
                    tvSuretyName.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvSuretyBirthPlace.getText())) {
                    tvSuretyBirthPlace.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvBirthdate.getText())) {
                    tvBirthdate.setError("Wajib Diisi !");

                    return;
                /*} else if (TextUtils.isEmpty(tvPerusahaan.getText())) {
                    tvPerusahaan.setError("Wajib Diisi !");
                    return;
                } else if (TextUtils.isEmpty(tvJabatan.getText())) {
                    tvJabatan.setError("Wajib Diisi !");
                    return;
                } else if (TextUtils.isEmpty(tvLamaKerja.getText())) {
                    tvLamaKerja.setError("Wajib Diisi !");
                    return;
                } else if (TextUtils.isEmpty(tvPenghasilan.getText())) {
                    tvPenghasilan.setError("Wajib Diisi !");
                    return;*/
                }  else {
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
//                    Toast.makeText(context, "Test" + idContact + idUser + idOutlet + nmrOrder + idDataSource + strStatKons + idCabangFif + tglJamSurvei, Toast.LENGTH_SHORT).show();
                    createOrder();
                }

            }
        });
        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countOrder();
                int idPajak = rgTax.getCheckedRadioButtonId();
                int idPemilik = rgOwner.getCheckedRadioButtonId();
                rbTax = findViewById(idPajak);
                rbOwner = findViewById(idPemilik);

                perusahaan = tvPerusahaan.getText().toString();
                jabatan = tvJabatan.getText().toString();

                if (TextUtils.isEmpty(tvPerusahaan.getText())) {
                    lamaKerja = null;
                } else {
                    lamaKerja = Integer.parseInt(tvLamaKerja.getText().toString());
                }
                if (TextUtils.isEmpty(tvPenghasilan.getText())) {
                    pendapatan = null;
                } else {
                    pendapatan = Integer.parseInt(tvPenghasilan.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));

                }
                if (TextUtils.isEmpty(tvPengeluaran.getText())) {
                    pengeluaran = null;
                } else {
                    pengeluaran = Integer.parseInt(tvPengeluaran.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));
                }

                Calendar clndr = Calendar.getInstance(TimeZone.getDefault());
                int year = clndr.get(Calendar.YEAR);
                int month = clndr.get(Calendar.MONTH) + 1;
                String idCnt = String.valueOf(count);
                String idMonth = String.valueOf(month);
                String urut, urutMonth;

                if (idCnt.length() == 1) {
                    urut = "00000" + count;
                } else if (idCnt.length() == 2) {
                    urut = "0000" + count;
                } else if (idCnt.length() == 3) {
                    urut = "000" + count;
                } else if (idCnt.length() == 4) {
                    urut = "00" + count;
                } else if (idCnt.length() == 5) {
                    urut = "0" + count;
                } else {
                    urut = "" + count;
                }

                if (idMonth.length() == 1) {
                    urutMonth = "0" + month;
                } else {
                    urutMonth = "" + month;
                }

                nmrOrder = year + "" + urutMonth + "OR" + urut;
//                Toast.makeText(context, "COBA " + nmrOrder, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(tvContact.getText())) {
                    tvContact.setError("Wajib Diisi !");

                    return;
                } else if (tglSurvei == null) {
//                    tvTanggalSurvei.setError("Wajib Diisi !");
                    Toast.makeText(getApplicationContext(), "Isi Jadwal Survei !", Toast.LENGTH_LONG).show();

                    return;
                } else if (TextUtils.isEmpty(tvNopol.getText())) {
                    tvNopol.setError("Wajib Diisi !");

                    return;
                } else if (rgTax.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Pajak STNK Harus diisi !", Toast.LENGTH_LONG).show();
                } else if (rgOwner.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Pemilik Kend. Harus diisi !", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(otrTaksasi.getText())
                        && !TextUtils.isEmpty(nomorTaksasi.getText()) ){

                    otrTaksasi.setError("Jika Nomor Taksasi diisi , OTR Taksasi wajib diisi");

                    return;

                }else if (!TextUtils.isEmpty(otrTaksasi.getText())
                        && TextUtils.isEmpty(nomorTaksasi.getText()) ){

                    nomorTaksasi.setError("Jika OTR Taksasi diisi , Nomor Taksasi wajib diisi");

                    return;

                }else if (TextUtils.isEmpty(tvOTR.getText())) {
                    tvOTR.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvPlafond.getText())) {
                    tvPlafond.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvDP.getText())) {
                    Toast.makeText(getApplicationContext(), "DP Kosong cek Plafond Pinjaman", Toast.LENGTH_LONG).show();

                    return;
                } else if (TextUtils.isEmpty(tvAngsuran.getText())) {
                    tvAngsuran.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvTenor.getText())) {
                    tvTenor.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvSuretyName.getText())) {
                    tvSuretyName.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvSuretyBirthPlace.getText())) {
                    tvSuretyBirthPlace.setError("Wajib Diisi !");

                    return;
                } else if (TextUtils.isEmpty(tvBirthdate.getText())) {
                    tvBirthdate.setError("Wajib Diisi !");

                    return;

                } else {
                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);
//                    Toast.makeText(context, "Test" + idContact + idUser + idOutlet + nmrOrder + idDataSource + strStatKons + idCabangFif + tglJamSurvei, Toast.LENGTH_SHORT).show();
                    createOrder();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

//    private void focus(final TextView tv) {
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.scrollTo(0, tv.getScrollY());
//            }
//        });
//    }

    private TextWatcher autoTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            sumDp();
            if (!tvPlafond.getText().toString().isEmpty() && !tvOTR.getText().toString().isEmpty() && jumlah >= 0) {
                tvDP.setText(formatter.format(jumlah));
            } else {
                tvDP.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void sumDp() {

        if (!tvOTR.getText().toString().isEmpty() && !tvPlafond.getText().toString().isEmpty()) {
            Integer MinOtr;
            Integer MinPlafond;
            MinOtr = Integer.parseInt(tvOTR.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));
            MinPlafond = Integer.parseInt(tvPlafond.getText().toString().replaceAll(",", "").replaceAll("\\.", ""));
            jumlah = MinOtr - MinPlafond;
        }

    }

    private void createOrder() {

        String stsAddress;
        if (strStatusAlamat.equalsIgnoreCase("SESUAI KTP")) {
            stsAddress = "S";
        } else {
            stsAddress = "TS";
        }

        mApiService.orderCreate(idContact, idUser, idOutlet, 4, 2, nmrOrder, idDataSource, strStatKons, idCabangFif, stsAddress, tglJamSurvei).enqueue(new Callback<RespPost>() {
            @Override
            public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                idOrder = response.body().getId();
                String taxStatus;
                if (rbTax.getText().toString().equals("Hidup")) {
                    taxStatus = "Y";
                } else {
                    taxStatus = "N";
                }

                String valOtrTaksasi = "",valNomorTaksasi ="";

                if (!TextUtils.isEmpty(otrTaksasi.getText())
                        && !TextUtils.isEmpty(nomorTaksasi.getText()) ){

                    valOtrTaksasi = otrTaksasi.getText().toString().replaceAll(",", "").replaceAll("\\.", "").toString();
                    valNomorTaksasi = nomorTaksasi.getText().toString();

                }

                mApiService.orderProductUfiCreate(idOrder, idUnit, idUser, tvNopol.getText().toString(), taxStatus, rbOwner.getText().toString(),valOtrTaksasi,valNomorTaksasi)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        });

                mApiService.orderLoanCreate(idOrder, idUser, Integer.parseInt(tvPlafond.getText().toString().replaceAll(",", "").replaceAll("\\.", "")), Integer.parseInt(tvDP.getText().toString().replaceAll(",", "").replaceAll("\\.", "")),
                        Integer.parseInt(tvTenor.getText().toString()), strNeed, Integer.parseInt(tvAngsuran.getText().toString().replaceAll(",", "").replaceAll("\\.", "")),
                        Integer.parseInt(tvOTR.getText().toString().replaceAll(",", "").replaceAll("\\.", ""))).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });

                mApiService.orderSuretyCreate(idOrder, idUser, tvSuretyName.getText().toString(), tvSuretyBirthPlace.getText().toString(),
                        tglPilih, idJob, perusahaan, idStatusPenjamin, jabatan, lamaKerja, pendapatan, pengeluaran).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });

                mApiService.userLogCreate(5, idOrder, "create", idUser).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });

                loading.dismiss();
                finish();
                Intent intent = new Intent(context, DetailDealActivity.class);
                intent.putExtra("idOrder", idOrder);
                intent.putExtra("idContact", idContact);
                context.startActivity(intent);

            }

            @Override
            public void onFailure(Call<RespPost> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void countOrder() {
        mApiService.listOrder(null, null, null).enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listOrder = response.body().getData();

                        try {
                            final List<Integer> list = new ArrayList<>();
                            for (int i = 0; i < listOrder.size(); i++) {
                                list.add(listOrder.get(i).getId());
                            }
                            count = Collections.max(list) + 1;
                        } catch (Exception e) {
                            e.printStackTrace();
                            count = 1;
                        }

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListOrder> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerStatusKonsumen() {
        String[] statuskonsumen = getResources().getStringArray(R.array.status_konsumen);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, statuskonsumen);
        tvStatusKonsumen.setAdapter(stringArrayAdapter);

        tvStatusKonsumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strStatKons = tvStatusKonsumen.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                strStatKons = null;
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
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListDataSource> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerCabangFif() {
        mApiService.cabangFif("", "").enqueue(new Callback<RespListCabangFif>() {
            @Override
            public void onResponse(Call<RespListCabangFif> call, Response<RespListCabangFif> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listCabangFif = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listCabangFif.size(); i++) {
                            list.add(listCabangFif.get(i).getBranchName());
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
                        spCabangFif.setAdapter(adapter);
                        spCabangFif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strCabangFif = spCabangFif.getSelectedItem().toString();
                                initSpinnerPosFif();
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
            public void onFailure(Call<RespListCabangFif> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerPosFif() {
        mApiService.cabangFif(strCabangFif, "").enqueue(new Callback<RespListCabangFif>() {
            @Override
            public void onResponse(Call<RespListCabangFif> call, final Response<RespListCabangFif> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listCabangFif = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listCabangFif.size(); i++) {
                            list.add(listCabangFif.get(i).getPosName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        spPosFif.setAdapter(adapter);
                        spPosFif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idCabangFif = listCabangFif.get(position).getId();
//                                Toast.makeText(context, "Cabang" + idCabangFif, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<RespListCabangFif> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerStatusAlamat() {
        String[] statusalamat = getResources().getStringArray(R.array.status_alamat);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, statusalamat);
        spStsAlamat.setAdapter(stringArrayAdapter);

        spStsAlamat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strStatusAlamat = spStsAlamat.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                strStatKons = null;
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
                        spMerk.setAdapter(adapter);
                        spMerk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strMerk = spMerk.getSelectedItem().toString();
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
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListUnitUfi> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerYear() {
        mApiService.unitUfi(idBranch, null, null).enqueue(new Callback<RespListUnitUfi>() {
            @Override
            public void onResponse(Call<RespListUnitUfi> call, Response<RespListUnitUfi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listUnitUfis = response.body().getData();
                        List<Integer> list = new ArrayList<>();
                        for (int i = 0; i < listUnitUfis.size(); i++) {
                            list.add(listUnitUfis.get(i).getYear());
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
                        spYear.setAdapter(adapter);
                        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strYear = spYear.getSelectedItem().toString();
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
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListUnitUfi> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
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
                            list.add(listUnitUfis.get(i).getType());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, list);
                        spModel.setAdapter(adapter);
                        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idUnit = listUnitUfis.get(position).getId();
                                if (listUnitUfis.get(position).getOtr() != null) {
                                    strOTR = listUnitUfis.get(position).getOtr().toString();
                                    tvOTR.setText(strOTR);
                                } else {
                                    tvOTR.setText("");
                                }
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
            public void onFailure(Call<RespListUnitList> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerNeed() {
        mApiService.need().enqueue(new Callback<RespListNeed>() {
            @Override
            public void onResponse(Call<RespListNeed> call, Response<RespListNeed> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        final List<ListNeed> listNeed = response.body().getData();
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < listNeed.size(); i++) {
                            list.add(listNeed.get(i).getNeed());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        tvNeed.setAdapter(adapter);
                        tvNeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strNeed = listNeed.get(position).getNeed();
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
            public void onFailure(Call<RespListNeed> call, Throwable t) {
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
                        spJob.setAdapter(adapterJob);
                        spJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListJob> call, Throwable t) {
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
                        ArrayAdapter<String> adapterSource = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        spStatus.setAdapter(adapterSource);
                        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idStatusPenjamin = listEmployees.get(position).getId();
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

}
