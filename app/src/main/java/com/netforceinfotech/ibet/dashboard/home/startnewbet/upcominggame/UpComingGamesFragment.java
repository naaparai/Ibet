package com.netforceinfotech.ibet.dashboard.home.startnewbet.upcominggame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netforceinfotech.ibet.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingGamesFragment extends Fragment {


    ArrayList<UpcomingGameData> upcomingGameDatas = new ArrayList<>();

    public UpComingGamesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming_games, container, false);
        setupData();
        setupRecycler(view);
        return view;
    }

    private void setupData() {
        try {
            upcomingGameDatas.clear();
        } catch (Exception ex) {

        }
        for (int i = 0; i < 20; i++) {
            upcomingGameDatas.add(new UpcomingGameData("", ""));
        }
    }

    private void setupRecycler(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        UpcomingGameAdapter upcomingGameAdapter = new UpcomingGameAdapter(getActivity(), upcomingGameDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(upcomingGameAdapter);
    }

}