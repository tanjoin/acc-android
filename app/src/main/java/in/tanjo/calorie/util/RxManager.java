package in.tanjo.calorie.util;

import android.support.annotation.Nullable;

import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public class RxManager {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public <T> Subscriber<T> composite(@Nullable final Subscriber<T> subscriber) {
        if (compositeSubscription == null || subscriber == null) {
            return null;
        }
        compositeSubscription.add(subscriber);
        subscriber.add(Subscriptions.create(new Action0() {
            @Override
            public void call() {
                compositeSubscription.remove(subscriber);
            }
        }));
        return subscriber;
    }

    public void clear() {
        compositeSubscription.clear();
    }
}
