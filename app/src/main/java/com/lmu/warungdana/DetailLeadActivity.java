package com.lmu.warungdana;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.lmu.warungdana.BottomSheet.BottomSheetLead;
import com.lmu.warungdana.FragmentDetailLead.InfoLeadFragment;
import com.lmu.warungdana.FragmentDetailLead.LogLeadFragment;
import com.lmu.warungdana.FragmentDetailLead.NoteLeadFragment;
import com.lmu.warungdana.FragmentDetailLead.VisumLeadFragment;
import com.lmu.warungdana.Response.DetailLead;
import com.lmu.warungdana.Response.RespListAddress;
import com.lmu.warungdana.Response.RespListPhone;
import com.lmu.warungdana.Response.RespListUnit;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLeadActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ApiEndPoint mApiService;
    public Integer idLead, idUser, tahun, idAddress, idUnit;
    private String nama, status, logTotal, NoteTotal, visumTotal, pekerjaan, sumber,
            firstName, lastName, mainPhone, address, prov, kab, kec, kel, postalcode, addressCat,
            merk, model, nopol, pajak, owner;
    private FloatingActionButton fab;
    Context context;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lead);
        toolbar = findViewById(R.id.toolbar);
        sharedPrefManager = new SharedPrefManager(this);
        idUser = sharedPrefManager.getSpId();

        toolbar.inflateMenu(R.menu.menu_detail_lead);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout = findViewById(R.id.tabsHome);

        mApiService = UtilsApi.getAPIService();
        idLead = getIntent().getIntExtra("idLead", 0);

        viewPager = findViewById(R.id.frame_container);

        tabLayout.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);

        getDetail();
        listener();
    }

    private void listener() {


        mApiService.listPhoneLead(idLead).enqueue(new Callback<RespListPhone>() {
            @Override
            public void onResponse(Call<RespListPhone> call, Response<RespListPhone> response) {
                if (response.isSuccessful()) {
                    try {
                        mainPhone = response.body().getData().get(0).getNumber();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
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

        mApiService.listAddressLead(idLead).enqueue(new Callback<RespListAddress>() {
            @Override
            public void onResponse(Call<RespListAddress> call, Response<RespListAddress> response) {
                if (response.isSuccessful()) {

                    try {
                        idAddress = response.body().getData().get(0).getId();
                        addressCat = response.body().getData().get(0).getMstCategoryAddressCategory();
                        address = response.body().getData().get(0).getAddress();
                        prov = response.body().getData().get(0).getMstAddressProvinsi();
                        kab = response.body().getData().get(0).getMstAddressKabupaten();
                        kec = response.body().getData().get(0).getMstAddressKecamatan();
                        kel = response.body().getData().get(0).getMstAddressKelurahan();

                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    if (idAddress != null || address != null){

                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BottomSheetLead bottomSheetLead = new BottomSheetLead(true);
                                bottomSheetLead.show(getSupportFragmentManager(), bottomSheetLead.getTag());
                            }
                        });
                    }else{


                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BottomSheetLead bottomSheetLead = new BottomSheetLead(false);
                                bottomSheetLead.show(getSupportFragmentManager(), bottomSheetLead.getTag());
                            }
                        });
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

        mApiService.listUnit(idLead).enqueue(new Callback<RespListUnit>() {
            @Override
            public void onResponse(Call<RespListUnit> call, Response<RespListUnit> response) {
                if (response.isSuccessful()) {

                    try {
                        idUnit = response.body().getData().get(0).getIdLeadProductDetail();
                        merk = response.body().getData().get(0).getMerk();
                        tahun = response.body().getData().get(0).getYear();
                        model = response.body().getData().get(0).getModel();
                        nopol = response.body().getData().get(0).getNopol();
                        pajak = response.body().getData().get(0).getTaxStatus();
                        owner = response.body().getData().get(0).getOwner();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListUnit> call, Throwable t) {
                try {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
                alertdialog.setMessage("Hapus Lead ?").setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mApiService.leadUpdateStatus(idLead, 6, idUser).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Berhasil Menghapus Data !", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        try {
                                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                return true;

            case R.id.edit:
                    Intent intent = new Intent(this, AddLeadActivity.class);
                    intent.putExtra("idLead", idLead);
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    intent.putExtra("job", pekerjaan);
                    intent.putExtra("source", sumber);
                    intent.putExtra("mainPhone", mainPhone);
                    intent.putExtra("status", status);
                    intent.putExtra("idAlamat", idAddress);
                    intent.putExtra("kategoriAlamat", addressCat);
                    intent.putExtra("alamat", address);
                    intent.putExtra("provinsi", prov);
                    intent.putExtra("kabupaten", kab);
                    intent.putExtra("kecamatan", kec);
                    intent.putExtra("kelurahan", kel);
                    intent.putExtra("idUnit", idUnit);
                    intent.putExtra("merk", merk);
                    intent.putExtra("tahun", tahun);
                    intent.putExtra("model", model);
                    intent.putExtra("nopol", nopol);
                    intent.putExtra("pajak", pajak);
                    intent.putExtra("owner", owner);
                    startActivity(intent);
                    finish();

        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = new LeadFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    private void getDetail() {
        mApiService.leadDetail(idLead).enqueue(new Callback<DetailLead>() {
            @Override
            public void onResponse(Call<DetailLead> call, Response<DetailLead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        if (response.body().getLastName() == null) {
                            toolbar.setTitle(response.body().getFirstName());
                        } else {
                            toolbar.setTitle(response.body().getFirstName() + " " + response.body().getLastName());
                        }
                        if (response.body().getDescription() != null) {
                            toolbar.setSubtitle(response.body().getDescription());
                        } else {
                            toolbar.setSubtitle("Belum di Follow Up");
                        }
                        if (response.body().getLogTotal() != null) {
                            logTotal = response.body().getLogTotal();
                        } else {
                            logTotal = "0";
                        }
                        if (response.body().getNoteTotal() != null) {
                            NoteTotal = response.body().getNoteTotal();
                        } else {
                            NoteTotal = "0";
                        }
                        if (response.body().getVisumTotal() != null) {
                            visumTotal = response.body().getVisumTotal();
                        } else {
                            visumTotal = "0";
                        }
                        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                        adapter.addFragment(new InfoLeadFragment(), "info");
                        adapter.addFragment(new LogLeadFragment(), "log" + " (" + logTotal + ")");
                        adapter.addFragment(new VisumLeadFragment(),"Visum" +" ("+visumTotal+")");
                        adapter.addFragment(new NoteLeadFragment(), "note" + " (" + NoteTotal + ")");
                        viewPager.setAdapter(adapter);

                        firstName = response.body().getFirstName();
                        lastName = response.body().getLastName();
                        pekerjaan = response.body().getMstJobJob();
                        sumber = response.body().getMstDataSourceDatasource();
                        status = response.body().getLeadMstStatusStatus();

                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailLead> call, Throwable t) {
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
