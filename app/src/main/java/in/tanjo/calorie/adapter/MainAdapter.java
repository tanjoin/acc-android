package in.tanjo.calorie.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.rx.MainItemAction;
import in.tanjo.calorie.rx.MainItemFilter;
import in.tanjo.calorie.rx.ThrowableAction;
import in.tanjo.calorie.viewholder.MainViewHolder;
import rx.Observable;

public class MainAdapter extends AbsAdapter<Campaign, MainViewHolder> implements MainViewHolder.Listener {

    private Listener listener;

    public MainAdapter(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCardViewClick(int position) {
        if (listener != null) {
            Campaign campaign = getItem(position);
            if (campaign != null) {
                listener.onCardViewClick(campaign, position);
            }
        }
    }

    @Override
    public void setItems(@NonNull List<Campaign> items) {
        List<Campaign> filteredCampaigns = new ArrayList<>();
        Observable.from(items).filter(new MainItemFilter())
                .forEach(new MainItemAction(filteredCampaigns), new ThrowableAction());
        super.setItems(filteredCampaigns);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.viewholder_main;
    }

    @NonNull
    @Override
    protected MainViewHolder createViewHolder(View view) {
        return new MainViewHolder(view, this);
    }

    public interface Listener {

        void onCardViewClick(@NonNull Campaign campaign, int position);
    }
}
