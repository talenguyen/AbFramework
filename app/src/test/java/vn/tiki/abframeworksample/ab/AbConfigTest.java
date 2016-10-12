package vn.tiki.abframeworksample.ab;

import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

/**
 * Created by Giang Nguyen on 10/10/16.
 */
public class AbConfigTest {

  @Mock RemoteConfig remoteConfig;
  private AbConfig abConfig;
  private TestScheduler testScheduler;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    testScheduler = Schedulers.test();
    abConfig = new AbConfig(remoteConfig, testScheduler);
  }

  @Test public void shouldName() throws Exception {
    Mockito.when(remoteConfig.stringValueOrFallback(Mockito.eq("variant_a"), Mockito.eq("fallback")))
        .thenReturn(Observable.just("success").delay(2, TimeUnit.SECONDS, testScheduler));

    final TestSubscriber<String> subscriber = TestSubscriber.create();
    abConfig.experimentOf("variant_a", 2000)
        .subscribe(subscriber);

    subscriber.assertNoValues();
    subscriber.assertNoErrors();
    subscriber.assertNotCompleted();

    testScheduler.advanceTimeBy(1000, TimeUnit.SECONDS);
    subscriber.assertValue("fallback");
  }
}