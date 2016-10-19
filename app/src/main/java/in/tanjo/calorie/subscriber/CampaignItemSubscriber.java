package in.tanjo.calorie.subscriber;

import java.util.List;

import in.tanjo.calorie.model.Campaign;

public class CampaignItemSubscriber extends AbsSubscriber<Campaign> {

    private final List<Campaign> filteredCampaigns;

    public CampaignItemSubscriber(List<Campaign> filteredCampaigns) {
        this.filteredCampaigns = filteredCampaigns;
    }

    @Override
    public void onNext(Campaign campaign) {
        filteredCampaigns.add(campaign);
    }
}
