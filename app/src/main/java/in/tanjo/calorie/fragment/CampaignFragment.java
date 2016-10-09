package in.tanjo.calorie.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import in.tanjo.calorie.R;
import in.tanjo.calorie.adapter.CampaignAdapter;
import in.tanjo.calorie.api.CampaignApi;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.response.CampaignsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CampaignFragment extends AbsFragment implements SwipeRefreshLayout.OnRefreshListener, CampaignAdapter.Listener {

    @BindView(R.id.fragment_main_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_main_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fragment_main_toolbar)
    Toolbar toolbar;

    private CampaignAdapter campaignAdapter;

    @NonNull
    public static CampaignFragment newInstance() {
        return new CampaignFragment();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_campaign;
    }

    @Override
    protected void initView() {
        toolbar.setTitle(R.string.fragment_campaign_title);
        swipeRefreshLayout.setOnRefreshListener(this);
        campaignAdapter = new CampaignAdapter(this);
        recyclerView.setAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new CampaignApi().getCampaigns().enqueue(new Callback<CampaignsResponse>() {
            @Override
            public void onResponse(Call<CampaignsResponse> call, Response<CampaignsResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.body() != null) {
                    campaignAdapter.setItems(response.body().getCampaigns());
                }
            }

            @Override
            public void onFailure(Call<CampaignsResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCardViewClick(@NonNull Campaign campaign, int position) {
        if (campaign.getUrls().size() > 0) {
            openUrl(campaign.getUrls().get(0));
        }
    }
}
