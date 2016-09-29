package in.tanjo.calorie.rx;

import java.util.Calendar;
import java.util.Date;

import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.Weekday;
import rx.functions.Func1;


public class MainItemFilter implements Func1<Campaign, Boolean> {

    private Weekday weekday;

    private Date now;

    public MainItemFilter() {
        Calendar calendar = Calendar.getInstance();
        weekday = Weekday.fromIndex(calendar.get(Calendar.DAY_OF_WEEK));
        now = new Date();
    }

    @Override
    public Boolean call(Campaign campaign) {
        return campaign != null && campaign.getOn().contains(Weekday.All) ||
                campaign != null && weekday != null && campaign.getOn().contains(weekday);
    }
}
