package com.task.model.json;

import com.google.gson.*;
import com.task.model.entities.Settings;
import com.task.model.entities.Subsettings;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class SettingsSerializer implements JsonSerializer<Settings>, JsonDeserializer<Settings> {
    @Override
    public JsonElement serialize(Settings settings, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        JsonObject settingsObject = new JsonObject();
        JsonArray array = new JsonArray();
        JsonObject subsettingsObject;
        for (Subsettings subsettings : settings.getSubsettings()) {
            subsettingsObject = new JsonObject();
            subsettingsObject.addProperty("table_item1", simpleDateFormat
                    .format(subsettings.getTableItem1().getTime()));
            subsettingsObject.addProperty("table_item2", simpleDateFormat
                    .format(subsettings.getTableItem2().getTime()));
            array.add(subsettingsObject);
        }
        settingsObject.add("table", array);
        settingsObject.addProperty("type", settings.getType());
        settingsObject.addProperty("item1", settings.getItem1());
        return settingsObject;
    }

    @Override
    public Settings deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Settings settings = new Settings();
        JsonObject object = jsonElement.getAsJsonObject();
        JsonElement element = object.get("type");
        settings.setType(element != null ? element.getAsString() : null);
        element = object.get("item1");
        settings.setItem1(element != null ? element.getAsDouble() : null);
        JsonElement tableElement = object.get("table");
        if (tableElement != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Set<Subsettings> subsettings = new HashSet<>();
            if (!tableElement.isJsonNull()) {
                Subsettings subsetting;
                JsonArray array = element.getAsJsonArray();
                for (JsonElement json : array) {
                    subsetting = new Subsettings();
                    try {
                        element = json.getAsJsonObject().get("table_item1");
                        subsetting.setTableItem1(element != null ? dateFormat.parse(element.getAsString()) : null);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        element = json.getAsJsonObject().get("table_item2");
                        subsetting.setTableItem2(element != null ? dateFormat.parse(element.getAsString()) : null);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    subsettings.add(subsetting);
                }
                settings.setSubsettings(subsettings);
            }

        }
        return settings;
    }
}
