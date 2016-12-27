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

    private  FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
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
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        latt = getIntent().getStringExtra("txt_loc_lati");
        longg = getIntent().getStringExtra("txt_loc_longi");
        edtxtLatitu.setText(latt);
        edtxtLongitu.setText(longg);
        place = edtxtPlace.getText().toString().trim();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo(place,latt,longg);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String key = databaseReference.push().getKey();
                databaseReference.child(key).setValue(userInfo);
                Toast.makeText(Timeline.this, "Information Saved !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}