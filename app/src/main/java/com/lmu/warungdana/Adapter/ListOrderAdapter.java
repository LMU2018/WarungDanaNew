package com.lmu.warungdana.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.ArrayList;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ListOrderHolder> {
    ArrayList<ListOrder> listOrders;
    String plafond;
    Context context;

    public ListOrderAdapter(Context context, ArrayList<ListOrder> list) {
        this.context = context;
        this.listOrders = list;
    }

    @Override
    public ListOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer, parent, false);
        return new ListOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(ListOrderHolder holder, int position) {
        final ListOrder listOrder = listOrders.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
        if (listOrder.getPlafond() != null) {
            double myNumber = listOrder.getPlafond();
            plafond = formatter.format(myNumber);
        }


        if (listOrder.getContactLastName() == null) {
            holder.deskripsi.setText(listOrder.getContactFirstName());
        } else {
            holder.deskripsi.setText(listOrder.getContactFirstName() + " " + listOrder.getContactLastName());
        }
        holder.nama.setText(listOrder.getModel());
        if (listOrder.getCreatedAt() == null) {
            holder.tanggal.setText("");
        } else {
            holder.tanggal.setText(convertTime(listOrder.getCreatedAt()));
        }
        holder.status.setText("IDR " + plafond);
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDealActivity.class);
                intent.putExtra("idContact", listOrder.getIdContact());
                intent.putExtra("idOrder",listOrder.getId());
                context.startActivity(intent);
            }
        });

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_attach_money_black_24dp);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.imgList.setBackground(drawable);


    }

    @Override
    public int getItemCount() {
        return listOrders == null ? 0 : listOrders.size();
    }

    public class ListOrderHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi, status, tanggal;
        RelativeLayout kotak;
        ImageView imgList;

        public ListOrderHolder(View itemView) {
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


}
