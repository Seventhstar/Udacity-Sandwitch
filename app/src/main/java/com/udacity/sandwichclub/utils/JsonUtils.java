package com.udacity.sandwichclub.utils;

import android.content.Context;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);

        JSONObject rootObject = jsonObject.getJSONObject(
                context.getString(R.string.json_key_root_name));

        String mainName = rootObject.getString(
                context.getString(R.string.json_key_main_name));

        String placeOfOrigin = jsonObject.getString(
                context.getString(R.string.json_key_place_of_origin));

        String description = jsonObject.getString(
                context.getString(R.string.json_key_description));

        String imageUrl = jsonObject.getString(
                context.getString(R.string.json_key_image));


        List<String> ingredientsList = new ArrayList<>();
        List<String> alsoKnownAsList = new ArrayList<>();

        return new Sandwich(mainName,
                alsoKnownAsList,
                placeOfOrigin,
                description,
                imageUrl,
                ingredientsList);
    }

}
