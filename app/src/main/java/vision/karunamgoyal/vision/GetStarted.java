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

public class GetStarted extends AppCompatActivity {

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
    DatabaseReference mdatabadereference;
    private Button button;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        Intent in = getIntent();
        ausername = in.getStringExtra(str);
        button=findViewById(R.id.button11);
        mdatabadereference=FirebaseDatabase.getInstance().getReference().child("interest").child(ausername);
        mdatabadereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    System.out.println("Hello");
                    sendtoHomepage();
                }
                else{
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Biology=findViewById(R.id.checkBox3);
                            Mathematics=findViewById(R.id.checkBox);
                            Technology=findViewById(R.id.checkBox7);
                            ComputerScience=findViewById(R.id.checkBox12);
                            GeneralKnowledge=findViewById(R.id.checkBox5);
                            Economics=findViewById(R.id.checkBox9);
                            Cricket=findViewById(R.id.checkBox11);
                            FootBall=findViewById(R.id.checkBox2);
                            Badminton=findViewById(R.id.checkBox4);
                            Geography=findViewById(R.id.checkBox10);
                            Politics=findViewById(R.id.checkBox8);
                            Accounts=findViewById(R.id.checkBox6);
                            Interest interest=new Interest(Mathematics.isChecked(),Biology.isChecked(),GeneralKnowledge.isChecked(),Economics.isChecked(),Technology.isChecked(),ComputerScience.isChecked(),
                                    Cricket.isChecked(),FootBall.isChecked(),Badminton.isChecked(),Geography.isChecked(),Politics.isChecked(),Accounts.isChecked());
                            Log.v("checkingtheerror","78");
                            mdatabadereference.setValue(interest);
                            Log.v("checkingtheerror","78");

                            sendtoHomepage();

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void sendtoHomepage(){
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("getstarted",true);
        editor.commit();
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra(str, ausername);
        startActivity(intent);
        finish();
    }
}
