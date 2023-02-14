package com.afundacion.gestorfinanzas.Screens;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.Menu;
import android.widget.Toast;

import com.afundacion.gestorfinanzas.R;

import com.afundacion.gestorfinanzas.graph.GraphDataFragment;
import com.afundacion.gestorfinanzas.rviewclass.RviewTransFragment;
<<<<<<< HEAD
=======
import com.afundacion.gestorfinanzas.graphline.GraphDataFrag;
>>>>>>> main
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class Navigation_Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Context context=this;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        
        menu=navigationView.getMenu();
        navigationView.getHeaderView(0);
        navigationView.bringToFront();


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId()){
            case R.id.nav_home:
                drawerLayout.close();
                Fragment fragmentHome = new HomeFragment();
               getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentHome).commit();
                //Toast.makeText(context, "Maracana", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_estadisticas:
                drawerLayout.close();
                Fragment fragmentEstadisticas = new GraphDataFrag();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentEstadisticas).commit();
                //Toast.makeText(context, "Maracana", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_historial:
                drawerLayout.close();
                Fragment fragmentHistorial = new MovementFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentHistorial).commit();
                //Toast.makeText(context, "Maracana", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_anadir:
                drawerLayout.close();
                Fragment fragmentAnadir = new TransictionFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentAnadir).commit();
                //Toast.makeText(context, "Maracana", Toast.LENGTH_LONG).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

}