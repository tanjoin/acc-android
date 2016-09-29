package in.tanjo.calorie.model.datahelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public abstract class AbsDataHelper<T> extends OrmLiteSqliteOpenHelper {

    private static final int VERSION = 1;

    private final String name;

    public AbsDataHelper(Context context, String name) {
        super(context, name, null, VERSION);
        this.name = name;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        createTable(connectionSource);
    }

    // TODO: TableUtils.createTable(connectionSource, T.class);
    abstract void createTable(ConnectionSource connectionSource);

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
