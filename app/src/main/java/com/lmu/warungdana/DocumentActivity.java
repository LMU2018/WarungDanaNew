package com.lmu.warungdana;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.Response.ProgressRequestBody;
import com.lmu.warungdana.Response.RespPost;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.CalendarContract.CalendarCache.URI;

public class DocumentActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {

    Button btnAmbilFoto, btnUpload;
    ImageView imageView;
    String[] value = new String[]{
            "Gallery",
            "Kamera"
    };

    String pathImage = "";
    File actualImage;
    File compressedImage;
    ApiEndPoint apiEndPoint;
    int idOrder, id_cms_users, id;
    String description = "";
    EditText edtDescription;
    String mode = "";
    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        apiEndPoint = UtilsApi.getAPIService();

        sharedPrefManager = new SharedPrefManager(getApplicationContext());


        Intent a = getIntent();
        id_cms_users = sharedPrefManager.getSpId();
        mode = a.getStringExtra("mode");

        findAllID();
        listener();

        if (mode.equals("tambah")) {

            btnUpload.setText("Upload");
            idOrder = a.getIntExtra("idOrder", 0);

        } else if (mode.equals("update")) {

            btnUpload.setText("Update");

            Picasso.get()
                    .load(a.getStringExtra("photo"))
                    .error(R.drawable.no_image)
                    .resize(600, 400)
                    .centerInside()// this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                    .into(imageView);

            edtDescription.setText(a.getStringExtra("description"));
            id = a.getIntExtra("id", 0);
            idOrder = a.getIntExtra("id_order", 0);

        }


    }

    private void listener() {

        btnAmbilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerdialogbuilder = new AlertDialog.Builder(DocumentActivity.this);
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

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validasiUpload();

            }
        });

    }

    private void validasiUpload() {

        description = edtDescription.getText().toString();

        if (pathImage.length() > 0) {

            if (description.length() > 0) {

                if (mode.equals("tambah")) {

                    upload();

                } else if (mode.equals("update")) {
                    update();
                }


            } else {

                //deskripsi kosong
                showDialog("Masukan deskripsi terlebih dahulu");
            }

        } else {

            showDialog("Pilih Foto terlebih dahulu");
            //foto belum diambil
        }
    }

    private void update() {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMax(100);
        progressDialog.setMessage("Sedang upload file");
        progressDialog.setTitle("Mohon tunggu");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        MultipartBody.Part fotoUpload = null;
        File file = new File(pathImage);

        ProgressRequestBody fileBody = new ProgressRequestBody(file, "multipart/form-data", this);

//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        fotoUpload = MultipartBody.Part.createFormData("photo", file.getName(), fileBody);
        RequestBody reqId = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(id));
        RequestBody reqIdOrder = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(idOrder));
        RequestBody reqDescription = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(description));
        RequestBody reqUpdatedBy = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(id_cms_users));

        apiEndPoint.updateOrderPhoto(reqId, reqIdOrder, fotoUpload, reqDescription, reqUpdatedBy).enqueue(new Callback<RespPost>() {
            @Override
            public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        progressDialog.dismiss();
                        showDialog(response.body().getApiMessage());

                    } else {

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<RespPost> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

    }

    private void showDialog(final String messages) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(messages);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        if (messages.equals("success")) {

                            setResult(2);
                            finish();
                        }
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void upload() {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMax(100);
        progressDialog.setMessage("Sedang upload file");
        progressDialog.setTitle("Mohon tunggu");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        MultipartBody.Part fotoUpload = null;
        File file = new File(pathImage);

        ProgressRequestBody fileBody = new ProgressRequestBody(file, "multipart/form-data", this);

//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        fotoUpload = MultipartBody.Part.createFormData("photo", file.getName(), fileBody);
        RequestBody reqIdOrder = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(idOrder));
        RequestBody reqDescription = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(description));
        RequestBody reqIdCms = MultipartBody.create(MediaType.parse("multipart/form-data"), String.valueOf(id_cms_users));

        apiEndPoint.orderUploadPhoto(reqIdOrder, fotoUpload, reqDescription, reqIdCms).enqueue(new Callback<RespPost>() {
            @Override
            public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        progressDialog.dismiss();
                        showDialog(response.body().getApiMessage());

                    } else {

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<RespPost> call, Throwable t) {

                progressDialog.dismiss();
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

    private void findAllID() {

        btnAmbilFoto = findViewById(R.id.btnAmbilFoto);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);
        edtDescription = findViewById(R.id.edtDescription);

    }

    @Override
    public void onProgressUpdate(int percentage) {

        progressDialog.setProgress(percentage);


    }

    @Override
    public void onError() {

        progressDialog.dismiss();
    }

    @Override
    public void onFinish() {

        progressDialog.setProgress(100);
        progressDialog.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
