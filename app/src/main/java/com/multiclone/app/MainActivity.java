package com.multiclone.app;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = (int)(24 * getResources().getDisplayMetrics().density);
        root.setPadding(pad, pad, pad, pad);
        TextView heading = new TextView(this);
        heading.setText("MultiClone");
        heading.setTextSize(30);
        root.addView(heading);
        TextView info = new TextView(this);
        info.setText("\nInstale uma segunda cópia de aplicativos em um perfil de trabalho independente. O Android precisa permitir a criação desse perfil.\n\nDepois de criar o perfil, abra a Play Store com o símbolo de maleta para instalar a segunda cópia. Os ícones aparecerão separados no launcher compatível.\n");
        info.setTextSize(16);
        root.addView(info);
        Button button = new Button(this);
        button.setText("Criar perfil separado");
        root.addView(button);
        button.setOnClickListener(v -> provision());
        setContentView(root);
    }

    private void provision() {
        DevicePolicyManager dpm = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        if (dpm == null || !getPackageManager().hasSystemFeature("android.software.managed_users")) {
            Toast.makeText(this, "Este aparelho não oferece perfis de trabalho.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE);
        intent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
            new ComponentName(this, AdminReceiver.class));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível iniciar o perfil: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
