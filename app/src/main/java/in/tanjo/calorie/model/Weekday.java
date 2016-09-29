package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.Nullable;

import rx.Observable;

public enum Weekday {

    @SerializedName(value = "Sun", alternate = {"Sunday", "Sun.", "日曜日", "日曜", "日"})
    Sunday(1),

    @SerializedName(value = "Mon", alternate = {"Monday", "Mon.", "月曜日", "月曜", "月"})
    Monday(2),

    @SerializedName(value = "Tue", alternate = {"Tuesday", "Tue.", "火曜日", "火曜", "火"})
    Tuesday(3),

    @SerializedName(value = "Wed", alternate = {"Wednesday", "Wed.", "水曜日", "水曜", "水"})
    Wednesday(4),

    @SerializedName(value = "Thu", alternate = {"Thursday", "Thu.", "木曜日", "木曜", "木"})
    Thursday(5),

    @SerializedName(value = "Fri", alternate = {"Friday", "Fri.", "金曜日", "金曜", "金"})
    Friday(6),

    @SerializedName(value = "Sat", alternate = {"Saturday", "Sat.", "土曜日", "土曜", "土"})
    Saturday(7),

    @SerializedName("All")
    All(10);

    final int index;

    Weekday(int index) {
        this.index = index;
    }

    @Nullable
    public static Weekday fromIndex(final int index) {
        for (Weekday weekday : Weekday.values()) {
            if (weekday.getIndex() == index) {
                return weekday;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
