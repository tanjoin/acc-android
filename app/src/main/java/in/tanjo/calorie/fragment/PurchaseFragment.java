package in.tanjo.calorie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.calorie.MainActivity;
import in.tanjo.calorie.R;
import in.tanjo.calorie.adapter.PurchaseAdapter;
import in.tanjo.calorie.model.Purchase;

public class PurchaseFragment extends AbsFragment implements SwipeRefreshLayout.OnRefreshListener, PurchaseAdapter.Listener {

    @BindView(R.id.fragment_purchase_coordinatorlayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.fragment_purchase_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_purchase_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fragment_purchase_toolbar)
    Toolbar toolbar;

    private PurchaseAdapter purchaseAdapter;

    @NonNull
    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_purchase;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.fragment_purchase_title);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        purchaseAdapter = new PurchaseAdapter(this);
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        purchaseAdapter.addItems(new ArrayList<Purchase>());
    }

    @OnClick(R.id.fragment_purchase_floatingactionbutton)
    void onFloatingActionButtonClick() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.add(PurchaseCreateFragment.newInstance());
        }
    }

    @Override
    public void onCardViewClick(@NonNull Purchase purchase, int position) {

    }
}
