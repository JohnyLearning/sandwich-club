package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getName();
    private static final String EMPTY_STRING = "";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwichReturned = null;
        try {
            JSONObject sandwichDetails = new JSONObject(json);
            JSONObject nameObject = sandwichDetails.getJSONObject("name");
            String mainName = EMPTY_STRING;
            List<String> alsoKnownAsList = null;
            if (nameObject != null) {
                mainName = nameObject.optString("mainName");
                alsoKnownAsList = convertJsonArrayToList(nameObject.getJSONArray("alsoKnownAs"));
            }
            sandwichReturned = new Sandwich(
                    mainName,
                    alsoKnownAsList,
                    sandwichDetails.optString("placeOfOrigin"),
                    sandwichDetails.optString("description"),
                    sandwichDetails.optString("image"),
                    convertJsonArrayToList(sandwichDetails.getJSONArray("ingredients"))
            );
        } catch (JSONException je) {
            Log.e(TAG, je.getMessage());
        }
        return sandwichReturned;
    }

    private static List<String> convertJsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> arrayAsList = null;
        if (jsonArray != null && jsonArray.length() > 0) {
            arrayAsList = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayAsList.add(jsonArray.optString(i));
            }
        }
        return arrayAsList;
    }
}
