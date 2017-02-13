package com.radiofarda.istgah.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.radiofarda.istgah.R;
import com.radiofarda.istgah.network.podcast.ProgramList;
import com.radiofarda.istgah.views.adapters.ProgramListAdapter;

public class PodcastsFragment extends Fragment {


    public PodcastsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PodcastsFragment newInstance() {
        return new PodcastsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_podcasts, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.podcastList);
        ArrayAdapter<ProgramList> programListAdapter = new ProgramListAdapter(getContext());
        listView.setAdapter(programListAdapter);

        return rootView;
    }
}
