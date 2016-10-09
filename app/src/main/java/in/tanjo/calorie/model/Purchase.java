package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.ZonedDateTime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class Purchase extends AbsGsonModel {

    @Nullable
    @SerializedName("date")
    Date date;

    @Nullable
    @SerializedName("store")
    String store;

    @Nullable
    @SerializedName("description")
    String description;

    @Nullable
    @SerializedName("payments")
    List<Payment> payments;

    @Nullable
    @SerializedName("items")
    List<Item> items;

    @NonNull
    public List<Payment> getPayments() {
        return wrapList(payments);
    }

    public void setPayments(@Nullable List<Payment> payments) {
        this.payments = payments;
    }

    @Nullable
    public Date getDate() {
        return date;
    }

    public void setDate(@Nullable Date date) {
        this.date = date;
    }

    @NonNull
    public String getStore() {
        return wrapString(store);
    }

    public void setStore(@Nullable String store) {
        this.store = store;
    }

    @NonNull
    public String getDescription() {
        return wrapString(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @NonNull
    public List<Item> getItems() {
        return wrapList(items);
    }

    public void setItems(@Nullable List<Item> items) {
        this.items = items;
    }
}
