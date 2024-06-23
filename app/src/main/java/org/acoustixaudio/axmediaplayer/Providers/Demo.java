package org.acoustixaudio.axmediaplayer.Providers;

import android.net.Uri;

import org.acoustixaudio.axmediaplayer.MediaFile;
import org.acoustixaudio.axmediaplayer.MediaProvider;

import java.util.ArrayList;

public class Demo extends MediaProvider {
    @Override
    public ArrayList<MediaFile> listFiles(Uri uri) {
        ArrayList <MediaFile> arrayList = new ArrayList<>();

        for (int i = 0 ; i < 14 ; i ++) {
            MediaFile mediaFile = new MediaFile();
            mediaFile.title = "Demo " + i;
            mediaFile.isDir = false;
            arrayList.add(mediaFile);
        }

        return arrayList ;
    }

    @Override
    public Uri dirname(Uri uri) {
        return null;
    }

    @Override
    public Uri basename(Uri uri) {
        return null;
    }
}
