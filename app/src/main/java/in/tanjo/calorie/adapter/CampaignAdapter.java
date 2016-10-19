package in.tanjo.calorie.adapter;

import com.google.common.base.Strings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.subscriber.CampaignItemSubscriber;
import in.tanjo.calorie.subscriber.filter.CampaignItemFilter;
import in.tanjo.calorie.viewholder.CampaignViewHolder;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class CampaignAdapter extends AbsAdapter<Campaign, CampaignViewHolder> implements CampaignViewHolder.Listener {

    private Listener listener;

    private List<Campaign> items = new ArrayList<>();

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
    public void addItems(@NonNull List<Campaign> items) {
        this.items.addAll(items);
        List<Campaign> filteredCampaigns = new ArrayList<>();
        Observable.from(items).filter(new CampaignItemFilter())
                .subscribe(new CampaignItemSubscriber(filteredCampaigns));
        super.addItems(filteredCampaigns);
    }

    @Override
    public void clear() {
        super.clear();
        items.clear();
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

    public void filter(@Nullable final String serviceTitles) {
        Observable.from(items).filter(new CampaignItemFilter())
                .filter(new Func1<Campaign, Boolean>() {
                    @Override
                    public Boolean call(Campaign campaign) {
                        return Strings.isNullOrEmpty(serviceTitles) || serviceTitles.equals(campaign.getServiceTitle());
                    }
                })
                .toList()
                .subscribe(new Action1<List<Campaign>>() {
                    @Override
                    public void call(List<Campaign> campaigns) {
                        CampaignAdapter.super.clear();
                        CampaignAdapter.super.addItems(campaigns);
                        notifyDataSetChanged();
                    }
                });
    }

    public interface Listener {

        void onCardViewClick(@NonNull Campaign campaign, int position);
    }
}
