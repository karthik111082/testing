package com.hik.trendycraftshow.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by codemagnus on 4/7/15.
 */
public class WebResponse {
    private String tag = "WebResponse";
    private JSONObject object;

    public WebResponse() {
    }

    public JSONObject getObject(String responseMessage) {
        try {
            object = new JSONObject(responseMessage);
        }catch (JSONException e){ e.printStackTrace(); }
        return object;
    }

    public JSONArray getJsonArray(JSONObject object, String key){
        JSONArray array = null;
        try {
            array = object.getJSONArray(key);
        } catch (JSONException e) { e.printStackTrace(); }
        return  array;
    }

    public JSONObject objectFromArray(JSONArray array, int position){
        JSONObject object = null;
        try {
            object = array.getJSONObject(position);
        } catch (JSONException e) { e.printStackTrace(); }

        return object;
    }

    public JSONObject getObjectFromObject(JSONObject aObject, String key){
        JSONObject object = null;
        try {
            object = aObject.getJSONObject(key);
        } catch (JSONException e) { e.printStackTrace(); }

        return object;
    }

    public String getStringFromObj(JSONObject object, String key){
        try {
            return object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*public boolean hasError(){
        boolean error = false;
        try {
            error = object.getBoolean(Api.ERROR);
        }catch (JSONException e){e.printStackTrace();}

        return error;
    }

    public String errorMessage(){
        String message = null;
        if(hasError()){
            try {
                message = object.getString(Api.MESSAGE);
            } catch (JSONException e) { e.printStackTrace(); }
        }else{
            message = "No error message";
        }
        return message;
    }*/

}
