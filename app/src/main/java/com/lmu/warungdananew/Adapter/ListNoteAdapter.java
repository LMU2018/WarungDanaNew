package com.lmu.warungdananew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListNote;
import com.lmu.warungdananew.Response.ListPhone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListNoteAdapter extends RecyclerView.Adapter<ListNoteAdapter.ListLeadHolder> {
    List<ListNote> listLeads;
    Integer idLead;
    Context context;

    public ListNoteAdapter(Context context, List<ListNote> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
        return new ListNoteAdapter.ListLeadHolder(view);
    }

    private String convertTime2(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate2 = format1.format(date);
        return convertedDate2;
    }

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListNote listLead = listLeads.get(position);
        idLead = listLead.getId();
        holder.isi.setText(listLead.getNote());
        holder.tanggal.setText(convertTime2(listLead.getCreatedAt()));
        holder.user.setText(listLead.getCmsUsersName());


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
        TextView isi, tanggal, user;

        public ListLeadHolder(View itemView) {
            super(itemView);
            isi = itemView.findViewById(R.id.tvNoteIsi);
            tanggal = itemView.findViewById(R.id.tvNoteTanggal);
            user = itemView.findViewById(R.id.tvNoteUser);


        }
    }

}
