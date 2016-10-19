package in.tanjo.calorie.subscriber.action;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import rx.functions.Action0;


public class SwipeRefreshLayoutRefreshingAction0 implements Action0 {

    @Nullable
    private final SwipeRefreshLayout swipeRefreshLayout;

    private final boolean isRefreshing;

    public SwipeRefreshLayoutRefreshingAction0(@Nullable SwipeRefreshLayout swipeRefreshLayout, boolean isRefreshing) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.isRefreshing = isRefreshing;
    }

    @Override
    public void call() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(isRefreshing);
        }
    }
}
