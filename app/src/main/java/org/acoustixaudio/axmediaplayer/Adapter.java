package org.acoustixaudio.axmediaplayer;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class Adapter extends RecyclerView.Adapter <Adapter.ViewHolder> {
    int totalItems = 0;
    String TAG = this.getClass().getSimpleName();
    ArrayList<ViewHolder> holders = new ArrayList<>();
    ArrayList<MediaFile> mediaFiles = new ArrayList<>();
    MainActivity mainActivity ;

    public Adapter (MainActivity _MainActivity) {
        mainActivity = _MainActivity ;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        totalItems++;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        holders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        MediaFile mediaFile = mediaFiles.get(position);

        if (! mediaFile.isDir)
            holder.imageView.setImageDrawable(new BitmapDrawable(String.valueOf(mediaFiles.get(position).thumb)));
        else
            holder.imageView.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.baseline_folder_24)));

        holder.textView.setText(mediaFiles.get(position).title);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainActivity, mediaFiles.get(holder.getAdapterPosition()).title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView textView ;
        LinearLayout linearLayout ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            imageView = linearLayout.findViewById(R.id.image);
            textView = linearLayout.findViewById(R.id.title);
        }
    }

    public void add (MediaFile mediaFile) {
        mediaFiles.add(mediaFile);
        notifyItemInserted(mediaFiles.size());
    }

    public void remove (int pos) {
        mediaFiles.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clear () {
        mediaFiles.clear();
        notifyDataSetChanged();
    }
}
