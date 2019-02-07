package com.lmu.warungdana;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lmu.warungdana.BottomSheet.BottomSheetHome;
import com.lmu.warungdana.FragmentHome.ActivityFragment;
import com.lmu.warungdana.FragmentHome.OverviewFragment;
import com.lmu.warungdana.SQLite.DatabaseHelper;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements android.support.v7.widget.Toolbar.OnMenuItemClickListener {

    private TabLayout tabLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    SharedPrefManager sharedPrefManager;
    DatabaseHelper databaseHelper;
    ApiEndPoint mApiService;
    String userAgent;
    Integer userId, lastLogin;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Warung Dana");
        toolbar.inflateMenu(R.menu.menu_home);
        toolbar.setOnMenuItemClickListener(this);
        viewPager = view.findViewById(R.id.frame_container);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OverviewFragment(), "Overview");
        adapter.addFragment(new ActivityFragment(), "Riwayat");
        viewPager.setAdapter(adapter);
        sharedPrefManager = new SharedPrefManager(getContext());
        mApiService = UtilsApi.getAPIService();
        tabLayout = view.findViewById(R.id.tabsHome);
        tabLayout.setupWithViewPager(viewPager);
        userAgent = System.getProperty("http.agent");
        userId = sharedPrefManager.getSpId();
        lastLogin = sharedPrefManager.getSpLastLogin();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
//        loginTimeout();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                BottomSheetHome bottomSheetHome = new BottomSheetHome();
                bottomSheetHome.show(getFragmentManager(), bottomSheetHome.getTag());
                return true;
            case R.id.navigation_target:
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
                alertdialog.setMessage("Log Out ?").setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                mApiService.userLogs(userAgent, "Logout", userId).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            case R.id.tentang:
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lmu2018.github.io/WarungDana/"));
//                startActivity(browserIntent);
                Intent tentang = new Intent(getActivity(),TentangApp.class);
                startActivity(tentang);

                return true;
            case R.id.ubahPassword:
                Intent intent = new Intent(getContext(),UbahPassword.class);
                startActivity(intent);

                return true;
        }
        return false;
    }

    private void loginTimeout() {
        Integer hasil = 0;
        Calendar calendar = Calendar.getInstance();
        Integer minute = calendar.get(Calendar.MINUTE);

        hasil = minute - lastLogin;

        if (hasil > 1) {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            mApiService.userLogs(userAgent, "Logout", userId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

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
