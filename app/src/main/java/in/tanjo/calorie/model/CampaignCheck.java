package in.tanjo.calorie.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "campaign_check")
public class CampaignCheck {
    @DatabaseField(id = true, columnName = "id")
    int id;

    @DatabaseField(columnName = "is_read", dataType = DataType.BOOLEAN)
    boolean isRead;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
