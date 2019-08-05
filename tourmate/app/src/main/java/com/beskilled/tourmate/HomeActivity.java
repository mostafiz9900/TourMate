package com.beskilled.tourmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.beskilled.tourmate.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);

       binding.backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
			   Toast.makeText(getApplicationContext(), "text code", Toast.LENGTH_SHORT).show();
           }
       });

    }
}
