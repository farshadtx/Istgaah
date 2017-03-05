package com.radiofarda.istgah.bejbej.models;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.radiofarda.istgah.bejbej.network.podcast.ProgramList;
import com.radiofarda.istgah.ui.BaseActivity;

import net.lingala.zip4j.core.ZipFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EpisodeFile extends RealmObject {
    @PrimaryKey
    private String id;
    private String quality;
    private String zipLink;
    private String zipPass;
    private String localCache;
    private String size;

    public EpisodeFile() {
    }

    public EpisodeFile(String episodeId, EpisodeQuality quality, String zipLink, String zipPass, String size) {
        this.id = formatId(episodeId, quality);
        this.quality = quality.name();
        this.zipLink = zipLink;
        this.zipPass = zipPass;
        this.size = size;
    }

    public static RealmList<EpisodeFile> createFromEpisode(ProgramList program, String zipPass) {
        RealmList<EpisodeFile> episodeFiles = new RealmList<>();
        episodeFiles.add(new EpisodeFile(program.getFileId(), EpisodeQuality.LOW, program.getLinkLowQuality().replace("https", "http"), zipPass, program.getSizeLowQualityMb()));
        episodeFiles.add(new EpisodeFile(program.getFileId(), EpisodeQuality.MEDIUM, program.getLinkLowQuality().replace("https", "http"), zipPass, program.getSizeMediumQualityMb()));
        episodeFiles.add(new EpisodeFile(program.getFileId(), EpisodeQuality.HIGH, program.getLinkLowQuality().replace("https", "http"), zipPass, program.getSizeHighQualityMb()));
        return episodeFiles;
    }

    public static String formatId(String fileId, EpisodeQuality episodeQuality) {
        return String.format("%s-%s", fileId, episodeQuality.name()).toLowerCase();
    }

    public static EpisodeFile findById(Realm realm, String id) {
        return realm.where(EpisodeFile.class).equalTo("id", id).findFirst();
    }

    public static EpisodeFile findById(String id) {
        return findById(Realm.getInstance(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()), id);
    }

    public static void downloadAndUnzipFile(String id) {
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());
        EpisodeFile episodeFile = findById(realm, id);

        if (episodeFile.doesAudioFileExist()) {
            Log.i("INFO", "CACHED");
            return;
        }
        realm.beginTransaction();
        episodeFile.downloadZipFile(new ProcessCallback() {
            @Override
            public void onSucceed(File file) {
                ProcessCallback processCallback = new ProcessCallback() {
                    @Override
                    public void onSucceed(File dir) {
                        File f = dir.listFiles()[0];
                        File audioFile = new File(f.getAbsolutePath() + ".mp3");
                        f.renameTo(audioFile);
                        episodeFile.localCache = audioFile.getPath();
                    }

                    @Override
                    public void onError(String message) {
                        episodeFile.localCache = null;
                        Log.e("ERROR", message);
                    }
                };
                episodeFile.unzip(file, processCallback);
            }

            @Override
            public void onError(String message) {
                episodeFile.localCache = null;
                Log.e("ERROR", message);
            }
        });
        realm.commitTransaction();
    }

    public boolean doesAudioFileExist() {
        return toUri() != null;
    }

    interface ProcessCallback {

        void onSucceed(File file);

        void onError(String message);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void downloadZipFile(ProcessCallback zipDownloadCallback) {
        try {
            URL url = new URL(getZipLink());
            File file = File.createTempFile(String.format("%s-%s.zip", id, quality), null, BaseActivity.context.getCacheDir());
            long startTime = System.currentTimeMillis();
            Log.d("DownloadManager", "download begining");
            Log.d("DownloadManager", "download url:" + url);
            Log.d("DownloadManager", "downloaded file name:" + file.getName());

            URLConnection ucon = url.openConnection();

            InputStream in = ucon.getInputStream();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

            byte b[] = new byte[1024];
            int n;
            while ((n = in.read(b, 0, 1024)) >= 0) {
                out.write(b, 0, n);
            }

            out.flush();
            out.close();
            Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");

            if (zipDownloadCallback != null) {
                zipDownloadCallback.onSucceed(file);
            }
        } catch (IOException e) {
            Log.d("DownloadManager", "Error: " + e.toString());
            if (zipDownloadCallback != null) {
                zipDownloadCallback.onError(e.getMessage());
            }
        }


    }

    public Uri toUri() {
        if (localCache == null || localCache.length() == 0) {
            return null;
        }
        return Uri.fromFile(new File(localCache));
    }


    public void unzip(File file, ProcessCallback processCallback) {
        try {
            ZipFile zipFile = new ZipFile(file);
            zipFile.setPassword(zipPass);
            File destination = new File(BaseActivity.context.getFilesDir(), id);
            if (!destination.mkdirs()) {
                Log.e("ERROR", "Directory not created");
            }
            zipFile.extractAll(destination.getPath());
            processCallback.onSucceed(destination);
            Log.d("Unzip", "Unzipping complete. path :  " + destination);
        } catch (Exception e) {
            Log.d("Unzip", "Unzipping failed");
            e.printStackTrace();
            processCallback.onError(e.getMessage());
        }

    }

    public String getId() {
        return id;
    }

    public String getQuality() {
        return quality;
    }

    public String getZipLink() {
        return zipLink;
    }

    public String getZipPass() {
        return zipPass;
    }

    public String getLocalCache() {
        return localCache;
    }

    public String getSize() {
        return size;
    }

    public void update() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpisodeFile that = (EpisodeFile) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (quality != null ? !quality.equals(that.quality) : that.quality != null) return false;
        if (zipLink != null ? !zipLink.equals(that.zipLink) : that.zipLink != null) return false;
        if (zipPass != null ? !zipPass.equals(that.zipPass) : that.zipPass != null) return false;
        if (localCache != null ? !localCache.equals(that.localCache) : that.localCache != null)
            return false;
        return size != null ? size.equals(that.size) : that.size == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quality != null ? quality.hashCode() : 0);
        result = 31 * result + (zipLink != null ? zipLink.hashCode() : 0);
        result = 31 * result + (zipPass != null ? zipPass.hashCode() : 0);
        result = 31 * result + (localCache != null ? localCache.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EpisodeFile{" +
                "id='" + id + '\'' +
                ", quality='" + quality + '\'' +
                ", zipLink='" + zipLink + '\'' +
                ", zipPass='" + zipPass + '\'' +
                ", localCache='" + localCache + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}

