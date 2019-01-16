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
import com.lmu.warungdananew.Response.ListPhone;
import com.lmu.warungdananew.Response.ListUnit;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ListUnitAdapter extends RecyclerView.Adapter<ListUnitAdapter.ListLeadHolder> {
    List<ListUnit> listLeads;
    Integer idLead;
    Context context;
    String otr;

    public ListUnitAdapter(Context context, List<ListUnit> list) {
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_unit, parent, false);
        return new ListUnitAdapter.ListLeadHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListUnit listLead = listLeads.get(position);
        idLead = listLead.getId();
        String pajak;

        NumberFormat formatter = new DecimalFormat("#,###");
        if (listLead.getOtr() != null) {
            double myNumber = listLead.getOtr();
            otr = formatter.format(myNumber);
        }

        if (listLead.getMerk() != null) {
            holder.merk.setText(listLead.getMerk());
        } else {
            holder.merk.setText("Empty");
        }

        if (listLead.getModel() != null) {
            holder.model.setText(listLead.getModel());
        } else {
            holder.model.setText("Empty");
        }

        if (listLead.getNopol() != null) {
            holder.nopol.setText(listLead.getNopol());
        } else {
            holder.nopol.setText("Empty");
        }

        if (listLead.getYear() != null) {
            holder.tahun.setText(listLead.getYear().toString());
        } else {
            holder.tahun.setText("Empty");
        }

        if (listLead.getTaxStatus() != null) {
            if (listLead.getTaxStatus().equalsIgnoreCase("Y")) {
                holder.pajak.setText("Pajak Kendaraan : Hidup");
            } else {
                holder.pajak.setText("Pajak Kendaraan : Mati");
            }
        } else {
            holder.pajak.setText("Pajak Kendaraan : Empty");
        }

        if (listLead.getOwner() != null) {
            holder.owner.setText("Atas Nama : " + listLead.getOwner());
        } else {
            holder.owner.setText("Atas Nama : Empty");
        }

        if (listLead.getOtr() != null) {
            holder.otr.setText("OTR : IDR " + otr);
        } else {
            holder.otr.setText("OTR : Empty");
        }


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
        TextView merk, model, nopol, tahun, pajak, owner, otr;
        RelativeLayout kotak;

        public ListLeadHolder(View itemView) {
            super(itemView);
            merk = itemView.findViewById(R.id.tvUnitMerk);
            model = itemView.findViewById(R.id.tvUnitModel);
            nopol = itemView.findViewById(R.id.tvUnitNopol);
            tahun = itemView.findViewById(R.id.tvUnitYear);
            pajak = itemView.findViewById(R.id.tvUnitPajak);
            owner = itemView.findViewById(R.id.tvUnitOwner);
            otr = itemView.findViewById(R.id.tvOTR);
//            kotak = itemView.findViewById(R.id.layoutPhone);
        }
    }

}
