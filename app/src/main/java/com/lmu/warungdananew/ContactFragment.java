package com.lmu.warungdananew;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.warungdananew.Adapter.ListContactAdapter;
import com.lmu.warungdananew.FragmentContact.CurrentContactFragment;
import com.lmu.warungdananew.Response.CheckContact;
import com.lmu.warungdananew.Response.CheckNIK;
import com.lmu.warungdananew.Response.ListContact;
import com.lmu.warungdananew.Response.RespListContact;
import com.lmu.warungdananew.Response.RespPost;
import com.lmu.warungdananew.Api.ApiEndPoint;
import com.lmu.warungdananew.Api.SharedPrefManager;
import com.lmu.warungdananew.Api.UtilsApi;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private Button fab;
    Context context;
    private ApiEndPoint mApiService;
    //    private AutoCompleteTextView filter;
    private Integer idUser, idLead;
    private String name;
    SharedPrefManager sharedPrefManager;
    List<ListContact> listAlamats;

    ArrayList<ListContact> listContacts;
    ListContactAdapter listContactAdapter;
    RecyclerView recyclerView;
    TextView tvPencarian;
    Call<RespListContact> call = null;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);
        idUser = sharedPrefManager.getSpId();
        mApiService = UtilsApi.getAPIService();
//        filter = view.findViewById(R.id.filter);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Contact");
        toolbar.setSubtitle("Customer");
        viewPager = view.findViewById(R.id.frame_container);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CurrentContactFragment(), "Current");
        viewPager.setAdapter(adapter);
        fab = view.findViewById(R.id.btnCheck);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvPencarian = view.findViewById(R.id.tvPencarian);

        listContacts = new ArrayList<>();
        listContactAdapter = new ListContactAdapter(getContext(), listContacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listContactAdapter);

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
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_add_contact, null);
                final MaterialEditText nik = view.findViewById(R.id.NIK);
                alert.setTitle("Tambah Contact Baru");
                alert.setView(view);

                alert.setPositiveButton("Check", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (nik.length() < 16) {
                            Toast.makeText(context, "NIK kurang dari 16 Angka!", Toast.LENGTH_LONG).show();
                        } else {
                            mApiService.checkNIK(nik.getText().toString()).enqueue(new Callback<CheckNIK>() {
                                @Override
                                public void onResponse(Call<CheckNIK> call, Response<CheckNIK> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getApiStatus() == 0) {
                                            Intent intent = new Intent(context, AddContactActivity.class);
                                            intent.putExtra("nik", nik.getText().toString());
                                            startActivity(intent);
                                        } else {
                                            final Integer idContact = response.body().getId();
                                            mApiService.CheckContact(idContact, sharedPrefManager.getSpId()).enqueue(new Callback<CheckContact>() {
                                                @Override
                                                public void onResponse(Call<CheckContact> call, Response<CheckContact> response) {
                                                    if (response.isSuccessful()) {
                                                        if (response.body().getApiStatus() == 0) {
                                                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                                            alert.setMessage("Contact sudah ada. Apakah anda ingin menambahkan ke Contact yang sama?");
                                                            alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    mApiService.contactCollabCreate(sharedPrefManager.getSpId(), idContact).enqueue(new Callback<RespPost>() {
                                                                        @Override
                                                                        public void onResponse(Call<RespPost> call, Response<RespPost> response) {
                                                                            Intent intent = new Intent(context, DetailContactActivity.class);
                                                                            intent.putExtra("idContact", idContact);
                                                                            context.startActivity(intent);
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<RespPost> call, Throwable t) {
                                                                            Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                            alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            });
                                                            alert.show();
                                                        } else {
                                                            Toast.makeText(context, "Data Contact sudah ada !", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(context, DetailContactActivity.class);
                                                            intent.putExtra("idContact", idContact);
                                                            context.startActivity(intent);
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<CheckContact> call, Throwable t) {
                                                    Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                        }
                                    } else {
                                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckNIK> call, Throwable t) {
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
/*
        initFilter();
*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contact, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari nama contact");

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
                listContacts.clear();
                listContactAdapter.notifyDataSetChanged();

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
                    listContacts.clear();
                    listContactAdapter.notifyDataSetChanged();
                }

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void callFilter(String newText) {

        tvPencarian.setVisibility(TextView.VISIBLE);
        tvPencarian.setText("Sedang Mencari . . .");

        call = mApiService.contactSearch(newText, "", idUser);
        call.enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {

                        tvPencarian.setText("Hasil Pencarian");
                        listContacts.clear();
                        listContactAdapter.notifyDataSetChanged();
                        recyclerView.setVisibility(RecyclerView.VISIBLE);

                        List<ListContact> list = response.body().getData();

                        if (list.size() <= 0) {
                            tvPencarian.setText("Pencarian Tidak Ditemukan");
                        }

                        for (int i = 0; i < list.size(); i++) {
                            String contactFirstName, contactLastName, contactGender, status;
                            Integer id, idContact, idOrderMstStatus;
                            id = list.get(i).getId();
                            idContact = list.get(i).getIdContact();
                            contactFirstName = list.get(i).getContactFirstName();
                            contactLastName = list.get(i).getContactLastName();
                            contactGender = list.get(i).getContactGender();
                            idOrderMstStatus = list.get(i).getIdOrderMstStatus();
                            status = list.get(i).getStatus();

                            listContacts.add(new ListContact(id, idContact, contactFirstName, contactLastName, contactGender, idOrderMstStatus, status));
                        }
                        listContactAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<RespListContact> call, Throwable t) {
                if (call.isCanceled()) {

                } else {
                    tvPencarian.setText("Gagal Mencari");
                }
            }
        });


    }

    private void initFilter() {
/*
        mApiService.listContact(idUser).enqueue(new Callback<RespListContact>() {
            @Override
            public void onResponse(Call<RespListContact> call, Response<RespListContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getApiStatus() != 0) {
                        listAlamats = response.body().getData();
                        final List<String> list = new ArrayList<>();
                        String name;
                        for (int i = 0; i < listAlamats.size(); i++) {
                            if (listAlamats.get(i).getContactLastName() == null) {
                                name = listAlamats.get(i).getContactFirstName();
                            } else {
                                name = listAlamats.get(i).getContactFirstName() + " " + listAlamats.get(i).getContactLastName();
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
                                    if (listAlamats.get(i).getContactLastName() == null) {
                                        nama = listAlamats.get(i).getContactFirstName();
                                    } else {
                                        nama = listAlamats.get(i).getContactFirstName() + " " + listAlamats.get(i).getContactLastName();
                                    }
                                    if (nama.equals(selection)) {
                                        idLead = listAlamats.get(i).getIdContact();
                                        break;
                                    }
                                }
                                Intent intent = new Intent(context, DetailContactActivity.class);
                                intent.putExtra("idContact", idLead);
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
            public void onFailure(Call<RespListContact> call, Throwable t) {
                Toast.makeText(context, "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
*/
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
