package in.tanjo.calorie.fragment;

import com.j256.ormlite.dao.Dao;

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
import in.tanjo.calorie.database.CampaignCheckDataBase;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.CampaignCheck;
import in.tanjo.calorie.subscriber.AbsSubscriber;
import in.tanjo.calorie.subscriber.CampaignsResponseSubscriber;
import in.tanjo.calorie.subscriber.action.SwipeRefreshLayoutRefreshingAction0;
import in.tanjo.calorie.subscriber.action.SwipeRefreshLayoutRefreshingAction1;
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

    private boolean isExcludeCheck = true;

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
                } else if (item.getItemId() == R.id.menu_fragment_campaign_unread) {
                    isExcludeCheck = !isExcludeCheck;
                    excludeCheck(isExcludeCheck);
                    return true;
                }
                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        campaignAdapter = new CampaignAdapter(this);
        excludeCheck(isExcludeCheck);
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
                final int swipePosition = viewHolder.getAdapterPosition();
                campaignAdapter.remove(swipePosition);
                new CampaignCheckDataBase(getContext()).find(campaignAdapter.getItem(swipePosition).getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new AbsSubscriber<CampaignCheck>() {
                            @Override
                            public void onNext(CampaignCheck campaignCheck) {
                                if (campaignCheck == null) {
                                    campaignCheck = new CampaignCheck();
                                    campaignCheck.setId(campaignAdapter.getItem(swipePosition).getId());
                                }
                                campaignCheck.setRead(!campaignCheck.isRead());
                                new CampaignCheckDataBase(getContext()).save(campaignCheck)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new AbsSubscriber<Dao.CreateOrUpdateStatus>() {
                                            @Override
                                            public void onNext(Dao.CreateOrUpdateStatus createOrUpdateStatus) {
                                                if (getView() != null) {
                                                    Snackbar.make(getView(), "既読にしました", Snackbar.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
            }
        }).attachToRecyclerView(recyclerView);
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        onRefresh();
    }

    private void excludeCheck(final boolean exclude) {
        new CampaignCheckDataBase(getContext()).findAll().observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, true))
                .doOnError(new SwipeRefreshLayoutRefreshingAction1<Throwable>(swipeRefreshLayout))
                .doOnCompleted(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, false))
                .subscribe(getRxManager().composite(new AbsSubscriber<List<CampaignCheck>>() {
                    @Override
                    public void onNext(List<CampaignCheck> campaignChecks) {
                        if (campaignChecks != null) {
                            campaignAdapter.excludeCheck(exclude, campaignChecks);
                        }
                    }
                }));
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
        Activity activity = getActivity();
        if (activity != null && activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.add(CampaignDetailFragment.newInstance(campaign));
        }
    }
}
