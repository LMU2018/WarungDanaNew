package com.lmu.warungdananew;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class TentangApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_app);

        ImageView a = findViewById(R.id.imageView);
        TextView b = findViewById(R.id.tvVerison);
        TextView btnApp = findViewById(R.id.btnApp);
        View visitDNA = findViewById(R.id.visitLinkDNA);
//        Button btnDNA = findViewById(R.id.btnDNA);

        b.setText("Aplikasi Versi "+BuildConfig.VERSION_NAME);

        Picasso.get()
                .load(R.drawable.about_800x600_png)
                .error(R.drawable.no_image)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(a);

        btnApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://warungdana.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        visitDNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://digitalnetworkasia.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

//        btnDNA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String url = "http://digitalnetworkasia.com/";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//
//            }
//        });

    }
}
