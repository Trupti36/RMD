package com.example.rmd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class main extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton button;
    String Domains,phn,Sat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        Intent intent=getIntent();
        Domains = intent.getStringExtra("Dom");
        phn = intent.getStringExtra("phn");
        Sat = intent.getStringExtra("sat");
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        //bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        //button=findViewById(R.id.fab);
        Home h= new Home();
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        Bundle bundle= new Bundle();
        bundle.putString("phn",phn);
        bundle.putString("Domain",Domains);bundle.putString("status",Sat);
        h.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment,new Home()).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Home()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener listener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment= new Home();
                            break;
                        case R.id.account:
                            selectedFragment= new Account();
                            break;
                    }

                    Bundle bundle= new Bundle();
                    bundle.putString("phn",phn);
                    bundle.putString("Domain",Domains);
                    bundle.putString("status",Sat);
                    selectedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFragment).commit();
                    return  true;
                }
            };
}