package org.acoustixaudio.axmediaplayer;

import android.graphics.Bitmap;
import android.net.Uri;
import java.util.Date;

public class MediaFile {
    enum Type {
        VIDEO,
        IMAGE,
        AUDIO,
        UNKNOWN
    }

    public String title ;
    public Uri uri ;
    public Bitmap thumb ;
    public String description ;
    public long size ;
    public Date modified ;
    public int rating ;
    public long duration ;
    public boolean favorite;
    public boolean isDir ;
    public Type type ;
}
