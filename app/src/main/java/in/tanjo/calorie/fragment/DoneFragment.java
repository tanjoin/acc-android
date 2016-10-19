package in.tanjo.calorie.fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.calorie.MainActivity;
import in.tanjo.calorie.R;
import in.tanjo.calorie.adapter.DoneAdapter;
import in.tanjo.calorie.database.DoneDataBase;
import in.tanjo.calorie.event.DoneDeleteEvent;
import in.tanjo.calorie.event.DoneSaveEvent;
import in.tanjo.calorie.model.Done;
import in.tanjo.calorie.subscriber.DoneDeleteSubscriber;
import in.tanjo.calorie.subscriber.DonesSubscriber;
import in.tanjo.calorie.subscriber.action.SwipeRefreshLayoutRefreshingAction0;
import in.tanjo.calorie.subscriber.action.SwipeRefreshLayoutRefreshingAction1;
import rx.android.schedulers.AndroidSchedulers;

public class DoneFragment extends AbsFragment implements SwipeRefreshLayout.OnRefreshListener, DoneAdapter.Listener {

    @BindView(R.id.fragment_done_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_done_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fragment_done_toolbar)
    Toolbar toolbar;

    private DoneAdapter doneAdapter;

    @NonNull
    public static DoneFragment newInstance() {
        return new DoneFragment();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_done;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        toolbar.setTitle(R.string.fragment_done_title);
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
        doneAdapter = new DoneAdapter(this);
        recyclerView.setAdapter(doneAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        new DoneDataBase(getContext()).findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, true))
                .doOnError(new SwipeRefreshLayoutRefreshingAction1<Throwable>(swipeRefreshLayout))
                .doOnCompleted(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, false))
                .subscribe(getRxManager().composite(new DonesSubscriber(doneAdapter)));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @OnClick(R.id.fragment_done_floatingactionbutton)
    void onFloatingActionButtonClick() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.add(DoneCreateFragment.newInstance());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void omDoneSaveEvent(DoneSaveEvent event) {
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDoneDeleteEvent(DoneDeleteEvent event) {
        onRefresh();
    }

    @Override
    public void onCardViewClick(Done done, int position) {
        // no action
    }

    @Override
    public void onCardViewLongClick(final Done done, int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("確認")
                .setMessage("削除しますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new DoneDataBase(getContext()).delete(done.getId())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, true))
                                .doOnError(new SwipeRefreshLayoutRefreshingAction1<Throwable>(swipeRefreshLayout))
                                .doOnCompleted(new SwipeRefreshLayoutRefreshingAction0(swipeRefreshLayout, false))
                                .subscribe(getRxManager().composite(new DoneDeleteSubscriber()));
                    }
                })
                .setNegativeButton("キャンセル", null)
                .setCancelable(true)
                .create();
        alertDialog.show();
    }
}
