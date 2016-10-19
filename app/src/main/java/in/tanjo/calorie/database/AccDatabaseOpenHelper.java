package in.tanjo.calorie.database;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

import in.tanjo.calorie.model.Done;


public class AccDatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "acc.db";

    public AccDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Done.class);
        } catch (SQLException e) {
            Log.e("ACC", AccDatabaseOpenHelper.class.getName() + " : データベースを作成できませんでした.", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // DB のアップグレード処理
    }
}
