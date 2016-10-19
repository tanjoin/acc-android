package in.tanjo.calorie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import in.tanjo.calorie.MainActivity;
import in.tanjo.calorie.R;

public class PurchaseCreateFragment extends AbsFragment {

    @BindView(R.id.fragment_purchase_create_toolbar)
    Toolbar toolbar;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_purchase_create;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setTitle(R.string.fragment_purchase_create_title);
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
    }

    public static PurchaseCreateFragment newInstance() {
        return new PurchaseCreateFragment();
    }
}
