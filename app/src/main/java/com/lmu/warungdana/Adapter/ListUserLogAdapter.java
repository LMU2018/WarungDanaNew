package com.lmu.warungdana.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.DetailLeadActivity;
import com.lmu.warungdana.DetailTargetActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.DetailContact;
import com.lmu.warungdana.Response.DetailJadwal;
import com.lmu.warungdana.Response.DetailLead;
import com.lmu.warungdana.Response.DetailOrder;
import com.lmu.warungdana.Response.DetailTarget;
import com.lmu.warungdana.Response.ListUserLog;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUserLogAdapter extends RecyclerView.Adapter<ListUserLogAdapter.ListUserLogHolder> {
    ArrayList<ListUserLog> listUserLogs;
    Integer idLead;
    Context context;
    private ApiEndPoint mApiService;

    public ListUserLogAdapter(Context context, ArrayList<ListUserLog> list) {
        this.context = context;
        this.listUserLogs = list;
    }

    @Override
    public ListUserLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history, parent, false);
        mApiService = UtilsApi.getAPIService();
        return new ListUserLogAdapter.ListUserLogHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListUserLogHolder holder, int position) {
        final ListUserLog listUserLog = listUserLogs.get(position);
        String deskripsi, status, created, namacfa, konsumen;
        namacfa = listUserLog.getCmsUsersName();
        holder.namacfa.setText(namacfa);
        switch (listUserLog.getIdModul()) {
            case 1:
                deskripsi = "Aktivitas";
                holder.konsumen.setText(" menambah report aktivitas.");
                holder.deskripsi.setText(deskripsi);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_event_note_white_24dp);
                drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                holder.imgList.setBackground(drawable);
                mApiService.jadwalDetail(listUserLog.getIdData()).enqueue(new Callback<DetailJadwal>() {
                    @Override
                    public void onResponse(Call<DetailJadwal> call, Response<DetailJadwal> response) {
                        if (response.isSuccessful()) {
                            holder.status.setText(response.body().getType());
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailJadwal> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 2:
                deskripsi = "Lead";
                holder.deskripsi.setText(deskripsi);
                Drawable wadau = ContextCompat.getDrawable(context, R.drawable.ic_play_for_work_white_24dp);
                holder.imgList.setBackground(wadau);
                switch (listUserLog.getJenis()) {
                    case "create":
                        holder.konsumen.setText(" menambah data baru.");
                        holder.cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, DetailLeadActivity.class);
                                intent.putExtra("idLead", listUserLog.getIdData());
                                context.startActivity(intent);
                            }
                        });
                        break;
                    case "call":
                        holder.konsumen.setText(" menghubungi data.");
                        holder.cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, DetailLeadActivity.class);
                                intent.putExtra("idLead", listUserLog.getIdData());
                                context.startActivity(intent);
                            }
                        });
                        break;
                    case "delete":
                        holder.konsumen.setText(" menghapus data.");
                        break;
                }
                mApiService.leadDetail(listUserLog.getIdData()).enqueue(new Callback<DetailLead>() {
                    @Override
                    public void onResponse(Call<DetailLead> call, Response<DetailLead> response) {
                        if (response.isSuccessful()) {
                            String nama;
                            if (response.body().getLastName() != null) {
                                nama = response.body().getFirstName() + " " + response.body().getLastName();
                            } else {
                                nama = response.body().getFirstName();
                            }
                            holder.status.setText(nama);
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailLead> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 3:
                deskripsi = "Target";
                holder.deskripsi.setText(deskripsi);
                Drawable wew = ContextCompat.getDrawable(context, R.drawable.ic_radio_button_checked_black_24dp);
                wew.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                holder.imgList.setBackground(wew);
                holder.konsumen.setText(" menghubungi data.");
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailTargetActivity.class);
                        intent.putExtra("idTarget", listUserLog.getIdData());
                        context.startActivity(intent);
                    }
                });
                mApiService.targetDetail(listUserLog.getIdData()).enqueue(new Callback<DetailTarget>() {
                    @Override
                    public void onResponse(Call<DetailTarget> call, Response<DetailTarget> response) {
                        if (response.isSuccessful()) {
                            String nama;
                            if (response.body().getLastName() != null) {
                                nama = response.body().getFirstName() + " " + response.body().getLastName();
                            } else {
                                nama = response.body().getFirstName();
                            }
                            holder.status.setText(nama);
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailTarget> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 4:
                deskripsi = "Contact";
                holder.deskripsi.setText(deskripsi);
                Drawable gas = ContextCompat.getDrawable(context, R.drawable.ic_account_circle_white_24dp);
                holder.imgList.setBackground(gas);
                holder.konsumen.setText(" menambah contact baru.");
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailContactActivity.class);
                        intent.putExtra("idContact", listUserLog.getIdData());
                        context.startActivity(intent);
                    }
                });
                mApiService.detailContact(listUserLog.getIdData()).enqueue(new Callback<DetailContact>() {
                    @Override
                    public void onResponse(Call<DetailContact> call, Response<DetailContact> response) {
                        if (response.isSuccessful()) {
                            String nama;
                            if (response.body().getLastName() != null) {
                                nama = response.body().getFirstName() + " " + response.body().getLastName();
                            } else {
                                nama = response.body().getFirstName();
                            }
                            holder.status.setText(nama);
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailContact> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 5:
                deskripsi = "Deal";
                holder.deskripsi.setText(deskripsi);
                Drawable yata = ContextCompat.getDrawable(context, R.drawable.ic_attach_money_white_24dp);
                holder.imgList.setBackground(yata);
                holder.konsumen.setText(" menambah order baru.");
                mApiService.orderDetail(listUserLog.getIdData()).enqueue(new Callback<DetailOrder>() {
                    @Override
                    public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                        if (response.isSuccessful()) {
                            String nama;
                            if (response.body().getContactLastName() != null) {
                                nama = response.body().getContactFirstName() + " " + response.body().getContactLastName();
                            } else {
                                nama = response.body().getContactFirstName();
                            }
                            holder.status.setText(nama);
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailOrder> call, Throwable t) {

                    }
                });
                break;
        }

        created = convertTime2(listUserLog.getCreatedAt());
        holder.created.setText(created);
    }

    @Override
    public int getItemCount() {
        return listUserLogs == null ? 0 : listUserLogs.size();

    }

    public class ListUserLogHolder extends RecyclerView.ViewHolder {
        TextView deskripsi, status, created, namacfa, konsumen;
        ImageView imgList;
        CardView cardView;

        public ListUserLogHolder(View itemView) {
            super(itemView);
            deskripsi = itemView.findViewById(R.id.tvModul);
            status = itemView.findViewById(R.id.tvTitle);
            cardView = itemView.findViewById(R.id.cardview);
            created = itemView.findViewById(R.id.tvLogCreated);
            namacfa = itemView.findViewById(R.id.tvItemCFA);
            konsumen = itemView.findViewById(R.id.tvItemCustomer);
            imgList = itemView.findViewById(R.id.imgList);

        }
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

}
