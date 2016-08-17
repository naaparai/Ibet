package com.netforceinfotech.ibet.profilesetting.selectteam;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.dashboard.home.startnewbet.upcominggame.UpcomingGameAdapter;
import com.netforceinfotech.ibet.profilesetting.ProfileSettingActivity;

import java.util.ArrayList;

public class SelectTeamActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ArrayList<SelectTeamData> selectTeamDatas = new ArrayList<>();
    private Toolbar toolbar;
    private SelectTeamAdapter upcomingGameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);
        findViewById(R.id.buttonDone).setOnClickListener(this);
        context = this;
        setupToolBar("Select Team");
        setupRecycler();
        getTeams();
    }

    private void getTeams() {
        //https://netforcesales.com/ibet_admin/api/services.php?opt=team_list
        String url = getResources().getString(R.string.url);
        url = url + "?opt=team_list";
        Log.i("result url", url);
        setHeader();
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            showMessage("nothings is here");
                        } else {
                            Log.i("kunsang_test_login", result.toString());
                            String status = result.get("status").getAsString().toLowerCase();
                            if (status.equalsIgnoreCase("success")) {
                                JsonArray data = result.getAsJsonArray("data");
                                for (int i = 0; i < data.size(); i++) {
                                    JsonObject object = data.get(i).getAsJsonObject();
                                    String id = object.get("id").getAsString();
                                    String name = object.get("name").getAsString();
                                    String logo = object.get("logo").getAsString();
                                    selectTeamDatas.add(new SelectTeamData(id, name, logo));
                                }
                                upcomingGameAdapter.notifyDataSetChanged();
                            } else {
                                showMessage("Something's wrong");
                            }
                        }

                    }
                });
    }

    private void showMessage(String s) {
        Toast.makeText(SelectTeamActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void setupToolBar(String app_name) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(app_name);

    }


    private void setupRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        upcomingGameAdapter = new SelectTeamAdapter(context, selectTeamDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(upcomingGameAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDone:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
    }

    private void setHeader() {
        final String appkey = getResources().getString(R.string.appkey);
        Ion.getDefault(context).getHttpClient().insertMiddleware(new AsyncHttpClientMiddleware() {
            @Override
            public void onRequest(OnRequestData data) {
                data.request.setHeader("APPKEY", appkey);
            }

            @Override
            public Cancellable getSocket(GetSocketData data) {
                return null;
            }

            @Override
            public boolean exchangeHeaders(OnExchangeHeaderData data) {
                return false;
            }

            @Override
            public void onRequestSent(OnRequestSentData data) {

            }

            @Override
            public void onHeadersReceived(OnHeadersReceivedDataOnRequestSentData data) {

            }

            @Override
            public void onBodyDecoder(OnBodyDataOnRequestSentData data) {

            }

            @Override
            public void onResponseComplete(OnResponseCompleteDataOnRequestSentData data) {

            }
        });
    }
}
