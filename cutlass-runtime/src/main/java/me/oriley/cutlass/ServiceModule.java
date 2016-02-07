/*
 * Copyright (C) 2016 Kane O'Riley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.oriley.cutlass;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.*;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.midi.MidiManager;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.NonNull;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("unused")
@Module
public final class ServiceModule {

    @Provides
    @Singleton
    public PackageManager providePackageManager(@NonNull Application app) {
        return app.getPackageManager();
    }

    @Provides
    @Singleton
    public PowerManager providePowerManager(@NonNull Application app) {
        return (PowerManager) app.getSystemService(Context.POWER_SERVICE);
    }

    @Provides
    @Singleton
    public WindowManager provideWindowManager(@NonNull Application app) {
        return (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
    }

    @Provides
    @Singleton
    public LayoutInflater provideLayoutInflater(@NonNull Application app) {
        return (LayoutInflater) app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    @Singleton
    public AccountManager provideAccountManager(@NonNull Application app) {
        return (AccountManager) app.getSystemService(Context.ACCOUNT_SERVICE);
    }

    @Provides
    @Singleton
    public ActivityManager provideActivityManager(@NonNull Application app) {
        return (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    public AlarmManager provideAlarmManager(@NonNull Application app) {
        return (AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    @Singleton
    public NotificationManager provideNotificationManager(@NonNull Application app) {
        return (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    public AccessibilityManager provideAccessibilityManager(@NonNull Application app) {
        return (AccessibilityManager) app.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public CaptioningManager provideCaptioningManager(@NonNull Application app) {
        return (CaptioningManager) app.getSystemService(Context.CAPTIONING_SERVICE);
    }

    @Provides
    @Singleton
    public KeyguardManager provideKeyguardManager(@NonNull Application app) {
        return (KeyguardManager) app.getSystemService(Context.KEYGUARD_SERVICE);
    }

    @Provides
    @Singleton
    public LocationManager provideLocationManager(@NonNull Application app) {
        return (LocationManager) app.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    public SearchManager provideSearchManager(@NonNull Application app) {
        return (SearchManager) app.getSystemService(Context.SEARCH_SERVICE);
    }

    @Provides
    @Singleton
    public SensorManager provideSensorManager(@NonNull Application app) {
        return (SensorManager) app.getSystemService(Context.SENSOR_SERVICE);
    }

    @Provides
    @Singleton
    public StorageManager provideStorageManager(@NonNull Application app) {
        return (StorageManager) app.getSystemService(Context.STORAGE_SERVICE);
    }

    @Provides
    @Singleton
    public WallpaperService provideWallpaperService(@NonNull Application app) {
        return (WallpaperService) app.getSystemService(Context.WALLPAPER_SERVICE);
    }

    @Provides
    @Singleton
    public Vibrator provideVibrator(@NonNull Application app) {
        return (Vibrator) app.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Provides
    @Singleton
    public ConnectivityManager provideConnectivityManager(@NonNull Application app) {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.M)
    public NetworkStatsManager provideNetworkStatsManager(@NonNull Application app) {
        return (NetworkStatsManager) app.getSystemService(Context.NETWORK_STATS_SERVICE);
    }

    @Provides
    @Singleton
    public WifiManager provideWifiManager(@NonNull Application app) {
        return (WifiManager) app.getSystemService(Context.WIFI_SERVICE);
    }

    @Provides
    @Singleton
    public WifiP2pManager provideWifiP2pManager(@NonNull Application app) {
        return (WifiP2pManager) app.getSystemService(Context.WIFI_P2P_SERVICE);
    }

    @Provides
    @Singleton
    public NsdManager provideNsdManager(@NonNull Application app) {
        return (NsdManager) app.getSystemService(Context.NSD_SERVICE);
    }

    @Provides
    @Singleton
    public AudioManager provideAudioManager(@NonNull Application app) {
        return (AudioManager) app.getSystemService(Context.AUDIO_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.M)
    public FingerprintManager provideFingerprintManager(@NonNull Application app) {
        return (FingerprintManager) app.getSystemService(Context.FINGERPRINT_SERVICE);
    }

    @Provides
    @Singleton
    public MediaRouter provideMediaRouter(@NonNull Application app) {
        return (MediaRouter) app.getSystemService(Context.MEDIA_ROUTER_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MediaSessionManager provideMediaSessionManager(@NonNull Application app) {
        return (MediaSessionManager) app.getSystemService(Context.MEDIA_SESSION_SERVICE);
    }

    @Provides
    @Singleton
    public TelephonyManager provideTelephonyManager(@NonNull Application app) {
        return (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.M)
    public SubscriptionManager provideSubscriptionManager(@NonNull Application app) {
        return (SubscriptionManager) app.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TelecomManager provideTelecomManager(@NonNull Application app) {
        return (TelecomManager) app.getSystemService(Context.TELECOM_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.M)
    public CarrierConfigManager provideCarrierConfigManager(@NonNull Application app) {
        return (CarrierConfigManager) app.getSystemService(Context.CARRIER_CONFIG_SERVICE);
    }

    @Provides
    @Singleton
    public ClipboardManager provideClipboardManager(@NonNull Application app) {
        return (ClipboardManager) app.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Provides
    @Singleton
    public InputMethodManager provideInputMethodManager(@NonNull Application app) {
        return (InputMethodManager) app.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Provides
    @Singleton
    public TextServicesManager provideTextServicesManager(@NonNull Application app) {
        return (TextServicesManager) app.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppWidgetManager provideAppWidgetManager(@NonNull Application app) {
        return (AppWidgetManager) app.getSystemService(Context.APPWIDGET_SERVICE);
    }

    @Provides
    @Singleton
    public DropBoxManager provideDropBoxManager(@NonNull Application app) {
        return (DropBoxManager) app.getSystemService(Context.DROPBOX_SERVICE);
    }

    @Provides
    @Singleton
    public DevicePolicyManager provideDevicePolicyManager(@NonNull Application app) {
        return (DevicePolicyManager) app.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    @Provides
    @Singleton
    public UiModeManager provideUiModeManager(@NonNull Application app) {
        return (UiModeManager) app.getSystemService(Context.UI_MODE_SERVICE);
    }

    @Provides
    @Singleton
    public DownloadManager provideDownloadManager(@NonNull Application app) {
        return (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BatteryManager provideBatteryManager(@NonNull Application app) {
        return (BatteryManager) app.getSystemService(Context.BATTERY_SERVICE);
    }

    @Provides
    @Singleton
    public NfcManager provideNfcManager(@NonNull Application app) {
        return (NfcManager) app.getSystemService(Context.NFC_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public BluetoothManager provideBluetoothManager(@NonNull Application app) {
        return (BluetoothManager) app.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    @Provides
    @Singleton
    public UsbManager provideUsbManager(@NonNull Application app) {
        return (UsbManager) app.getSystemService(Context.USB_SERVICE);
    }

    @Provides
    @Singleton
    public InputManager provideInputManager(@NonNull Application app) {
        return (InputManager) app.getSystemService(Context.INPUT_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public DisplayManager provideDisplayManager(@NonNull Application app) {
        return (DisplayManager) app.getSystemService(Context.DISPLAY_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public UserManager provideUserManager(@NonNull Application app) {
        return (UserManager) app.getSystemService(Context.USER_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LauncherApps provideLauncherApps(@NonNull Application app) {
        return (LauncherApps) app.getSystemService(Context.LAUNCHER_APPS_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RestrictionsManager provideRestrictionsManager(@NonNull Application app) {
        return (RestrictionsManager) app.getSystemService(Context.RESTRICTIONS_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public AppOpsManager provideAppOpsManager(@NonNull Application app) {
        return (AppOpsManager) app.getSystemService(Context.APP_OPS_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraManager provideCameraManager(@NonNull Application app) {
        return (CameraManager) app.getSystemService(Context.CAMERA_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public PrintManager providePrintManager(@NonNull Application app) {
        return (PrintManager) app.getSystemService(Context.PRINT_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public ConsumerIrManager provideConsumerIrManager(@NonNull Application app) {
        return (ConsumerIrManager) app.getSystemService(Context.CONSUMER_IR_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TvInputManager provideTvInputManager(@NonNull Application app) {
        return (TvInputManager) app.getSystemService(Context.TV_INPUT_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.M)
    public UsageStatsManager provideUsageStatsManager(@NonNull Application app) {
        return (UsageStatsManager) app.getSystemService(Context.USAGE_STATS_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JobScheduler provideJobScheduler(@NonNull Application app) {
        return (JobScheduler) app.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MediaProjectionManager provideMediaProjectionManager(@NonNull Application app) {
        return (MediaProjectionManager) app.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Provides
    @Singleton
    @TargetApi(Build.VERSION_CODES.M)
    public MidiManager provideMidiManager(@NonNull Application app) {
        return (MidiManager) app.getSystemService(Context.MIDI_SERVICE);
    }
}
