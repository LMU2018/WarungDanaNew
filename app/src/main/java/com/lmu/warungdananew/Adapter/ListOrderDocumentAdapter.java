package com.lmu.warungdananew.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.UtilsApi;
import com.lmu.warungdananew.DocumentActivity;
import com.lmu.warungdananew.Picasso.CircleTransform;
import com.lmu.warungdananew.Picasso.RoundedCornerTransform;
import com.lmu.warungdananew.PreviewImageActivity;
import com.lmu.warungdananew.R;
import com.lmu.warungdananew.Response.ListOrderDocument;
import com.lmu.warungdananew.Response.RespPost;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ListOrderDocumentAdapter extends RecyclerView.Adapter<ListOrderDocumentAdapter.ListHolder> {

    List<ListOrderDocument> listDocument;
    Dialog dialog;
    private OnReloadListener mOnReloadListener;

    Context context;

    ApiEndPoint apiEndPoint = UtilsApi.getAPIService();

    public void setOnReloadListener(OnReloadListener l){

        mOnReloadListener= l;

    }

    public interface OnReloadListener{

        void OnReload();
    }

    public ListOrderDocumentAdapter(List<ListOrderDocument> listDocument, Context context) {
        this.listDocument = listDocument;
        this.context = context;
    }



    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order_document, parent, false);
        return new ListOrderDocumentAdapter.ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, final int position) {

        final ListOrderDocument listOrder = listDocument.get(position);

        holder.txtDescipt.setText(listOrder.getDescription());

        Picasso.get()
                .load(listOrder.getPhoto())
                .transform(new RoundedCornerTransform())
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.no_image)
                .resize(600,400)
                .centerInside()// this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

//                        holder.imgLoading.setVisibility(ImageView.GONE);
                        holder.pgLoading.setVisibility(ProgressBar.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

//                        holder.imgLoading.setVisibility(ImageView.GONE);
                        holder.pgLoading.setVisibility(ProgressBar.GONE);
                    }
                });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(v,position);

            }
        });

    }

    private void showDialog(View view , final int position) {

        final View viewA = view;

        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.custom_dialog_order_document);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnLihatGambar , btnEdit , btnHapus;
        TextView tvDeskripsil;
        final ImageView imageView;

        btnLihatGambar = dialog.findViewById(R.id.btnLihat);
        btnEdit = dialog.findViewById(R.id.btnUpdate);
        btnHapus = dialog.findViewById(R.id.btnDelete);
        tvDeskripsil = dialog.findViewById(R.id.tvDesc);
        imageView = dialog.findViewById(R.id.imgThum);

        Picasso.get()
                .load(listDocument.get(position).getPhoto())
                .transform(new CircleTransform())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.no_image)
                .resize(600,400)
                .centerInside()// this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                .into(imageView);

        tvDeskripsil.setText(listDocument.get(position).getDescription());

        btnLihatGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent preview = new Intent(context.getApplicationContext(),PreviewImageActivity.class);
                preview.putExtra("photo",listDocument.get(position).getPhoto());
                context.startActivity(preview);
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiEndPoint.deleteOrderPhoto(listDocument.get(position).getId()).enqueue(new retrofit2.Callback<RespPost>() {
                    @Override
                    public void onResponse(Call<RespPost> call, Response<RespPost> response) {

                        if (response.isSuccessful()){

                            if (response.body() != null){

                                showDialog(viewA , response.body().getApiMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RespPost> call, Throwable t) {

                    }
                });
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent updateIntent = new Intent(context.getApplicationContext(),DocumentActivity.class);
                updateIntent.putExtra("mode","update");
                updateIntent.putExtra("photo",listDocument.get(position).getPhoto());
                updateIntent.putExtra("id",listDocument.get(position).getId());
                updateIntent.putExtra("id_order",listDocument.get(position).getIdOrder());
                updateIntent.putExtra("description",listDocument.get(position).getDescription());
                dialog.dismiss();
                ((FragmentActivity) context).startActivityForResult(updateIntent,2);

            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        try {
            return listDocument.size();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtDescipt;
        ProgressBar pgLoading;

        public ListHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgView);
            txtDescipt = itemView.findViewById(R.id.txtDescription);
            pgLoading = itemView.findViewById(R.id.pgLoading);
//            imgLoading = itemView.findViewById(R.id.imgLoading);
        }
    }

    private void showDialog(View v,final String messages) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
        builder1.setMessage(messages);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogA, int id) {

                        mOnReloadListener.OnReload();
                        dialog.dismiss();
                        dialogA.cancel();


                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
