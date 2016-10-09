package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

public class Item extends AbsGsonModel {

    @Nullable
    @SerializedName("name")
    String name;

    @SerializedName("price")
    int price;

    @SerializedName("count")
    int count;

    @Nullable
    @SerializedName("description")
    String description;

    @Nullable
    @SerializedName("period")
    Period period;

    @NonNull
    public String getName() {
        return wrapString(name);
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    public String getDescription() {
        return wrapString(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(@Nullable Period period) {
        this.period = period;
    }
}
