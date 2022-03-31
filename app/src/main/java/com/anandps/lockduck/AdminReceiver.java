package com.anandps.lockduck;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class AdminReceiver extends DeviceAdminReceiver {
  // GPSTracker class
  GPSTracker gps;
  String lat = "";
  String lan = "";
  @Override
  public void onEnabled(Context ctxt, Intent intent) {
    ComponentName cn=new ComponentName(ctxt, AdminReceiver.class);
    DevicePolicyManager mgr=
        (DevicePolicyManager)ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);

    try {
      mgr.setPasswordQuality(cn,
              DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC);

    }catch (Exception e){
      e.printStackTrace();
    }

    onPasswordChanged(ctxt, intent);
  }

  @Override
  public void onPasswordChanged(Context ctxt, Intent intent) {
    DevicePolicyManager mgr=
        (DevicePolicyManager)ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);
    int msgId;

  /*  if (mgr.isActivePasswordSufficient()) {
      msgId=R.string.compliant;
         }
    else {
      msgId=R.string.not_compliant;
    }

    Toast.makeText(ctxt, msgId, Toast.LENGTH_LONG).show();*/
  }
  @Override
  public void onPasswordFailed(Context ctxt, Intent intent) {
    getLastKnownLocation(ctxt);
    Toast.makeText(ctxt, R.string.password_failed, Toast.LENGTH_LONG)
         .show();
  }
  private void getLastKnownLocation(Context ctxt) {
    gps = new GPSTracker(ctxt);
    if(gps.canGetLocation()) {
      double latitude = gps.getLatitude();
      double longitude = gps.getLongitude();
      lat = latitude +"";
      lan = longitude+"";
      SmsManager smsManager= SmsManager.getDefault();
      smsManager.sendTextMessage("7667673691",null,
              "Emergency\n"+
              latitude+"\n"+
                      longitude,
              null,null);
    } else {
        gps.showSettingsAlert();
    }
  }

  @Override
  public void onPasswordSucceeded(Context ctxt, Intent intent) {
    Toast.makeText(ctxt, R.string.password_success, Toast.LENGTH_LONG)
         .show();
  }
}