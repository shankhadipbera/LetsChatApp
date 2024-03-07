package com.shankha.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.shankha.letschat.Adapter.FragmentAdapter;
import com.shankha.letschat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
       if(id==R.id.setting){
           Intent inten= new Intent(MainActivity.this, SettingActivity.class);
           startActivity(inten);
       }
       else if (id==R.id.logout) {
           auth.signOut();
           Intent intent= new Intent(MainActivity.this,SignInActivity.class);
           startActivity(intent);
       }else if (id==R.id.groupChat) {
           Intent intentt= new Intent(MainActivity.this, GroupChatActivity.class);
           startActivity(intentt);

       }
        return true;
    }
}