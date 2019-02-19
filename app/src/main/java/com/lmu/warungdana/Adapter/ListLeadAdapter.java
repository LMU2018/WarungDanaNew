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

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListLead;
import com.lmu.warungdana.DetailLeadActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListLeadAdapter extends RecyclerView.Adapter<ListLeadAdapter.ListLeadHolder> implements Filterable {
    ArrayList<ListLead> listLeads;
    Integer idLead;
    Context context;
    Boolean connected = false;
    ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;
    Integer fav;
    public int num = 1;

    public ListLeadAdapter(Context context, ArrayList<ListLead> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer, parent, false);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(context);
        return new ListLeadHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListLeadHolder holder, int position) {
        final ListLead listLead = listLeads.get(position);

        if (listLead.getLastName() == null) {
            holder.nama.setText(listLead.getFirstName());
        } else {
            holder.nama.setText(listLead.getFirstName() + " " + listLead.getLastName());
        }
        if (listLead.getDescription() == null) {
            holder.deskripsi.setText("Belum di Follow Up");
        } else {
            holder.deskripsi.setText(listLead.getDescription());
        }
        if (listLead.getRecall() == null) {
            holder.tanggal.setText("");
        } else {
            holder.tanggal.setText(convertTime(listLead.getRecall()));
        }
        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailLeadActivity.class);
                intent.putExtra("idLead", listLead.getId());
                context.startActivity(intent);
            }
        });
        holder.status.setText("Hubungi Ulang");

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_play_for_work_black_24dp);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        holder.imgList.setBackground(drawable);
        Drawable drawable1 = ContextCompat.getDrawable(context, R.drawable.ovalgold);
        fav = listLead.getFavorite();

        if (fav == 1) {
            holder.indikator.setBackground(drawable1);
            holder.indikator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mApiService.leadUpdateFavorite(listLead.getId(), 0, sharedPrefManager.getSpId()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Berhasil HAPUS favorit", Toast.LENGTH_SHORT).show();
                                holder.indikator.setBackground(ContextCompat.getDrawable(context, R.drawable.oval));
                                fav = 0;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, "Gagal Hapus favorit", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            holder.indikator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mApiService.leadUpdateFavorite(listLead.getId(), 1, sharedPrefManager.getSpId()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Berhasil TAMBAH favorit", Toast.LENGTH_SHORT).show();
                                holder.indikator.setBackground(ContextCompat.getDrawable(context, R.drawable.ovalgold));
                                fav = 1;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, "Gagal TAMBAH favorit", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


    }

    @Override
    public int getItemCount() {

        return listLeads == null ? 0 : listLeads.size();
//        if (num*20 > listLeads.size()){
//
//            return listLeads.size();
//        }else {
//
//            return num *20;
//        }

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

    public class ListLeadHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi, status, tanggal;
        RelativeLayout kotak;
        ImageView imgList, indikator;

        public ListLeadHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvItemNama);
            deskripsi = itemView.findViewById(R.id.tvItemDeskripsi);
            status = itemView.findViewById(R.id.tvItemStatus);
            tanggal = itemView.findViewById(R.id.tvItemTanggal);
            kotak = itemView.findViewById(R.id.layoutItemCustomer);
            imgList = itemView.findViewById(R.id.imgList);
            indikator = itemView.findViewById(R.id.indikator);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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
