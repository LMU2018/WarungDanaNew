package com.lmu.warungdana;


import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.EditText;
import android.widget.TextView;

import com.lmu.warungdana.Adapter.ListTargetAdapter;
import com.lmu.warungdana.FragmentTarget.HotTargetFragment;
import com.lmu.warungdana.FragmentTarget.NewTargetFragment;
import com.lmu.warungdana.FragmentTarget.UnqualifiedTargetFragment;
import com.lmu.warungdana.FragmentTarget.VisitTargetFragment;
import com.lmu.warungdana.FragmentTarget.WorkingTargetFragment;
import com.lmu.warungdana.Response.ListTarget;
import com.lmu.warungdana.Response.RespListTarget;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TargetFragment extends Fragment {
    private TabLayout tabLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    Context context;
    private ApiEndPoint mApiService;
    //    private AutoCompleteTextView filter;
    private Integer idUser, idLead;
    private String name;
    SharedPrefManager sharedPrefManager;
    List<ListTarget> listAlamats;

    ListTargetAdapter listTargetAdapter;
    ArrayList<ListTarget> listTargets;
    RecyclerView recyclerView;
    Call<RespListTarget> call = null;
    TextView tvPencarian;

    public TargetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_target, container, false);
        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvPencarian = view.findViewById(R.id.tvPencarian);
        tvPencarian.setVisibility(View.GONE);

        listTargets = new ArrayList<>();
        listTargetAdapter = new ListTargetAdapter(getContext(), listTargets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listTargetAdapter);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setTitle("Target");
        toolbar.setSubtitle("Telemarketing & Visit Customer");
        viewPager = view.findViewById(R.id.frame_container);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HotTargetFragment(), "HOT");
        adapter.addFragment(new NewTargetFragment(), "New");
        adapter.addFragment(new WorkingTargetFragment(), "working");
        adapter.addFragment(new VisitTargetFragment(), "visit");
        adapter.addFragment(new UnqualifiedTargetFragment(), "unqualified");
        viewPager.setAdapter(adapter);
//        filter = view.findViewById(R.id.filter);

        tabLayout = view.findViewById(R.id.tabsHome);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        initFilter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_target, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari nama target");

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
                tvPencarian.setVisibility(View.GONE);
                recyclerView.setVisibility(RecyclerView.INVISIBLE);
                listTargets.clear();
                listTargetAdapter.notifyDataSetChanged();

                return true;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPencarian.setText("Minimal 3 Huruf");
                tvPencarian.setVisibility(View.GONE);
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
                    listTargets.clear();
                    listTargetAdapter.notifyDataSetChanged();
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

        call = mApiService.targetSearch(newText, "", idUser);

        call.enqueue(new Callback<RespListTarget>() {
            @Override
            public void onResponse(Call<RespListTarget> call, Response<RespListTarget> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {

                        tvPencarian.setText("Hasil Pencarian");
                        listTargets.clear();
                        listTargetAdapter.notifyDataSetChanged();
                        recyclerView.setVisibility(RecyclerView.VISIBLE);

                        List<ListTarget> list = response.body().getData();

                        if (list.size() <= 0) {
                            tvPencarian.setText("Pencarian Tidak Ditemukan");
                        }

                        for (int i = 0; i < list.size(); i++) {
                            String category, firstName, lastName, recall, description, status, revisit, visitStatus,updated_by,created_at_log;
                            Integer id, idTargetMstStatus, idMstLogDesc, idMstLogStatus, idMstVisumStatus;
                            category = list.get(i).getCategory();
                            firstName = list.get(i).getFirstName();
                            lastName = list.get(i).getLastName();
                            recall = list.get(i).getRecall();
                            description = list.get(i).getDescription();
                            status = list.get(i).getStatus();
                            revisit = list.get(i).getRevisit();
                            visitStatus = list.get(i).getVisitStatus();
                            id = list.get(i).getId();
                            idTargetMstStatus = list.get(i).getIdTargetMstStatus();
                            idMstLogDesc = list.get(i).getIdMstLogDesc();
                            idMstLogStatus = list.get(i).getIdMstLogStatus();
                            idMstVisumStatus = list.get(i).getIdMstVisumStatus();
                            updated_by = list.get(i).getUpdated_by();
                            created_at_log = list.get(i).getCreated_at_target_log();

                            listTargets.add(new ListTarget(id, idTargetMstStatus, category, firstName, lastName,updated_by, recall, idMstLogDesc,
                                    idMstLogStatus, description, status, idMstVisumStatus, revisit, visitStatus,created_at_log));
                        }

                        listTargetAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<RespListTarget> call, Throwable t) {

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

    private void initFilter() {
/*
        mApiService.listTarget(idUser, null).enqueue(new Callback<RespListTarget>() {
            @Override
            public void onResponse(Call<RespListTarget> call, Response<RespListTarget> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listAlamats = response.body().getData();
                        final List<String> list = new ArrayList<>();
                        String name;


                        for (int i = 0; i < listAlamats.size(); i++) {
                            if (listAlamats.get(i).getLastName() == null) {
                                name = listAlamats.get(i).getFirstName();
                            } else {
                                name = listAlamats.get(i).getFirstName() + " " + listAlamats.get(i).getLastName();
                            }
                            list.add(name);
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, list);
                        filter.setAdapter(adapter);
                        filter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selection = (String) parent.getItemAtPosition(position);
                                for (int i = 0; i < listAlamats.size(); i++) {
                                    String nama;
                                    if (listAlamats.get(i).getLastName() == null) {
                                        nama = listAlamats.get(i).getFirstName();
                                    } else {
                                        nama = listAlamats.get(i).getFirstName() + " " + listAlamats.get(i).getLastName();
                                    }
                                    if (nama.equals(selection)) {
                                        idLead = listAlamats.get(i).getId();
                                        break;
                                    }
                                }
                                Intent intent = new Intent(context, DetailTargetActivity.class);
                                intent.putExtra("idTarget", idLead);
                                context.startActivity(intent);
                                filter.setText("");

                            }

                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespListTarget> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

}
