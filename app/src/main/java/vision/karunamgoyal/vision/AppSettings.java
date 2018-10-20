package vision.karunamgoyal.vision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class AppSettings extends AppCompatActivity {
    private String ausername;
    private String str="Message Checking";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        Intent intent=getIntent();
        ausername=intent.getStringExtra(str);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               sendmain();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void sendmain(){
        Intent in=new Intent(this,StudentActivity.class);
        in.putExtra(str,ausername);
        startActivity(in);
    }

}
