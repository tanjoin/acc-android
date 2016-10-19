package in.tanjo.calorie.database;

import com.j256.ormlite.dao.Dao;

import android.content.Context;
import android.support.annotation.NonNull;

import java.sql.SQLException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AbsDataBase {

    private final Context context;

    public AbsDataBase(@NonNull Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    protected  <S, D> Observable<S> createObservable(final Class<D> dClass, final Listener<S, D> listener) {
        return Observable.create(new Observable.OnSubscribe<S>() {
            @Override
            public void call(Subscriber<? super S> subscriber) {
                AccDatabaseOpenHelper helper = new AccDatabaseOpenHelper(context);
                try {
                    Dao<D, Integer> dao = helper.getDao(dClass);
                    if (listener != null) {
                        subscriber.onNext(listener.call(dao));
                    }
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                } finally {
                    helper.close();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public interface Listener<S, D> {

        /**
         *
         */
        S call(Dao<D, Integer> dao) throws SQLException;
    }
}
