package com.lmu.warungdana;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lmu.warungdana.Response.DetailAdditionalContact;
import com.lmu.warungdana.Response.DetailContact;
import com.lmu.warungdana.Response.DetailOrder;
import com.lmu.warungdana.Response.DetailOrderLoan;
import com.lmu.warungdana.Response.DetailOrderSurety;
import com.lmu.warungdana.Response.DetailProductUFI;
import com.lmu.warungdana.Response.ListAddress;
import com.lmu.warungdana.Response.ListPhone;
import com.lmu.warungdana.Response.RespListAddress;
import com.lmu.warungdana.Response.RespListPhone;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PrintActivity extends AppCompatActivity {

    private ApiEndPoint mApiService;
    private Integer idContact, idOrder;
    TextView namaCfa, tglPooling, jamPooling, sumberOrder, stsKonsumen, namaOutlet, cabangFif, posFif, tglSurvei, jamSurvei,
            namaPemohon, noHp, noHp2, tmpLhr, tglLhr, pNama, pTmpLhr, pTglLhr, namaIbu, jmlTanggungan,
            pekerjaan, stsKaryawan, jabatan, lamaKerja, perusahaan, penghasilan, pengeluaran,
            pPekerjaan, pStatusKaryawan, pJabatan, pLamaKerja, pPerusahaan, pPenghasilan, pPengeluaran,
            otr, plafond, dp, angsuran, tenor, keperluan,
            merk, type, vehicle, tahunKendaraan, nopol, sPajak, owner,
            alamat, rtrw, kel, kec, kab, prov, sRumah, sAlamat;
    FloatingActionButton btnScrrenShot;
    String model, tahun;
    NumberFormat formatter = new DecimalFormat("#,###");
    SharedPrefManager sharedPrefManager;
    String lokasiGambar;
    ScrollView scrollView;
    ProgressDialog progressDialog;

    /*String nik, get_nama, get_noTlp, get_alamat, get_rt, get_rw, get_prov, get_kec, get_pendidikan, get_pekerjaan, get_perusahaan,
            get_gaji, get_tanggungan, get_pengeluaran, get_agama,
            get_tglLahir, get_t4Lahir, get_rumah, get_kawin, get_karyawan;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        sharedPrefManager = new SharedPrefManager(this);

        namaCfa = findViewById(R.id.tv_hsl_nama_cfa);
        tglPooling = findViewById(R.id.tv_hsl_tgl_pooling);
        jamPooling = findViewById(R.id.tv_hsl_jam_pooling);
        sumberOrder = findViewById(R.id.tv_hsl_sumber_order);
        stsKonsumen = findViewById(R.id.tv_hsl_status_konsumen);
        namaOutlet = findViewById(R.id.tv_hsl_nama_outlet);
        cabangFif = findViewById(R.id.tv_hsl_cabang_fif);
        posFif = findViewById(R.id.tv_hsl_pos_fif);
        tglSurvei = findViewById(R.id.tv_hsl_tgl_survey);
        jamSurvei = findViewById(R.id.tv_hsl_jam_survey);

        namaPemohon = findViewById(R.id.tv_hsl_nama_pemohon);
        noHp = findViewById(R.id.tv_hsl_no_hp);
        noHp2 = findViewById(R.id.tv_hsl_no_hp_2);
        tmpLhr = findViewById(R.id.tv_hsl_tempat_lahir);
        tglLhr = findViewById(R.id.tv_hsl_tgl_lahir);
        pNama = findViewById(R.id.tv_hsl_nama_penjamin);
        pTmpLhr = findViewById(R.id.tv_hsl_ptempat_lahir);
        pTglLhr = findViewById(R.id.tv_hsl_ptgl_lahir);
        namaIbu = findViewById(R.id.tv_hsl_ibu_kandung);
        jmlTanggungan = findViewById(R.id.tv_hsl_jml_tanggungan);

        pekerjaan = findViewById(R.id.tv_hsl_pekerjaan);
        stsKaryawan = findViewById(R.id.tv_hsl_status_karyawan);
        jabatan = findViewById(R.id.tv_hsl_jabatan);
        lamaKerja = findViewById(R.id.tv_hsl_lama_kerja);
        perusahaan = findViewById(R.id.tv_hsl_perusahaan);
        penghasilan = findViewById(R.id.tv_hsl_penghasilan);
        pengeluaran = findViewById(R.id.tv_hsl_pengeluaran);

        pPekerjaan = findViewById(R.id.tv_hsl_pekerjaan_pasangan);
        pStatusKaryawan = findViewById(R.id.tv_hsl_status_pasangan);
        pJabatan = findViewById(R.id.tv_hsl_jabatan_pasangan);
        pLamaKerja = findViewById(R.id.tv_hsl_lama_kerja_pasangan);
        pPerusahaan = findViewById(R.id.tv_hsl_perusahaan_pasangan);
        pPenghasilan = findViewById(R.id.tv_hsl_penghasilan_pasangan);
        pPengeluaran = findViewById(R.id.tv_hsl_pengeluaran_pasangan);

        otr = findViewById(R.id.tv_hsl_otr);
        plafond = findViewById(R.id.tv_hsl_plafond);
        dp = findViewById(R.id.tv_hsl_dp);
        angsuran = findViewById(R.id.tv_hsl_angsuran);
        tenor = findViewById(R.id.tv_hsl_tenor);
        keperluan = findViewById(R.id.tv_hsl_keperluan);

        merk = findViewById(R.id.tv_hsl_merk_kendaraan);
        type = findViewById(R.id.tv_hsl_type_kendaraan);
        vehicle = findViewById(R.id.tv_hsl_vehicle);
        tahunKendaraan = findViewById(R.id.tv_hsl_thn_kendaraan);
        nopol = findViewById(R.id.tv_hsl_nopol);
        sPajak = findViewById(R.id.tv_hsl_pajak);
        owner = findViewById(R.id.tv_hsl_owner);

        alamat = findViewById(R.id.tv_hsl_alamat);
        rtrw = findViewById(R.id.tv_hsl_rtrw);
        kel = findViewById(R.id.tv_hsl_kelurahan);
        kec = findViewById(R.id.tv_hsl_kecamatan);
        kab = findViewById(R.id.tv_hsl_kabupaten);
        prov = findViewById(R.id.tv_hsl_provinsi);
        sRumah = findViewById(R.id.tv_hsl_sts_rumah);
        sAlamat = findViewById(R.id.tv_hsl_sts_alamat);

        btnScrrenShot = findViewById(R.id.btnScreenShot);

        scrollView = findViewById(R.id.scrlPrint);

        mApiService = UtilsApi.getAPIService();
        idContact = getIntent().getIntExtra("idContact", 0);
        idOrder = getIntent().getIntExtra("idOrder", 0);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        progressDialog = new ProgressDialog(this);
        getDataPrint();

    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMMM yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }

    private String convertTime2(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate2 = format1.format(date);
        return convertedDate2;
    }

    private void getDataPrint() {

        progressDialog.setMessage("Proses Pengambilan Data Print");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mApiService.orderDetail(idOrder).enqueue(new Callback<DetailOrder>() {
            @Override
            public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getCreatedAt() != null) {
                            tglPooling.setText(convertTime(response.body().getCreatedAt()));
                        } else {
                            tglPooling.setText("Empty");
                        }

                        if (response.body().getCreatedAt() != null) {
                            jamPooling.setText(convertTime2(response.body().getCreatedAt()));
                        } else {
                            jamPooling.setText("Empty");
                        }

                        if (response.body().getMstDataSourceDatasource() != null) {
                            sumberOrder.setText(response.body().getMstDataSourceDatasource());
                        } else {
                            sumberOrder.setText("Empty");
                        }

                        if (response.body().getCategory() != null) {
                            stsKonsumen.setText(response.body().getCategory());
                        } else {
                            stsKonsumen.setText("Empty");
                        }

                        if (response.body().getCabangFif() != null) {
                            cabangFif.setText(response.body().getCabangFif());
                        } else {
                            cabangFif.setText("Empty");
                        }

                        if (response.body().getCabangFif() != null) {
                            posFif.setText(response.body().getPosFif());
                        } else {
                            posFif.setText("Empty");
                        }

                        if (response.body().getSurvey() != null) {
                            tglSurvei.setText(convertTime(response.body().getSurvey()));
                        } else {
                            tglSurvei.setText("Empty");
                        }

                        if (response.body().getSurvey() != null) {
                            jamSurvei.setText(convertTime2(response.body().getSurvey()));
                        } else {
                            jamSurvei.setText("Empty");
                        }

                        if (response.body().getStatusAddress() != null) {
                            if (response.body().getStatusAddress().equalsIgnoreCase("S")) {
                                sAlamat.setText("SESUAI");
                            } else {
                                sAlamat.setText("TIDAK SESUAI");
                            }
                        } else {
                            sAlamat.setText("Empty");
                        }

                        getDetailContact();

                    } else {

                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }
            }

            @Override
            public void onFailure(Call<DetailOrder> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });

    }

    private void showDialogGagal(String message) {

        progressDialog.dismiss();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PrintActivity.this);
        builder1.setMessage(message);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Iya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        getDataPrint();
                    }
                });

        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder1.show();
    }

    private void getDetailContact(){

        mApiService.detailContact(idContact).enqueue(new Callback<DetailContact>() {
            @Override
            public void onResponse(Call<DetailContact> call, Response<DetailContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getFirstName() != null & response.body().getLastName() != null) {
                            namaPemohon.setText(response.body().getFirstName().toUpperCase() + " " + response.body().getLastName().toUpperCase());
                        } else if (response.body().getFirstName() != null & response.body().getLastName() == null) {
                            namaPemohon.setText(response.body().getFirstName().toUpperCase());
                        } else if (response.body().getFirstName() == null & response.body().getLastName() != null) {
                            namaPemohon.setText(response.body().getLastName().toUpperCase());
                        } else {
                            namaPemohon.setText("Empty");
                        }

                        if (response.body().getBirthPlace() != null) {
                            tmpLhr.setText(response.body().getBirthPlace().toUpperCase());
                        } else {
                            tmpLhr.setText("Empty");
                        }

                        if (response.body().getBirthDate() != null) {
                            tglLhr.setText(convertTime(response.body().getBirthDate()));
                        } else {
                            tglLhr.setText("Empty");
                        }

                        if (response.body().getMstJobJob() != null) {
                            pekerjaan.setText(response.body().getMstJobJob());
                        } else {
                            pekerjaan.setText("Empty");
                        }

                        getDetailAdditional();

                    } else {
                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }

            }

            @Override
            public void onFailure(Call<DetailContact> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });


    }
    private void getDetailAdditional(){
        mApiService.detailAdditionalContact(idContact).enqueue(new Callback<DetailAdditionalContact>() {
            @Override
            public void onResponse(Call<DetailAdditionalContact> call, Response<DetailAdditionalContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getMother() != null) {
                            namaIbu.setText(response.body().getMother().toUpperCase());
                        } else {
                            namaIbu.setText("Empty");
                        }

                        if (response.body().getFamily() != null) {
                            jmlTanggungan.setText(response.body().getFamily().toString() + " Orang");
                        } else {
                            jmlTanggungan.setText("Empty");
                        }

                        if (response.body().getContactMstStatusEmployeeStatus() != null) {
                            stsKaryawan.setText(response.body().getContactMstStatusEmployeeStatus());
                        } else {
                            stsKaryawan.setText("Empty");
                        }

                        if (response.body().getPosition() != null) {
                            jabatan.setText(response.body().getPosition().toUpperCase());
                        } else {
                            jabatan.setText("Empty");
                        }

                        if (response.body().getWorkingTime() != null) {
                            lamaKerja.setText(response.body().getWorkingTime().toString() + " Tahun");
                        } else {
                            lamaKerja.setText("Empty");
                        }

                        if (response.body().getCompany() != null) {
                            perusahaan.setText(response.body().getCompany().toUpperCase());
                        } else {
                            perusahaan.setText("Empty");
                        }

                        if (response.body().getIncome() != null) {
                            penghasilan.setText(formatter.format(response.body().getIncome()));
                        } else {
                            penghasilan.setText("Empty");
                        }

                        if (response.body().getOutlay() != null) {
                            pengeluaran.setText(formatter.format(response.body().getOutlay()));
                        } else {
                            pengeluaran.setText("Empty");
                        }

                        if (response.body().getContactMstStatusPlaceStatus() != null) {
                            sRumah.setText(response.body().getContactMstStatusPlaceStatus().toString());
                        } else {
                            sRumah.setText("Empty");
                        }

                        getOrderSurvey();

                    } else {
                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }

            }

            @Override
            public void onFailure(Call<DetailAdditionalContact> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });
    }
    private void getOrderSurvey(){
        mApiService.orderSuretyDetail(idOrder).enqueue(new Callback<DetailOrderSurety>() {
            @Override
            public void onResponse(Call<DetailOrderSurety> call, Response<DetailOrderSurety> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getName() != null) {
                            pNama.setText(response.body().getName().toUpperCase());
                        } else {
                            pNama.setText("Empty");
                        }

                        if (response.body().getBirthPlace() != null) {
                            pTmpLhr.setText(response.body().getBirthPlace().toUpperCase());
                        } else {
                            pTmpLhr.setText("Empty");
                        }

                        if (response.body().getBirthDate() != null) {
                            pTglLhr.setText(convertTime(response.body().getBirthDate()));
                        } else {
                            pTglLhr.setText("Empty");
                        }

                        if (response.body().getMstJobJob() != null) {
                            pPekerjaan.setText(response.body().getMstJobJob());
                        } else {
                            pPekerjaan.setText("Empty");
                        }

                        if (response.body().getContactMstStatusEmployeeStatus() != null) {
                            pStatusKaryawan.setText(response.body().getContactMstStatusEmployeeStatus());
                        } else {
                            pStatusKaryawan.setText("Empty");
                        }

                        if (response.body().getPosition() != null) {
                            pJabatan.setText(response.body().getPosition().toUpperCase());
                        } else {
                            pJabatan.setText("Empty");
                        }

                        if (response.body().getWorkingTime() != null) {
                            pLamaKerja.setText(response.body().getWorkingTime() + " Tahun");
                        } else {
                            pLamaKerja.setText("Empty");
                        }

                        if (response.body().getCompany() != null) {
                            pPerusahaan.setText(response.body().getCompany().toUpperCase());
                        } else {
                            pPerusahaan.setText("Empty");
                        }

                        if (response.body().getIncome() != null) {
                            pPenghasilan.setText(formatter.format(response.body().getIncome()));
                        } else {
                            pPenghasilan.setText("Empty");
                        }

                        if (response.body().getOutlay() != null) {
                            pPengeluaran.setText(formatter.format(response.body().getOutlay()));
                        } else {
                            pPengeluaran.setText("Empty");
                        }

                        getProductDetail();

                    } else {
                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }
            }

            @Override
            public void onFailure(Call<DetailOrderSurety> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });
    }
    private void getProductDetail(){
        mApiService.productUfiDetail(idOrder).enqueue(new Callback<DetailProductUFI>() {
            @Override
            public void onResponse(Call<DetailProductUFI> call, Response<DetailProductUFI> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        model = response.body().getMstUnitModel();
                        tahun = response.body().getMstUnitYear().toString();

                        if (response.body().getMstUnitOtr() != null) {
                            otr.setText(formatter.format(response.body().getMstUnitOtr()));
                        } else {
                            otr.setText("Empty");
                        }

                        if (response.body().getMstUnitMerk() != null) {
                            merk.setText(response.body().getMstUnitMerk().trim());
                        } else {
                            merk.setText("Empty");
                        }

                        if (response.body().getMstUnitType() != null) {
                            type.setText(response.body().getMstUnitType().trim());
                        } else {
                            type.setText("Empty");
                        }

                        if (response.body().getMstUnitKodeUnit() != null) {
                            vehicle.setText(response.body().getMstUnitKodeUnit());
                        } else {
                            vehicle.setText("Empty");
                        }

                        if (response.body().getMstUnitYear() != null) {
                            tahunKendaraan.setText(response.body().getMstUnitYear().toString());
                        } else {
                            tahunKendaraan.setText("Empty");
                        }

                        if (response.body().getNopol() != null) {
                            nopol.setText(response.body().getNopol());
                        } else {
                            nopol.setText("Empty");
                        }

                        if (response.body().getTaxStatus() != null) {
                            if (response.body().getTaxStatus().equalsIgnoreCase("Y")) {
                                sPajak.setText("HIDUP");
                            } else {
                                sPajak.setText("MATI");
                            }
                        } else {
                            sPajak.setText("Empty");
                        }

                        if (response.body().getOwner() != null) {
                            owner.setText("MILIK " + response.body().getOwner().toUpperCase());
                        } else {
                            owner.setText("Empty");
                        }

                        getOrderRoanDetail();

                    } else {
                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }
            }

            @Override
            public void onFailure(Call<DetailProductUFI> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });
    }
    private void getOrderRoanDetail(){
        mApiService.orderLoanDetail(idOrder).enqueue(new Callback<DetailOrderLoan>() {
            @Override
            public void onResponse(Call<DetailOrderLoan> call, Response<DetailOrderLoan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        if (response.body().getPlafond() != null) {
                            plafond.setText(formatter.format(response.body().getPlafond()));
                        } else {
                            plafond.setText("Empty");
                        }

                        if (response.body().getOtr_custom() != null){

                            otr.setText(formatter.format(response.body().getOtr_custom()));
                        }

                        if (response.body().getDownPayment() != null) {
                            dp.setText(formatter.format(response.body().getDownPayment()));
                        } else {
                            dp.setText("Empty");
                        }

                        if (response.body().getInstallment() != null) {
                            angsuran.setText(formatter.format(response.body().getInstallment()));
                        } else {
                            angsuran.setText("Empty");
                        }

                        if (response.body().getTenor() != null) {
                            tenor.setText(response.body().getTenor().toString() + " Bulan");
                        } else {
                            tenor.setText("Empty");
                        }

                        if (response.body().getNeed() != null) {
                            keperluan.setText(response.body().getNeed());
                        } else {
                            keperluan.setText("Empty");
                        }

                        getListAddressContact();

                    } else {
                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }

            }

            @Override
            public void onFailure(Call<DetailOrderLoan> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });
    }
    private void getListAddressContact(){
        mApiService.listAddressContact(idContact).enqueue(new Callback<RespListAddress>() {
            @Override
            public void onResponse(Call<RespListAddress> call, Response<RespListAddress> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        List<ListAddress> listLeads = response.body().getData();

                        if (listLeads.get(0).getAddress() != null) {
                            alamat.setText(listLeads.get(0).getAddress().trim().toUpperCase());
                        } else {
                            alamat.setText("Empty");
                        }

                        if (listLeads.get(0).getRt() != null & listLeads.get(0).getRw() != null) {
                            rtrw.setText(listLeads.get(0).getRt() + " / " + listLeads.get(0).getRw());
                        } else if (listLeads.get(0).getRt() != null & listLeads.get(0).getRw() == null) {
                            rtrw.setText(listLeads.get(0).getRt() + " / " + "-");
                        } else if (listLeads.get(0).getRt() == null & listLeads.get(0).getRw() != null) {
                            rtrw.setText("-" + " / " + listLeads.get(0).getRw());
                        } else {
                            rtrw.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressKelurahan() != null) {
                            kel.setText(listLeads.get(0).getMstAddressKelurahan().trim().toUpperCase());
                        } else {
                            kel.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressKecamatan() != null) {
                            kec.setText(listLeads.get(0).getMstAddressKecamatan().trim().toUpperCase());
                        } else {
                            kec.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressKabupaten() != null) {
                            kab.setText(listLeads.get(0).getMstAddressKabupaten().trim().toUpperCase());
                        } else {
                            kab.setText("Empty");
                        }

                        if (listLeads.get(0).getMstAddressProvinsi() != null) {
                            prov.setText(listLeads.get(0).getMstAddressProvinsi().trim().toUpperCase());
                        } else {
                            prov.setText("Empty");
                        }

                        getListPhoneContact();

                    } else {
                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }
            }

            @Override
            public void onFailure(Call<RespListAddress> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });
    }
    private void getListPhoneContact(){
        mApiService.listPhoneContact(idContact).enqueue(new Callback<RespListPhone>() {
            @Override
            public void onResponse(Call<RespListPhone> call, Response<RespListPhone> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        List<ListPhone> listLeads = response.body().getData();

                        if (listLeads.get(0).getNumber() != null) {
                            noHp.setText(listLeads.get(0).getNumber());
                        } else {
                            noHp.setText("Empty");
                        }

                        if (listLeads.get(1).getNumber() != null) {
                            noHp2.setText(listLeads.get(1).getNumber());
                        } else {
                            noHp2.setText("Empty");
                        }

                        progressDialog.dismiss();

                    } else {
                        showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                    }
                } else {
                    showDialogGagal("Data tidak ditemukan , apakah ingin mengambil data ulang");
                }
            }

            @Override
            public void onFailure(Call<RespListPhone> call, Throwable t) {
                showDialogGagal("Koneksi Bermasalah , apakah ingin mengambil data ulang");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        btnScrrenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot(scrollView,scrollView.getChildAt(0).getHeight(),scrollView.getChildAt(0).getWidth());
                saveBitmap(bitmap);

            }
        });

        namaCfa.setText(sharedPrefManager.getSPName().toUpperCase());
        namaOutlet.setText(sharedPrefManager.getSPOutletName());

    }

    public Bitmap takeScreenshot(ScrollView scrollView, int height, int width) {

        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        Drawable drawable = scrollView.getBackground();

        if (drawable != null){

            drawable.draw(c);
        }else {

            c.drawColor(Color.WHITE);
        }

        scrollView.draw(c);

        return b;

    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() +"/" + model.toLowerCase() + tahun.toLowerCase() + ".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            lokasiGambar = imagePath.getPath();
            Log.d("Lokasi image",lokasiGambar);


                File file = new File(lokasiGambar);
                Uri uri = FileProvider.getUriForFile(PrintActivity.this,BuildConfig.APPLICATION_ID+".provider",file);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setAction(Intent.ACTION_SEND)
                        .setDataAndTypeAndNormalize(uri, "image/jpeg")
                        .putExtra(Intent.EXTRA_STREAM, uri);


                startActivityForResult(Intent.createChooser(shareIntent,"Share Image"),10);




        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10){

            File file = new File(lokasiGambar);
            file.delete();
            finish();
        }
    }
}