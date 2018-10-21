package vision.karunamgoyal.vision;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetStarted2 extends AppCompatActivity {
    private String ausername;

    private String str = "Message Checking";
    private CheckBox Mathematics;
    CheckBox Biology;
    CheckBox GeneralKnowledge;
    CheckBox Economics;
    CheckBox Technology;
    CheckBox ComputerScience;
    CheckBox Cricket;
    CheckBox FootBall;
    CheckBox Badminton;
    CheckBox Geography;
    CheckBox Politics;
    CheckBox Accounts;
    DatabaseReference mdatabadereference,mydatabadereference;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started2);
        Intent in = getIntent();
        ausername = in.getStringExtra(str);
        System.out.print(ausername);
        Log.v("checktheerror","41");

        button=findViewById(R.id.button11);
        mdatabadereference=FirebaseDatabase.getInstance().getReference().child("interest").child(ausername);
        mdatabadereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    sendtoHomepage();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mydatabadereference=FirebaseDatabase.getInstance().getReference().child("interest");
        Log.v("checktheerror","59");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Biology=findViewById(R.id.bio);
                Mathematics=findViewById(R.id.maths);
                Technology=findViewById(R.id.tech);
                ComputerScience=findViewById(R.id.cs);
                GeneralKnowledge=findViewById(R.id.gk);
                Economics=findViewById(R.id.eco);
                Cricket=findViewById(R.id.cric);
                FootBall=findViewById(R.id.foot);
                Badminton=findViewById(R.id.bad);
                Geography=findViewById(R.id.geo);
                Politics=findViewById(R.id.pol);
                Accounts=findViewById(R.id.acc);
                Log.v("checktheerror","75");
                Interest interest1=new Interest(Mathematics.isChecked(),Biology.isChecked(),GeneralKnowledge.isChecked(),Economics.isChecked(),Technology.isChecked(),ComputerScience.isChecked(),
                        Cricket.isChecked(),FootBall.isChecked(),Badminton.isChecked(),Geography.isChecked(),Politics.isChecked(),Accounts.isChecked());
                Log.v("checktheerror","78");

                mydatabadereference.child(ausername).setValue(interest1);
                Log.v("checktheerror","80");
                sendtoHomepage();
                Log.v("checktheerror","82");
            }
        });


    }

    public void sendtoHomepage(){
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("getstarted2",true);
        editor.commit();
        Intent intent = new Intent(this, CounsellorActivity.class);
        intent.putExtra(str, ausername);
        startActivity(intent);
        finish();
    }
}

