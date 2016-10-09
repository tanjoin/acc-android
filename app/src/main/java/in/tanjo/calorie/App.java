package in.tanjo.calorie;

import com.jakewharton.threetenabp.AndroidThreeTen;

import android.app.Application;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
