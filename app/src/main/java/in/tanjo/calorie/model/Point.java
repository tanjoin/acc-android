package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Point extends AbsGsonModel {

    @Nullable
    @SerializedName("type")
    String type;

    @Nullable
    @SerializedName("value")
    int value;

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
}
