package com.lmu.warungdana.BottomSheet;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lmu.warungdana.AddAdditionalLeadActivity;
import com.lmu.warungdana.AddLeadVisumActivity;
import com.lmu.warungdana.DetailLeadActivity;
import com.lmu.warungdana.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class BottomSheetLead extends BottomSheetDialogFragment {
    private LinearLayout bsNote, bsVisum,bsUnit, bsDocument;
    private Integer idLead;
    private Boolean visumStatus = true;


    @SuppressLint("ValidFragment")
    public BottomSheetLead(Boolean visumStatus) {
        this.visumStatus = visumStatus;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_lead, container, false);
        bsNote = view.findViewById(R.id.bsNote);
        bsVisum = view.findViewById(R.id.bsVisum);
//        bsUnit = view.findViewById(R.id.bsUnit);
//        bsDocument = view.findViewById(R.id.bsDocument);
        DetailLeadActivity activity = (DetailLeadActivity) getActivity();
        idLead = activity.idLead;
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        if (visumStatus == false){

            bsVisum.setVisibility(LinearLayout.GONE);

        }else if(visumStatus == true){

            bsVisum.setVisibility(LinearLayout.VISIBLE);

        }

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
        bsVisum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddLeadVisumActivity.class);
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
