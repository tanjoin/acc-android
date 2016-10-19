package in.tanjo.calorie.subscriber;

import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;

import android.support.design.widget.Snackbar;
import android.view.View;

import in.tanjo.calorie.event.DoneSaveEvent;

public class DoneSaveSubscriber extends AbsSubscriber<Dao.CreateOrUpdateStatus> {

    private View view;

    public DoneSaveSubscriber(View view) {
        super();
        this.view = view;
    }

    @Override
    public void onNext(Dao.CreateOrUpdateStatus createOrUpdateStatus) {
        EventBus.getDefault().post(new DoneSaveEvent());
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (view != null) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }
}
