package com.netforceinfotech.ibet.live_event.thearena.comments_comment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.general.UserSessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentComments extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    CCAdapter adapter;
    ArrayList<CCData> ccDatas = new ArrayList<>();
    Context context;
    String matchid, team, commentkey, comment, likecount, dislikecount, sharecount;
    DatabaseReference _root, _comments;
    EditText editText;
    CircleImageView circleImageView;
    private String tempKey;
    UserSessionManager userSessionManager;
    TextView textViewLC, textViewDC, textViewSC, textViewComment, textViewDate, textViewTime;
    Long timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_comments);
        context = this;
        userSessionManager = new UserSessionManager(context);

        try {

            Bundle bundle = getIntent().getExtras();
            matchid = bundle.getString("matchid");
            team = bundle.getString("team");
            commentkey = bundle.getString("commentkey");
            comment = bundle.getString("comment");
            dislikecount = bundle.getString("dislikecount");
            likecount = bundle.getString("likecount");
            sharecount = bundle.getString("sharecount");
            timestamp = bundle.getLong("timestamp");
            Log.i("localtime", getDateCurrentTimeZone(timestamp));

        } catch (Exception ex) {
            showMessage("bundle error");
        }
        setupToolbar();
        initView();
        setupRecyclerView();
        setupFirebase();
    }

    private void initView() {
        textViewDC = (TextView) findViewById(R.id.textViewDC);
        textViewLC = (TextView) findViewById(R.id.textViewLC);
        textViewSC = (TextView) findViewById(R.id.textViewSC);
        textViewComment = (TextView) findViewById(R.id.textViewComment);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        editText = (EditText) findViewById(R.id.editText);
        circleImageView = (CircleImageView) findViewById(R.id.imageViewSend);
        circleImageView.setOnClickListener(this);
        textViewDC.setText(dislikecount);
        textViewLC.setText(likecount);
        textViewSC.setText(sharecount);
        textViewComment.setText(comment);
    }

    private void setupFirebase() {
        _root = FirebaseDatabase.getInstance().getReference();
        try {
            _comments = _root.child("all").child(matchid).child(team).child("comments").child(commentkey).child("comments");
            _comments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot election : dataSnapshot.getChildren()) {
                        String message = election.child("message").getValue(String.class);
                        String image = election.child("image").getValue(String.class);
                        String name = election.child("name").getValue(String.class);
                        Long timestamp = election.child("timestamp").getValue(Long.class);
                        String key = election.getKey();
                        ccDatas.add(new CCData(image, name, timestamp, message, key));

                    }
                    Log.i("messagecount", "" + ccDatas.size());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            showMessage("firebase error");
        }

    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new CCAdapter(context, ccDatas, matchid, team);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewSend:
                if (editText.length() > 0) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    tempKey = _comments.push().getKey();
                    _comments.updateChildren(map);
                    DatabaseReference message_root = _comments.child(tempKey);
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("name", userSessionManager.getName());
                    map1.put("message", editText.getText().toString());
                    map1.put("timestamp", ServerValue.TIMESTAMP);
                    map1.put("image", userSessionManager.getProfilePic());
                    message_root.updateChildren(map1);
                } else {
                    showMessage("Type text");
                }
                break;
        }
    }

    private void showMessage(String s) {
        Toast.makeText(CommentComments.this, s, Toast.LENGTH_SHORT).show();
    }

    public String getDateCurrentTimeZone(long timestamp) {
        try {
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sfd.setTimeZone(Calendar.getInstance().getTimeZone());
            String date = sfd.format(new Date(timestamp));
            Log.i("localtime", timestamp + "");
            // date = sfd.getCalendar().getTime().toString();
            return date;

        } catch (Exception e) {
            return "";
        }

    }
}
