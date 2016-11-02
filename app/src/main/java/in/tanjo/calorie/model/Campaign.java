package in.tanjo.calorie.model;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import com.j256.ormlite.field.DatabaseField;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import in.tanjo.calorie.util.GsonUtils;

public class Campaign extends AbsGsonModel {

    @DatabaseField(columnName = "id")
    @SerializedName("id")
    int id;

    @Nullable
    @DatabaseField(columnName = "id")
    @SerializedName("title")
    String title;

    @Nullable
    @SerializedName("description")
    String description;

    @Nullable
    @SerializedName("urls")
    List<String> urls;

    @Nullable
    @SerializedName("service_title")
    String serviceTitle;

    @Nullable
    @SerializedName(value = "date", alternate = {"period", "during", "term"})
    Period period;

    @Nullable
    @SerializedName("on")
    List<Weekday> on;

    @Override
    public String toJson() {
        return super.toJson();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return wrapString(title);
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return wrapString(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @NonNull
    public List<String> getUrls() {
        return wrapList(urls);
    }

    public void setUrls(@Nullable List<String> urls) {
        this.urls = urls;
    }

    @NonNull
    public String getServiceTitle() {
        return wrapString(serviceTitle);
    }

    public void setServiceTitle(@Nullable String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    @NonNull
    public List<Weekday> getOn() {
        return wrapList(on);
    }

    public void setOn(@Nullable List<Weekday> on) {
        this.on = on;
    }

    @Nullable
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(@Nullable Period period) {
        this.period = period;
    }

    public static Campaign fromJson(String string) {
        try {
            return GsonUtils.createGson().fromJson(string, Campaign.class);
        } catch (JsonParseException e) {
            Log.e("acc", string, e);
            return null;
        }
    }
}
