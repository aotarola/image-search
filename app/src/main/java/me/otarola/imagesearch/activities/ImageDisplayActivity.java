package me.otarola.imagesearch.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import me.otarola.imagesearch.R;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        String url = getIntent().getStringExtra("url");
        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);

        Picasso.with(this).load(url).into(ivImageResult);
    }

}
