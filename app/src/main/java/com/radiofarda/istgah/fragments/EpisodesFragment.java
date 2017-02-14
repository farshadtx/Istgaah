package com.radiofarda.istgah.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.radiofarda.istgah.R;
import com.radiofarda.istgah.views.adapters.EpisodesAdapter;

public class EpisodesFragment extends Fragment {


    public EpisodesFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EpisodesFragment newInstance() {
        return new EpisodesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_episodes, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.episodeList);
        listView.setAdapter( EpisodesAdapter.newInstance(getContext()));
        return rootView;
    }
}
