package in.tanjo.calorie.rx;

import java.util.Calendar;

import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.Weekday;
import rx.functions.Func1;


public class MainItemFilter implements Func1<Campaign, Boolean> {

    private Weekday weekday;

    private Weekday date;

    public MainItemFilter() {
        Calendar calendar = Calendar.getInstance();
        weekday = Weekday.getWeekByIndex(calendar.get(Calendar.DAY_OF_WEEK));
        date = Weekday.getDateByIndex(calendar.get(Calendar.DATE));
    }

    @Override
    public Boolean call(Campaign campaign) {
        return campaign != null && campaign.getOn().contains(Weekday.All) ||
                campaign != null && weekday != null && campaign.getOn().contains(weekday) ||
                campaign != null && date != null && campaign.getOn().contains(date);
    }
}
