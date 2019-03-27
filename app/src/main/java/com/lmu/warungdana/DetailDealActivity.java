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
import com.lmu.warungdana.BottomSheet.BottomSheetOrder;
import com.lmu.warungdana.FragmentDetailDeal.ContactDealFragment;
import com.lmu.warungdana.FragmentDetailDeal.DocumentDealFragment;
import com.lmu.warungdana.FragmentDetailDeal.InfoDealFragment;
import com.lmu.warungdana.FragmentDetailDeal.NoteDealFragment;
import com.lmu.warungdana.Response.DetailOrder;
import com.lmu.warungdana.Response.DetailProductUFI;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDealActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Context context;
    public Integer idOrder, idContact,idProductUfi;
    private ApiEndPoint mApiService;
    ViewPagerAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deal);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.inflateMenu(R.menu.menu_detail_deal);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout = findViewById(R.id.tabsHome);
        viewPager = findViewById(R.id.frame_container);

        getDetail();



    }

    public void getTabDoc(){

        getDetail();
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment dealFragment = new DealFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, dealFragment).commit();
    }

    public void getDetail() {

        idOrder = getIntent().getIntExtra("idOrder", 0);
        idContact = getIntent().getIntExtra("idContact", 0);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InfoDealFragment(), "info");
        adapter.addFragment(new ContactDealFragment(), "contact");
        adapter.addFragment(new DocumentDealFragment(), "document");
        adapter.addFragment(new NoteDealFragment(),"note");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        mApiService = UtilsApi.getAPIService();

        mApiService.orderDetail(idOrder).enqueue(new Callback<DetailOrder>() {
            @Override
            public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        toolbar.setSubtitle(response.body().getContactFirstName());
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailOrder> call, Throwable t) {
                try {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        mApiService.productUfiDetail(idOrder).enqueue(new Callback<DetailProductUFI>() {
            @Override
            public void onResponse(Call<DetailProductUFI> call, Response<DetailProductUFI> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        toolbar.setTitle(response.body().getMstUnitModel());

                        idProductUfi = response.body().getId();
                        if (response.body().getOtr_taksasi() != null || response.body().getNomor_taksasi() != null){

                            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    BottomSheetOrder bottomSheetLead = new BottomSheetOrder(false);
                                    bottomSheetLead.show(getSupportFragmentManager(), bottomSheetLead.getTag());
                                }
                            });
                        }else{

                            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    BottomSheetOrder bottomSheetLead = new BottomSheetOrder(true);
                                    bottomSheetLead.show(getSupportFragmentManager(), bottomSheetLead.getTag());
                                }
                            });

                        }
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailProductUFI> call, Throwable t) {
                try {
                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_print:
                Intent intent = new Intent(DetailDealActivity.this, PrintActivity.class);
                intent.putExtra("idOrder", idOrder);
                intent.putExtra("idContact", idContact);
                startActivity(intent);
                return true;

        }
        return false;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2){

            int pos = viewPager.getCurrentItem();
            Fragment activeFragment = adapter.getItem(pos);

            if(pos == 2){

                ((DocumentDealFragment)activeFragment).getOrderDocList();
            }


//            DocumentDealFragment documentDealFragment = (DocumentDealFragment) getFragmentManager().findFragmentByTag()
//            documentDealFragment.getOrderDocList();
//            adapter.addFragment(new DocumentDealFragment(), "dokumen");
        }
    }
}
