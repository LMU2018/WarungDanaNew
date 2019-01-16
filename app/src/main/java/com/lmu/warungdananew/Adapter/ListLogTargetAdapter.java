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
import com.lmu.warungdananew.Response.ListLogTarget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListLogTargetAdapter extends RecyclerView.Adapter<ListLogTargetAdapter.ListLeadHolder> {
    List<ListLogTarget> listLeads;
    Integer idLead;
    Context context;

    public ListLogTargetAdapter(Context context, List<ListLogTarget> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_log, parent, false);
        return new ListLogTargetAdapter.ListLeadHolder(view);
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

    private static int hoursToSeconds(int hours) {
        return hours * 60 * 60;
    }

    private static int minutesToSeconds(int minutes) {
        return minutes * 60;
    }

    private String timeConversion(int totalSeconds) {
        int hours = totalSeconds / 60 / 60;
        int minutes = (totalSeconds - (hoursToSeconds(hours))) / 60;
        int seconds = totalSeconds - ((hoursToSeconds(hours)) + (minutesToSeconds(minutes)));

        return minutes + " min " + seconds + " sec";
    }

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListLogTarget listLead = listLeads.get(position);
        String deskripsi, status, recall, durasi, created, namacfa, konsumen;
        deskripsi = listLead.getMstLogDescDescription();
        status = listLead.getStatus();
        recall = convertTime(listLead.getRecall());
        durasi = timeConversion(listLead.getDuration());
        created = convertTime2(listLead.getCreatedAt());
        namacfa = listLead.getCmsUsersName();
        if (listLead.getTargetLastName() == null) {
            konsumen = listLead.getTargetFirstName();
        } else {
            konsumen = listLead.getTargetFirstName() + " " + listLead.getTargetLastName();
        }
        holder.namacfa.setText(namacfa + " called ");
        holder.konsumen.setText(konsumen);
        holder.deskripsi.setText(deskripsi);
        holder.status.setText(status);
        holder.recall.setText(recall);
        holder.created.setText(created);
        holder.durasi.setText(durasi);
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
        TextView deskripsi, status, recall, durasi, created, namacfa, konsumen;

        public ListLeadHolder(View itemView) {
            super(itemView);
            deskripsi = itemView.findViewById(R.id.tvLogDeskripsi);
            status = itemView.findViewById(R.id.tvLogStatus);
            recall = itemView.findViewById(R.id.tvLogRecall);
            durasi = itemView.findViewById(R.id.tvLogDurasi);
            created = itemView.findViewById(R.id.tvLogCreated);
            namacfa = itemView.findViewById(R.id.tvItemCFA);
            konsumen = itemView.findViewById(R.id.tvItemCustomer);

        }
    }

}
