package org.acoustixaudio.axmediaplayer.Providers;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import org.acoustixaudio.axmediaplayer.MainActivity;
import org.acoustixaudio.axmediaplayer.MediaFile;
import org.acoustixaudio.axmediaplayer.MediaProvider;

import java.io.IOException;
import java.util.ArrayList;

public class Local extends MediaProvider {
    private final Uri collection;
    private final MainActivity mainActivity;
    String TAG = getClass().getCanonicalName();
    String[] projection = new String[] {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.DESCRIPTION,
            MediaStore.Video.Media.MIME_TYPE
    };

    String sortOrder = MediaStore.Video.Media.DATE_MODIFIED + " DESC";
    String selection = MediaStore.Video.Media.DURATION +
            " >= ?";
    String[] selectionArgs = new String[] {};

    public Local(MainActivity _mainActivity) {
        super();
        mainActivity = _mainActivity;
        collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
    }

    @Override
    public ArrayList<MediaFile> listFiles(Uri uri) {
        ArrayList <MediaFile> mediaFiles = new ArrayList<>();
        try (Cursor cursor = mainActivity.getContentResolver().query(
                collection,
                projection,
                selection,
                null,
                sortOrder
        )) {
            if (cursor == null)
                Toast.makeText(mainActivity, "null cursor", Toast.LENGTH_SHORT).show();
            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                long id = cursor.getLong(idColumn);
                Log.d(TAG, "listFiles: id " + id);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

                MediaFile mediaFile = new MediaFile();
                mediaFile.uri = contentUri ;
                mediaFile.isDir = false ;
                mediaFile.thumb = mainActivity.getContentResolver().loadThumbnail(
                        contentUri, new Size(640, 480), null);
                mediaFiles.add(mediaFile);
            }
        } catch (IOException e) {
            Toast.makeText(mainActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Local: cannot load thumbnail", e);
        }

        return mediaFiles;
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
