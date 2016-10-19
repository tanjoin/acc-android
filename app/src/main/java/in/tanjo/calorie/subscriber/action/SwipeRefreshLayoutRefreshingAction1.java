package in.tanjo.calorie.subscriber.action;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import rx.functions.Action1;


public class SwipeRefreshLayoutRefreshingAction1<T> implements Action1<T> {

    @Nullable
    private final SwipeRefreshLayout swipeRefreshLayout;

    public SwipeRefreshLayoutRefreshingAction1(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public void call(T t) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
