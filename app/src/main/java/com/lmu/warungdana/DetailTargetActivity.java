package com.lmu.warungdana;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.BottomSheet.BottomSheetTarget;
import com.lmu.warungdana.FragmentDetailTarget.InfoTargetFragment;
import com.lmu.warungdana.FragmentDetailTarget.LogTargetFragment;
import com.lmu.warungdana.FragmentDetailTarget.NoteTargetFragment;
import com.lmu.warungdana.FragmentDetailTarget.VisumTargetFragment;
import com.lmu.warungdana.Response.DetailTarget;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTargetActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ApiEndPoint mApiService;
    public Integer idTarget;
    Context context;
    private String noteTotal, logTotal, visumTotal;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_target);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout = findViewById(R.id.tabsHome);
        viewPager = findViewById(R.id.frame_container);
        context = this;
        tabLayout.setupWithViewPager(viewPager);
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        idTarget = getIntent().getIntExtra("idTarget", 0);

        getDetail();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetTarget bottomSheetLead = new BottomSheetTarget();
                bottomSheetLead.show(getSupportFragmentManager(), bottomSheetLead.getTag());
            }
        });
    }

    private void getDetail() {

        mApiService.targetDetailTotal(idTarget,sharedPrefManager.getSpId()).enqueue(new Callback<DetailTarget>() {
            @Override
            public void onResponse(Call<DetailTarget> call, Response<DetailTarget> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        if (response.body().getLastName() == null) {
                            toolbar.setTitle(response.body().getFirstName());
                        } else {
                            toolbar.setTitle(response.body().getFirstName() + " " + response.body().getLastName());
                        }
                        if (response.body().getIdTargetMstStatus() == 4) {
                            if (response.body().getVisumStatus() != null) {
                                toolbar.setSubtitle(response.body().getVisumStatus());
                            } else {
                                toolbar.setSubtitle("Belum di Kunjungi");
                            }
                        } else {
                            if (response.body().getDescription() != null) {
                                toolbar.setSubtitle(response.body().getDescription());
                            } else {
                                toolbar.setSubtitle("Belum di Follow Up");
                            }
                        }
                        if (response.body().getLogTotal() != null) {
                            logTotal = response.body().getLogTotal().toString();
                        } else {
                            logTotal = "0";
                        }
                        if (response.body().getNoteTotal() != null) {
                            noteTotal = response.body().getNoteTotal().toString();
                        } else {
                            noteTotal = "0";
                        }
                        if (response.body().getVisumTotal() != null) {
                            visumTotal = response.body().getVisumTotal().toString();
                        } else {
                            visumTotal = "0";
                        }

                        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                        adapter.addFragment(new InfoTargetFragment(), "info");
                        adapter.addFragment(new LogTargetFragment(), "log" + " (" + logTotal + ")");
                        adapter.addFragment(new VisumTargetFragment(), "visum" + " (" + visumTotal + ")");
                        adapter.addFragment(new NoteTargetFragment(), "note" + " (" + noteTotal + ")");
                        viewPager.setAdapter(adapter);

                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailTarget> call, Throwable t) {
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
