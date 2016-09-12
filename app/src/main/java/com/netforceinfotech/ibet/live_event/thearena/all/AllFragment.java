package com.netforceinfotech.ibet.live_event.thearena.all;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.general.UserSessionManager;
import com.netforceinfotech.ibet.live_event.thearena.top.TopAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment implements View.OnClickListener, ChildEventListener {
    private static final String TAG = "kunsang_firebase";
    static Context context;
    String teamaid, teambid, teama, teamb, team, matchid;
    EditText editText;
    CircleImageView imageViewSend;
    ArrayList<AllData> allDatas = new ArrayList<>();
    static UserSessionManager userSessionManager;
    String profileimage;
    private String chat_message, chatUsername;
    LinearLayout linearLayoutNoComment;
    private String stringImage;
    private String stringComment;
    private Long timestamp;
    private AllAdapter adapter;
    DatabaseReference _root;
    DatabaseReference _all;
    DatabaseReference _matchid;
    DatabaseReference _team;
    static DatabaseReference _comment;
    DatabaseReference _teamdetail;
    Map<String, Object> map_all, map_matchid, map_team, map_comment, map_teamdetail;
    private static String tempKey;
    private RecyclerView recyclerView;

    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        context = getActivity();
        userSessionManager = new UserSessionManager(context);
        editText = (EditText) view.findViewById(R.id.editText);
        imageViewSend = (CircleImageView) view.findViewById(R.id.imageViewSend);
        imageViewSend.setOnClickListener(this);

        try {
            teamaid = this.getArguments().getString("teamaid");
            teambid = this.getArguments().getString("teambid");
            matchid = this.getArguments().getString("matchid");
            teama = this.getArguments().getString("teama");
            teamb = this.getArguments().getString("teamb");
            team = this.getArguments().getString("team");
            linearLayoutNoComment = (LinearLayout) view.findViewById(R.id.linearLayoutNoComment);
            profileimage = userSessionManager.getProfilePic();
            setupHashMap();
            setupRecycler(view);

        } catch (Exception ex) {
            Log.i("kunsang_exception", "paramenter not set");
        }
        setupFirebaseReferences();
        return view;
    }

    private void setupFirebaseReferences() {
        _root = FirebaseDatabase.getInstance().getReference();
        _root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("all").exists()) {
                    Log.i(TAG, "all exist");
                    _all = _root.child("all");
                    _all.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(matchid).exists()) {
                                Log.i(TAG, "matchid exist");
                                _matchid = _all.child(matchid);
                                _matchid.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child(team).exists()) {
                                            Log.i(TAG, "team exist");
                                            _team = _matchid.child(team);
                                            _team.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.child("comments").exists()) {
                                                        _comment = _team.child("comments");
                                                        Log.i(TAG, "comments exist");
                                                        _comment.addChildEventListener(AllFragment.this);

                                                    } else {
                                                        _team.updateChildren(map_teamdetail);
                                                        _team.addChildEventListener(AllFragment.this);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        } else {
                                            _matchid.updateChildren(map_team);
                                            _matchid.addChildEventListener(AllFragment.this);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                _all.updateChildren(map_matchid);
                                _all.addChildEventListener(AllFragment.this);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    _root.updateChildren(map_all);
                    _root.addChildEventListener(AllFragment.this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupHashMap() {
        map_all = new HashMap<String, Object>();
        map_all.put("all", "");

        map_matchid = new HashMap<>();
        map_matchid.put(matchid, "");

        map_team = new HashMap<>();
        map_team.put(team, "");

        map_teamdetail = new HashMap<>();
        map_teamdetail.put("id", 12);
        map_teamdetail.put("name", "barcelona");
        map_teamdetail.put("logo", "http://brazil.com");
        map_teamdetail.put("comments", "");

        map_comment = new HashMap<>();

    }

    public void setupRecycler(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new AllAdapter(getActivity(), allDatas);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void appendChatConversation(DataSnapshot dataSnapshot) {
        Log.i("kunsang_firebase", dataSnapshot.toString());
        DataSnapshot data = dataSnapshot.child(tempKey);
        stringImage = dataSnapshot.child("image").getValue(String.class);
        chatUsername = dataSnapshot.child("name").getValue(String.class);
        stringComment = dataSnapshot.child("message").getValue(String.class);
        timestamp = dataSnapshot.child("timestamp").getValue(Long.class);
        allDatas.add(new AllData(stringImage, chatUsername, timestamp, stringComment, "0", "0", "0", "0"));
        if (allDatas.size() != 0) {
            linearLayoutNoComment.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(allDatas.size());


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewSend:
                showMessage("clicked");
                Map<String, Object> map = new HashMap<String, Object>();
                tempKey = _comment.push().getKey();
                _comment.updateChildren(map);
                DatabaseReference message_root = _comment.child(tempKey);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("name", userSessionManager.getName());
                map1.put("message", editText.getText().toString());
                map1.put("timestamp", ServerValue.TIMESTAMP);
                map1.put("image", userSessionManager.getProfilePic());
                message_root.updateChildren(map1);
                editText.setText("");

                break;
        }

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.getKey().equalsIgnoreCase("all")) {
            _all = _root.child("all");
            _all.updateChildren(map_matchid);
            _all.addChildEventListener(AllFragment.this);
        } else if (dataSnapshot.getKey().equalsIgnoreCase(matchid)) {
            _matchid = _all.child(matchid);
            _matchid.updateChildren(map_team);
            _matchid.addChildEventListener(AllFragment.this);
        } else if (dataSnapshot.getKey().equalsIgnoreCase(team)) {
            _team = _matchid.child(team);
            _team.updateChildren(map_teamdetail);
            _team.addChildEventListener(AllFragment.this);
        } else if (dataSnapshot.getKey().equalsIgnoreCase(tempKey)) {
            appendChatConversation(dataSnapshot);
        } else if (dataSnapshot.getKey().equalsIgnoreCase("comments")) {
            _comment = _team.child("comments");
            _comment.addChildEventListener(AllFragment.this);
        }

    }

    private static void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public class Comment {
        String name, comment, image;
        Long timestamp;

        public Comment(String image, String name, String comment, Long timestamp) {
            this.image = image;
            this.name = name;
            this.timestamp = timestamp;
            this.comment = comment;
        }
    }

    public static void sendMessage(String chat_message) {
        showMessage("clicked");
        Map<String, Object> map = new HashMap<String, Object>();
        tempKey = _comment.push().getKey();
        _comment.updateChildren(map);
        DatabaseReference message_root = _comment.child(tempKey);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("name", userSessionManager.getName());
        map1.put("message", chat_message);
        map1.put("timestamp", ServerValue.TIMESTAMP);
        map1.put("image", userSessionManager.getProfilePic());
        message_root.updateChildren(map1);

    }
}
