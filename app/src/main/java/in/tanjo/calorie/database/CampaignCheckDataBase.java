package in.tanjo.calorie.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import android.content.Context;
import android.support.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import in.tanjo.calorie.model.CampaignCheck;
import rx.Observable;

public class CampaignCheckDataBase extends AbsDataBase {

    public CampaignCheckDataBase(@NonNull Context context) {
        super(context);
    }

    @NonNull
    public Observable<List<CampaignCheck>> findAll() {
        return createObservable(CampaignCheck.class, new Listener<List<CampaignCheck>, CampaignCheck>() {
            @Override
            public List<CampaignCheck> call(Dao<CampaignCheck, Integer> dao) throws SQLException {
                return dao.queryForAll();
            }
        });
    }

    @NonNull
    public Observable<CampaignCheck> find(final int id) {
        return createObservable(CampaignCheck.class, new Listener<CampaignCheck, CampaignCheck>() {
            @Override
            public CampaignCheck call(Dao<CampaignCheck, Integer> dao) throws SQLException {
                return dao.queryForId(id);
            }
        });
    }

    @NonNull
    public Observable<Dao.CreateOrUpdateStatus> save(@NonNull final CampaignCheck campaignCheck) {
        return createObservable(CampaignCheck.class, new Listener<Dao.CreateOrUpdateStatus, CampaignCheck>() {
            @Override
            public Dao.CreateOrUpdateStatus call(Dao<CampaignCheck, Integer> dao) throws SQLException {
                return dao.createOrUpdate(campaignCheck);
            }
        });
    }

    @NonNull
    public Observable<Integer> delete(final int id) {
        return createObservable(CampaignCheck.class, new Listener<Integer, CampaignCheck>() {
            @Override
            public Integer call(Dao<CampaignCheck, Integer> dao) throws SQLException {
                DeleteBuilder<CampaignCheck, Integer> deleteBuilder = dao.deleteBuilder();
                deleteBuilder.where().eq("id", id);
                return deleteBuilder.delete();
            }
        });
    }
}
