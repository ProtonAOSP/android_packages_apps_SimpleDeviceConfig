/*
 * Copyright (C) 2020 The Proton AOSP Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.protonaosp.deviceconfig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.DeviceConfig;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "SimpleDeviceConfig";

    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(() -> {
            Log.i(TAG, "Updating device config at boot");
            updateConfig(context);
        }).start();
    }

    private void updateConfig(Context context) {
        // Set current properties
        String[] rawProperties = context.getResources().getStringArray(R.array.device_config);
        for (String property : rawProperties) {
            String[] kv = property.split("=");
            String fullKey = kv[0];
            String[] nk = fullKey.split("/");

            String namespace = nk[0];
            String key = nk[1];
            String value = kv[1];

            DeviceConfig.setProperty(namespace, key, value, true);
        }
    }
}
