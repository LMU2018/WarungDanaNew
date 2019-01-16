package com.lmu.warungdananew.Adapter;

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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmu.warungdananew.DetailContactActivity;
import com.lmu.warungdananew.DetailLeadActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListContact;
import com.lmu.warungdananew.Response.ListLead;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListContactAdapter extends RecyclerView.Adapter<ListContactAdapter.ListContactHolder> {
    ArrayList<ListContact> listContacts;
    Context context;

    public ListContactAdapter(Context context, ArrayList<ListContact> list) {
        this.context = context;
        this.listContacts = list;
    }

    @Override
    public ListContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer, parent, false);
        return new ListContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ListContactHolder holder, int position) {
        final ListContact listContact = listContacts.get(position);
        if (listContact.getContactLastName() == null) {
            holder.nama.setText(listContact.getContactFirstName());
        } else {
            holder.nama.setText(listContact.getContactFirstName() + " " + listContact.getContactLastName());
        }
        if (listContact.getStatus() != null) {
            holder.deskripsi.setText(listContact.getStatus());
        } else {
            holder.deskripsi.setText("Belum Order");
        }

        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailContactActivity.class);
                intent.putExtra("idContact", listContact.getIdContact());
                context.startActivity(intent);
            }
        });

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_account_circle_black_24dp);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.imgList.setBackground(drawable);

    }

    @Override
    public int getItemCount() {
        return listContacts == null ? 0 : listContacts.size();
    }

    public class ListContactHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi, status, tanggal;
        RelativeLayout kotak;
        ImageView imgList;

        public ListContactHolder(View itemView) {
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
