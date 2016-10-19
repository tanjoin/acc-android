package in.tanjo.calorie.subscriber;

import android.support.annotation.Nullable;

import in.tanjo.calorie.adapter.CampaignAdapter;
import in.tanjo.calorie.model.response.CampaignsResponse;

public class CampaignsResponseSubscriber extends AbsSubscriber<CampaignsResponse> {

    @Nullable
    private final CampaignAdapter campaignAdapter;

    public CampaignsResponseSubscriber(@Nullable CampaignAdapter campaignAdapter) {
        this.campaignAdapter = campaignAdapter;
    }


    @Override
    public void onNext(CampaignsResponse campaignsResponse) {
        if (campaignAdapter != null && campaignsResponse != null) {
            campaignAdapter.clear();
            campaignAdapter.addItems(campaignsResponse.getCampaigns());
        }
    }
}
