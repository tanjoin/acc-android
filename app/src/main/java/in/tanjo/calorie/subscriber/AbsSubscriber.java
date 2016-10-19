package in.tanjo.calorie.subscriber;

import rx.Subscriber;


public abstract class AbsSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
        }
    }
}
