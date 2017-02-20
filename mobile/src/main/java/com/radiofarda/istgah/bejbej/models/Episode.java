package com.radiofarda.istgah.bejbej.models;

import android.os.AsyncTask;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import com.radiofarda.istgah.model.MusicProviderSource;
import com.radiofarda.istgah.bejbej.network.podcast.Info;
import com.radiofarda.istgah.bejbej.network.podcast.ProgramList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import okio.ByteString;

public class Episode extends RealmObject {


    private static final String SOURCE = "https://www.youtube.com/audiolibrary/music/Jazz_In_Paris.mp3";
    @PrimaryKey
    @Required
    private String id;
    private String remoteFileId;
    private String title;
    private String desc;
    private Date date;
    private long duration;
    private String iconUrl;
    private RealmList<EpisodeFile> episodeFiles;

    public Episode() {
    }

    public Episode(ProgramList programList, Info info) {
        if (programList.getFileId() == null) {
            return;
        }
        this.id = String.valueOf(programList.getFileId().hashCode());
        this.iconUrl = programList.getArtwork500().replace("https", "http");
        this.duration = convertDurationToMS(programList.getDuration());
        this.title = ByteString.decodeBase64(programList.getTitle()).utf8();
        this.desc = ByteString.decodeBase64(programList.getDescription()).utf8();
        this.remoteFileId = programList.getFileId();
        try {
            this.date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(programList.getDate());
        } catch (ParseException e) {
            Log.e("", e.getMessage());
        }
        episodeFiles = EpisodeFile.createFromEpisode(programList, info.getZipPassword());
    }


    public MediaMetadataCompat toMediaMetadataCompat() {


        int trackNumber = 1;
        int totalTrackCount = 1;


        // Adding the music source to the MediaMetadata (and consequently using it in the
        // mediaSession.setMetadata) is not a good idea for a real world music app, because
        // the session metadata can be accessed by notification listeners. This is done in this
        // sample for convenience only.
        //noinspection ResourceType
        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                .putString(MusicProviderSource.CUSTOM_METADATA_EPISODE_ID, id)
                .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, findAvailableFile() != null ? findAvailableFile().getLocalCache() : null)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, desc)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, desc)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, date == null ? null : date.toString())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, trackNumber)
                .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, totalTrackCount)
                .build();
    }


    private long convertDurationToMS(String duration) {
        if (duration == null) {
            return 0;
        }
        try {
            return new SimpleDateFormat("HH:mm:ss").parse(duration).getTime();
        } catch (ParseException e) {
            Log.e(".", e.getMessage());
            return 0;
        }
    }

    public static Episode findById(Realm realm, String id) {
        return realm.where(Episode.class).equalTo("id", id).findFirst();
    }

    public static Episode findById(String id) {
        return findById(Realm.getInstance(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()), id);
    }

    public EpisodeFile findAvailableFile() {
        if (episodeFiles == null || episodeFiles.isEmpty()) {
            return null;
        }
        for (EpisodeFile episodeFile : episodeFiles) {
            if (episodeFile.doesAudioFileExist()) {
                return episodeFile;
            }
        }
        return null;
    }

    public void download(EpisodeQuality quality) {

        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... episodeFileId) {
                EpisodeFile.downloadAndUnzipFile(episodeFileId[0]);
                return null;
            }
        }.execute(EpisodeFile.formatId(remoteFileId, quality));
    }

}
