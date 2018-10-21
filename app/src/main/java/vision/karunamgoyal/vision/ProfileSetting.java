package vision.karunamgoyal.vision;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileSetting extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button b3;
    private TextView name;
    private TextView mail;
    private TextView phno;
    private TextView userType;
    private String str = "Message Checking";
    private String ausername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent in = getIntent();
        ausername = in.getStringExtra(str);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        try {
            name = (TextView) findViewById(R.id.name1);
            Log.v("Checkingerror", "53");

            mail = (TextView) findViewById(R.id.email1);
            phno = (TextView) findViewById(R.id.phnumber1);
            final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("registeradminuser").child(ausername);

            reference1.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        RegisterAdminUser user = dataSnapshot.getValue(RegisterAdminUser.class);
                        name.setText(user.getName());
                        mail.setText(user.getMail());
                        phno.setText(user.getPhno());

                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });


            b3 = (Button) findViewById(R.id.button4);
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name1 = name.getText().toString();
                    final String mail1 = mail.getText().toString();
                    final String phno1 = phno.getText().toString();

                    /*RegisterUser ruser = new RegisterUser(name1, mail1, phno1, rollno1, roomno1, hno1);
                    reference1.setValue(ruser);
                    Snackbar.make(v, "Profile Updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    // reference1.child(ausername).setValue();*/
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AppSettings.class);
            intent.putExtra(str, ausername);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = pref.edit();
            String userType = pref.getString("userType", "");
            Intent in;
            if (userType.equals("Student"))
                in = new Intent(this, StudentActivity.class);
            else
                in = new Intent(this, CounsellorActivity.class);
            in.putExtra(str, ausername);
            startActivity(in);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, CounsellorProfile.class);
            intent.putExtra(str, ausername);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra(str, ausername);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            final SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_discuss) {
            Intent intent = new Intent(this, DiscussActivity.class);
            intent.putExtra(str, ausername);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
