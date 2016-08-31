package vn.tiki.abframeworksample;

import android.app.Application;
import android.content.Context;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Giang Nguyen on 8/31/16.
 */
public class ABFrameworkApp extends Application {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private AbFramework abFramework;
    private Navigator navigator;

    public static ABFrameworkApp get(Context context) {
        return (ABFrameworkApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseRemoteConfig = setupRemoteConfig();

        setupAbFramework();

        setupNavigator();
    }

    private void setupNavigator() {
        navigator = new Navigator();
        navigator.registerControllerForKey(HomeActivity.class, "Home");
        navigator.registerControllerForKey(OnBoardingActivity.class, "OnBoarding");
    }

    private void setupAbFramework() {
        abFramework = new AbFramework(firebaseRemoteConfig);
    }

    public AbFramework getAbFramework() {
        return abFramework;
    }

    private FirebaseRemoteConfig setupRemoteConfig() {
        // Initialize Firebase Remote Config.
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = getRemoteConfigSettings();
        firebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);

        final Map<String, Object> defaultConfigMap = getDefaultConfigMap();
        firebaseRemoteConfig.setDefaults(defaultConfigMap);

        return firebaseRemoteConfig;
    }

    private FirebaseRemoteConfigSettings getRemoteConfigSettings() {
        return new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
    }

    private Map<String, Object> getDefaultConfigMap() {
        final Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("after_splash", "Home");
        return defaultConfigMap;
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
