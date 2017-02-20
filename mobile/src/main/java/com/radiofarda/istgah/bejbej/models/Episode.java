package com.radiofarda.istgah.bejbej.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import com.radiofarda.istgah.bejbej.network.podcast.Info;
import com.radiofarda.istgah.bejbej.network.podcast.ProgramList;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import okio.ByteString;

public class Episode extends RealmObject {
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


        // Adding the music source to the MediaMetadata (and consequently using it in the
        // mediaSession.setMetadata) is not a good idea for a real world music app, because
        // the session metadata can be accessed by notification listeners. This is done in this
        // sample for convenience only.
        //noinspection ResourceType

        return new MediaMetadataCompat.Builder()
                       .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                       .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, resolvePlayableUri())
                       .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, desc)
                       .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, desc)
                       .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
                       .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                       .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                       .build();
    }

    private String resolvePlayableUri() {
        EpisodeFile file = findAvailableFile();
        if (file != null) {
            Uri uri = file.toUri();
            return uri != null ? uri.toString() :SOURCE ;
        }
        return SOURCE;
    }

    private static final String SOURCE = "http://www.youtube.com/audiolibrary/music/Jazz_In_Paris.mp3";

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
