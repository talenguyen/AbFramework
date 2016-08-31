package vn.tiki.abframeworksample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;

/**
 * Created by Giang Nguyen on 8/31/16.
 */
public class Navigator {

    private final ArrayMap<String, Class<? extends Activity>> controllerMap = new ArrayMap<>();

    public void registerControllerForKey(Class<? extends Activity> controller, String key) {
        controllerMap.put(key, controller);
    }

    public void open(Context context, String key) {
        final Class<? extends Activity> activityClass = controllerMap.get(key);
        final Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}
