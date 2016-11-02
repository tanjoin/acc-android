package in.tanjo.calorie.fragment;

import com.google.common.base.Strings;

import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.calorie.R;
import in.tanjo.calorie.database.CampaignCheckDataBase;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.CampaignCheck;
import in.tanjo.calorie.subscriber.AbsSubscriber;
import in.tanjo.calorie.util.HtmlUtils;
import rx.android.schedulers.AndroidSchedulers;

public class CampaignDetailFragment extends AbsFragment {

    private static final String ARGS_CAMPAIGN = "campaign";

    @BindView(R.id.fragment_campaign_detail_description)
    TextView description;

    @BindView(R.id.fragment_campaign_detail_label)
    TextView label;

    @BindView(R.id.fragment_campaign_detail_title)
    TextView title;

    @BindView(R.id.fragment_campaign_detail_complete)
    Button complete;

    private CampaignCheck campaignCheck = new CampaignCheck();

    private Campaign campaign;

    public static CampaignDetailFragment newInstance(Campaign campaign) {
        CampaignDetailFragment fragment = new CampaignDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_CAMPAIGN, campaign.toJson());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            campaign = Campaign.fromJson(getArguments().getString(ARGS_CAMPAIGN));
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_campaign_detail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (campaign != null) {
            title.setText(campaign.getTitle());
            description.setText(campaign.getDescription());
            label.setText(campaign.getServiceTitle());
            find(campaign.getId());
        } else {
            title.setText("");
            description.setText("");
            label.setText("");
        }
    }

    @OnClick(R.id.fragment_campaign_detail_complete)
    void onCompleteClick() {
        if (campaign != null) {
            campaignCheck.setId(campaign.getId());
            campaignCheck.setRead(!campaignCheck.isRead());
            new CampaignCheckDataBase(getContext()).save(campaignCheck).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new AbsSubscriber<Dao.CreateOrUpdateStatus>() {
                        @Override
                        public void onNext(Dao.CreateOrUpdateStatus createOrUpdateStatus) {
                            // no action
                        }
                    });
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @OnClick(R.id.fragment_campaign_detail_container)
    void onContainerClick() {
        if (campaign != null && campaign.getUrls().size() > 0) {
            if (Strings.isNullOrEmpty(campaign.getUrls().get(0))) {
                return;
            }
            HtmlUtils.openUrl(getContext(), campaign.getUrls().get(0));
        }
    }

    private void find(int id) {
        new CampaignCheckDataBase(getContext()).find(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRxManager().composite(new AbsSubscriber<CampaignCheck>() {
                    @Override
                    public void onNext(CampaignCheck campaignCheck) {
                        if (campaignCheck != null) {
                            CampaignDetailFragment.this.campaignCheck = campaignCheck;
                            complete.setText(campaignCheck.isRead() ? "キャンセル" : "完了");
                        }
                    }
                }));
    }

}