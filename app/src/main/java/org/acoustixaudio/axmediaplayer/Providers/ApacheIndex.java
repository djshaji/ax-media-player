package org.acoustixaudio.axmediaplayer.Providers;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.acoustixaudio.axmediaplayer.MainActivity;
import org.acoustixaudio.axmediaplayer.MediaFile;
import org.acoustixaudio.axmediaplayer.MediaProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ApacheIndex extends MediaProvider {
    public String TAG = getClass().getSimpleName();
    @Override
    public ArrayList<MediaFile> listFiles(Uri uri) {
        return null;
    }

    @Override
    public Uri dirname(Uri uri) {
        return null;
    }

    @Override
    public Uri basename(Uri uri) {
        return null;
    }

    public void loadAsync (MainActivity mainActivity, String url) {
        RequestQueue queue = Volley.newRequestQueue(mainActivity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d(getClass().getSimpleName(), "onResponse: " + response);
                        Document document = Jsoup.parse(response);
                        Elements elements = document.getElementsByTag("a");
                        mainActivity.adapter.clear();

                        for (Element a:
                             elements) {
                            if (a.text().isEmpty() || a.attribute("href").getKey().startsWith("?"))
                                continue;

                            Log.d(TAG, String.format ("%s: %s", a.text(), a.attribute("href")));
                            MediaFile mediaFile = new MediaFile();
                            mediaFile.title = a.text();
                            mediaFile.uri = Uri.parse(a.attribute("href").getKey());
                            // the following is not correct
                            mediaFile.isDir = a.attribute("href").getKey().endsWith("/\"");
                            mainActivity.adapter.add(mediaFile);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });

        queue.add(stringRequest);

    }
}
