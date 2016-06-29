package com.jamessimshaw.cosplaycompanion.serialization;

import android.content.Context;
import android.net.Uri;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.models.Convention;

import java.lang.reflect.Type;

/**
 * Created by james on 11/27/15.
 */
public class ConventionDeserializer implements JsonDeserializer<Convention> {

    public ConventionDeserializer() {

    }

    @Override
    public Convention deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        long id = jsonObject.get("id").getAsLong();

        JsonObject logoObject = jsonObject.getAsJsonObject("logo");
        JsonElement urlElement = logoObject.get("url");
        String logo = "";
        if (!urlElement.isJsonNull())
            logo = logoObject.get("url").getAsString();

        Convention convention = new Convention(id, name, description, Uri.parse(logo));
        return convention;
    }
}
