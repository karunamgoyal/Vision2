package vision.karunamgoyal.vision;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CounsellorProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static String ausername;
    private String str = "Message Checking";
    public static final String MESSAGES_CHILD = "registeradminuser";
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<RegisterAdminUser, InstituteFragment.MessageViewHolder>
            mFirebaseAdapter,myadapter;
    private DatabaseReference myFirebaseDatabaseReference;
    private Interest interest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_profile);
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

        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView1);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        SnapshotParser<RegisterAdminUser> parser = new SnapshotParser<RegisterAdminUser>() {
            @Override
            public RegisterAdminUser parseSnapshot(DataSnapshot dataSnapshot) {
                RegisterAdminUser friendlyMessage = dataSnapshot.getValue(RegisterAdminUser.class);
                if (friendlyMessage != null&&friendlyMessage.getUserType().equals("Counsellor")) {
                    Log.v("Checkingmsg", "15");
                    friendlyMessage.setId(dataSnapshot.getKey());
                }
                return friendlyMessage;
            }
        };
        myFirebaseDatabaseReference=FirebaseDatabase.getInstance().getReference().child("interests").child(ausername);
        myFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                interest =dataSnapshot.getValue(Interest.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.v("Checkingmsg", "12");
        DatabaseReference messagesRef = mFirebaseDatabaseReference.child(MESSAGES_CHILD);
        FirebaseRecyclerOptions<RegisterAdminUser> options =
                new FirebaseRecyclerOptions.Builder<RegisterAdminUser>()
                        .setQuery(messagesRef, parser)
                        .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<RegisterAdminUser, InstituteFragment.MessageViewHolder>(options) {
            @Override
            public InstituteFragment.MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                Log.v("Checkingmsg", "15");
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                Log.v("Checkingmsg", "15");
                View v1=inflater.inflate(R.layout.item_card, viewGroup, false);
                Log.v("Checkingmsg", "16");
                return new InstituteFragment.MessageViewHolder(v1);
            }

            @Override
            protected void onBindViewHolder(final InstituteFragment.MessageViewHolder viewHolder,
                                            int position,
                                            final RegisterAdminUser friendlyMessage) {



                if (friendlyMessage.getName() != null) {
                    Log.v("Checkingmsg", "17");
                    if(friendlyMessage.getUserType().equals("Counsellor")){

                        Log.v("itsbeenahell","hello");

                        // viewHolder.imageview.setImageResource(R.drawable.notice);
                        viewHolder.examNameView.setText(friendlyMessage.getName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getUserType());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getPhno());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url=friendlyMessage.getPhno();
                                    if (!url.startsWith("tel:"))
                                        url = "tel:" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_DIAL);
                                    myIntent.setData(Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {

                                    e.printStackTrace();
                                }
                            }
                        });

                    }else{
                        viewHolder.cardview.setVisibility(CardView.GONE);

                    }





                }




            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                Log.v("Checkingmsg", "16");
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

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
        getMenuInflater().inflate(R.menu.counsellor_profile, menu);
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
            Intent intent =new Intent(this,AppSettings.class);
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
            Intent intent =new Intent(this,StudentActivity.class);
            intent.putExtra(str, ausername);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_slideshow) {

            Intent intent =new Intent(this,ProfileSetting.class);
            intent.putExtra(str, ausername);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            final SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);

        }
        else if(id==R.id.nav_discuss){
            Intent intent =new Intent(this,DiscussActivity.class);
            intent.putExtra(str, ausername);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView examNameView;
        CardView cardview;
        TextView conducterView;
        TextView examUrl;

        ImageView imageview;

        public MessageViewHolder(View v) {
            super(v);
            examNameView= (TextView) itemView.findViewById(R.id.cardtext1);

            cardview = (CardView) itemView.findViewById(R.id.cardview12);
            conducterView = (TextView) itemView.findViewById(R.id.cardtext2);
            examUrl=itemView.findViewById(R.id.textView3);
        }
    }
    public void putViewHolder(final InstituteFragment.MessageViewHolder viewHolder,
                              int position,
                              final RegisterAdminUser friendlyMessage){

    }
    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

}
