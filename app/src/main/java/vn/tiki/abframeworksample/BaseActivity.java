package vn.tiki.abframeworksample;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Giang Nguyen on 8/31/16.
 */
public class BaseActivity extends AppCompatActivity {

    public ABFrameworkApp getApp() {
        return ABFrameworkApp.get(this);
    }

    protected AbFramework getAbFramework() {
        return getApp().getAbFramework();
    }

    protected Navigator getNavigator() {
        return getApp().getNavigator();
    }
}
