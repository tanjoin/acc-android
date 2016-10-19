package in.tanjo.calorie.model;

import com.google.gson.annotations.SerializedName;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

@DatabaseTable(tableName = "done")
public class Done extends AbsGsonModel {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @Nullable
    @DatabaseField(columnName = "title")
    @SerializedName("title")
    private String title;

    @Nullable
    @DatabaseField(columnName = "description", canBeNull = true)
    @SerializedName("description")
    private String description;

    @Nullable
    @DatabaseField(columnName = "created_at", dataType = DataType.DATE, version = true)
    @SerializedName("created_at")
    private Date createdAt;

    @Nullable
    @DatabaseField(columnName = "completed_at", dataType = DataType.DATE)
    @SerializedName("completed_at")
    private Date completedAt;

    public String getTitle() {
        return wrapString(title);
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getDescription() {
        return wrapString(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(@Nullable Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getCompletedAtString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(completedAt);
        return DateFormat.format("yy/MM/dd HH:mm", calendar).toString();
    }
}
