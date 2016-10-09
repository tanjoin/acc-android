package in.tanjo.calorie.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;

import in.tanjo.calorie.R;


public class BlankFragment extends AbsFragment {

    @NonNull
    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void initView() {
        super.initView();
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
