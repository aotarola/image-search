package me.otarola.imagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aotarolaalvarad on 10/27/15.
 */
public class ImageResult {
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public ImageResult(JSONObject json){
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();

        for(int i=0; i < array.length(); i++){
            try{
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
