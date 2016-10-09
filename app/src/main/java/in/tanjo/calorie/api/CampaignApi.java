package in.tanjo.calorie.api;

import in.tanjo.calorie.model.AbsGsonModel;
import in.tanjo.calorie.model.response.CampaignsResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CampaignApi implements CampaignService {

    private final Retrofit retrofit;

    public CampaignApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://tanjo.in/")
                .addConverterFactory(GsonConverterFactory.create(AbsGsonModel.createGson()))
                .build();
    }

    @Override
    public Call<CampaignsResponse> getCampaigns() {
        return retrofit.create(CampaignService.class).getCampaigns();
    }
}
