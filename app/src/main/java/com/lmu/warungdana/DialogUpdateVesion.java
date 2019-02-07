package com.lmu.warungdana;

import android.support.v4.app.Fragment;

import com.kcode.lib.dialog.UpdateActivity;

public class DialogUpdateVesion extends UpdateActivity {

    @Override
    protected Fragment getUpdateDialogFragment() {
        return DialogUpdateFragment.newInstance(mModel);
    }
}
