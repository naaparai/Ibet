package com.netforceinfotech.ibet.dashboard.home.startnewbet.currentgame;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.dashboard.home.detail_bet_to_join.WhoWillWinActivity;
import com.netforceinfotech.ibet.general.UserSessionManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentGameFragment extends Fragment implements View.OnClickListener {


    private RecyclerView recyclerView;
    Context context;
    private LinearLayoutManager layoutManager;
    private CurrentGameAdapter adapter;
    ArrayList<CurrentGameData> currentGameDatas = new ArrayList<>();
    FrameLayout currentgame_layout;
    private UserSessionManager userSessionManager;
    LinearLayout linearLayout;
    Button buttonNext;
    int theme;

    public CurrentGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_game, container, false);
        context = getActivity();
        linearLayout= (LinearLayout) view.findViewById(R.id.linearLayout);
        buttonNext = (Button) view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);
        userSessionManager = new UserSessionManager(getActivity());
        theme = userSessionManager.getTheme();
        setupRecyclerView(view);
        getLiveMatch();
        return view;
    }

    private void setupRecyclerView(View view) {

        currentgame_layout = (FrameLayout) view.findViewById(R.id.currentgame_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        if (theme == 0) {
            currentgame_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_background_theme1));

        } else if (theme == 1) {

            currentgame_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_background_theme2));

        } else if (theme == 2) {
            currentgame_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_background_theme3));

        } else if (theme == 3) {
            currentgame_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_background_theme4));

        } else if (theme == 4) {
            currentgame_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_background_theme5));
        }


        recyclerView.setLayoutManager(layoutManager);
        adapter = new CurrentGameAdapter(context, currentGameDatas);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNext:
                Intent intent = new Intent(context, WhoWillWinActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getLiveMatch() {
        //https://netforcesales.com/ibet_admin/api/current_matches.php?todaydate=2016-08-20
        String url = getResources().getString(R.string.url);
        url = url + "/current_matches.php?";
        Log.i("result url", url);
        setHeader();
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        linearLayout.setVisibility(View.GONE);
                        if (result == null) {
                            showMessage("Somethings wrong");
                        } else {
                            String status = result.get("status").getAsString();
                            if (status.equalsIgnoreCase("success")) {
                                JsonArray data = result.getAsJsonArray("data");
                                for (int i = 0; i < data.size(); i++) {

                                    JsonObject object = data.get(i).getAsJsonObject();
                                    String matchid = object.get("matchid").getAsString();
                                    String teama = null;
                                    if (!object.get("home_teamname").isJsonNull()) {
                                        teama = object.get("home_teamname").getAsString();
                                    }
                                    String teamb = null;
                                    if (!object.get("away_teamname").isJsonNull()) {
                                        teamb = object.get("away_teamname").getAsString();
                                    }
                                    String logohome_team = "";
                                    if (!object.get("logohome_team").isJsonNull()) {
                                        logohome_team = object.get("logohome_team").getAsString();
                                    }

                                    String awayteam_team = "";
                                    if (!object.get("logoaway_team").isJsonNull()) {
                                        awayteam_team = object.get("logoaway_team").getAsString();//dummy
                                    }

                                    if (!(matchid == null || teama == null || teamb == null)) {
                                        currentGameDatas.add(new CurrentGameData(matchid, teama, teamb, logohome_team, awayteam_team));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                showMessage("json error");
                            }
                        }

                    }
                });
    }

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

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
