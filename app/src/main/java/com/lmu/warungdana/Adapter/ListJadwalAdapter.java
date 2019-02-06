package com.lmu.warungdana.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmu.warungdana.AddJadwalReportActivity;
import com.lmu.warungdana.DetailJadwalActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListJadwal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListJadwalAdapter extends RecyclerView.Adapter<ListJadwalAdapter.ListLeadHolder> {
    List<ListJadwal> listLeads;
    Integer idJadwal;
    Context context;

    public ListJadwalAdapter(Context context, List<ListJadwal> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_jadwal, parent, false);
        return new ListJadwalAdapter.ListLeadHolder(view);
    }

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListJadwal listJadwal = listLeads.get(position);
        idJadwal = listJadwal.getId();
        holder.kategori.setText(listJadwal.getType());
        holder.lokasi.setText(listJadwal.getLocation());
        if (listJadwal.getActivityScheduleNote() != null) {
            holder.note.setText(listJadwal.getActivityScheduleNote());
        } else {
            holder.kotakNote.setVisibility(View.GONE);
        }
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailJadwalActivity.class);
                intent.putExtra("idJadwal", listJadwal.getIdActivitySchedule());
                intent.putExtra("nama", listJadwal.getType());
                context.startActivity(intent);
            }
        });

        holder.kotak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, AddJadwalReportActivity.class);
                intent.putExtra("idJadwal", listJadwal.getIdActivitySchedule());
                intent.putExtra("type", listJadwal.getType());
                intent.putExtra("lokasi", listJadwal.getLocation());
                intent.putExtra("tanggal", listJadwal.getActivityScheduleStartDate());
                context.startActivity(intent);
                return true;
            }
        });

//        if(listJadwal.getIdStatus()==1){
//            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.oval);
//            drawable.setColorFilter(Color.RED,PorterDuff.Mode.SRC_IN);
//            holder.indikator.setBackground(drawable);
//        } else if(listJadwal.getIdStatus()==2){
//            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.oval);
//            drawable.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_IN);
//            holder.indikator.setBackground(drawable);
//        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm", Locale.US);
        String jammulai, jamselesai;
        jammulai = listJadwal.getActivityScheduleStarted();
        jamselesai = listJadwal.getActivityScheduleEnded();
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(jammulai);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(jamselesai);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = date1.getTime() - date2.getTime();

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

        String diffHours = simpleDateFormat2.format(hours);
        holder.durasi.setText(diffHours);
        holder.jam.setText(listJadwal.getActivityScheduleStarted());
        holder.selesai.setText(listJadwal.getActivityScheduleEnded());

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
        TextView jam, kategori, durasi, lokasi, note, selesai;
        ImageView indikator;
        LinearLayout kotak, kotakNote;

        public ListLeadHolder(View itemView) {
            super(itemView);
            kotak = itemView.findViewById(R.id.kotakItemJadwal);
            jam = itemView.findViewById(R.id.tvJamJadwal);
            selesai = itemView.findViewById(R.id.tvSelesaiJadwal);
            kategori = itemView.findViewById(R.id.tvKategoriJadwal);
            durasi = itemView.findViewById(R.id.tvDurasiJadwal);
            lokasi = itemView.findViewById(R.id.tvAlamatJadwal);
            note = itemView.findViewById(R.id.tvNoteJadwal);
            indikator = itemView.findViewById(R.id.imgJadwalIndicator);
            kotakNote = itemView.findViewById(R.id.kotakNoteJadwal);

        }
    }

}
