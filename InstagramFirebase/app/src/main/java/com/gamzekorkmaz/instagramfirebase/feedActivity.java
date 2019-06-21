package com.gamzekorkmaz.instagramfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class feedActivity extends AppCompatActivity {

    ListView listView;
    PostClass postClass; //bu class adapter görevinde
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailFromFB;
    ArrayList<String> userimageFromFB;
    ArrayList<String> usercommentFromFB;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    //db den gelecek olan veriler bunlara atılacak

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_post)
        {
            Intent intent = new Intent(getApplicationContext(), uploadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.sign_out)
        {
            mAuth.signOut();

            Intent intent = new Intent(getApplicationContext(), signInActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Başarıyla çıkış yaptınız!", Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.my_profile)
        {
            Intent intent = new Intent(getApplicationContext(), myProfileActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        listView = findViewById(R.id.listView);

        useremailFromFB = new ArrayList<String>();
        usercommentFromFB = new ArrayList<String>();
        userimageFromFB = new ArrayList<String>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        postClass = new PostClass(useremailFromFB, usercommentFromFB, userimageFromFB, this);

        listView.setAdapter(postClass); //post class sayesinde listview dolacak

        getDataFromFirebase();
    }

    public void getDataFromFirebase() {

        DatabaseReference newReference = firebaseDatabase.getReference("Posts");
        newReference.addValueEventListener(new ValueEventListener() { //bir şeyin değeri değiştiginde işlem yapacak
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //System.out.println("FBV children: " + dataSnapshot.getChildren() ); --> root un altındaki verileri getirir
                //System.out.println("FBV key: " + dataSnapshot.getKey() ); --> root u getirir
                //System.out.println("FBV value: " + dataSnapshot.getValue() ); --> tüm verileri json formatında verir
                //System.out.println("FBV priority: " + dataSnapshot.getPriority() );

                for (DataSnapshot ds : dataSnapshot.getChildren()) { //bütün child lar içerisinde bir loop elde edilir ve tüm verileri hashmap formatında verir

                    //System.out.println("FBV ds value: " + ds.getValue());

                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();

                    //System.out.println("FBV useremail:" + hashMap.get("useremail"));

                    useremailFromFB.add(hashMap.get("useremail"));
                    usercommentFromFB.add(hashMap.get("comment"));
                    userimageFromFB.add(hashMap.get("downloadurl"));
                    postClass.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
