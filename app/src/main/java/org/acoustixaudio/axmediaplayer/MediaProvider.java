package org.acoustixaudio.axmediaplayer;

import android.net.Uri;

import java.util.ArrayList;

public abstract class MediaProvider {
    public abstract ArrayList<MediaFile> listFiles(Uri uri);
    public abstract Uri dirname (Uri uri);
    public abstract Uri basename (Uri uri);
}
