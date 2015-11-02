package me.otarola.imagesearch.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.otarola.imagesearch.adapters.ImageResultAdapter;
import me.otarola.imagesearch.dialogs.SettingsDialog;
import me.otarola.imagesearch.listeners.EndlessScrollListener;
import me.otarola.imagesearch.models.Filter;
import me.otarola.imagesearch.models.ImageResult;
import me.otarola.imagesearch.R;

public class SearchActivity extends AppCompatActivity {

    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultAdapter aImageResult;
    private String currentSearchQuery;

    //Filters
    private Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setupViews();

        imageResults = new ArrayList<ImageResult>();
        aImageResult = new ImageResultAdapter(this, R.layout.item_image_result ,imageResults);

        gvResults.setAdapter(aImageResult);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearchQuery = query;
                fetchResults(currentSearchQuery, true, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void setupViews() {
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);

        LayoutInflater layoutInflater = getLayoutInflater();


        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                ImageResult result;

                if(!isNetworkAvailable()){
                    Toast.makeText(SearchActivity.this, "Network unavailable :(", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                i = new Intent(SearchActivity.this, ImageDisplayActivity.class);

                result = imageResults.get(position);

                i.putExtra("url", result.fullUrl);

                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                fetchResults(currentSearchQuery, false, page * 8);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(filter == null) {
                filter = new Filter();
            }
//            FragmentManager fm = getSupportFragmentManager();
//            SettingsDialog settingsDialog = SettingsDialog.newInstance("Settings");
//            settingsDialog.show(fm, "fragment_settings");
            boolean wrapInScrollView = true;
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.settings_title)
                    .customView(R.layout.settings_dialog, wrapInScrollView)
                    .positiveText(R.string.ok)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            TextView etSite = (TextView) materialDialog.getView().findViewById(R.id.etSite);
                            Spinner spColor = (Spinner) materialDialog.getView().findViewById(R.id.spColor);
                            Spinner spSize = (Spinner) materialDialog.getView().findViewById(R.id.spSize);
                            Spinner spType = (Spinner) materialDialog.getView().findViewById(R.id.spType);

                            filter.selectedSite = etSite.getText().toString();
                            filter.selectedColor = spColor.getSelectedItemPosition();
                            filter.selectedType = spType.getSelectedItemPosition();
                            filter.selectedSize = spSize.getSelectedItemPosition();
                        }
                    })
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            MaterialDialog materialDialog = (MaterialDialog) dialog;
                            View view = materialDialog.getView();
                            if (filter.selectedSite != null) {
                                TextView etSite = (TextView) view.findViewById(R.id.etSite);
                                etSite.setText(filter.selectedSite);
                            }
                            if (filter.selectedColor != -1) {
                                Spinner spColor = (Spinner) view.findViewById(R.id.spColor);
                                spColor.setSelection(filter.selectedColor);
                            }
                            if (filter.selectedSize != -1) {
                                Spinner spSize = (Spinner) view.findViewById(R.id.spSize);
                                spSize.setSelection(filter.selectedSize);
                            }
                            if (filter.selectedType != -1) {
                                Spinner spType = (Spinner) view.findViewById(R.id.spType);
                                spType.setSelection(filter.selectedType);
                            }
                        }
                    })
                    .negativeText(R.string.cancel)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchResults(String query, boolean shouldClearResults,int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        String searchURl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+ "&rsz=8&start=" + String.valueOf(page) ;

        searchURl = Filter.applyFilters(searchURl, filter);

        Log.i("DEBUG|", searchURl);

        if(!isNetworkAvailable()){
            Toast.makeText(SearchActivity.this, "Network unavailable :(", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if(shouldClearResults) {
            aImageResult.clear();
        }

        client.get(searchURl, new JsonHttpResponseHandler(){
            JSONArray imageResultsJson;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(!response.isNull("responseData")) {
                        imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");

                        aImageResult.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    }
                    else{
                        Toast.makeText(SearchActivity.this, "No more results to display :(", Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }

        });
    }
}
