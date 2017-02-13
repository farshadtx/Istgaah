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
import android.widget.Toast;
import com.radiofarda.istgah.R;
import com.radiofarda.istgah.network.podcast.PodcastsFetcher;
import com.radiofarda.istgah.network.podcast.ProgramList;

/**
 * Created by maz on 2/11/17.
 */
public class ProgramListAdapter extends ArrayAdapter<ProgramList> {
    private final Context context;

    public ProgramListAdapter(Context context) {
        super(context, -1, new ArrayList<>());
        this.context = context;
        new LoadProgramTask().execute(this);
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
    private class LoadProgramTask extends AsyncTask<ArrayAdapter, ProgramList, Void> {
        @Override
        protected Void doInBackground(ArrayAdapter... adapters) {
            for (ArrayAdapter item : adapters) {
                new PodcastsFetcher().fetch(programList -> {
                    item.addAll(programList);
                });
            }
            return (null);
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT)
                 .show();
        }
    }
}
