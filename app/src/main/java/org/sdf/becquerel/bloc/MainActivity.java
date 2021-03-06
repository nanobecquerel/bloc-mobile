package org.sdf.becquerel.bloc;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final Map<Integer, Integer> idMap;
    static {
        //Map IDs in the pullout menu to IDs of the fragments/pages
        idMap = new HashMap<Integer, Integer>();
        idMap.put(R.id.nav_domestic, R.layout.domestic);
        idMap.put(R.id.nav_login, R.layout.login);
        idMap.put(R.id.nav_home, R.layout.nation);
        idMap.put(R.id.nav_military, R.layout.military);
        idMap.put(R.id.nav_comms, R.layout.comms);
        idMap.put(R.id.nav_commsout, R.layout.comms);
        idMap.put(R.id.nav_market, R.layout.market);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("dummy", "main onCreate()");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ////Stuff to clear out old preferences if needed
        //android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        //android.content.SharedPreferences.Editor editor = preferences.edit();
        //editor.clear();
        //editor.apply();
        android.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        //Start on login page
        Fragment fragment = BlocFragment.fragmentChooser(R.layout.login, 0);
        getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("dummy", "main onStart()");

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
            Intent myIntent = new Intent(this, SettingsActivity.class);
            //myIntent.putExtra("key", value);
            this.startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    public static void goToView(int navID, FragmentManager mgr) {
        // pass the page ID to the general-purpose fragment constructor so we get the right page
        int fragmentID = idMap.get(navID);
        Fragment fragment = BlocFragment.fragmentChooser(fragmentID, navID);
        if (fragment != null) {
            mgr.beginTransaction().replace(R.id.frame_container, fragment).commit();
        } else {
            // error in creating fragment
            //Log.e("MainActivity", "Error in creating fragment");
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (idMap.containsKey(id)) {
            //given the menu ID, get the corresponding fragment
            goToView(id, getFragmentManager());
            setTitle(item.getTitle());
            //menu.setItemChecked(position, true);
            //menu.setSelection(position);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateBottomBar(String text) {
        TextView bar = (TextView) findViewById(R.id.bottom_bar);
        bar.setText(text);
        int layoutHeight = bar.getHeight();

        //set layout margin here
        //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //lp.topMargin = layoutHeight;
        //lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        //lp.setMargins(layoutHeight, 0, 0, 0);
        //bar.setLayoutParams(lp);
    }

}