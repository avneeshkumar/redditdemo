package com.atarashi.picreddit.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.atarashi.picreddit.R;
import com.atarashi.picreddit.utils.DAOS;
import com.atarashi.picreddit.utils.OfflineGson;
import com.atarashi.picreddit.utils.RedditService;
import com.atarashi.picreddit.utils.Utils;
import com.atarashi.picreddit.utils.redditjsonclass.Child;
import com.atarashi.picreddit.utils.redditjsonclass.Image;
import com.atarashi.picreddit.utils.redditjsonclass.Subredditpost;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    Retrofit retrofit;
    RedditService redditservice;
    ListView listview;
    List<Child> listforlistview;
    StableArrayAdapter adapter;
    String subredditname;
    String lastpost;
    Gson gson;
    private AdView mAdView;
    int mLastFirstVisibleItem=1000;
    private Realm realm;
    DAOS daos;
    // CAST THE LINEARLAYOUT HOLDING THE MAIN PROGRESS (SPINNER)
    LinearLayout linlaHeaderProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Realm.init(this);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Fresco.initialize(getApplicationContext());
        realm = Realm.getDefaultInstance();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        subredditname = "popular";
        getSupportActionBar().setTitle(subredditname);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        gson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .create();



        retrofit = new Retrofit.Builder().baseUrl("https://www.reddit.com/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        redditservice = retrofit.create(RedditService.class);
        listview = (ListView) findViewById(R.id.mainlistview);
        mAdView = (AdView) findViewById(R.id.adView);
        listforlistview = new ArrayList<Child>();
        adapter = new StableArrayAdapter(getBaseContext(),listforlistview);
        listview.setAdapter(adapter);
        daos = new DAOS(realm);
        if(Utils.isNetworkConnected(this)){
            makerequest(false);
        }
        else{

            listforlistview.clear();
            ArrayList<Child> temp =  daos.getArray(subredditname,gson);
            if(temp!=null){
                for (Child child : temp) {
                    listforlistview.add(child);
                }
            }

            adapter.notifyDataSetChanged();
        }
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listview.getLastVisiblePosition() - listview.getHeaderViewsCount() -
                        listview.getFooterViewsCount()) >= (adapter.getCount() - 1)) {


                        if(Utils.isNetworkConnected(getApplicationContext())){
                            makerequest(true);
                        }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /**
                 * Hide actionbar when scroll down
                 */
                if (mLastFirstVisibleItem < firstVisibleItem)
                    if (getSupportActionBar().isShowing())
                        getSupportActionBar().hide();

                if (mLastFirstVisibleItem > firstVisibleItem)
                    if (!getSupportActionBar().isShowing())
                        getSupportActionBar().show();
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });




    }
    @Override
    public void  onResume(){
        super.onResume();
        MobileAds.initialize(this, "ca-app-pub-9408156079023384~9468564956");
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A87A77F12C94EB9648BD7C69679A4EE").build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.popular) {
            subredditname = "popular";
            makerequest(false);
        }
        if (id == R.id.alternativeart) {
            subredditname = "alternativeart";
            makerequest(false);
        }
        /*if (id == R.id.random) {
            subredditname = "random";
            makerequest(false);
        }*/
        else if (id == R.id.pics) {
            subredditname = "pics";

        } /*else if (id == R.id.gifs) {
            subredditname = "gifs";

        }*/ else if (id == R.id.adviceanimals) {
            subredditname = "adviceanimals";

        } else if (id == R.id.cats) {
            subredditname = "cats";

        } else if (id == R.id.images) {
            subredditname = "images";

        }
        else if (id == R.id.photoshopbattles) {
            subredditname = "photoshopbattles";

        }
        else if (id == R.id.all) {
            subredditname = "all";

        }
        else if (id == R.id.aww) {
            subredditname = "aww";

        }
        getSupportActionBar().setTitle(subredditname);
        if(Utils.isNetworkConnected(this)){
            makerequest(false);
        }
        else{

            listforlistview.clear();
            ArrayList<Child> temp =  daos.getArray(subredditname,gson);
            if(temp!=null){
                for (Child child : temp) {
                    listforlistview.add(child);
                }
            }

            adapter.notifyDataSetChanged();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void makerequest(final boolean isMore){

        Call<Subredditpost > repos;
        if(isMore){
            repos = redditservice.listnextPost(subredditname,lastpost);
        }
        else{
            linlaHeaderProgress.setVisibility(View.VISIBLE);
            repos = redditservice.listPost(subredditname);
        }

        repos.enqueue(new Callback<Subredditpost>() {
            @Override
            public void onResponse(Call<Subredditpost> call, Response<Subredditpost> response) {
                int statusCode = response.code();
                linlaHeaderProgress.setVisibility(View.GONE);
                Subredditpost user = response.body();
                //Log.d("Reddit","hahahaha" +user.getData().getChildren().get(0).getData().getTitle());
                if(!isMore){
                    listforlistview.clear();
                    for (Child child : user.getData().getChildren())
                    {
                        if(child.getData().getPreview()!=null){
                            for (Image allimage:child.getData().getPreview().getImages()) {

                                if(!allimage.getSource().getUrl().contains(".gif")){

                                    //i.putExtra("imageurl", allimage.getSource().getUrl());
                                    //context.startActivity(i);
                                    listforlistview.add(child);
                                    break;
                                }
                            }
                        }
                    }
                    //listforlistview.add(new Child());
                    lastpost=user.getData().getAfter();

                    adapter.notifyDataSetChanged();

                }
                else{
                    for (Child child : user.getData().getChildren()) {
                        if(child.getData().getPreview()!=null){
                            for (Image allimage:child.getData().getPreview().getImages()) {

                                if(!allimage.getSource().getUrl().contains(".gif")){

                                    //i.putExtra("imageurl", allimage.getSource().getUrl());
                                    //context.startActivity(i);
                                    listforlistview.add(child);
                                    break;
                                }
                            }
                        }


                    }
                    //listforlistview.add(new Child());
                    lastpost=user.getData().getAfter();
                    adapter.notifyDataSetChanged();
                }
                final String user_gson = gson.toJson(listforlistview);
                daos.insert(user_gson,subredditname);





            }

            @Override
            public void onFailure(Call<Subredditpost> call, Throwable t) {
                // Log error here since request failed
                Log.e("Retrofit",t.getMessage());
            }
        });
    }

    private static final TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }
        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(in.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };
}
