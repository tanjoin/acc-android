package in.tanjo.calorie.rx;

import java.util.Calendar;
import java.util.Date;

import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.Weekday;
import rx.functions.Func1;


public class CampaignItemFilter implements Func1<Campaign, Boolean> {

    private Weekday weekday;

    private Weekday date;

    private Date now;

    public CampaignItemFilter() {
        Calendar calendar = Calendar.getInstance();
        weekday = Weekday.getWeekByIndex(calendar.get(Calendar.DAY_OF_WEEK));
        date = Weekday.getDateByIndex(calendar.get(Calendar.DATE));
        now = new Date();
    }

    @Override
    public Boolean call(Campaign campaign) {
        if (campaign != null && campaign.getPeriod() != null) {
            try {
                if (campaign.getPeriod().getStart() != null && campaign.getPeriod().getStart().compareTo(now) > 0) {
                    return false;
                }
                if (campaign.getPeriod().getEnd() != null && campaign.getPeriod().getEnd().compareTo(now) < 0) {
                    return false;
                }
            } catch (NullPointerException ignore) {
            }
        }
        return campaign != null && campaign.getOn().contains(Weekday.All) ||
                campaign != null && weekday != null && campaign.getOn().contains(weekday) ||
                campaign != null && date != null && campaign.getOn().contains(date);
    }
}
