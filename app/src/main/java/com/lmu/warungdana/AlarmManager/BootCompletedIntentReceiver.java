package com.lmu.warungdana.AlarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.lmu.warungdana.LoginActivity;
import com.lmu.warungdana.Api.SharedPrefManager;


/**
 * Created by Gigabyte on 12/15/2018.
 */

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("LogOutAction".equals(intent.getAction())) {
            Log.e("LogOutAuto", intent.getAction());
            Toast.makeText(context, "Logout Action", Toast.LENGTH_SHORT).show();
            SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            Intent intentl = new Intent();
            intentl.setClass(context, LoginActivity.class);
            intentl.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentl.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentl.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentl);
            //Do your action
        }
    }

}
