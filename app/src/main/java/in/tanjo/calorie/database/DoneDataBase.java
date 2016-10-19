package in.tanjo.calorie.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import android.content.Context;
import android.support.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import in.tanjo.calorie.model.Done;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DoneDataBase {

    private final Context context;

    public DoneDataBase(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    public Observable<List<Done>> findAll() {
        return Observable.create(new Observable.OnSubscribe<List<Done>>() {
            @Override
            public void call(Subscriber<? super List<Done>> subscriber) {
                AccDatabaseOpenHelper helper = new AccDatabaseOpenHelper(context);
                try {
                    Dao<Done, Integer> dao = helper.getDao(Done.class);
                    subscriber.onNext(dao.queryForAll());
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                } finally {
                    helper.close();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @NonNull
    public Observable<Dao.CreateOrUpdateStatus> save(@NonNull final Done done) {
        return Observable.create(new Observable.OnSubscribe<Dao.CreateOrUpdateStatus>() {
            @Override
            public void call(Subscriber<? super Dao.CreateOrUpdateStatus> subscriber) {
                AccDatabaseOpenHelper helper = new AccDatabaseOpenHelper(context);
                try {
                    Dao<Done, Integer> dao = helper.getDao(Done.class);
                    subscriber.onNext(dao.createOrUpdate(done));
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                } finally {
                    helper.close();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @NonNull
    public Observable<Integer> delete(final int id) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                AccDatabaseOpenHelper helper = new AccDatabaseOpenHelper(context);
                try {
                    Dao<Done, Integer> dao = helper.getDao(Done.class);
                    DeleteBuilder<Done, Integer> deleteBuilder = dao.deleteBuilder();
                    deleteBuilder.where().eq("id", id);
                    subscriber.onNext(deleteBuilder.delete());
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                } finally {
                    helper.close();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

}
