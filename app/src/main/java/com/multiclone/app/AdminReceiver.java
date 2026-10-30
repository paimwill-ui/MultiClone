package com.multiclone.app;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AdminReceiver extends DeviceAdminReceiver {
    @Override public void onProfileProvisioningComplete(Context context, Intent intent) {
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(context, AdminReceiver.class);
        if (dpm != null && dpm.isProfileOwnerApp(context.getPackageName())) {
            dpm.setProfileName(admin, "MultiClone");
            dpm.setProfileEnabled(admin);
            Toast.makeText(context, "Perfil MultiClone configurado", Toast.LENGTH_LONG).show();
        }
    }
}
