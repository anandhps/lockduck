package com.anandps.lockduck;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ComponentName cn = new ComponentName(this, AdminReceiver.class);
        DevicePolicyManager mgr =
                (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        if (mgr.isAdminActive(cn)) {
            /* int msgId;

             *//* if (mgr.isActivePasswordSufficient()) {
                msgId=R.string.compliant;
            }
            else {*//*
                msgId=R.string.not_compliant;


            Toast.makeText(this, msgId, Toast.LENGTH_LONG).show();*/
        } else {
            Intent intent =
                    new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    getString(R.string.device_admin_explanation));
            startActivity(intent);
        }

        /*finish();*/
    }


}
