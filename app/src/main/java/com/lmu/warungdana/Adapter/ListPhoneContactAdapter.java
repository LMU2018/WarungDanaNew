package com.lmu.warungdana.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListPhone;

import java.util.List;

public class ListPhoneContactAdapter extends RecyclerView.Adapter<ListPhoneContactAdapter.ListLeadHolder> {
    List<ListPhone> listLeads;
    Integer idLead;
    Context context;

    public ListPhoneContactAdapter(Context context, List<ListPhone> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_phone, parent, false);
        return new ListPhoneContactAdapter.ListLeadHolder(view);
    }

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListPhone listLead = listLeads.get(position);
        idLead = listLead.getId();

        if (listLead.getNumber() != null) {
            holder.number.setText(listLead.getNumber());
        } else {
            holder.number.setText("Empty");
        }

        if (listLead.getStatus() != null) {
            if (listLead.getStatus().equalsIgnoreCase("Y")) {
                holder.status.setText("Aktif");
            } else {
                holder.status.setText("Tidak Aktif");
            }
        } else {
            holder.status.setText("Empty");
        }

        holder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(listLead.getNumber());
                String[] animals = {"Call with phone"};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                /*Intent intent = new Intent(context, AddLeadLogActivity.class);
                                intent.putExtra("idData", listLead.getIdLead());
                                intent.putExtra("idSource", 1);
                                context.startActivity(intent);*/

                                Intent caa = new Intent(Intent.ACTION_CALL);
                                String telp = "tel:" + listLead.getNumber();
                                caa.setData(Uri.parse(telp));
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                context.startActivity(caa);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


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
        TextView number, status;
        RelativeLayout kotak;

        public ListLeadHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.tvItemPhone);
            status = itemView.findViewById(R.id.tvItemPhoneStatus);
            kotak = itemView.findViewById(R.id.layoutPhone);

        }
    }

}
