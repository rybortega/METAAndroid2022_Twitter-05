package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imgUrl;


    public Tweet(){}
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        if(jsonObject.has("full_text")) {
            tweet.body = jsonObject.getString("full_text");
        }
        else {
            tweet.body = jsonObject.getString("text");
        }


        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        if(jsonObject.has("entities")) {
            // Get JSONObject from the object for 'entities'
            JSONObject entities = jsonObject.getJSONObject("entities");
            // Get JSONArray from JSONObject for 'media'
            if(entities.has("media")) {
                JSONArray media = entities.getJSONArray("media");
                // Get JSONObject at position 0
                JSONObject o = media.getJSONObject(0);
                tweet.imgUrl = o.getString("media_url_https");
            }

        } else {
            tweet.imgUrl = "";
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }




}
