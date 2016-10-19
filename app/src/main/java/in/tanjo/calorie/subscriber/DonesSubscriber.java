package in.tanjo.calorie.subscriber;

import android.support.annotation.Nullable;

import java.util.List;

import in.tanjo.calorie.adapter.DoneAdapter;
import in.tanjo.calorie.model.Done;

public class DonesSubscriber extends AbsSubscriber<List<Done>> {

    @Nullable
    private final DoneAdapter doneAdapter;

    public DonesSubscriber(DoneAdapter doneAdapter) {
        this.doneAdapter = doneAdapter;
    }

    @Override
    public void onNext(List<Done> dones) {
        if (doneAdapter != null && dones != null) {
            doneAdapter.clear();
            doneAdapter.addItems(dones);
        }
    }
}
