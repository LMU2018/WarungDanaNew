package com.lmu.warungdananew.BottomSheet;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lmu.warungdananew.AddAdditionalLeadActivity;
import com.lmu.warungdananew.DetailLeadActivity;
import com.lmu.warungdananew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetLead extends BottomSheetDialogFragment {
    private LinearLayout bsNote, bsUnit, bsDocument;
    private Integer idLead;

    public BottomSheetLead() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_lead, container, false);
        bsNote = view.findViewById(R.id.bsNote);
//        bsUnit = view.findViewById(R.id.bsUnit);
//        bsDocument = view.findViewById(R.id.bsDocument);
        DetailLeadActivity activity = (DetailLeadActivity) getActivity();
        idLead = activity.idLead;
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        bsNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAdditionalLeadActivity.class);
                intent.putExtra("idIndicator", 1);
                intent.putExtra("idModul", 1);
                intent.putExtra("idData", idLead);
                getContext().startActivity(intent);
            }
        });

        /*bsUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAdditionalLeadActivity.class);
                intent.putExtra("idIndicator", 4);
                intent.putExtra("idModul", 1);
                intent.putExtra("idData", idLead);

                getContext().startActivity(intent);
            }
        });*/
    }

}
