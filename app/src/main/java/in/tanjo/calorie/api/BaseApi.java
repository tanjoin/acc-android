package in.tanjo.calorie.api;

import android.support.annotation.NonNull;

import in.tanjo.calorie.model.AbsGsonModel;
import in.tanjo.calorie.util.GsonUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class BaseApi<T> {

    private final Retrofit retrofit;

    private T service;

    public BaseApi(Class<T> tClass) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://tanjo.in/")
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.createGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = this.retrofit.create(tClass);
    }

    protected <R> Observable<R> makeObservable(@NonNull Func0<Observable<R>> observableFactory) {
        return Observable.defer(observableFactory).subscribeOn(Schedulers.io());
    }

    protected T getService() {
        return service;
    }
}
