package com.lmu.warungdana.FragmentDetailLead;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lmu.warungdana.Adapter.ListNoteAdapter;
import com.lmu.warungdana.DetailLeadActivity;
import com.lmu.warungdana.R;
import com.lmu.warungdana.Response.ListNote;
import com.lmu.warungdana.Response.RespListNote;
import com.lmu.warungdana.Api.ApiEndPoint;
import com.lmu.warungdana.Api.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteLeadFragment extends Fragment {
    private ApiEndPoint mApiService;
    private RecyclerView recyclerView;
    private Integer idLead;
    private Context context;

    public NoteLeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        DetailLeadActivity activity = (DetailLeadActivity) getActivity();
        idLead = activity.idLead;
        mApiService = UtilsApi.getAPIService();
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiService.listNote(idLead).enqueue(new Callback<RespListNote>() {
            @Override
            public void onResponse(Call<RespListNote> call, Response<RespListNote> response) {
                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    List<ListNote> listLeads = response.body().getData();
                    recyclerView.setAdapter(new ListNoteAdapter(getContext(), listLeads));
                }
            }

            @Override
            public void onFailure(Call<RespListNote> call, Throwable t) {
                Toast.makeText(getActivity(), "Not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
