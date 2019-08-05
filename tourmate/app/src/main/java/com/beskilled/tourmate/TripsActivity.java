package com.beskilled.tourmate;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.beskilled.tourmate.databinding.ActivityTripsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class TripsActivity extends AppCompatActivity {

    private ActivityTripsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trips);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replecFragemt(new TripsFragment());

    }

    private void replecFragemt(Fragment fragment) {
        //FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.tripsFrameLayout, fragment);
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trips:
                    replecFragemt(new TripsFragment());
                    return true;
                case R.id.navigation_memorirics:

                    replecFragemt(new MemoriesFragment());
                    return true;
                case R.id.navigation_wallet:
                    replecFragemt(new WallerFragment());
                    return true;
            }
            return false;
        }
    };
}
