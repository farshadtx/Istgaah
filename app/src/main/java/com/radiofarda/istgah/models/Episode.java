package com.radiofarda.istgah.models;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.radiofarda.istgah.network.podcast.PodcastsFetcher;
import com.radiofarda.istgah.network.podcast.ProgramList;
import com.radiofarda.istgah.views.adapters.EpisodesAdapter;

/**
 * Created by maz on 2/13/17.
 */
public class Episode {
    private final ProgramList programList;

    public Episode(ProgramList programList) {
        this.programList = programList;
    }

    public String getDescription() {
        return programList.getLinkHighQuality();
    }

    public String getDate() {
        return programList.getDate();
    }

    public static void loadEpisodeAdapter(EpisodesAdapter episodesAdapter) {
        new AsyncTask<ArrayAdapter, Void, Void>() {
            @Override
            protected Void doInBackground(ArrayAdapter[] params) {
                for (ArrayAdapter item : params) {
                    new PodcastsFetcher().fetch(programList -> {
                        for (ProgramList program : programList) {
                            item.addAll(new Episode(program));
                        }
                    });
                }
                return null;
            }
        }.execute(episodesAdapter);
    }

    public void loadIcon(ImageView imageView) {
        try {
            URL url = new URL(programList.getArtwork500().replace("https","http"));
            new AsyncTask<URL, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(URL... params) {
                    try {
                        for (URL url : params) {
                            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
