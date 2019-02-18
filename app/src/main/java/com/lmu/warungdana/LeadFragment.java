package com.lmu.warungdana;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListLeadAdapter;
import com.lmu.warungdana.BottomSheet.BottomSheetLeadAvail;
import com.lmu.warungdana.FragmentLead.CollabLeadFragment;
import com.lmu.warungdana.FragmentLead.FavoriteLeadFragment;
import com.lmu.warungdana.FragmentLead.HotLeadFragment;
import com.lmu.warungdana.FragmentLead.NewLeadFragment;
import com.lmu.warungdana.FragmentLead.UnqualifiedLeadFragment;
import com.lmu.warungdana.FragmentLead.VisitLeadFragment;
import com.lmu.warungdana.FragmentLead.WorkingLeadFragment;
import com.lmu.warungdana.Response.CheckNewDB;
import com.lmu.warungdana.Response.ListLead;
import com.lmu.warungdana.Response.RespListLead;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.SharedPrefManager;
import com.lmu.warungdana.Api.UtilsApi;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeadFragment extends Fragment {

    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button fab;
    List<ListLead> listAlamats;
    private ArrayAdapter<String> mAdapter;
    Context context;
    private ApiEndPoint mApiService;
    //    private AutoCompleteTextView filter;
    private Integer idUser, idLead;
    private int hot, newData, working, unqualified;
    SharedPrefManager sharedPrefManager;

    ListLeadAdapter listLeadAdapter;
    ArrayList<ListLead> listLeads;
    RecyclerView recyclerView;
    Call<RespListLead> call = null;
    TextView tvPencarian;

    public LeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lead, container, false);
        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);
        idUser = sharedPrefManager.getSpId();

        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvPencarian = view.findViewById(R.id.tvPencarian);

        tvPencarian.setVisibility(View.GONE);

        listLeads = new ArrayList<>();
        listLeadAdapter = new ListLeadAdapter(getContext(), listLeads);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listLeadAdapter);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Lead");
        toolbar.setSubtitle("New Database");
//        toolbar.inflateMenu(R.menu.menu_list_data);
//        setHasOptionsMenu(true);

        mApiService = UtilsApi.getAPIService();
        viewPager = view.findViewById(R.id.frame_container);
        tabLayout = view.findViewById(R.id.tabsHome);
        tabLayout.setupWithViewPager(viewPager);
        fab = view.findViewById(R.id.btnCheck);
//        filter = view.findViewById(R.id.filter);

        initAdapter();
        setupTabIcons();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View view = inflater.inflate(R.layout.dialog_add_lead, null);
                        final MaterialEditText nohp = view.findViewById(R.id.phoneNumber);

                        alert.setTitle("Tambah Lead Baru");
                        alert.setView(view);
                        alert.setPositiveButton("Check", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                String nope;
                                if (nohp.length() < 10) {
                                    Toast.makeText(context, "Nomor HP kurang dari 10 Angka!", Toast.LENGTH_LONG).show();
                                } else {
                                    nope = nohp.getText().toString();
                                    mApiService.checkNewDB(nope).enqueue(new Callback<CheckNewDB>() {
                                        @Override
                                        public void onResponse(Call<CheckNewDB> call, Response<CheckNewDB> response) {
                                            if (response.isSuccessful()) {
                                                if (response.body().getApiStatus() == 0) {
                                                    Intent intent = new Intent(context, AddLeadActivity.class);
                                                    intent.putExtra("noHP", nohp.getText().toString());
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(context, "Data Sudah Ada!", Toast.LENGTH_LONG).show();
                                                    BottomSheetLeadAvail bottomSheet = new BottomSheetLeadAvail();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putInt("idLead", response.body().getIdLead());
                                                    bottomSheet.setArguments(bundle);
                                                    bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
                                                }
                                            } else {
                                                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckNewDB> call, Throwable t) {
                                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        });


                        alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });

                        alert.show();

                    }
                });

//        initFilter();

    }

    private void initAdapter() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FavoriteLeadFragment(), "");
        adapter.addFragment(new NewLeadFragment(), "New");
        adapter.addFragment(new HotLeadFragment(), "HOT");
        adapter.addFragment(new WorkingLeadFragment(), "working");
        adapter.addFragment(new VisitLeadFragment(),"Visit");
        adapter.addFragment(new CollabLeadFragment(), "collab");
        adapter.addFragment(new UnqualifiedLeadFragment(), "unqualified");
        viewPager.setAdapter(adapter);

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lead, menu);
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
                searchView.setQuery("", false);
                tvPencarian.setVisibility(View.GONE);
                recyclerView.setVisibility(RecyclerView.INVISIBLE);
                listLeads.clear();
                listLeadAdapter.notifyDataSetChanged();

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
                    tvPencarian.setVisibility(View.GONE);
                    callFilter(newText);
                } else {
                    tvPencarian.setText("Minimal 3 Huruf");
                    tvPencarian.setVisibility(TextView.VISIBLE);
                    listLeads.clear();
                    listLeadAdapter.notifyDataSetChanged();
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

        tvPencarian.setVisibility(View.GONE);
        tvPencarian.setText("Sedang Mencari . . .");

        call = mApiService.leadSearch(newText, idUser);

        call.enqueue(new Callback<RespListLead>() {
            @Override
            public void onResponse(Call<RespListLead> call, Response<RespListLead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {

                        tvPencarian.setText("Hasil Pencarian");
                        listLeads.clear();
                        listLeadAdapter.notifyDataSetChanged();
                        recyclerView.setVisibility(RecyclerView.VISIBLE);

                        List<ListLead> list = response.body().getData();

                        if (list.size() <= 0) {
                            tvPencarian.setText("Pencarian Tidak Ditemukan");
                        }

//                        for (int i = 0; i < list.size(); i++) {
//                            String firstName, lastName, recall, description, status,created_at;
//                            Integer id, idLeadMstStatus, idMstLogDesc, idMstLogStatus, favorite;
//                            id = list.get(i).getId();
//                            firstName = list.get(i).getFirstName();
//                            lastName = list.get(i).getLastName();
//                            idLeadMstStatus = list.get(i).getIdLeadMstStatus();
//                            recall = list.get(i).getRecall();
//                            idMstLogDesc = list.get(i).getIdMstLogDesc();
//                            idMstLogStatus = list.get(i).getIdMstLogStatus();
//                            description = list.get(i).getDescription();
//                            status = list.get(i).getStatus();
//                            favorite = list.get(i).getFavorite();
//                            created_at = list.get(i).getCreated_at_lead();
//
//                            listLeads.add(new ListLead(id, firstName, lastName, idLeadMstStatus, recall, idMstLogDesc, idMstLogStatus, description, status, favorite,created_at));
//                        }
                        listLeads.addAll(list);

                        listLeadAdapter.notifyDataSetChanged();


                    }
                }
            }

            @Override
            public void onFailure(Call<RespListLead> call, Throwable t) {
                if (call.isCanceled()) {

                } else {
                    tvPencarian.setText("Gagal Mencari");
                }
            }
        });


    }

    private void initFilter() {
/*        mApiService.listLead(idUser, null).enqueue(new Callback<RespListLead>() {
            @Override
            public void onResponse(Call<RespListLead> call, Response<RespListLead> response) {
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
                                Intent intent = new Intent(context, DetailLeadActivity.class);
                                intent.putExtra("idLead", idLead);
                                context.startActivity(intent);
                                filter.setText("");
                            }
                        });
                    } else {
                        Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespListLead> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_star_black_24dp);
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
