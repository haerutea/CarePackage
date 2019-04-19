package camelcase.technovation;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;

import camelcase.technovation.chat.activities.ConnectActivity;
import camelcase.technovation.chat.activities.ProfileActivity;
import camelcase.technovation.chat.activities.SavedMessagesActivity;
import camelcase.technovation.chat.activities.SettingsActivity;

//https://stackoverflow.com/questions/16144399/sidebar-in-each-activity
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.base_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.chat)
        {
            Intent intent = new Intent(getApplicationContext(), ConnectActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.saved)
        {
            Intent intent = new Intent(getApplicationContext(), SavedMessagesActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.mood)
        {
        }
        else if(id == R.id.todo)
        {
        }
        else if (id == R.id.settings)
        {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
