package in.tanjo.calorie.model.dao;

import com.google.common.collect.Lists;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import in.tanjo.calorie.model.datahelper.AbsDataHelper;

public abstract class AbsDao<T, DH extends AbsDataHelper<T>> {

    private Context context;

    private final String name;

    public AbsDao(Context context, String name) {
        this.context = context;
        this.name = name;
    }

    // TODO: helper.getDao(T.class);
    abstract Dao<T, Integer> createDao();

    // TODO: AbsDataHelper helper = new AbsDataHelper(context, name);
    abstract DH createDataHelper(Context context, String name);

    public ArrayList<T> findAll() {
        DH helper = createDataHelper(context, name);
        try {
            Dao<T, Integer> dao = createDao();
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            return Lists.newArrayList(dao.query(queryBuilder.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        } finally {
            helper.close();
        }
    }

    public void createOrUpdate(T meal) {
        DH helper = createDataHelper(context, name);
        try {
            Dao<T, Integer> dao = createDao();
            dao.createOrUpdate(meal);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }
    }
}
