package com.gamzekorkmaz.instagramfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class myProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_profile,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.profili_Duzenle)
        {
            Intent intent = new Intent(getApplicationContext(), editProfileActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.profilim_cikis_yap)
        {
            mAuth.signOut();

            Intent intent = new Intent(getApplicationContext(), signInActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Başarıyla çıkış yaptınız!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }
}
