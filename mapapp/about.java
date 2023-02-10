package com.example.mapapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class about extends AppCompatActivity {

    android.widget.Button bttngithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        bttngithub=findViewById(R.id.bgithub);

        bttngithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLink("https://github.com/meaaww1317/MapApp");
            }
        });
    }

    private void goLink(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int item_id=item.getItemId();

        if (item_id==R.id.rumah) {
            Intent intent = new Intent(about.this, MainActivity.class);
            startActivity(intent);
        }
        else if (item_id==R.id.maps) {
            Intent intent = new Intent(about.this, MapsActivity.class);
            startActivity(intent);
        }
        else if (item_id==R.id.news) {
            Intent intent = new Intent(about.this, news.class);
            startActivity(intent);
        }
        else if (item_id==R.id.about) {
            Intent intent = new Intent(about.this, about.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        Intent intent = new Intent(about.this, MainActivity.class);
        startActivity(intent);
    }

}