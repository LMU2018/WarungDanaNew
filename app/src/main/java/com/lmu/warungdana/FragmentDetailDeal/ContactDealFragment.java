package com.lmu.warungdana.FragmentDetailDeal;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListPhoneContactAdapter;
import com.lmu.warungdana.DetailDealActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.DetailAdditionalContact;
import com.lmu.warungdana.Response.DetailContact;
import com.lmu.warungdana.Response.ListPhone;
import com.lmu.warungdana.Response.RespListPhone;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDealFragment extends Fragment {

    private Context context;
    private ApiEndPoint mApiService;
    private Integer idContact;
    private TextView Status, job, birth, religion, marital, email, mother, family, domicile, company, employee, position, working, income, outlay;
    private RecyclerView rvPhone, rvAddress;
    private LinearLayout llStatus;
    NumberFormat formatter = new DecimalFormat("#,###");


    public ContactDealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_contact, container, false);
        DetailDealActivity activity = (DetailDealActivity) getActivity();
        idContact = activity.idContact;

        mApiService = UtilsApi.getAPIService();
        Status = view.findViewById(R.id.tvStatus);
        job = view.findViewById(R.id.tvJob);
        birth = view.findViewById(R.id.tvBirth);
        religion = view.findViewById(R.id.tvReligion);
        marital = view.findViewById(R.id.tvMarital);

        mother = view.findViewById(R.id.tvMother);
        family = view.findViewById(R.id.tvFamily);
        domicile = view.findViewById(R.id.tvHouseStatus);
        company = view.findViewById(R.id.tvCompany);
        employee = view.findViewById(R.id.tvEmployeeStatus);
        position = view.findViewById(R.id.tvPosition);
        working = view.findViewById(R.id.tvWorkingTime);
        income = view.findViewById(R.id.tvIncome);
        outlay = view.findViewById(R.id.tvOutlay);
        llStatus = view.findViewById(R.id.llStatus);

        rvAddress = view.findViewById(R.id.rvAddress);
        rvPhone = view.findViewById(R.id.rvPhone);


        getDetail();
        return view;
    }

    private String convertTime(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd MMMM yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format1.format(date);
        return convertedDate;
    }

    private void getDetail() {
        mApiService.detailContact(idContact).enqueue(new Callback<DetailContact>() {
            @Override
            public void onResponse(Call<DetailContact> call, Response<DetailContact> response) {
                if (response.isSuccessful()) {

                    llStatus.setVisibility(View.GONE);

                    if (response.body().getMstJobJob() != null) {
                        job.setText(response.body().getMstJobJob());
                    } else {
                        job.setText("Empty");
                    }

                    if (response.body().getBirthPlace() != null & response.body().getBirthDate() != null) {
                        birth.setText(response.body().getBirthPlace() + ", " + convertTime(response.body().getBirthDate()));
                    } else if (response.body().getBirthPlace() != null & response.body().getBirthDate() == null) {
                        birth.setText(response.body().getBirthPlace() + ", " + "Empty");
                    } else if (response.body().getBirthPlace() == null & response.body().getBirthDate() != null) {
                        birth.setText("Empty" + ", " + convertTime(response.body().getBirthDate()));
                    } else
                        birth.setText("Empty");

                    if (response.body().getMstReligionAgama() != null) {
                        religion.setText(response.body().getMstReligionAgama());
                    } else {
                        religion.setText("Empty");
                    }

                    if (response.body().getContactMstStatusMaritalStatus() != null) {
                        marital.setText(response.body().getContactMstStatusMaritalStatus());
                    } else {
                        marital.setText("Empty");
                    }

                }
            }

            @Override
            public void onFailure(Call<DetailContact> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        mApiService.detailAdditionalContact(idContact).enqueue(new Callback<DetailAdditionalContact>() {
            @Override
            public void onResponse(Call<DetailAdditionalContact> call, Response<DetailAdditionalContact> response) {
                if (response.isSuccessful()) {


                    if (response.body().getMother() != null) {
                        mother.setText(response.body().getMother());
                    } else {
                        mother.setText("Empty");
                    }

                    if (response.body().getFamily() != null) {
                        family.setText(response.body().getFamily().toString() + " " + "Orang");
                    } else {
                        family.setText("Empty");
                    }

                    if (response.body().getContactMstStatusPlaceStatus() != null) {
                        domicile.setText(response.body().getContactMstStatusPlaceStatus());
                    } else {
                        domicile.setText("Empty");
                    }

                    if (response.body().getCompany() != null) {
                        company.setText(response.body().getCompany());
                    } else {
                        company.setText("Empty");
                    }

                    if (response.body().getContactMstStatusEmployeeStatus() != null) {
                        employee.setText(response.body().getContactMstStatusEmployeeStatus());
                    } else {
                        employee.setText("Empty");
                    }

                    if (response.body().getPosition() != null) {
                        position.setText(response.body().getPosition());
                    } else {
                        position.setText("Empty");
                    }

                    if (response.body().getWorkingTime() != null) {
                        working.setText(response.body().getWorkingTime().toString() + " " + "Tahun");
                    } else {
                        working.setText("Empty");
                    }

                    if (response.body().getIncome() != null) {
                        income.setText("IDR " + formatter.format(response.body().getIncome()));
                    } else {
                        income.setText("Empty");
                    }

                    if (response.body().getOutlay() != null) {
                        outlay.setText("IDR " + formatter.format(response.body().getOutlay()));
                    } else {
                        outlay.setText("Empty");
                    }

                }
            }

            @Override
            public void onFailure(Call<DetailAdditionalContact> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.listPhoneContact(idContact).enqueue(new Callback<RespListPhone>() {
            @Override
            public void onResponse(Call<RespListPhone> call, Response<RespListPhone> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rvPhone.setLayoutManager(layoutManager);
                    List<ListPhone> listLeads = response.body().getData();
                    rvPhone.setAdapter(new ListPhoneContactAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListPhone> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
