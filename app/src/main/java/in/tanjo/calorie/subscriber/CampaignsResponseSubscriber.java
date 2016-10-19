package in.tanjo.calorie.subscriber;

import android.support.annotation.Nullable;

import java.util.List;

import in.tanjo.calorie.adapter.CampaignAdapter;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.response.CampaignsResponse;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CampaignsResponseSubscriber extends AbsSubscriber<CampaignsResponse> {

    @Nullable
    private final CampaignAdapter campaignAdapter;

    private final List<String> serviceTitles;

    public CampaignsResponseSubscriber(@Nullable CampaignAdapter campaignAdapter, List<String> serviceTitles) {
        this.campaignAdapter = campaignAdapter;
        this.serviceTitles = serviceTitles;
    }

    @Override
    public void onNext(CampaignsResponse campaignsResponse) {
        if (campaignAdapter != null && campaignsResponse != null) {
            campaignAdapter.clear();
            campaignAdapter.addItems(campaignsResponse.getCampaigns());

            Observable.from(campaignAdapter.getItems())
                    .filter(new Func1<Campaign, Boolean>() {
                        @Override
                        public Boolean call(Campaign campaign) {
                            return !serviceTitles.contains(campaign.getServiceTitle());
                        }
                    })
                    .map(new Func1<Campaign, String>() {
                        @Override
                        public String call(Campaign campaign) {
                            return campaign.getServiceTitle();
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String string) {
                            serviceTitles.add(string);
                        }
                    });
        }
    }
}
