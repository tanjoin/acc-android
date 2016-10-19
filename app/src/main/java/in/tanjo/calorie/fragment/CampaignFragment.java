package in.tanjo.calorie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
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
import in.tanjo.calorie.util.HtmlUtils;
import rx.android.schedulers.AndroidSchedulers;


public class CampaignFragment extends AbsFragment implements SwipeRefreshLayout.OnRefreshListener, CampaignAdapter.Listener {

    @BindView(R.id.fragment_campaign_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_campaign_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fragment_campaign_toolbar)
    Toolbar toolbar;

    private CampaignAdapter campaignAdapter;

    private List<String> serviceTitles = new ArrayList<>();

    private int filterIndex = -1;

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
        toolbar.inflateMenu(R.menu.fragment_campaign);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_fragment_campaign_filter) {
                    filter();
                    return true;
                }
                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        campaignAdapter = new CampaignAdapter(this);
        recyclerView.setAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                    RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipePosition = viewHolder.getAdapterPosition();
                campaignAdapter.remove(swipePosition);
            }
        }).attachToRecyclerView(recyclerView);
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        onRefresh();
    }

    public void filter() {
        filterIndex += 1;
        if (serviceTitles.size() > filterIndex) {
            if (getView() != null) {
                Snackbar.make(getView(), serviceTitles.get(filterIndex), Snackbar.LENGTH_SHORT).show();
            }
            campaignAdapter.filter(serviceTitles.get(filterIndex));
        } else {
            filterIndex = -1;
            if (getView() != null) {
                Snackbar.make(getView(), "全部", Snackbar.LENGTH_SHORT).show();
            }
            campaignAdapter.filter(null);
        }
    }

    @Override
    public void onRefresh() {
        new CampaignApi().getCampaigns()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, true))
                .doOnError(new SwipeRefreshLayoutRefreshingAction1<Throwable>(swipeRefreshLayout))
                .doOnCompleted(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, false))
                .subscribe(getRxManager().composite(new CampaignsResponseSubscriber(campaignAdapter, serviceTitles)));
    }

    @Override
    public void onCardViewClick(@NonNull Campaign campaign, int position) {
        if (campaign.getUrls().size() > 0) {
            HtmlUtils.openUrl(getContext(), campaign.getUrls().get(0));
        }
    }
}
