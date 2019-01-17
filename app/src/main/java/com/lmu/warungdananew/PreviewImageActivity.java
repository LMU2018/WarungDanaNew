package com.lmu.warungdananew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;



public class PreviewImageActivity extends AppCompatActivity {

//    ImageView imageView;
    ImageView imageView;

    boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        imageView = findViewById(R.id.imgView);

        Intent a = getIntent();

        Picasso.get()
                .load(a.getStringExtra("photo"))
                .error(R.drawable.no_image)
                .into(imageView);

        imageView.setOnTouchListener(new ImageMatrixTouchHandler(getApplicationContext()));


    }
}
