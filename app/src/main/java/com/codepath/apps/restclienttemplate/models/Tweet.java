package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RecursiveTask;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imgUrl;
    public String time;


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
        tweet.time = getRelativeTimeAgo(tweet.createdAt);

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


    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
