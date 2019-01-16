package com.lmu.warungdananew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListReportJadwal;
import com.lmu.warungdananew.Response.ListUserJadwal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListReportJadwalAdapter extends RecyclerView.Adapter<ListReportJadwalAdapter.ListLeadHolder> {
    List<ListReportJadwal> listLeads;
    Integer idLead;
    Context context;

    public ListReportJadwalAdapter(Context context, List<ListReportJadwal>list){
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_jadwal_report,parent,false);
        return new ListReportJadwalAdapter.ListLeadHolder(view);
    }

    private String convertTime(String time){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMM, yyyy  HH:mm");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        }catch (ParseException e){
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListReportJadwal listLead = listLeads.get(position);

        holder.nama.setText(listLead.getCmsUsersName());
        holder.status.setText(listLead.getActivityMstStatusStatus());
        holder.tanggal.setText(convertTime(listLead.getCreatedAt()));
        holder.deskripsi.setText(listLead.getNote());
        if (listLead.getBrosur() != null){
            holder.brosur.setText("Brosur :  " + listLead.getBrosur());
        }else {
            holder.brosur.setVisibility(View.GONE);
        }

        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_description_black_24dp);
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
        TextView nama,deskripsi,status,tanggal,brosur;
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
            brosur = itemView.findViewById(R.id.tvItemBrosur);

        }
    }

}
