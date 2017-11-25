package com.nurerkizilkaya.news;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nurerkizilkaya.news.adapter.NewsAdapter;
import com.nurerkizilkaya.news.model.Haber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressDialog;
    public NewsAdapter adapter;
    @Bind(R.id.listRcyViewId)
    RecyclerView listRcyViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.VERTICAL);
        listRcyViewId.setLayoutManager(ll);

        new FetchTitle().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sondakikaId) {

        } else if (id == R.id.gundemId) {

        } else if (id == R.id.ekonomiId) {

        } else if (id == R.id.dunyaId) {

        } else if (id == R.id.magazinId) {

        } else if (id == R.id.teknolojiId) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class FetchTitle extends AsyncTask<Void, Void, List<Haber>> {
        private final String URL="http://www.milliyet.com.tr/rss/rssNew/SonDakikaRss.xml";
        String descrition;
        public List<String> data=new ArrayList<String>();
        public List<Haber> haberlist=new ArrayList<Haber>();
        public Haber haber;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Title");
            progressDialog.setMessage("Haberler geliyor");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }
        @Override
        protected List<Haber> doInBackground(Void... params) {
            try{
                Document doc  = Jsoup.connect(URL).get();    // web siteye bağlantıyı gerçeleştirme
                Elements elements = doc.select("item");

                for (int i = 0; i < elements.size(); i++) {
                    haber=new Haber();
                    data.add(elements.get(i).toString());

                    String deger=data.get(i).toString();
                    Elements title=elements.get(i).select("title");
                    haber.setTitle(title.toString());
                    Elements date=elements.get(i).select("pubDate");
                    haber.setDate(date.toString());
                    String bword = "<link />";
                    String sword = "<description>";
                    int bn = deger.indexOf(bword);
                    int sn= deger.indexOf(sword, bn);
                    String link=deger.substring(bn + 8, sn - 2);
                    haber.setLink(link.toString());

                    String bimage = "src=";
                    String simage = ".Jpeg";
                    int bi = deger.indexOf(bimage);
                    int si= deger.indexOf(simage, bi);
                    String image=deger.substring(bi+10, si+5);
                    haber.setImage(image.toString());

                    String bdesc = "t;p&gt;";
                    String sdesc = "</description>";
                    int bd = deger.indexOf(bdesc);
                    int sd= deger.indexOf(sdesc, bd);
                    String description=deger.substring(bd + bdesc.length(), sd);
                    haber.setSpot(description.toString());
                    haberlist.add(haber);
                }

            }catch (Exception e){

                e.printStackTrace();
            }
            return haberlist;
        }
        @Override
        protected void onPostExecute(List<Haber> result) {
           if(result!=null){
                adapter=new NewsAdapter(getApplicationContext(),result);
                listRcyViewId.setAdapter(adapter);
            }
            else{
               // adapter=new BurcAdapter( Arrays.asList("Rüya yorumunuz için farklı kelime yazınız..Veya internet ayarını açınız"));
               // grdListDreamResult.setAdapter(adapter);
            }

            progressDialog.dismiss();
        }
    }
}
