package vn.tiki.abframeworksample;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.lang.ref.WeakReference;

/**
 * Created by Giang Nguyen on 8/31/16.
 */
public class AbFramework {

    private static final String TAG = "ABFramework";
    private Handler fallbackHandler = new Handler(Looper.getMainLooper());
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private WeakReference<Callback> callbackWeakReference;

    public AbFramework(FirebaseRemoteConfig firebaseRemoteConfig) {
        this.firebaseRemoteConfig = firebaseRemoteConfig;
    }

    public void get(final String key, Callback callback, long timeout) {
        callbackWeakReference = new WeakReference<>(callback);
        firebaseRemoteConfig.fetch()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: ");
                        fallbackHandler.removeCallbacksAndMessages(null);
                        firebaseRemoteConfig.activateFetched();
                        deliverResult(key);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: ");
                    }
                });

        fallbackHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                deliverResult(key);
            }
        }, timeout);
    }

    private void deliverResult(String key) {
        final String result = firebaseRemoteConfig.getString(key);
        Log.d(TAG, "deliverResult: {key: " + key + ", result: " + result + "}");
        final Callback callback = callbackWeakReference.get();
        if (callback != null) {
            callback.onReceive(result);
        }
        callbackWeakReference.clear();
    }

    public interface Callback {

        void onReceive(String value);
    }
}
