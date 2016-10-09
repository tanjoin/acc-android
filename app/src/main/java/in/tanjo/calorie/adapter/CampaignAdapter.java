package in.tanjo.calorie.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.rx.CampaignItemAction;
import in.tanjo.calorie.rx.CampaignItemFilter;
import in.tanjo.calorie.rx.ThrowableAction;
import in.tanjo.calorie.viewholder.CampaignViewHolder;
import rx.Observable;

public class CampaignAdapter extends AbsAdapter<Campaign, CampaignViewHolder> implements CampaignViewHolder.Listener {

    private Listener listener;

    public CampaignAdapter(Listener listener) {
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
        Observable.from(items).filter(new CampaignItemFilter())
                .forEach(new CampaignItemAction(filteredCampaigns), new ThrowableAction());
        super.setItems(filteredCampaigns);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.viewholder_campaign;
    }

    @NonNull
    @Override
    protected CampaignViewHolder createViewHolder(View view) {
        return new CampaignViewHolder(view, this);
    }

    public interface Listener {

        void onCardViewClick(@NonNull Campaign campaign, int position);
    }
}
