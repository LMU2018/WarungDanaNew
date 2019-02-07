package com.lmu.warungdana.FragmentDetailContact;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListAddressAdapter;
import com.lmu.warungdana.Adapter.ListPhoneContactAdapter;
import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.DetailAdditionalContact;
import com.lmu.warungdana.Response.DetailContact;
import com.lmu.warungdana.Response.ListAddress;
import com.lmu.warungdana.Response.ListPhone;
import com.lmu.warungdana.Response.RespListAddress;
import com.lmu.warungdana.Response.RespListPhone;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoContactFragment extends Fragment {
    private ApiEndPoint mApiService;
    private Integer idContact;
    private TextView Status, job, birth, religion, marital, email, mother, family, domicile, company, employee, position, working, income, outlay;
    private RecyclerView rvPhone, rvAddress;
    private Context context;


    public InfoContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_contact, container, false);
        DetailContactActivity activity = (DetailContactActivity) getActivity();
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

        rvAddress = view.findViewById(R.id.rvAddress);
        rvPhone = view.findViewById(R.id.rvPhone);
        getDetail();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getDetailAdditional();
    }

    private void getDetail() {
        mApiService.detailContact(idContact).enqueue(new Callback<DetailContact>() {
            @Override
            public void onResponse(Call<DetailContact> call, Response<DetailContact> response) {
                if (response.isSuccessful()) {
                    String status;
                    if (response.body().getIdOrderMstStatus() == null) {
                        status = "Non Customer";
                    } else if (response.body().getIdOrderMstStatus() == 2) {
                        status = "Customer";
                    } else {
                        status = "Past Customer";
                    }
                    Status.setText(status);
                    job.setText(response.body().getMstJobJob());
                    birth.setText(response.body().getBirthPlace() + ", " + response.body().getBirthDate());
                    religion.setText(response.body().getMstReligionAgama());
                    marital.setText(response.body().getContactMstStatusMaritalStatus());
                }
            }

            @Override
            public void onFailure(Call<DetailContact> call, Throwable t) {
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
        mApiService.listAddressContact(idContact).enqueue(new Callback<RespListAddress>() {
            @Override
            public void onResponse(Call<RespListAddress> call, Response<RespListAddress> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rvAddress.setLayoutManager(layoutManager);
                    List<ListAddress> listLeads = response.body().getData();
                    rvAddress.setAdapter(new ListAddressAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListAddress> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailAdditional() {
        mApiService.detailAdditionalContact(idContact).enqueue(new Callback<DetailAdditionalContact>() {
            @Override
            public void onResponse(Call<DetailAdditionalContact> call, Response<DetailAdditionalContact> response) {
                if (response.isSuccessful()) {
                    NumberFormat formatter = new DecimalFormat("#,###");

                    if (response.body().getMother() != null) {
                        mother.setText(response.body().getMother());
                    }
                    if (response.body().getFamily() != null) {
                        family.setText(response.body().getFamily().toString() + " Orang");
                    }
                    if (response.body().getContactMstStatusPlaceStatus() != null) {
                        domicile.setText(response.body().getContactMstStatusPlaceStatus());
                    }

                    if (response.body().getCompany() != null) {
                        company.setText(response.body().getCompany());
                    }
                    if (response.body().getContactMstStatusEmployeeStatus() != null) {
                        employee.setText(response.body().getContactMstStatusEmployeeStatus());
                    }
                    if (response.body().getPosition() != null) {
                        position.setText(response.body().getPosition());
                    }
                    if (response.body().getWorkingTime() != null) {
                        working.setText(response.body().getWorkingTime().toString() + " Tahun");
                    }
                    if (response.body().getIncome() != null) {
                        income.setText("IDR " + formatter.format(response.body().getIncome()));
                    }
                    if (response.body().getOutlay() != null) {
                        outlay.setText("IDR " + formatter.format(response.body().getOutlay()));
                    }


                }
            }

            @Override
            public void onFailure(Call<DetailAdditionalContact> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
