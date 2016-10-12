package vn.tiki.abframeworksample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Giang Nguyen on 8/31/16.
 */
public class SplashActivity extends BaseActivity {

  private static final String TAG = "SplashActivity";

  private Subscription subscription;

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

  @Override protected void onPause() {
    super.onPause();
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }

  private void showNextScreen() {
    Log.d(TAG, "showNextScreen");
    subscription = getAbFramework().get("after_splash", 2000)
        .subscribe(new Subscriber<String>() {
          @Override public void onCompleted() {
            Log.d(TAG, "onCompleted");
          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override public void onNext(String value) {
            Log.d(TAG, "onNext: " + value);
            getNavigator().open(SplashActivity.this, value);
            finish();
          }
        });
  }
}
