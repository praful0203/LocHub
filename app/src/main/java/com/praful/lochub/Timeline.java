package com.praful.lochub;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
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
    public EditText edtxtLatitu,edtxtLongitu;
    public Button btnSave;
    public EditText edTxtPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        SpannableString spannableString = new SpannableString(actionBar.getTitle());
        spannableString.setSpan(new mytypeface("", Typeface.createFromAsset(getAssets(), "lochub.ttf")), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableString);

        edtxtLatitu = (EditText) findViewById(R.id.edtxtLatitu);
        edtxtLongitu = (EditText) findViewById(R.id.edtxtLongitu);
        edTxtPlace = (EditText)findViewById(R.id.edTxtPlace);
        btnSave = (Button) findViewById(R.id.btnSave);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        latt = getIntent().getStringExtra("txt_loc_lati");
        longg = getIntent().getStringExtra("txt_loc_longi");
        edtxtLatitu.setText(latt);
        edtxtLongitu.setText(longg);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = firebaseAuth.getCurrentUser().getUid();
                String key = databaseReference.push().getKey();
                place = edTxtPlace.getText().toString().trim();
                UserInfo userInfo = new UserInfo(place,latt,longg);
                FirebaseUser user = firebaseAuth.getCurrentUser();

                databaseReference.child(uid).child(key).setValue(userInfo);
                Toast.makeText(Timeline.this, "Information Saved !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}