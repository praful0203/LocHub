package com.praful.lochub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Timeline extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;
    public String place;
    public String latt, longg;
    public Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        EditText edtxtLatitu = (EditText) findViewById(R.id.edtxtLatitu);
        EditText edtxtLongitu = (EditText) findViewById(R.id.edtxtLongitu);
        EditText edtxtPlace = (EditText) findViewById(R.id.edtxtPlace);
        btnSave = (Button) findViewById(R.id.btnSave);

        place = edtxtPlace.getText().toString();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        latt = getIntent().getStringExtra("txt_loc_lati");
        longg = getIntent().getStringExtra("txt_loc_longi");
        edtxtLatitu.setText(latt);
        edtxtLongitu.setText(longg);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveUserInformation();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
            }
        });

    }

    public void saveUserInformation() {
        String loc = place;
        String laati = latt;
        String loongi = longg;

        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserInfo userInfo = new UserInfo(loc, laati, loongi);
        databaseReference.child(user.getUid()).setValue(userInfo);
        Toast.makeText(this, "Place is Saved Successfully...!", Toast.LENGTH_SHORT).show();
    }
}