package in.tanjo.calorie.api;

import in.tanjo.calorie.model.response.CampaignsResponse;
import retrofit2.http.GET;
import rx.Observable;
import rx.functions.Func0;

public class CampaignApi extends BaseApi<CampaignApi.Service> {

    public CampaignApi() {
        super(Service.class);
    }

    public Observable<CampaignsResponse> getCampaigns() {
        return makeObservable(new Func0<Observable<CampaignsResponse>>() {
            @Override
            public Observable<CampaignsResponse> call() {
                return getService().getCampaigns();
            }
        });
    }

    interface Service {

        @GET("acc/campaign.json")
        Observable<CampaignsResponse> getCampaigns();
    }
}
