package in.tanjo.calorie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.calorie.fragment.BlankFragment;
import in.tanjo.calorie.fragment.CampaignFragment;
import in.tanjo.calorie.fragment.PurchaseFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.activity_main_drawerlayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.activity_main_navigationview)
    NavigationView navigationView;

    private FragmentType fragmentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentType = FragmentType.CAMPAIGN;
        replace(fragmentType.getFragment());
    }

    public void replace(@NonNull Fragment fragment) {
        commit(fragment, false, false);
    }

    private void commit(@NonNull Fragment fragment, boolean isAddToBackStack, boolean isSlideIn) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isSlideIn) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        }
        fragmentTransaction.replace(R.id.activity_main_relativelayout, fragment);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    public void add(@NonNull Fragment fragment) {
        commit(fragment, true, true);
    }

    public void setDrawerLockMode(int lockMode) {
        drawerLayout.setDrawerLockMode(lockMode, navigationView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentType newFragmentType = FragmentType.UNKNOWN;
        switch (id) {
            case R.id.menu_activity_main_calorie:
                newFragmentType = FragmentType.BLANK;
                break;
            case R.id.menu_activity_main_campaign:
                newFragmentType = FragmentType.CAMPAIGN;
                break;
            case R.id.menu_activity_main_purchase:
                newFragmentType = FragmentType.PURCHASE;
                break;
            case R.id.menu_activity_main_setting:
                newFragmentType = FragmentType.BLANK;
                break;
        }
        if (newFragmentType != fragmentType && newFragmentType != FragmentType.UNKNOWN) {
            fragmentType = newFragmentType;
            replace(fragmentType.getFragment());
        }
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView, true);
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (drawerLayout.getDrawerLockMode(navigationView) != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            if (keyCode == KeyEvent.KEYCODE_BACK && !drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.openDrawer(navigationView);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private enum FragmentType {
        UNKNOWN {
            @Override
            Fragment getFragment() {
                return null;
            }
        },
        BLANK {
            @Override
            Fragment getFragment() {
                return BlankFragment.newInstance();
            }
        },
        CAMPAIGN {
            @Override
            Fragment getFragment() {
                return CampaignFragment.newInstance();
            }
        },
        PURCHASE {
            @Override
            Fragment getFragment() {
                return PurchaseFragment.newInstance();
            }
        };

        abstract Fragment getFragment();
    }
}