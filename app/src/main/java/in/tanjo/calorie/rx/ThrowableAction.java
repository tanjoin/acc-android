package in.tanjo.calorie.rx;

import rx.functions.Action1;


public class ThrowableAction implements Action1<Throwable> {

    @Override
    public void call(Throwable throwable) {
        throwable.printStackTrace();
    }
}
