package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.Nullable;

import java.util.Date;

public class Period extends AbsGsonModel {

    @SerializedName("start")
    Date start;

    @SerializedName("end")
    Date end;

    @Nullable
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Nullable
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
