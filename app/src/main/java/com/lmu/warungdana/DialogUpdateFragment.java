package com.lmu.warungdana;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kcode.lib.bean.VersionModel;
import com.kcode.lib.common.Constant;
import com.kcode.lib.dialog.UpdateDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogUpdateFragment extends UpdateDialog {

    public static DialogUpdateFragment newInstance(VersionModel model) {

        Bundle args = new Bundle();
        args.putSerializable(Constant.MODEL,model);
        DialogUpdateFragment fragment = new DialogUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_dialog_update;
    }

    @Override
    protected void setContent(View view, int contentId) {
        super.setContent(view, R.id.content);
    }

    @Override
    protected void bindUpdateListener(View view, int updateId) {
        super.bindUpdateListener(view, R.id.update);
    }

    @Override
    protected void bindCancelListener(View view, int cancelId) {
        super.bindCancelListener(view, R.id.cancel);
    }

    @Override
    protected void initIfMustUpdate(View view, int id) {
        super.initIfMustUpdate(view, R.id.cancel);
    }
}
