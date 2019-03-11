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
import com.lmu.warungdana.AddVisumActivity;
import com.lmu.warungdana.DetailDealActivity;
import com.lmu.warungdana.DetailTargetActivity;
import com.lmu.warungdana.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetOrder extends BottomSheetDialogFragment {
    private LinearLayout bsNote,bsVisum,bsDocument;
    private Integer idOrder;


    public BottomSheetOrder() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_order,container,false);
        bsNote = view.findViewById(R.id.bsNote);
        DetailDealActivity activity = (DetailDealActivity) getActivity();
        idOrder = activity.idOrder;
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
                intent.putExtra("idModul",4);
                intent.putExtra("idData",idOrder);
                getContext().startActivity(intent);
            }
        });

    }
}
