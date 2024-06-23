package org.acoustixaudio.axmediaplayer;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.acoustixaudio.axmediaplayer.Providers.Demo;
import org.acoustixaudio.axmediaplayer.Providers.Local;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    Context context ;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this ;
        recyclerView = findViewById(R.id.recycler);

        int numberOfColumns = 3 ;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);

        Local local = new Local(this);
        for (MediaFile m :
                local.listFiles(null)) {
            adapter.add(m);
        }
    }
}