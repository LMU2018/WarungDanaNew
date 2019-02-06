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

import com.lmu.warungdana.AddOrderActivity;
import com.lmu.warungdana.ContactOrderActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListContact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListContactOrderAdapter extends RecyclerView.Adapter<ListContactOrderAdapter.ListContactOrderHolder> {
    ArrayList<ListContact> listContactOrders;
    Integer idLead;
    Context context;
    String namalengkap;

    public ListContactOrderAdapter(Context context, ArrayList<ListContact> list) {
        this.context = context;
        this.listContactOrders = list;
    }

    @Override
    public ListContactOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer, parent, false);
        return new ListContactOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(ListContactOrderHolder holder, int position) {
        final ListContact listContactOrder = listContactOrders.get(position);
        if (listContactOrder.getContactLastName() == null) {
            holder.nama.setText(listContactOrder.getContactFirstName());
            namalengkap = listContactOrder.getContactFirstName();
        } else {
            holder.nama.setText(listContactOrder.getContactFirstName() + " " + listContactOrder.getContactLastName());
            namalengkap = listContactOrder.getContactFirstName() + " " + listContactOrder.getContactLastName();
        }
        if (listContactOrder.getStatus() != null) {
            holder.deskripsi.setText(listContactOrder.getStatus());
        } else {
            holder.deskripsi.setText("Belum Order");
        }

        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddOrderActivity.class);
                intent.putExtra("idContact", listContactOrder.getIdContact());
                if (listContactOrder.getContactLastName() == null) {
                    intent.putExtra("nameContact", listContactOrder.getContactFirstName());
                } else {
                    intent.putExtra("nameContact", listContactOrder.getContactFirstName() + " " + listContactOrder.getContactLastName());
                }

                context.startActivity(intent);
                ((ContactOrderActivity) context).finish();

            }
        });

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_account_circle_black_24dp);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.imgList.setBackground(drawable);

    }

    @Override
    public int getItemCount() {
        return listContactOrders == null ? 0 : listContactOrders.size();
    }

    public class ListContactOrderHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi, status, tanggal;
        RelativeLayout kotak;
        ImageView imgList;

        public ListContactOrderHolder(View itemView) {
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

}
