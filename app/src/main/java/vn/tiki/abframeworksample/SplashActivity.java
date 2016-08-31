package vn.tiki.abframeworksample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by Giang Nguyen on 8/31/16.
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS) {
            showNextScreen();
        } else {
            GoogleApiAvailability.getInstance().showErrorDialogFragment(this, resultCode, 1, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    showNextScreen();
                }
            });
        }
    }

    private void showNextScreen() {
        getAbFramework()
                .get("after_splash", new AbFramework.Callback() {
                    @Override
                    public void onReceive(String value) {
                        Log.d(TAG, "onReceive: " + value);
                        getNavigator().open(SplashActivity.this, value);
                    }
                }, 5000);
    }
}
