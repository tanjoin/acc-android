package in.tanjo.calorie.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.tanjo.calorie.model.AbsGsonModel;
import in.tanjo.calorie.model.Campaign;

public class CampaignsResponse extends AbsGsonModel {

    @SerializedName("campaigns")
    List<Campaign> campaigns;

    public List<Campaign> getCampaigns() {
        return wrapList(campaigns);
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    @Override
    public String toJson() {
        return super.toJson();
    }
}