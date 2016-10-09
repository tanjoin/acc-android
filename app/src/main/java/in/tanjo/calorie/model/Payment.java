package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class Payment extends AbsGsonModel {

    @Nullable
    @SerializedName("type")
    String type;

    @SerializedName("value")
    int value;

    @Nullable
    @SerializedName("point")
    List<Point> points;

    @NonNull
    public String getType() {
        return wrapString(type);
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @NonNull
    public List<Point> getPoints() {
        return wrapList(points);
    }

    public void setPoints(@Nullable List<Point> points) {
        this.points = points;
    }
}
