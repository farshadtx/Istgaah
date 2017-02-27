package com.radiofarda.istgah.bejbej.models;

import android.os.AsyncTask;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import com.radiofarda.istgah.bejbej.network.podcast.Info;
import com.radiofarda.istgah.bejbej.network.podcast.ProgramList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import okio.ByteString;

public class Episode extends RealmObject implements Comparable<Episode> {
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
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .build();
    }

    private String resolvePlayableUri() {
        EpisodeFile file = findAvailableFile();
        return file != null ? file.toUri().toString() : null;
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

    public static Episode findByMediaItem(MediaBrowserCompat.MediaItem mediaItem) {
        return findById(mediaItem.getMediaId());
    }

    public static RealmResults<Episode> findAll() {
        return Realm.getInstance(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()).where(Episode.class).findAllSorted("date", Sort.DESCENDING);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Episode episode = (Episode) o;

        if (duration != episode.duration) return false;
        if (id != null ? !id.equals(episode.id) : episode.id != null) return false;
        if (remoteFileId != null ? !remoteFileId.equals(episode.remoteFileId) : episode.remoteFileId != null)
            return false;
        if (title != null ? !title.equals(episode.title) : episode.title != null) return false;
        if (desc != null ? !desc.equals(episode.desc) : episode.desc != null) return false;
        if (date != null ? !date.equals(episode.date) : episode.date != null) return false;
        if (iconUrl != null ? !iconUrl.equals(episode.iconUrl) : episode.iconUrl != null)
            return false;
        return episodeFiles != null ? episodeFiles.equals(episode.episodeFiles) : episode.episodeFiles == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (remoteFileId != null ? remoteFileId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        result = 31 * result + (episodeFiles != null ? episodeFiles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id='" + id + '\'' +
                ", remoteFileId='" + remoteFileId + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", iconUrl='" + iconUrl + '\'' +
                ", episodeFiles=" + episodeFiles +
                '}';
    }

    @Override
    public int compareTo(Episode o) {
        if (this.date == null || o.date == null) {
            return 0;
        }
        return this.date.compareTo(o.date);
    }

    public String getId() {
        return id;
    }
}
