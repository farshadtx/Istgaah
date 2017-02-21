/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.radiofarda.istgah.model;

import android.support.v4.media.MediaMetadataCompat;

import com.radiofarda.istgah.bejbej.models.Episode;
import com.radiofarda.istgah.bejbej.models.EpisodeFile;
import com.radiofarda.istgah.utils.LogHelper;

import java.util.ArrayList;
import java.util.Iterator;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class BejbejSource implements MusicProviderSource {


    private static final String TAG = LogHelper.makeLogTag(BejbejSource.class);

    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        try {
            ArrayList<MediaMetadataCompat> tracks = new ArrayList<>();
            Realm realm = Realm.getInstance(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());
            RealmResults<Episode> episodes = realm.where(Episode.class).findAllSorted("date", Sort.DESCENDING);
            for (Episode episode : episodes) {
                tracks.add(episode.toMediaMetadataCompat());
            }
            return tracks.iterator();
        } catch (Exception e) {
            LogHelper.e(TAG, e, "Could not retrieve music list");
            throw new RuntimeException("Could not retrieve music list", e);
        }
    }


}
