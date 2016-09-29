package in.tanjo.calorie.rx;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.calorie.model.Campaign;
import rx.functions.Action1;


public class MainItemAction implements Action1<Campaign> {

    private List<Campaign> filteredCampaigns;

    public MainItemAction(@NonNull List<Campaign> filteredCampaigns) {
        this.filteredCampaigns = filteredCampaigns;
    }

    @Override
    public void call(Campaign campaign) {
        filteredCampaigns.add(campaign);
    }
}
