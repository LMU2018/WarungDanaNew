package com.lmu.warungdana.Adapter;

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

import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListContactCollaborate;

import java.util.List;

public class ListContactCollabAdapter extends RecyclerView.Adapter<ListContactCollabAdapter.ListLeadHolder> {
    List<ListContactCollaborate> listLeads;
    Integer idLead;
    Context context;

    public ListContactCollabAdapter(Context context, List<ListContactCollaborate>list){
        this.context = context;
        this.listLeads = list;
    }

    @Override
    public ListLeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_customer,parent,false);
        return new ListContactCollabAdapter.ListLeadHolder(view);
    }

    @Override
    public void onBindViewHolder(ListLeadHolder holder, int position) {
        final ListContactCollaborate listLead = listLeads.get(position);
        holder.nama.setText(listLead.getCmsUsersName());
        holder.deskripsi.setText(listLead.getCmsUsersNpm());



        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_account_circle_black_24dp);
        drawable.setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN);
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
        TextView nama,deskripsi,status,tanggal;
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
        }
    }

}
