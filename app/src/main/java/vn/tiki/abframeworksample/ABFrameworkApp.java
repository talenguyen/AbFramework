package vn.tiki.abframeworksample;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Giang Nguyen on 8/31/16.
 */
public class AbFrameworkApp extends Application {

    private static final String TAG = "AbFrameworkApp";
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private AbFramework abFramework;
    private Navigator navigator;

    public static AbFrameworkApp get(Context context) {
        return (AbFrameworkApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseRemoteConfig = setupRemoteConfig();

        setupAbFramework();

        setupNavigator();

        firebaseRemoteConfig.fetch()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        final String value = firebaseRemoteConfig.getString("after_splash");
                        Log.d(TAG, "onComplete: " + value);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        final String value = firebaseRemoteConfig.getString("after_splash");
                        Log.d(TAG, "onSuccess: " + value);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
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

    public Navigator getNavigator() {
        return navigator;
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

}
