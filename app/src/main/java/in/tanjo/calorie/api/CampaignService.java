package in.tanjo.calorie.api;

import java.util.List;

import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.response.CampaignsResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CampaignService {

    @GET("acc/campaign.json")
    Call<CampaignsResponse> getCampaigns();
}

