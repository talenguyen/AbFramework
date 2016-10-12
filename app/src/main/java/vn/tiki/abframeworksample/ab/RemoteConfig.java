package vn.tiki.abframeworksample.ab;

import rx.Observable;

/**
 * Created by Giang Nguyen on 10/10/16.
 */

public interface RemoteConfig {

  Observable<String> stringValueOrFallback(String key, String fallbackValue);
}
