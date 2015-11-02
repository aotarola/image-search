package me.otarola.imagesearch.activities;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import me.otarola.imagesearch.R;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        String url = getIntent().getStringExtra("url");
        final TouchImageView ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
        final TextView tvLoading = (TextView) findViewById(R.id.tvLoading);

        Picasso.with(this).load(url).into(ivImageResult, new Callback() {

            @Override
            public void onSuccess() {
                ivImageResult.setVisibility(View.VISIBLE);
                tvLoading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                ivImageResult.setVisibility(View.GONE);
                tvLoading.setVisibility(View.VISIBLE);
            }

        });
    }

}
