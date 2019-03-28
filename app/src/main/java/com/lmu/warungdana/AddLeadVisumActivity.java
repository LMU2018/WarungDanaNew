package com.lmu.warungdana;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.Response.DetailLead;
import com.lmu.warungdana.Response.DetailTarget;
import com.lmu.warungdana.Response.ListLogDesc;
import com.lmu.warungdana.Response.ListLogStatus;
import com.lmu.warungdana.Response.ProgressRequestBody;
import com.lmu.warungdana.Response.RespListLogStatus;
import com.lmu.warungdana.Response.RespPost;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.CalendarContract.CalendarCache.URI;

public class AddLeadVisumActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {
    private Integer idData, idSource, idUser;
    private ApiEndPoint mApiService;
    private TextView nama, status, tanggal,tvPhoto;
    private Context context;
    private Spinner spLogStatus;
    List<ListLogStatus> listLogStatuses;
    List<ListLogDesc> listLogDescs;
    private Integer idDesc, idLog, idLead, idStatus;
    private Calendar calendar;
    private ImageView imgList;
    private Button btnCheck;
    private String tglPilih, statusLead;
    ProgressDialog loading ,progressDialogUploadPhoto;
    SharedPrefManager sharedPrefManager;
    String pathImage = "";
    File actualImage;
    File compressedImage;
    ImageView imageView;
    String[] value = new String[]{
            "Gallery",
            "Kamera"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_add_visum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_delete_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mApiService = UtilsApi.getAPIService();
        idData = getIntent().getIntExtra("idData", 0);
        getDetailDataLead();
        nama = findViewById(R.id.tvItemNama);
        status = findViewById(R.id.tvItemDeskripsi);
        spLogStatus = findViewById(R.id.spLogStatus);
        sharedPrefManager = new SharedPrefManager(this);
        idUser = sharedPrefManager.getSpId();
        tanggal = findViewById(R.id.tvTangal);
        calendar = Calendar.getInstance();
        imgList = findViewById(R.id.imgList);
        btnCheck = findViewById(R.id.btnCheck);
        tvPhoto = findViewById(R.id.tvPhoto);
        imageView = findViewById(R.id.imgPhoto);
        loading = new ProgressDialog(context);
        loading.setMessage("Tunggu");
//                    loading.setIndeterminate(true);
        loading.setCancelable(false);
        listener();
    }

    private void listener() {

        mApiService.visumStatus().enqueue(new Callback<RespListLogStatus>() {
            @Override
            public void onResponse(Call<RespListLogStatus> call, Response<RespListLogStatus> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listLogStatuses = response.body().getData();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listLogStatuses.size(); i++) {
                            list.add(listLogStatuses.get(i).getStatus());
                        }
                        ArrayAdapter<String> adapterSource = new ArrayAdapter<>(context, R.layout.spinner_item_log, list);
                        spLogStatus.setAdapter(adapterSource);
                        spLogStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idStatus = listLogStatuses.get(position).getId();
                                Log.d("IDSTATUS",""+idStatus);
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
            public void onFailure(Call<RespListLogStatus> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,listener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1);
                datePickerDialog.show();
//                new DatePickerDialog(context, listener,
//                        calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }


            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    tanggal.setText(dateFormat.format(calendar.getTime()));
                    tglPilih = dateFormat1.format(calendar.getTime());
                }
            };
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tanggal.getText()) || pathImage.equals("")) {
//                    tanggal.setError("Wajib Diisi Semua");
                    Toast.makeText(getApplicationContext(),"Wajib diisi semua",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    loading.show();
//                    loading = ProgressDialog.show(context, null, "Tunggu...", true, false);

//                    Log.d("IDSTATUSE",""+idStatus);
                    mApiService.leadVisumCreate(idData, idUser, tglPilih, idStatus).enqueue(new Callback<RespPost>() {
                        @Override
                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {

                            if(response.isSuccessful()){

                                if (response.body().getApiStatus() == 1){

                                    loading.dismiss();
                                    uploadPhoto(response.body().getId());

                                }else{
                                    loading.dismiss();
                                    Toast.makeText(context, "Gagal Menyimpan Visum", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                loading.dismiss();
                                Toast.makeText(context, "Gagal Menyimpan Visum", Toast.LENGTH_LONG).show();

                            }
//                            finish();
//                            loading.dismiss();
//                            Toast.makeText(context, "Berhasil menambah visum!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<RespPost> call, Throwable t) {
                            loading.dismiss();
                            Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            private void uploadPhoto(Integer idTargetVisumX) {

                progressDialogUploadPhoto = new ProgressDialog(AddLeadVisumActivity.this);
                progressDialogUploadPhoto.setMax(100);
                progressDialogUploadPhoto.setMessage("Sedang Menunggah Foto");
                progressDialogUploadPhoto.setTitle("Mohon tunggu");
                progressDialogUploadPhoto.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialogUploadPhoto.show();


                MultipartBody.Part fotoUpload = null;
                File file = new File(pathImage);

                ProgressRequestBody fileBody = new ProgressRequestBody(file, "multipart/form-data", AddLeadVisumActivity.this);

//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);

                fotoUpload = MultipartBody.Part.createFormData("photo", file.getName(), fileBody);
                RequestBody idTarget = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(idData));
                RequestBody idTargetVisum = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(idTargetVisumX));
                RequestBody reqIdCms = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(idUser));

                mApiService.uploadLeadVisumPhoto(idTarget,idTargetVisum,fotoUpload,reqIdCms).enqueue(new Callback<RespPost>() {
                    @Override
                    public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                        if (response.isSuccessful()) {

                            if (response.body() != null) {

                                if (response.body().getApiStatus() == 1){

                                    finish();
                                    progressDialogUploadPhoto.dismiss();
                                    Toast.makeText(context, "Berhasil menambah visum!", Toast.LENGTH_LONG).show();

                                }else{

                                    progressDialogUploadPhoto.dismiss();
                                    Toast.makeText(context, "Gagal menambah visum!", Toast.LENGTH_LONG).show();
                                }


                            } else {

                                progressDialogUploadPhoto.dismiss();
                                Toast.makeText(context, "Gagal menambah visum!", Toast.LENGTH_LONG).show();

                            }

                        } else {

                            progressDialogUploadPhoto.dismiss();
                            Toast.makeText(context, "Error , gagal menambahkan visum!", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<RespPost> call, Throwable t) {

                        progressDialogUploadPhoto.dismiss();
                        Toast.makeText(context, "Periksa koneksi anda", Toast.LENGTH_LONG).show();
                    }
                });




            }


        });

        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerdialogbuilder = new AlertDialog.Builder(AddLeadVisumActivity.this);
                alerdialogbuilder.setTitle("Ambil Gambar Menggunakan");
                alerdialogbuilder.setItems(value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selected = Arrays.asList(value).get(i);

                        if (selected == "Gallery") {

                            getImageGallery();

                        } else if (selected == "Kamera") {

                            captureImage();
                        }
                    }
                });
                AlertDialog dialog = alerdialogbuilder.create();
                dialog.show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();




    }

    private void getDetailDataLead() {
        mApiService.leadDetail(idData).enqueue(new Callback<DetailLead>() {
            @Override
            public void onResponse(Call<DetailLead> call, Response<DetailLead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        if (response.body().getLastName() == null) {
                            nama.setText(response.body().getFirstName());
                        } else {
                            nama.setText(response.body().getFirstName() + " " + response.body().getLastName());
                        }
                        if (response.body().getDescription() != null) {
                            status.setText(response.body().getDescription());
                        } else {
                            status.setText("Belum di Follow Up");
                        }
                        statusLead = response.body().getLeadMstStatusStatus();
                        if (response.body().getIdLeadMstStatus() != null) {
                            idLead = 0;
                        } else {
                            idLead = response.body().getIdLeadMstStatus();
                        }
                        if (response.body().getIdMstLogDesc() != null) {
                            idLead = 0;
                        } else {
                            idLog = response.body().getIdMstLogDesc();
                        }

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailLead> call, Throwable t) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImageGallery() {

        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");

        galleryIntent.setAction(Intent.ACTION_PICK);
        Intent intentChoose = Intent.createChooser(galleryIntent, "Pilih Gambar Untuk Di upload");
        startActivityForResult(intentChoose, 10);
    }

    private void captureImage() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            actualImage = getMediaFileName();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, URI.fromFile(actualImage));
            startActivityForResult(takePictureIntent, 100);
        }
    }

    private static File getMediaFileName() {
// Lokasi External sdcard
        File mediaStorageDir = new
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "WarungDana");


// Buat directori tidak direktori tidak eksis
        if (!mediaStorageDir.exists()) {


            if (!mediaStorageDir.mkdirs()) {
                Log.d("WarungDana", "Gagal membuat directory" + "WarungDana");
                return null;
            }
        }
// Membuat nama file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = null;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                + ".jpg");
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            try {
                compressedImage = new Compressor(this)
                        .setMaxWidth(600)
                        .setMaxHeight(400)
                        .setQuality(60)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(actualImage);

                pathImage = compressedImage.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }


//            Picasso.get()
//                    .load(new File(pathImage)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)
//                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                    .resize(600,400)
//                    .centerInside()// this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
//                    .into(imageView);

            Picasso.get()
                    .load(new File(pathImage))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(600, 400)
                    .centerInside()// this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                    .into(imageView);

        } else if (requestCode == 10) {

            if (data == null) {
                Toast.makeText(getApplicationContext(), "Gambar Gagal Di load",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                    null, null, null);

            if (cursor != null) {

                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                pathImage = cursor.getString(columnIndex);

                actualImage = new File(pathImage);

                try {
                    compressedImage = new Compressor(this)
                            .setMaxWidth(600)
                            .setMaxHeight(400)
                            .setQuality(60)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(actualImage);

                    pathImage = compressedImage.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Picasso.get()
                        .load(new File(pathImage))
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .resize(600, 400)
                        .centerInside()// this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                        .into(imageView);

                cursor.close();

            } else {
                Toast.makeText(getApplicationContext(), "Gambar Gagal Di load",
                        Toast.LENGTH_LONG).show();


            }

        }

    }

    @Override
    public void onProgressUpdate(int percentage) {

        progressDialogUploadPhoto.setProgress(percentage);

    }

    @Override
    public void onError() {

        progressDialogUploadPhoto.dismiss();

    }

    @Override
    public void onFinish() {

        progressDialogUploadPhoto.setProgress(100);
        progressDialogUploadPhoto.dismiss();

    }
}
