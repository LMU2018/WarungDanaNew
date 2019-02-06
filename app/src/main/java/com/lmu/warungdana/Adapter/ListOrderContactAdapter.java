package com.lmu.warungdana.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmu.warungdana.DetailDealActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListOrder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListOrderContactAdapter extends RecyclerView.Adapter<ListOrderContactAdapter.ListLeadHolder> {
    List<ListOrder> listLeads;

    Context context;

    public ListOrderContactAdapter(Context context, List<ListOrder> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer, parent, false);
        return new ListOrderContactAdapter.ListLeadHolder(view);
    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListOrder listLead = listLeads.get(position);
        String tanggal = convertTime(listLead.getCreatedAt());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = listLead.getPlafond();
        String plafond = formatter.format(myNumber);

        holder.deskripsi.setText(listLead.getOrderMstStatusStatus());
        holder.nama.setText(listLead.getModel());
//        holder.tanggal.setText(listLead.getCreatedAt());
        holder.status.setText("IDR " + plafond);
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDealActivity.class);
                intent.putExtra("idOrder", listLead.getId());
                intent.putExtra("idContact", listLead.getIdContact());
                context.startActivity(intent);
            }
        });

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_attach_money_black_24dp);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.imgList.setBackground(drawable);


    }

    @Override
    public int getItemCount() {
        try {
            return listLeads.size();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class ListLeadHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi, status, tanggal;
        RelativeLayout kotak;
        ImageView imgList;

        public ListLeadHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvItemNama);
            deskripsi = itemView.findViewById(R.id.tvItemDeskripsi);
            status = itemView.findViewById(R.id.tvItemStatus);
            tanggal = itemView.findViewById(R.id.tvItemTanggal);
            kotak = itemView.findViewById(R.id.layoutItemCustomer);
            imgList = itemView.findViewById(R.id.imgList);
            tanggal = null;
        }
    }

}
