package com.netforceinfotech.ibet.live_event.stand;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.netforceinfotech.ibet.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StandFragment extends Fragment implements View.OnClickListener {

    CircleImageView circleImageViewTeamA, circleImageViewTeamB;
    Button buttonNeutral;
    Context context;
    private TheArenaFragment theArenaFragment;
    CircleImageView imageViewTeamA,imageViewTeamB;
    private String tagName;

    public StandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stand, container, false);
        context = getActivity();
        imageViewTeamA= (CircleImageView) view.findViewById(R.id.imageViewTeamA);
        imageViewTeamB= (CircleImageView) view.findViewById(R.id.imageViewTeamB);
        Picasso.with(context).load(R.drawable.ic_error).into(imageViewTeamA);
        Picasso.with(context).load(R.drawable.ic_error).into(imageViewTeamB);
        initView(view);
        return view;
    }

    private void initView(View view) {
        circleImageViewTeamA = (CircleImageView) view.findViewById(R.id.imageViewTeamA);
        circleImageViewTeamB = (CircleImageView) view.findViewById(R.id.imageViewTeamB);
        buttonNeutral = (Button) view.findViewById(R.id.buttonNeutral);
        circleImageViewTeamA.setOnClickListener(this);
        circleImageViewTeamB.setOnClickListener(this);
        buttonNeutral.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNeutral:
            case R.id.imageViewTeamA:
            case R.id.imageViewTeamB:
                startActivity(new Intent(context, StandActivity.class));
                //   setupArenaFragment();
                break;
        }
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newFragment, tag);
        transaction.commit();
    }

    private void setupArenaFragment() {
        theArenaFragment = new TheArenaFragment();
        tagName = theArenaFragment.getClass().getName();
        replaceFragment(theArenaFragment, tagName);

    }
}