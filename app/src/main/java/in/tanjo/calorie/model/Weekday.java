package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public enum Weekday {

    @SerializedName(value = "1", alternate = {"1th", "1st"})
    FIRST(1),

    @SerializedName(value = "2", alternate = {"2th", "2nd"})
    SECOND(2),

    @SerializedName(value = "3", alternate = {"3th", "3rd"})
    THIRD(3),

    @SerializedName(value = "4", alternate = {"4th"})
    FOURTH(4),

    @SerializedName(value = "5", alternate = {"5th"})
    FIFTH(5),

    @SerializedName(value = "6", alternate = {"6th"})
    SIXTH(6),

    @SerializedName(value = "7", alternate = {"7th"})
    SEVENTH(7),

    @SerializedName(value = "8", alternate = {"8th"})
    EIGHTH(8),

    @SerializedName(value = "9", alternate = {"9th"})
    NINTH(9),

    @SerializedName(value = "10", alternate = {"10th"})
    TENTH(10),

    @SerializedName(value = "11", alternate = {"11th"})
    ELEVENTH(11),

    @SerializedName(value = "12", alternate = {"12th"})
    TWELFTH(12),

    @SerializedName(value = "13", alternate = {"13th"})
    THIRTEENTH(13),

    @SerializedName(value = "14", alternate = {"14th"})
    FOURTEENTH(14),

    @SerializedName(value = "15", alternate = {"15th"})
    FIFTEENTH(15),

    @SerializedName(value = "16", alternate = {"16th"})
    SIXTEENTH(16),

    @SerializedName(value = "17", alternate = {"17th"})
    SEVENTEENTH(17),

    @SerializedName(value = "18", alternate = {"18th"})
    EIGHTEENTH(18),

    @SerializedName(value = "19", alternate = {"19th"})
    NINETEENTH(19),

    @SerializedName(value = "20", alternate = {"20th"})
    TWENTIETH(20),

    @SerializedName(value = "21", alternate = {"21th", "21st"})
    TWENTY_FIRST(21),

    @SerializedName(value = "22", alternate = {"22th", "22nd"})
    TWENTY_SECOND(22),

    @SerializedName(value = "23", alternate = {"23th", "23rd"})
    TWENTY_THIRD(23),

    @SerializedName(value = "24", alternate = {"24th"})
    TWENTY_FOURTH(24),

    @SerializedName(value = "25", alternate = {"25th"})
    TWENTY_FIFTH(25),

    @SerializedName(value = "26", alternate = {"26th"})
    TWENTY_SIXTH(26),

    @SerializedName(value = "27", alternate = {"27th"})
    TWENTY_SEVENTH(27),

    @SerializedName(value = "28", alternate = {"28th"})
    TWENTY_EIGHTH(28),

    @SerializedName(value = "29", alternate = {"29th"})
    TWENTY_NINTH(29),

    @SerializedName(value = "30", alternate = {"30th"})
    THIRTIETH(30),

    @SerializedName(value = "31", alternate = {"31th", "31st"})
    THIRTY_FIRST(31),

    @SerializedName(value = "32", alternate = {"32th", "32nd"})
    THIRTY_SECOND(32),

    @SerializedName(value = "Sun", alternate = {"Sunday", "Sun.", "日曜日", "日曜", "日"})
    Sunday(101),

    @SerializedName(value = "Mon", alternate = {"Monday", "Mon.", "月曜日", "月曜", "月"})
    Monday(102),

    @SerializedName(value = "Tue", alternate = {"Tuesday", "Tue.", "火曜日", "火曜", "火"})
    Tuesday(103),

    @SerializedName(value = "Wed", alternate = {"Wednesday", "Wed.", "水曜日", "水曜", "水"})
    Wednesday(104),

    @SerializedName(value = "Thu", alternate = {"Thursday", "Thu.", "木曜日", "木曜", "木"})
    Thursday(105),

    @SerializedName(value = "Fri", alternate = {"Friday", "Fri.", "金曜日", "金曜", "金"})
    Friday(106),

    @SerializedName(value = "Sat", alternate = {"Saturday", "Sat.", "土曜日", "土曜", "土"})
    Saturday(107),

    @SerializedName("All")
    All(110);

    final int index;

    private static final int WEEK_THRESHOLD = 100;

    private static final List<Weekday> WEEKDAYS =
            Arrays.asList(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday);

    private static final List<Weekday> DATES =
            Arrays.asList(FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH, NINTH, TENTH, ELEVENTH, TWELFTH,
                    THIRTEENTH, FOURTEENTH, FIFTEENTH, SIXTEENTH, SEVENTEENTH, EIGHTEENTH, NINETEENTH, TWENTIETH, TWENTY_FIRST,
                    TWENTY_SECOND, TWENTY_THIRD, TWENTY_FOURTH, TWENTY_FIFTH, TWENTY_SIXTH, TWENTY_SEVENTH, TWENTY_EIGHTH,
                    TWENTY_NINTH, THIRTIETH, THIRTY_FIRST, THIRTY_SECOND);

    Weekday(int index) {
        this.index = index;
    }

    @Nullable
    public static Weekday getWeekByIndex(final int index) {
        for (Weekday weekday : WEEKDAYS) {
            if (weekday.getIndex() == index + WEEK_THRESHOLD) {
                return weekday;
            }
        }
        return null;
    }

    @Nullable
    public static Weekday getDateByIndex(final int index) {
        for (Weekday weekday : DATES) {
            if (weekday.getIndex() == index + WEEK_THRESHOLD) {
                return weekday;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
