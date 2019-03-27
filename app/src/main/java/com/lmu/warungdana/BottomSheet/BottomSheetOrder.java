package com.lmu.warungdana.BottomSheet;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.AddAdditionalLeadActivity;
import com.lmu.warungdana.AddLeadActivity;
import com.lmu.warungdana.AddVisumActivity;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.DetailDealActivity;
import com.lmu.warungdana.DetailTargetActivity;
import com.lmu.warungdana.DocumentActivity;
import com.lmu.warungdana.Helper.NumberTextWatcher;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.CheckNewDB;
import com.lmu.warungdana.Response.RespPost;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class BottomSheetOrder extends BottomSheetDialogFragment {
    private LinearLayout bsNote,bsDocument,bsTaksasi;
    private Integer idOrder;
    private Boolean statusTaksasi = true;
    SharedPrefManager sharedPreferences;
    Integer id_cms_users,idProductUfi;
    DetailDealActivity activity;
    Context context;
    private ApiEndPoint mApiService;

    @SuppressLint("ValidFragment")
    public BottomSheetOrder(Boolean statusTaksasi) {
        this.statusTaksasi = statusTaksasi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_order,container,false);
        mApiService = UtilsApi.getAPIService();
        context = getContext();
        bsNote = view.findViewById(R.id.bsNote);
        bsDocument = view.findViewById(R.id.bsDocument);
        bsTaksasi = view.findViewById(R.id.bsTaksasi);
        sharedPreferences = new SharedPrefManager(getActivity());
        id_cms_users = sharedPreferences.getSpId();
        activity = (DetailDealActivity) getActivity();
        idOrder = activity.idOrder;
        idProductUfi = activity.idProductUfi;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (statusTaksasi == false){

            bsTaksasi.setVisibility(LinearLayout.GONE);
        }
        bsNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddAdditionalLeadActivity.class);
                intent.putExtra("idIndicator",1);
                intent.putExtra("idModul",4);
                intent.putExtra("idData",idOrder);
                getContext().startActivity(intent);
            }
        });

        bsDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadFoto = new Intent(getActivity(), DocumentActivity.class);
                uploadFoto.putExtra("idOrder",idOrder);
                uploadFoto.putExtra("id_cms_users",id_cms_users);
                uploadFoto.putExtra("mode","tambah");
                startActivityForResult(uploadFoto,2);
            }
        });

        bsTaksasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taksasiInput();
//                Intent intent = new Intent(getContext(),AddAdditionalLeadActivity.class);
//                getContext().startActivity(intent);
            }
        });

    }

    private void taksasiInput() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sedang menyimpan ..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_taksasi, null);
        final EditText tvNomorTaksasi = view.findViewById(R.id.edtNomorTaksasi);
        final EditText tvOTRTaksasi = view.findViewById(R.id.edtOTRTaksasi);
        final TextView tvNoTaksasiX = view.findViewById(R.id.tvNoTaksasi);
        final TextView tvOTRTaksasiX = view.findViewById(R.id.tvOTRTaksasi);
        final TextView tvWajibOTR = view.findViewById(R.id.tvWajibOTRTaksasi);
        final TextView tvWajibNomor = view.findViewById(R.id.tvWajibNomorTaksasi);

        tvOTRTaksasi.addTextChangedListener(new NumberTextWatcher(tvOTRTaksasi));
        tvNomorTaksasi.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(20)});
        tvOTRTaksasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (tvOTRTaksasi.length() >= 1){

                    tvWajibOTR.setVisibility(TextView.GONE);
                }else{

                    tvWajibOTR.setVisibility(TextView.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvNomorTaksasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tvNoTaksasiX.setText("Nomor Taksasi ("+tvNomorTaksasi.length()+"/20) :");

                if (tvNomorTaksasi.length() >= 1){

                    tvWajibNomor.setVisibility(TextView.GONE);
                }else{

                    tvWajibNomor.setVisibility(TextView.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        alert.setView(view);

        alert.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (tvOTRTaksasi.getText().toString().isEmpty() || tvNomorTaksasi.getText().toString().isEmpty()){

                    final AlertDialog dialogKonfirmasi = new AlertDialog.Builder(context).create();
                    dialogKonfirmasi.setMessage("Semua data wajib diisi");
                    dialogKonfirmasi.setButton(AlertDialog.BUTTON_POSITIVE, "Oke",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                    dialogKonfirmasi.show();
//                    Toast.makeText(getActivity(),"OTR Taksasi wajib diisi !",Toast.LENGTH_LONG);
//                    dismiss();
                }else{

                    final AlertDialog dialogKonfirmasi = new AlertDialog.Builder(context).create();
                    dialogKonfirmasi.setTitle("Konfirmasi penambahan hasil Taksasi");
                    dialogKonfirmasi.setMessage("Simpan data taksasi ?");
                    dialogKonfirmasi.setButton(AlertDialog.BUTTON_NEUTRAL, "Batal",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                    dialogKonfirmasi.setButton(AlertDialog.BUTTON_NEGATIVE, "Simpan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            progressDialog.show();

                            mApiService.orderProducctUfiUpdate(idProductUfi,Integer.parseInt(tvOTRTaksasi.getText().toString().replaceAll(",", "").replaceAll("\\.", "")),tvNomorTaksasi.getText().toString(),id_cms_users).
                                    enqueue(new Callback<RespPost>() {
                                        @Override
                                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {

                                            if (response.isSuccessful()){

                                                if (response.body().getApiStatus() != 0){

                                                    Toast.makeText(context,"Berhasil menyimpan Taksasi",Toast.LENGTH_LONG).show();
                                                    activity.getDetail();
                                                    dismiss();

                                                }else{

                                                    Toast.makeText(context,"Gagal menyimpan Taksasi",Toast.LENGTH_LONG).show();
                                                }
                                            }else{

                                                Toast.makeText(context,"Terjadi kesalahan , silahkan coba lagi",Toast.LENGTH_LONG).show();
                                            }

                                            progressDialog.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<RespPost> call, Throwable t) {

                                            Toast.makeText(context,"Not Responding",Toast.LENGTH_LONG).show();

                                            progressDialog.dismiss();
                                        }
                                    });


                        }
                    });
                    dialogKonfirmasi.show();

                }



            }
        });

        alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dismiss();
            }
        });

        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){


            case 1 :

                if (resultCode == Activity.RESULT_OK){

                    dismiss();
                    activity.getDetail();
                }

                break;

            case 2:

                if (resultCode == Activity.RESULT_OK){

                    dismiss();
                    activity.getTabDoc();
                }

                break;

                default:

                    break;
        }


    }
}
