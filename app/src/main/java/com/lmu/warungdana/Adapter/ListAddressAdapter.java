package com.lmu.warungdana.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;


import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListAddress;


import java.util.List;

public class ListAddressAdapter extends RecyclerView.Adapter<ListAddressAdapter.ListLeadHolder>  {
    List<ListAddress> listLeads;
    Integer idLead;
    Context context;

    public ListAddressAdapter(Context context, List<ListAddress>list){
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_address,parent,false);
        return new ListAddressAdapter.ListLeadHolder(view);
    }

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListAddress listLead = listLeads.get(position);
        String jalan, kelurahan,kecamatan,kota,provinsi,kodepos,rt,rw;
        jalan = listLead.getAddress() + ", ";
        kelurahan = listLead.getMstAddressKelurahan() + ", ";
        kecamatan = listLead.getMstAddressKecamatan()+ ", ";
        kota = listLead.getMstAddressKabupaten()+ ", ";
        provinsi = listLead.getMstAddressProvinsi()+ ", ";
        kodepos = listLead.getMstAddressKodepos();
        rt = " RT "+listLead.getRt();
        rw = " RW "+listLead.getRw();


        holder.tvItemAddress.setText(jalan+kelurahan+kecamatan+kota+provinsi+kodepos+rt+rw);

        holder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapsNav = Uri.parse("google.navigation:q="+ listLead.getAddress() + ", " + listLead.getMstAddressKabupaten() + "&avoid=t");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapsNav);
                mapIntent.setPackage("com.google.android.apps.maps");

                try {

                    context.startActivity(mapIntent);

                }catch (ActivityNotFoundException e){

                    Toast.makeText(context,"Aplikasi Google Maps tidak ada di hp anda !",Toast.LENGTH_LONG).show();
                }
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
        TextView tvItemAddress,direction;

        public ListLeadHolder(View itemView) {
            super(itemView);
            tvItemAddress = itemView.findViewById(R.id.tvItemAddress);
            direction = itemView.findViewById(R.id.tvDirection);
        }
    }

}
