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
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Api.UtilsApi;
import com.lmu.warungdananew.DetailLeadActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListLead;
import com.lmu.warungdananew.Response.ListLeadCollab;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListLeadCollabAdapter extends RecyclerView.Adapter<ListLeadCollabAdapter.ListLeadCollabHolder> implements Filterable {

    ArrayList<ListLeadCollab> listLeadCollabs;
    Integer idLead;
    Context context;
    Boolean connected = false;
    ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    Integer fav;

    public ListLeadCollabAdapter(Context context, ArrayList<ListLeadCollab> list) {
        this.context = context;
        this.listLeadCollabs = list;
    }

    @Override
    public ListLeadCollabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer, parent, false);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(context);
        return new ListLeadCollabHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListLeadCollabHolder holder, int position) {
        final ListLeadCollab listLeadCollab = listLeadCollabs.get(position);

        if (listLeadCollab.getLeadLastName() == null) {
            holder.nama.setText(listLeadCollab.getLeadFirstName());
        } else {
            holder.nama.setText(listLeadCollab.getLeadFirstName() + " " + listLeadCollab.getLeadLastName());
        }
        if (listLeadCollab.getDescription() == null) {
            holder.deskripsi.setText("Belum di Follow Up");
        } else {
            holder.deskripsi.setText(listLeadCollab.getDescription());
        }
        if (listLeadCollab.getRecall() == null) {
            holder.tanggal.setText("");
        } else {
            holder.tanggal.setText(convertTime(listLeadCollab.getRecall()));
        }
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailLeadActivity.class);
                intent.putExtra("idLead", listLeadCollab.getIdLead());
                context.startActivity(intent);
            }
        });
        holder.status.setText("Hubungi Ulang");

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_play_for_work_black_24dp);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.imgList.setBackground(drawable);

    }

    @Override
    public int getItemCount() {

        return listLeadCollabs == null ? 0 : listLeadCollabs.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }

    public class ListLeadCollabHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi, status, tanggal;
        RelativeLayout kotak;
        ImageView imgList, indikator;

        public ListLeadCollabHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvItemNama);
            deskripsi = itemView.findViewById(R.id.tvItemDeskripsi);
            status = itemView.findViewById(R.id.tvItemStatus);
            tanggal = itemView.findViewById(R.id.tvItemTanggal);
            kotak = itemView.findViewById(R.id.layoutItemCustomer);
            imgList = itemView.findViewById(R.id.imgList);
            indikator = itemView.findViewById(R.id.indikator);

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
