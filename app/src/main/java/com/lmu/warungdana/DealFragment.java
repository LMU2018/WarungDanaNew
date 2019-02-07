package com.lmu.warungdana;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.lmu.warungdana.Adapter.ListOrderAdapter;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.lmu.warungdana.FragmentOrder.LostOrderFragment;
import com.lmu.warungdana.FragmentOrder.ProspectOrderFragment;
import com.lmu.warungdana.FragmentOrder.QualifiedOrderFragment;
import com.lmu.warungdana.FragmentOrder.UnqualifiedOrderFragment;
import com.lmu.warungdana.FragmentOrder.WonOrderFragment;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListOrder;
import com.lmu.warungdana.Response.RespListOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DealFragment extends Fragment {
    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button fab;
    Context context;
    Integer idUser;

    private ApiEndPoint mApiService;
    SharedPrefManager sharedPrefManager;

    ListOrderAdapter listOrderAdapter;
    ArrayList<ListOrder> listOrders;
    RecyclerView recyclerView;
    retrofit2.Call<RespListOrder> call = null;
    TextView tvPencarian;

    public DealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deal, container, false);
        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        tvPencarian = view.findViewById(R.id.tvPencarian);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Deal");
        toolbar.setSubtitle("Order");
        tabLayout = view.findViewById(R.id.tabsHome);
        viewPager = view.findViewById(R.id.frame_container);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ProspectOrderFragment(), "Prospecting");
        adapter.addFragment(new QualifiedOrderFragment(), "qualified");
        adapter.addFragment(new WonOrderFragment(), "won");
        adapter.addFragment(new UnqualifiedOrderFragment(), "unqualified");
        adapter.addFragment(new LostOrderFragment(), "lost");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        fab = view.findViewById(R.id.btnCheck);

        listOrders = new ArrayList<>();
        listOrderAdapter = new ListOrderAdapter(getContext(), listOrders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listOrderAdapter);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddOrderActivity.class));
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_deal, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari nama deal");

        EditText searchEt = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEt.setTextColor(getResources().getColor(R.color.pencarianText));
        searchEt.setHintTextColor(getResources().getColor(R.color.pencarianText));

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchView.setQuery("",false);
                tvPencarian.setVisibility(TextView.INVISIBLE);
                recyclerView.setVisibility(RecyclerView.INVISIBLE);
                listOrders.clear();
                listOrderAdapter.notifyDataSetChanged();

                return true;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPencarian.setText("Minimal 3 Huruf");
                tvPencarian.setVisibility(TextView.VISIBLE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (call != null) {
                    call.cancel();
                }

                if (newText.length() >= 3) {
                    tvPencarian.setVisibility(TextView.INVISIBLE);
                    callFilter(newText);
                } else {
                    tvPencarian.setText("Minimal 3 Huruf");
                    tvPencarian.setVisibility(TextView.VISIBLE);
                    listOrders.clear();
                    listOrderAdapter.notifyDataSetChanged();
                }

                /*if (newText.equalsIgnoreCase("") || newText == null || newText.isEmpty()) {
                    listTargets.clear();
                    listTargetAdapter.notifyDataSetChanged();
                } else {
                    callFilter(newText);
                }*/

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void callFilter(String newText) {

        tvPencarian.setVisibility(TextView.VISIBLE);
        tvPencarian.setText("Sedang Mencari . . .");

        call = mApiService.orderSearch(newText, idUser);
        call.enqueue(new Callback<RespListOrder>() {
            @Override
            public void onResponse(retrofit2.Call<RespListOrder> call, Response<RespListOrder> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {

                        tvPencarian.setText("Hasil Pencarian");
                        listOrders.clear();
                        listOrderAdapter.notifyDataSetChanged();
                        recyclerView.setVisibility(RecyclerView.VISIBLE);

                        List<ListOrder> list = response.body().getData();

                        if (list.size() <= 0) {
                            tvPencarian.setText("Pencarian Tidak Ditemukan");
                        }

                        for (int i = 0; i < list.size(); i++) {
                            String firstName, lastName, status, model, createdAt;
                            Integer id, idContact, plafond, idUnit;
                            id = list.get(i).getId();
                            idContact = list.get(i).getIdContact();
                            firstName = list.get(i).getContactFirstName();
                            lastName = list.get(i).getContactLastName();
                            status = list.get(i).getOrderMstStatusStatus();
                            plafond = list.get(i).getPlafond();
                            idUnit = list.get(i).getIdMstUnit();
                            model = list.get(i).getModel();
                            createdAt = list.get(i).getCreatedAt();

                            listOrders.add(new ListOrder(id, idContact, firstName, lastName, status, plafond, idUnit, model, createdAt));

                        }

                        listOrderAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RespListOrder> call, Throwable t) {
                if (call.isCanceled()) {

                } else {
                    tvPencarian.setText("Gagal Mencari");
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
