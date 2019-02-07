package com.lmu.warungdana.BottomSheet;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lmu.warungdana.AddAdditionalLeadActivity;
import com.lmu.warungdana.AddOrderActivity;
import com.lmu.warungdana.DetailContactActivity;
import com.lmu.warungdana.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class  BottomSheetContact extends BottomSheetDialogFragment {
    private LinearLayout bsNote,bsNumber,bsAddress,bsUnit,bsDocument;
    private Integer idLead;
    private String nama;


    public BottomSheetContact() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_contact,container,false);
        bsNote = view.findViewById(R.id.bsNote);
        bsUnit = view.findViewById(R.id.bsUnit);
        DetailContactActivity activity = (DetailContactActivity) getActivity();
        idLead = activity.idContact;
        nama = activity.nama;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        bsNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddAdditionalLeadActivity.class);
                intent.putExtra("idIndicator",1);
                intent.putExtra("idModul",3);
                intent.putExtra("idData",idLead);
                getContext().startActivity(intent);
            }
        });

        bsUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddOrderActivity.class);
                intent.putExtra("nameContact",nama);
                intent.putExtra("idContact",idLead);
                getContext().startActivity(intent);
            }
        });
    }

}
