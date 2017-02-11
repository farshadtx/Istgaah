package com.radiofarda.istgah.istgaah.tools;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Farshad Sheykhi on 2/5/17.
 * Test SoundCloud Parser
 */
@RunWith(AndroidJUnit4.class)

public class SoundCloudParserSpec {
    @Test
    public void useAppContext() throws Exception {
        SoundCloudParser subject = new SoundCloudParser();

        assertEquals(true, subject.parse(new JSONObject()));
    }
}
