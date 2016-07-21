package com.netforceinfotech.ibet.dashboard.language;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.dashboard.Chart.HighestAdapter;
import com.netforceinfotech.ibet.dashboard.Chart.HighestFragmentData;
import com.netforceinfotech.ibet.dashboard.Dashboard;

import java.util.ArrayList;


public class LanguageFragment extends Fragment
{

    private RecyclerView recyclerView;
    Context context;
    private LinearLayoutManager layoutManager;
    private languageAdapter adapter;

    ArrayList<String>  languagedatas = new ArrayList<String>();

    public LanguageFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        context = getActivity();

        Dashboard.title.setText("Language");
        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view)
    {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        languagedatas.add("English");
        languagedatas.add("Hindi");
        languagedatas.add("Spanish");
        adapter = new languageAdapter(context, languagedatas);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }


}
