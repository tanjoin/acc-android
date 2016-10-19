package in.tanjo.calorie.model;

import com.google.common.base.Strings;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.calorie.util.GsonUtils;

public class AbsGsonModel {

    protected String toJson() {
        return GsonUtils.createGson().toJson(this, this.getClass());
    }

    @NonNull
    protected String wrapString(String string) {
        return Strings.nullToEmpty(string);
    }

    @NonNull
    protected <T> List<T> wrapList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
