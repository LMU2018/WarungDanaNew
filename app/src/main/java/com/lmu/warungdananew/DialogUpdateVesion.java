package com.lmu.warungdananew;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kcode.lib.dialog.UpdateActivity;

public class DialogUpdateVesion extends UpdateActivity {

    @Override
    protected Fragment getUpdateDialogFragment() {
        return DialogUpdateFragment.newInstance(mModel);
    }
}
