package in.tanjo.calorie.fragment;

import com.google.common.base.Strings;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.calorie.MainActivity;
import in.tanjo.calorie.R;
import in.tanjo.calorie.database.DoneDataBase;
import in.tanjo.calorie.model.Done;
import in.tanjo.calorie.subscriber.DoneSaveSubscriber;
import rx.android.schedulers.AndroidSchedulers;

public class DoneCreateFragment extends AbsFragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.fragment_done_create_toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragment_done_create_edittext_title)
    EditText editTitle;

    @BindView(R.id.fragment_done_create_edittext_completed_at)
    EditText editCompletedAt;

    @BindView(R.id.fragment_done_create_edittext_description)
    EditText editDescription;

    private Done done;

    private Calendar completedAtCalendar;

    public static DoneCreateFragment newInstance() {
        return new DoneCreateFragment();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_done_create;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        toolbar.setTitle(R.string.fragment_done_create_title);
        toolbar.inflateMenu(R.menu.fragment_done_create);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menU_fragment_done_create_save) {
                    if (save()) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    return true;
                }
                return false;
            }
        });
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
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        setHasOptionsMenu(true);
        completedAtCalendar = Calendar.getInstance();
        updateCompletedAt(completedAtCalendar);
        done = new Done();
    }

    private boolean save() {
        String title = editTitle.getText().toString();
        if (Strings.isNullOrEmpty(title)) {
            View view = getView();
            if (view != null) {
                Snackbar.make(view, R.string.fragment_done_create_error_message_title, Snackbar.LENGTH_SHORT).show();
            }
            return false;
        }
        done.setTitle(title);

        String description = editDescription.getText().toString();
        done.setDescription(description);

        done.setCompletedAt(completedAtCalendar.getTime());

        done.setCreatedAt(new Date());

        new DoneDataBase(getContext()).save(done)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRxManager().composite(new DoneSaveSubscriber(getView())));
        return true;
    }

    private void updateCompletedAt(Calendar calendar) {
        String date = DateFormat.format("yyyy/MM/dd HH:mm", calendar).toString();
        editCompletedAt.setText(date);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_done_create, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menU_fragment_done_create_save) {
            if (save()) {
                getFragmentManager().popBackStack();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fragment_done_create_edittext_completed_at)
    void onCompletedAtEditTextClick() {
        showDatePickerDialog();
    }

    void showDatePickerDialog() {
        new DatePickerDialog(getContext(),
                this,
                completedAtCalendar.get(Calendar.YEAR),
                completedAtCalendar.get(Calendar.MONTH),
                completedAtCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        completedAtCalendar.set(Calendar.YEAR, year);
        completedAtCalendar.set(Calendar.MONTH, month);
        completedAtCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateCompletedAt(completedAtCalendar);
        showTimePickerDialog();
    }

    void showTimePickerDialog() {
        new TimePickerDialog(getContext(),
                this,
                completedAtCalendar.get(Calendar.HOUR_OF_DAY),
                completedAtCalendar.get(Calendar.MINUTE),
                true).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        completedAtCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        completedAtCalendar.set(Calendar.MINUTE, minute);
        updateCompletedAt(completedAtCalendar);
    }
}
