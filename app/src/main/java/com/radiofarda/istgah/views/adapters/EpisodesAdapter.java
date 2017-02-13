package com.radiofarda.istgah.views.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.radiofarda.istgah.R;
import com.radiofarda.istgah.models.Episode;
import com.radiofarda.istgah.network.podcast.PodcastsFetcher;
import com.radiofarda.istgah.network.podcast.ProgramList;

/**
 * Created by maz on 2/11/17.
 */
public class EpisodesAdapter extends ArrayAdapter<Episode> {
    private final Context context;

    private EpisodesAdapter(Context context) {
        super(context, -1, new ArrayList<>());
        this.context = context;
    }

    public static EpisodesAdapter newInstance(Context context) {
        EpisodesAdapter episodesAdapter = new EpisodesAdapter(context);
        new LoadProgramTask().execute(episodesAdapter);
        return episodesAdapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                                                           .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.view_podcast, parent, false);
        TextView firstLine = (TextView) rowView.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        firstLine.setText(getItem(position).getDescription());
        secondLine.setText(getItem(position).getDate());
        return rowView;
    }

    private static class LoadProgramTask extends AsyncTask<ArrayAdapter, Void, Void> {
        @Override
        protected Void doInBackground(ArrayAdapter... adapters) {
            for (ArrayAdapter item : adapters) {
                new PodcastsFetcher().fetch(programList -> {
                    for (ProgramList program : programList) {
                        item.addAll(new Episode(program));
                    }
                });
            }
            return (null);
        }
    }
}
