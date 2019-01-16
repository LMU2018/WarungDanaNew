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
import com.lmu.warungdananew.AddVisumActivity;
import com.lmu.warungdananew.DetailLeadActivity;
import com.lmu.warungdananew.DetailTargetActivity;
import com.lmu.warungdananew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetTarget extends BottomSheetDialogFragment {
    private LinearLayout bsNote,bsVisum,bsDocument;
    private Integer idTarget;


    public BottomSheetTarget() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_target,container,false);
        bsNote = view.findViewById(R.id.bsNote);
        bsVisum = view.findViewById(R.id.bsVisum);
//        bsDocument = view.findViewById(R.id.bsDocument);
        DetailTargetActivity activity = (DetailTargetActivity) getActivity();
        idTarget = activity.idTarget;
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
                intent.putExtra("idModul",2);
                intent.putExtra("idData",idTarget);
                getContext().startActivity(intent);
            }
        });
        bsVisum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddVisumActivity.class);
                intent.putExtra("idIndicator",1);
                intent.putExtra("idModul",2);
                intent.putExtra("idData",idTarget);
                getContext().startActivity(intent);
            }
        });

    }
}
