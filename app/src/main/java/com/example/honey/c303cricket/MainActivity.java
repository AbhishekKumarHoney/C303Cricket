package com.example.honey.c303cricket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }


    public void btnAddTeamClicked(View view) {
        Intent addMatchesIntent = new Intent(MainActivity.this, AddTeam.class);
        startActivity(addMatchesIntent);

    }

    public void btnViewTeamsClicked(View view) {
        Intent viewTeamsIntent = new Intent(MainActivity.this, ViewTeam.class);
        startActivity(viewTeamsIntent);
    }

    public void btnAddMatchClicked(View view) {
        Intent addMatchIntent = new Intent(MainActivity.this, AddMatches.class);
        startActivity(addMatchIntent);
    }

    public void btnViewMatchesClicked(View view) {
        Intent viewMatchesIntent = new Intent(MainActivity.this, ViewMatches.class);
        startActivity(viewMatchesIntent);
    }

    public void btndeleteTeamClicked(View view) {
        Intent deleteTeamIntent = new Intent(MainActivity.this, DeleteTeam.class);
        startActivity(deleteTeamIntent);
    }

    public void btndeleteMatchClicked(View view) {
        Intent deleteMatchIntent = new Intent(MainActivity.this, DeleteMatches.class);
        startActivity(deleteMatchIntent);
    }

    public void btnGODMODEClicked(View view) {
        Intent GODMODEIntent = new Intent(MainActivity.this, GODMODE.class);
        startActivity(GODMODEIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
