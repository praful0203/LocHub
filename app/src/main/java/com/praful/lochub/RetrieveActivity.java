package com.praful.lochub;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class RetrieveActivity extends AppCompatActivity {

    private String userPlace,p_latitude,p_longitude;
    private TextView showDataTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        SpannableString spannableString = new SpannableString(actionBar.getTitle());
        spannableString.setSpan(new mytypeface("", Typeface.createFromAsset(getAssets(), "lochub.ttf")), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableString);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final String userid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users").child(userid);

        showDataTxt = (TextView)findViewById(R.id.showDataTxt);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.e("Count :", " " + dataSnapshot.getChildrenCount());
                for (DataSnapshot locdata : children) {
                    UserInfo userInfo = locdata.getValue(UserInfo.class);
                    Map<String , String> map = (Map) locdata.getValue();
                    Log.i("inside","onDatachanged : "+map);

                    userPlace = userInfo.getPlace();
                    p_latitude = userInfo.getLatitude();
                    p_longitude = userInfo.getLongitude();

                    Log.d("Place :", "" +userPlace);
                    Log.d("Latitude :", "" +p_latitude);
                    Log.d("Longitude :", "" +p_longitude);
                    showData(userPlace,p_latitude,p_longitude);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(RetrieveActivity.this, "Server Side Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void showData(String userPlace, String p_latitude, String p_longitude) {
        showDataTxt.setText(showDataTxt.getText().toString()+userPlace+p_latitude+p_longitude+"\n\t");
    }

}