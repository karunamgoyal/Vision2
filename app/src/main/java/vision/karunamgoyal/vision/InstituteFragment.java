package vision.karunamgoyal.vision;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import java.security.SecureRandom;


public class InstituteFragment extends Fragment {
    View v;
    public static final String MESSAGES_CHILD = "institute";
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Institutes, InstituteFragment.MessageViewHolder>
            mFirebaseAdapter, myadapter;
    private String ausername;
    private DatabaseReference myFirebaseDatabaseReference;
    private Interest interest;

    public InstituteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        String userType = pref.getString("userType", "");
        if (userType.equals("Student"))
            ausername = StudentActivity.getMyData();
        else
            ausername = CounsellorActivity.getMyData();
        myFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("interest").child(ausername);
        myFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                interest = dataSnapshot.getValue(Interest.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_institute, container, false);
        mMessageRecyclerView = (RecyclerView) v.findViewById(R.id.messageRecyclerView1);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(false);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        SnapshotParser<Institutes> parser = new SnapshotParser<Institutes>() {
            @Override
            public Institutes parseSnapshot(DataSnapshot dataSnapshot) {
                Institutes friendlyMessage = dataSnapshot.getValue(Institutes.class);
                if (friendlyMessage != null) {
                    Log.v("Checkingmsg", "15");
                    friendlyMessage.setId(dataSnapshot.getKey());
                }
                return friendlyMessage;
            }
        };

        Log.v("Checkingmsg", "12");
        DatabaseReference messagesRef = mFirebaseDatabaseReference.child(MESSAGES_CHILD);
        FirebaseRecyclerOptions<Institutes> options =
                new FirebaseRecyclerOptions.Builder<Institutes>()
                        .setQuery(messagesRef, parser)
                        .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Institutes, InstituteFragment.MessageViewHolder>(options) {
            @Override
            public InstituteFragment.MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                Log.v("Checkingmsg", "15");
                LayoutInflater inflater = LayoutInflater.from(getContext());
                Log.v("Checkingmsg", "15");
                View v1 = inflater.inflate(R.layout.item_card, viewGroup, false);
                Log.v("Checkingmsg", "16");
                return new InstituteFragment.MessageViewHolder(v1);
            }

            @Override
            protected void onBindViewHolder(final InstituteFragment.MessageViewHolder viewHolder,
                                            int position,
                                            final Institutes friendlyMessage) {


                if (friendlyMessage.getInstitureName() != null) {
                    Log.v("Checkingmsg", "17");
                    if (friendlyMessage.getInstituteTag().equals("ComputerScience") && interest.isComputerScience()) {
                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });


                    } else if (friendlyMessage.getInstituteTag().equals("Biology") && interest.isBiology()) {
                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Mathematics") && interest.isMathematics()) {
                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("GeneralKnowledge") && interest.isGeneralKnowledge()) {
                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Geography") && interest.isGeography()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Accounts") && interest.isAccounts()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Politics") && interest.isPolitics()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Economics") && interest.isEconomics()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Cricket") && interest.isCricket()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Football") && interest.isFootball()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Badminton") && interest.isBadminton()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (friendlyMessage.getInstituteTag().equals("Technology") && interest.isTechnology()) {

                        viewHolder.examNameView.setText(friendlyMessage.getInstitureName());
                        viewHolder.cardview.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        viewHolder.cardview.setVisibility(CardView.VISIBLE);

                        viewHolder.imageview.setImageResource(getImage());
                        viewHolder.imageview.setVisibility(ImageView.VISIBLE);

                        viewHolder.examNameView.setVisibility(TextView.VISIBLE);
                        viewHolder.conducterView.setText(friendlyMessage.getInstituteDesc());
                        viewHolder.conducterView.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setText(friendlyMessage.getUrl());
                        viewHolder.examUrl.setVisibility(TextView.VISIBLE);
                        viewHolder.examUrl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String url = friendlyMessage.getUrl();
                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(myIntent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getContext(), "No application can handle this request."
                                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else {
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
        Log.v("Checkingmsg", "13");
        return v;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView examNameView;
        CardView cardview;
        TextView conducterView;
        TextView examUrl;

        ImageView imageview;

        public MessageViewHolder(View v) {
            super(v);
            examNameView = (TextView) itemView.findViewById(R.id.cardtext1);
            imageview = itemView.findViewById(R.id.imageView6);
            cardview = (CardView) itemView.findViewById(R.id.cardview12);
            conducterView = (TextView) itemView.findViewById(R.id.cardtext2);
            examUrl = itemView.findViewById(R.id.textView3);
        }
    }

    public void putViewHolder(final InstituteFragment.MessageViewHolder viewHolder,
                              int position,
                              final Institutes friendlyMessage) {

        // viewHolder.imageview.setImageResource(R.drawable.notice);

    }

    public int getImage() {
        int[] array = {R.drawable.download, R.drawable.downloads, R.drawable.downloadw, R.drawable.stanford};
        SecureRandom r = new SecureRandom();
        int i = r.nextInt(3);
        return array[i];
    }
}

