package com.netforceinfotech.ibet1.profilesetting.selectteam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.netforceinfotech.ibet1.R;
import com.netforceinfotech.ibet1.general.UserSessionManager;
import com.netforceinfotech.ibet1.profilesetting.ProfileSettingActivity;
import com.netforceinfotech.ibet1.profilesetting.selectteam.expandteam.ExpandHeaderData;
import com.netforceinfotech.ibet1.profilesetting.selectteam.expandteam.ExpandableListAdapter;
import com.netforceinfotech.ibet1.profilesetting.selectteam.listofteam.TeamListAdapter;
import com.netforceinfotech.ibet1.profilesetting.selectteam.listofteam.TeamListData;
import com.netforceinfotech.ibet1.profilesetting.selectteam.selectedteam.SelectTeamAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SelectTeamActivity extends AppCompatActivity implements View.OnClickListener, ExpandableListAdapter.clickListner {

    Context context;
    ArrayList<TeamListData> teamListDatas = new ArrayList<>();
    ArrayList<TeamListData> teamListDatas1 = new ArrayList<>();
    private Toolbar toolbar;
    public static TeamListAdapter upcomingGameAdapter;
    LinearLayout linearLayoutProgress;
    public static LinearLayout linearlayoutMain, linearLayoutSelectedTeams, linearLayoutTeams;
    EditText editTextSearch;
    public static ArrayList<TeamListData> selectTeamDatas = new ArrayList<>();
    public static SelectTeamAdapter selectTeamAdapter;
    private MaterialSearchView searchView;
    ExpandableListView expListView;
    public static ExpandableListAdapter listAdapter;
    ArrayList<ExpandHeaderData> expandHeaderDatas = new ArrayList<>();
    HashMap<ExpandHeaderData, List<TeamListData>> expandChildDatas = new HashMap<>();
    LinearLayout linearLayoutSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team1);
        selectTeamDatas.clear();
        linearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayoutSearch);
        linearLayoutSearch.setVisibility(View.GONE);
        findViewById(R.id.buttonDone).setOnClickListener(this);
        linearLayoutProgress = (LinearLayout) findViewById(R.id.linearLayoutInput);
        linearlayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);
        linearLayoutSelectedTeams = (LinearLayout) findViewById(R.id.linearLayoutSelectedTeams);
        linearLayoutTeams = (LinearLayout) findViewById(R.id.linearLayoutTeams);
        context = this;
        setSearchView();
        setupToolBar("Select Team");
        setupSelectedRecyler();
        setupExpandable();
        setupRecycler();
        getTeams();
    }

    private void getTeams1() {
        UserSessionManager userSessionManager = new UserSessionManager(context);
        String token = userSessionManager.getApitoken();
        String url = "https://api.soccerama.pro/v1.1/competitions?api_token=DLhRgpl372eKkR1o7WzSDn3SlGntcDVQMTWn9HkrTaRwdFWVhveFfaH7K4QP&include=currentSeason";
        Log.i("kunsangresult", url);
        showMessage("implement get team");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
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

    private void setupRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        //prepareListData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        upcomingGameAdapter = new TeamListAdapter(context, teamListDatas1);
        // setting list adapter
        recyclerView.setAdapter(upcomingGameAdapter);

    }

    private void setSearchView() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                linearLayoutTeams.setVisibility(View.GONE);
                linearLayoutProgress.setVisibility(View.VISIBLE);
                linearLayoutTeams.setVisibility(View.GONE);
                teamListDatas1.clear();
                upcomingGameAdapter.notifyDataSetChanged();
                getTeam(query);
                searchView.setHint(query);
                View view = SelectTeamActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if (newText.length() > 2) {
                    teamListDatas1.clear();
                    upcomingGameAdapter.notifyDataSetChanged();
                    linearLayoutTeams.setVisibility(View.GONE);
                    linearLayoutProgress.setVisibility(View.VISIBLE);
                    getTeam(newText);
                }

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                linearLayoutSearch.setVisibility(View.GONE);
                linearLayoutTeams.setVisibility(View.VISIBLE);


            }
        });
    }

    private void getTeam(String string) {
        String url = getResources().getString(R.string.urlsearch);
        url = url + "?sstr=" + string;
        Log.i("result url", url);
        setHeader();
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        linearLayoutProgress.setVisibility(View.GONE);
                        linearlayoutMain.setVisibility(View.VISIBLE);
                        linearLayoutSearch.setVisibility(View.VISIBLE);
                        teamListDatas1.clear();
                        upcomingGameAdapter.notifyDataSetChanged();
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
                                    teamListDatas1.add(new TeamListData(id, name, logo, 0, ""));
                                }
                                upcomingGameAdapter.notifyDataSetChanged();
                            } else {
                                showMessage("Something's wrong");
                            }
                        }

                    }
                });
    }

    private void getTeams() {
        //https://netforcesales.com/ibet_admin/api/services.php?opt=team_list
        String url = getResources().getString(R.string.url);
        url = url + "/services.php?opt=team_list";
        Log.i("result url", url);
        setHeader();
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        linearLayoutProgress.setVisibility(View.GONE);
                        linearlayoutMain.setVisibility(View.VISIBLE);
                        linearLayoutTeams.setVisibility(View.VISIBLE);

                        if (result == null) {
                            showMessage("nothings is here");
                        } else {
                            Log.i("kunsang_test_login", result.toString());
                            String status = result.get("status").getAsString().toLowerCase();
                            if (status.equalsIgnoreCase("success")) {
                                JsonArray data = result.getAsJsonArray("data");
                                for (int i = 0; i < data.size(); i++) {
                                    JsonObject object = data.get(i).getAsJsonObject();
                                    if (!(object.get("id").isJsonNull() || object.get("name").isJsonNull())) {
                                        String id = object.get("id").getAsString();
                                        String name = object.get("name").getAsString();
                                        int competition_id = Integer.parseInt(object.get("competition_id").getAsString());
                                        String competition_name = object.get("comptition_name").getAsString();
                                        String logo = "";
                                        if (!object.get("logo").isJsonNull()) {
                                            logo = object.get("logo").getAsString();
                                        }
                                        if (competition_name.equalsIgnoreCase("")) {
                                            competition_name = "No Name";
                                        }

                                        ExpandHeaderData competition = new ExpandHeaderData(competition_id, competition_name);
                                        if (!expandHeaderDatas.contains(competition)) {

                                            expandHeaderDatas.add(competition);
                                        }

                                        teamListDatas.add(new TeamListData(id, name, logo, competition_id, competition_name));
                                        Collections.sort(teamListDatas);
                                    }
                                }
                                setupExpandableData();
                                //  upcomingGameAdapter.notifyDataSetChanged();
                            } else {
                                showMessage("Something's wrong");
                            }
                        }

                    }
                });
    }

    private void setupExpandableData() {

        int prevCompId = teamListDatas.get(0).compid;
        int counter = 0;
        ArrayList<TeamListData> expandChildData = new ArrayList<>();
        for (TeamListData teamData : teamListDatas) {
            expandChildData.add(teamData);
            if (teamData.compid != prevCompId) {
                expandChildDatas.put(expandHeaderDatas.get(counter), expandChildData);
                counter++;
                prevCompId = teamData.compid;
                expandChildData = new ArrayList<>();

            }
        }
        listAdapter.notifyDataSetChanged();
    }

    private void setupSelectedRecyler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerSelected);
        selectTeamAdapter = new SelectTeamAdapter(context, selectTeamDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(selectTeamAdapter);
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


    private void setupExpandable() {
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        //prepareListData();
        listAdapter = new ExpandableListAdapter(context, expandHeaderDatas, expandChildDatas);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        listAdapter.setClickListner(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDone:
                try {
                    ProfileSettingActivity.arrayListTeamids.clear();
                } catch (Exception ex) {

                }
                for (int i = 0; i < selectTeamDatas.size(); i++) {
                    if (!ProfileSettingActivity.arrayListTeamids.contains(selectTeamDatas.get(i))) {
                        ProfileSettingActivity.arrayListTeamids.add(selectTeamDatas.get(i).id);
                    }
                }
                String teams = TextUtils.join(",", ProfileSettingActivity.arrayListTeamids);
                updateTeam(teams);

                break;
        }
    }

    private void updateTeam(String teams) {
        UserSessionManager userSessionManager = new UserSessionManager(context);
        //https://netforcesales.com/ibet_admin/api/services.php?opt=insert_team_list&customer_id=46&team=1,2,3
        String baseUrl = getString(R.string.url);
        String updateTeamUrl = "/services.php?opt=insert_team_list&customer_id=" + userSessionManager.getCustomerId()
                + "&team=" + teams;
        String url = baseUrl + updateTeamUrl;
        Log.i("kunsangurl", url);
        setupSelfSSLCert();
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            showMessage("Something is wrong");
                        } else {
                            Log.i("kunsangresult", result.toString());
                            if (result.get("status").getAsString().equalsIgnoreCase("success")) {
                                showMessage("Favourite Team Selected");
                                finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                            } else {
                                showMessage("Could not set team. Try again");
                            }
                        }
                    }
                });

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

    @Override
    public void itemClicked(View view, int groupview, int childview) {
    }

    private static class Trust implements X509TrustManager {

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }

    public void setupSelfSSLCert() {
        final Trust trust = new Trust();
        final TrustManager[] trustmanagers = new TrustManager[]{trust};
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustmanagers, new SecureRandom());
            Ion.getInstance(context, "rest").getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustmanagers);
            Ion.getInstance(context, "rest").getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (final KeyManagementException e) {
            e.printStackTrace();
        }
    }
}