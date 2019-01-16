package com.lmu.warungdananew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdananew.DetailTargetActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListTarget;
import com.lmu.warungdananew.Utils.UtilsConnected;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListTargetAdapter extends RecyclerView.Adapter<ListTargetAdapter.ListTargetHolder> {
    ArrayList<ListTarget> listTargets;
    Integer idTarget;
    Context context;

    public ListTargetAdapter(Context context, ArrayList<ListTarget> list) {
        this.context = context;
        this.listTargets = list;
    }



    @Override
    public ListTargetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer, parent, false);
        return new ListTargetHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTargetHolder holder, int position) {
        final ListTarget listTarget = listTargets.get(position);
        idTarget = listTarget.getId();
        final Context mcontext;
        if (listTarget.getLastName() == null) {
            holder.nama.setText(listTarget.getFirstName());
        } else {
            holder.nama.setText(listTarget.getFirstName() + " " + listTarget.getLastName());
        }
        if (listTarget.getDescription() == null || listTarget.getIdTargetMstStatus() == 1) {
            holder.deskripsi.setText("Belum di Follow Up");
        } else if (listTarget.getIdTargetMstStatus() != 4) {
            holder.deskripsi.setText(listTarget.getDescription());
        }

        if (listTarget.getRecall() == null || listTarget.getIdTargetMstStatus() == 1) {
            holder.tanggal.setText("");
        } else if (listTarget.getIdTargetMstStatus() != 4) {
            holder.tanggal.setText(convertTime(listTarget.getRecall()));
        }
        if (listTarget.getIdTargetMstStatus() == 4) {
            if (listTarget.getRevisit() == null) {
                holder.tanggal.setText("");
            } else {
                holder.tanggal.setText(convertTime2(listTarget.getRevisit()));
                holder.deskripsi.setText(listTarget.getVisitStatus());
            }

        }

        holder.status.setText(listTarget.getCategory());
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConn = UtilsConnected.isNetworkConnected(context);
                if (isConn) {
                    Intent intent = new Intent(context, DetailTargetActivity.class);
                    intent.putExtra("idTarget", listTarget.getId());
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Periksa Koneksi", Toast.LENGTH_LONG).show();
                }
            }
        });

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_radio_button_checked_black_24dp);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.imgList.setBackground(drawable);

    }

    @Override
    public int getItemCount() {
        return listTargets == null ? 0 : listTargets.size();
    }

    public class ListTargetHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi, status, tanggal;
        RelativeLayout kotak;
        ImageView imgList;

        public ListTargetHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvItemNama);
            deskripsi = itemView.findViewById(R.id.tvItemDeskripsi);
            status = itemView.findViewById(R.id.tvItemStatus);
            tanggal = itemView.findViewById(R.id.tvItemTanggal);
            kotak = itemView.findViewById(R.id.layoutItemCustomer);
            imgList = itemView.findViewById(R.id.imgList);
        }
    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMM, yyyy");
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMM, yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format1.format(date);
    }

}
