package vn.tiki.abframeworksample.ab;

import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * Created by Giang Nguyen on 10/10/16.
 */

public class AbConfig {
  private final RemoteConfig remoteConfig;
  private final Scheduler scheduler;

  public AbConfig(RemoteConfig remoteConfig, Scheduler scheduler) {
    this.remoteConfig = remoteConfig;
    this.scheduler = scheduler;
  }

  public Subscription sync() {
    return Subscriptions.empty();
  }

  public Observable<Boolean> enabling(String variant, long timeout) {
    return Observable.empty();
  }

  public Observable<String> experimentOf(String variant, long timeout) {
    return remoteConfig.stringValueOrFallback(variant, "fallback")
        .timeout(timeout, TimeUnit.MILLISECONDS, scheduler)
        .onErrorReturn(new Func1<Throwable, String>() {
          @Override public String call(Throwable throwable) {
            return "fallback";
          }
        });
  }
}
