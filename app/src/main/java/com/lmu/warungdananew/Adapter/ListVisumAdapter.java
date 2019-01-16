package com.lmu.warungdananew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListLog;
import com.lmu.warungdananew.Response.ListVisum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListVisumAdapter extends RecyclerView.Adapter<ListVisumAdapter.ListLeadHolder> {
    List<ListVisum> listLeads;
    Integer idLead;
    Context context;

    public ListVisumAdapter(Context context, List<ListVisum> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_visum, parent, false);
        return new ListVisumAdapter.ListLeadHolder(view);
    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("EEEE, dd MMMM yyyy");
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
        final ListVisum listLead = listLeads.get(position);
        String deskripsi, recall, created, namacfa, konsumen;
        deskripsi = listLead.getMstVisumStatusStatus();
        created = convertTime2(listLead.getCreatedAt());
        namacfa = listLead.getCmsUsersName();
        if (listLead.getTargetLastName() == null) {
            konsumen = listLead.getTargetFirstName();
        } else {
            konsumen = listLead.getTargetFirstName() + " " + listLead.getTargetLastName();
        }
        holder.namacfa.setText(namacfa + " visited ");
        holder.konsumen.setText(konsumen);
        holder.deskripsi.setText(deskripsi);
        holder.recall.setText(convertTime(listLead.getRevisit()));
        holder.created.setText(created);
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
        TextView deskripsi, recall, created, namacfa, konsumen;

        public ListLeadHolder(View itemView) {
            super(itemView);
            deskripsi = itemView.findViewById(R.id.tvLogDeskripsi);
            recall = itemView.findViewById(R.id.tvLogRecall);
            created = itemView.findViewById(R.id.tvLogCreated);
            namacfa = itemView.findViewById(R.id.tvItemCFA);
            konsumen = itemView.findViewById(R.id.tvItemCustomer);

        }
    }

}
