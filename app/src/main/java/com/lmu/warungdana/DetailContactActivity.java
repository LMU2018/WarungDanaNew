package com.lmu.warungdana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lmu.warungdana.BottomSheet.BottomSheetContact;
import com.lmu.warungdana.FragmentDetailContact.CollabContactFragment;
import com.lmu.warungdana.FragmentDetailContact.DealContactFragment;
import com.lmu.warungdana.FragmentDetailContact.InfoContactFragment;
import com.lmu.warungdana.FragmentDetailContact.NoteContactFragment;
import com.lmu.warungdana.Response.DetailAdditionalContact;
import com.lmu.warungdana.Response.DetailContact;
import com.lmu.warungdana.Response.ListAddress;
import com.lmu.warungdana.Response.ListPhone;
import com.lmu.warungdana.Response.RespListAddress;
import com.lmu.warungdana.Response.RespListPhone;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailContactActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public Integer idContact, idAddress, idAddContact, idUpdateContact, idMainphone, idMobilephone;
    public String nama,
            nik, firstName, lastName, birthPlace, birthDate, gender, idReligion, idStsMarital, idJob,
            idDataSource, mainphone, mobilephone, addressCat, address, rt, rw, prov, kab, kec, kel,
            mother, tanggungan, stsRumah, perusahaan, stsKaryawan, jabatan, lamakerja, pendapatan, pengeluaran;
    private ApiEndPoint mApiService;
    private Toolbar toolbar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_detail_contact);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mApiService = UtilsApi.getAPIService();
        tabLayout = findViewById(R.id.tabsHome);
        viewPager = findViewById(R.id.frame_container);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InfoContactFragment(), "info");
        adapter.addFragment(new DealContactFragment(), "Deals");
        adapter.addFragment(new NoteContactFragment(), "note");
//        adapter.addFragment(new DocumentContactFragment(), "document");
        adapter.addFragment(new CollabContactFragment(), "collab");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        idContact = getIntent().getIntExtra("idContact", 0);
        context = getApplicationContext();

        getDetail();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetContact bottomSheetLead = new BottomSheetContact();
                bottomSheetLead.show(getSupportFragmentManager(), bottomSheetLead.getTag());
            }
        });

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.edit:
                Intent intent = new Intent(this, AddContactActivity.class);
                intent.putExtra("idContact", idContact);
                intent.putExtra("idContactUpdate", idUpdateContact);
                intent.putExtra("nik", nik);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("birthPlace", birthPlace);
                intent.putExtra("birthDate", birthDate);
                intent.putExtra("gender", gender);
                intent.putExtra("religion", idReligion);
                intent.putExtra("marital", idStsMarital);
                intent.putExtra("idJob", idJob);
                intent.putExtra("idSource", idDataSource);
                intent.putExtra("idMainPhone", idMainphone);
                intent.putExtra("mainPhone", mainphone);
                intent.putExtra("idMobilePhone", idMobilephone);
                intent.putExtra("mobilePhone", mobilephone);
                intent.putExtra("idAddress", idAddress);
                intent.putExtra("idAddressCat", addressCat);
                intent.putExtra("alamat", address);
                intent.putExtra("rt", rt);
                intent.putExtra("rw", rw);
                intent.putExtra("provinsi", prov);
                intent.putExtra("kabupaten", kab);
                intent.putExtra("kecamatan", kec);
                intent.putExtra("kelurahan", kel);
                intent.putExtra("idAddContact", idAddContact);
                intent.putExtra("mother", mother);
                intent.putExtra("tanggungan", tanggungan);
                intent.putExtra("stsRumah", stsRumah);
                intent.putExtra("perusahaan", perusahaan);
                intent.putExtra("stsKaryawan", stsKaryawan);
                intent.putExtra("jabatan", jabatan);
                intent.putExtra("lamakerja", lamakerja);
                intent.putExtra("pendapatan", pendapatan);
                intent.putExtra("pengeluaran", pengeluaran);
                startActivity(intent);
                finish();

        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = new ContactFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    private void getDetail() {

        mApiService.detailContact(idContact).enqueue(new Callback<DetailContact>() {
            @Override
            public void onResponse(Call<DetailContact> call, Response<DetailContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        String jeniskelamin;
                        if (response.body().getLastName() == null) {
                            nama = response.body().getFirstName();
                        } else {
                            nama = response.body().getFirstName() + " " + response.body().getLastName();
                        }

                        toolbar.setTitle(nama);
                        toolbar.setSubtitle(response.body().getNik());

                        idUpdateContact = response.body().getId();
                        nik = response.body().getNik();
                        firstName = response.body().getFirstName();
                        lastName = response.body().getLastName();
                        birthPlace = response.body().getBirthPlace();
                        birthDate = response.body().getBirthDate();
                        gender = response.body().getGender();
                        idReligion = response.body().getMstReligionAgama();
                        idStsMarital = response.body().getContactMstStatusMaritalStatus();
                        idJob = response.body().getMstJobJob();
                        idDataSource = response.body().getMstDataSourceDatasource();

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailContact> call, Throwable t) {
                try {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        mApiService.listPhoneContact(idContact).enqueue(new Callback<RespListPhone>() {
            @Override
            public void onResponse(Call<RespListPhone> call, Response<RespListPhone> response) {
                if (response.isSuccessful()) {
                    List<ListPhone> listPhones = response.body().getData();
                    if (listPhones.size() != 0) {
                        idMainphone = response.body().getData().get(0).getId();
                        mainphone = response.body().getData().get(0).getNumber();

                        try {
                            idMobilephone = response.body().getData().get(1).getId();
                            mobilephone = response.body().getData().get(1).getNumber();
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListPhone> call, Throwable t) {
                try {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        mApiService.listAddressContact(idContact).enqueue(new Callback<RespListAddress>() {
            @Override
            public void onResponse(Call<RespListAddress> call, Response<RespListAddress> response) {
                if (response.isSuccessful()) {
                    List<ListAddress> listAddress = response.body().getData();

                    if (listAddress.size() != 0) {
                        idAddress = response.body().getData().get(0).getId();
                        addressCat = response.body().getData().get(0).getMstCategoryAddressCategory();
                        address = response.body().getData().get(0).getAddress();
                        rt = response.body().getData().get(0).getRt();
                        rw = response.body().getData().get(0).getRw();
                        prov = response.body().getData().get(0).getMstAddressProvinsi();
                        kab = response.body().getData().get(0).getMstAddressKabupaten();
                        kec = response.body().getData().get(0).getMstAddressKecamatan();
                        kel = response.body().getData().get(0).getMstAddressKelurahan();
                    }

                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListAddress> call, Throwable t) {
                try {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        mApiService.detailAdditionalContact(idContact).enqueue(new Callback<DetailAdditionalContact>() {
            @Override
            public void onResponse(Call<DetailAdditionalContact> call, Response<DetailAdditionalContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {

                        idAddContact = response.body().getId();
                        mother = response.body().getMother();
                        tanggungan = response.body().getFamily().toString();
                        stsRumah = response.body().getContactMstStatusPlaceStatus();
                        perusahaan = response.body().getCompany();
                        stsKaryawan = response.body().getContactMstStatusEmployeeStatus();
                        jabatan = response.body().getPosition();
                        lamakerja = response.body().getWorkingTime().toString();
                        pendapatan = response.body().getIncome().toString();
                        pengeluaran = response.body().getOutlay().toString();

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DetailAdditionalContact> call, Throwable t) {
                try {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
