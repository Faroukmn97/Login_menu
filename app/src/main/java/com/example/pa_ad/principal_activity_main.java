package com.example.pa_ad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.io.UnsupportedEncodingException;

public class principal_activity_main extends AppCompatActivity {

Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity_main);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            Log.d("Response",bundle.getString("name"));
            final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
            NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
            menu = navigationView.getMenu();
            if(!bundle.getString("type").equals("Administrador")){
                MenuItem  visivel = menu.findItem(R.id.menuother);
                MenuItem  visivel2 = menu.findItem(R.id.menuOrdersAdm);
                visivel.setVisible(false);
                visivel2.setVisible(false);
            }
            View hView = navigationView.getHeaderView(0);
            ImageView foto = (ImageView) hView.findViewById(R.id.imageProfile);
            TextView Usuario = (TextView) hView.findViewById(R.id.viewNameUser);
            TextView RolUsuario = (TextView) hView.findViewById(R.id.viewRol);
            Usuario.setText(bundle.getString("name") + " " + bundle.getString("last_name"));
            RolUsuario.setText(bundle.getString("type"));
            Glide.with(this).load(bundle.getString("imguser").replace('\\', '/')).into(foto);

            findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
            // NavigationView navigationView = findViewById(R.id.navigationView);
            //AÑADIENDO ICONOS
            navigationView.setItemIconTintList(null);
            //AÑADIENDO LOS FRAGMENTOS
            NavController navcontrller = Navigation.findNavController(this,R.id.NavHostFragment);
            NavigationUI.setupWithNavController(navigationView,navcontrller);
        }else{
            Log.d("Response","Vacio");
        }
    }
}
