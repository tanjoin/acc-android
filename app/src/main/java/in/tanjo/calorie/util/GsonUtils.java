package in.tanjo.calorie.util;

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
import java.util.Date;
import java.util.Locale;

public class GsonUtils {

    private GsonUtils() {
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
}
