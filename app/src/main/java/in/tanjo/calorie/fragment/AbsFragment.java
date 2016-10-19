package in.tanjo.calorie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.tanjo.calorie.MainActivity;
import in.tanjo.calorie.util.RxManager;


public abstract class AbsFragment extends Fragment {

    private Unbinder unbinder;

    private RxManager rxManager = new RxManager();

    public RxManager getRxManager() {
        return rxManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        rxManager.clear();
        unbinder.unbind();
        super.onDestroyView();
    }

    @LayoutRes
    abstract protected int layoutRes();

    protected void initView(@Nullable Bundle savedInstanceState) {
        // no action
    }

    protected void setDrawerLockMode(int lockMode) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setDrawerLockMode(lockMode);
        }
    }


}
