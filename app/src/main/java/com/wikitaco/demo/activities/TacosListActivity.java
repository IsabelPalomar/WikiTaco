package com.wikitaco.demo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wikitaco.demo.App;
import com.wikitaco.demo.R;
import com.wikitaco.demo.models.Taco;

public class TacosListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private App app;
    private RecyclerView rvTacos;
    private RecyclerView.LayoutManager mLayoutManager;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacos_list);

        app = (App) getApplicationContext();

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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        rvTacos = (RecyclerView) findViewById(R.id.rvTacos);

        if (rvTacos != null) {
            rvTacos.setHasFixedSize(true);
        }

        mLayoutManager = new LinearLayoutManager(this);
        rvTacos.setLayoutManager(mLayoutManager);

        FirebaseRecyclerAdapter<Taco,TacoViewHolder> adapter = new FirebaseRecyclerAdapter<Taco, TacoViewHolder>(
                Taco.class,
                R.layout.item_taco,
                TacoViewHolder.class,
                mDatabaseReference.child("tacos").getRef()
        ) {
            @Override
            protected void populateViewHolder(TacoViewHolder viewHolder, Taco model, int position) {
                viewHolder.tvTacoName.setText(model.getName());
                Picasso.with(TacosListActivity.this).load(model.getImageUrl()).into(viewHolder.ivTacoImg);
            }
        };

        rvTacos.setAdapter(adapter);


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
        getMenuInflater().inflate(R.menu.tacos_list, menu);
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

        if (id == R.id.nav_signout) {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences pref = getSharedPreferences(app.getSharedPrefsName(), MODE_PRIVATE);
                        pref.edit().remove("provider").commit();
                        pref.edit().remove("email").commit();
                        pref.edit().remove("name").commit();

                        Intent i = new Intent(TacosListActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class TacoViewHolder extends RecyclerView.ViewHolder{

        TextView tvTacoName;
        ImageView ivTacoImg;

        public TacoViewHolder(View v) {
            super(v);
            tvTacoName = (TextView) v.findViewById(R.id.tvTacoName);
            ivTacoImg = (ImageView) v.findViewById(R.id.ivTacoImg);
        }
    }


}
