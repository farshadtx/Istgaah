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

package com.radiofarda.istgah.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.text.TextUtils;

import java.util.Arrays;

/**
 * Utility class to help on queue related tasks.
 */
public class MediaIDHelper {

    // Media IDs used on browseable items of MediaBrowser
    public static final String MEDIA_ID_EMPTY_ROOT = "__EMPTY_ROOT__";



    /**
     * Determine if media item is playing (matches the currently playing media item).
     *
     * @param context   for retrieving the {@link MediaControllerCompat}
     * @param mediaItem to compare to currently playing {@link MediaBrowserCompat.MediaItem}
     * @return boolean indicating whether media item matches currently playing media item
     */
    public static boolean isMediaItemPlaying(Context context,
                                             MediaBrowserCompat.MediaItem mediaItem) {
        // Media item is considered to be playing or paused based on the controller's current
        // media id
        MediaControllerCompat controller = ((FragmentActivity) context)
                .getSupportMediaController();
        if (controller != null && controller.getMetadata() != null) {
            String currentPlayingMediaId = controller.getMetadata().getDescription()
                    .getMediaId();
            String itemMusicId =
                    mediaItem.getDescription().getMediaId();
            if (currentPlayingMediaId != null
                    && TextUtils.equals(currentPlayingMediaId, itemMusicId)) {
                return true;
            }
        }
        return false;
    }
}
