package in.tanjo.calorie.subscriber;

import org.greenrobot.eventbus.EventBus;

import in.tanjo.calorie.event.DoneDeleteEvent;

public class DoneDeleteSubscriber extends AbsSubscriber<Integer> {

    @Override
    public void onNext(Integer integer) {
        EventBus.getDefault().post(new DoneDeleteEvent());
    }
}
