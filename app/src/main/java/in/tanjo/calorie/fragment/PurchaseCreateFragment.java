package in.tanjo.calorie.fragment;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import in.tanjo.calorie.R;

public class PurchaseCreateFragment extends AbsFragment {

    @BindView(R.id.fragment_purchase_create_toolbar)
    Toolbar toolbar;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_purchase_create;
    }

    @Override
    protected void initView() {
        super.initView();
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setTitle(R.string.fragment_purchase_create_title);
    }

    public static PurchaseCreateFragment newInstance() {
        return new PurchaseCreateFragment();
    }
}
