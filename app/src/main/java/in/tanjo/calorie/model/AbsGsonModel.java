package in.tanjo.calorie.model;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AbsGsonModel {

    protected String toJson() {
        return createGson().toJson(this, this.getClass());
    }

    public static Gson createGson() {
        return new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {

                for (String format : Arrays.asList("yyyy/MM/dd HH:mm", "yyyy/MM/dd")) {
                    try {
                        return new SimpleDateFormat(format, Locale.JAPAN).parse(json.getAsString());
                    } catch (ParseException ignored) {
                    }
                }
                throw new JsonParseException("Unparseable date: " + json.getAsString());
            }
        }).create();
    }

    protected String wrapString(String string) {
        return Strings.nullToEmpty(string);
    }

    protected <T> List<T> wrapList(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }
}
