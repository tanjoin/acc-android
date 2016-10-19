package in.tanjo.calorie.fragment;

import com.google.common.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import in.tanjo.calorie.MainActivity;
import in.tanjo.calorie.R;
import in.tanjo.calorie.adapter.CampaignAdapter;
import in.tanjo.calorie.api.CampaignApi;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.subscriber.CampaignsResponseSubscriber;
import in.tanjo.calorie.subscriber.action.SwipeRefreshLayoutRefreshingAction0;
import in.tanjo.calorie.subscriber.action.SwipeRefreshLayoutRefreshingAction1;
import in.tanjo.calorie.util.GsonUtils;
import in.tanjo.calorie.util.HtmlUtils;
import rx.android.schedulers.AndroidSchedulers;


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
    protected void initView(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.fragment_campaign_title);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                if (activity != null && activity instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) activity;
                    mainActivity.openDrawer();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        campaignAdapter = new CampaignAdapter(this);
        recyclerView.setAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        if (savedInstanceState == null) {
            onRefresh();
        } else {
            // @formatter:off
            List<Campaign> campaigns = GsonUtils.createGson().fromJson(
                    savedInstanceState.getString("campaigns"), new TypeToken<List<Campaign>>() {}.getType());
            // @formatter:on
            campaignAdapter.addItems(campaigns);
        }
    }

    @Override
    public void onRefresh() {
        new CampaignApi().getCampaigns()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, true))
                .doOnError(new SwipeRefreshLayoutRefreshingAction1<Throwable>(swipeRefreshLayout))
                .doOnCompleted(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, false))
                .subscribe(getRxManager().composite(new CampaignsResponseSubscriber(campaignAdapter)));
    }

    @Override
    public void onCardViewClick(@NonNull Campaign campaign, int position) {
        if (campaign.getUrls().size() > 0) {
            HtmlUtils.openUrl(getContext(), campaign.getUrls().get(0));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("campaigns", GsonUtils.createGson().toJson(campaignAdapter.getItems()));
    }
}
