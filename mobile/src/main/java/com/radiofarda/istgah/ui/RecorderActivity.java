package com.radiofarda.istgah.ui;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import com.radiofarda.istgah.bejbej.network.podcast.VoiceMessageRepository;
import com.radiofarda.istgah.utils.CountDownTimer;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.radiofarda.istgah.R;
import com.radiofarda.istgah.bejbej.models.EpisodeQuality;
import com.radiofarda.istgah.utils.LogHelper;
import com.radiofarda.istgah.utils.RandomString;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;

/**
 * Created by Farshad on 2/25/17.
 *
 */

public class RecorderActivity extends BaseActivity implements MediaBrowserFragment.MediaFragmentListener {

    interface CompletionHandler {
        void complete();
    }

    private static final String TAG = LogHelper.makeLogTag(PodcastsActivity.class);

    private TextView txtTimer;
    private ImageButton btnRecord;
    private ImageButton btnPlay;
    private ImageButton btnDelete;
    private Button btnUpload;

    private CountDownTimer timerRecord;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String pathToSaveTrack = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    public static String tempFileName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.d(TAG, "Activity onCreate");

        setContentView(R.layout.activity_recorder);
        tempFileName = RandomString.create();

        initializeToolbar();

        initializeView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @NonNull
    private EpisodeQuality getPreferredEpisodeQuality() {
        return EpisodeQuality.LOW;
    }

    @Override
    public void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {

    }

    @Override
    public void setToolbarTitle(CharSequence title) {
        LogHelper.d(TAG, "Setting toolbar title to ", title);
        if (title == null) {
            title = getString(R.string.app_name);
        }
        setTitle(title);
    }

    private void initializeView() {
        txtTimer = (TextView) findViewById(R.id.textViewTimer);
        btnRecord = (ImageButton) findViewById(R.id.btnRecord);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnUpload = (Button) findViewById(R.id.button_upload);

        btnRecord.setTag(R.drawable.microphone);
        btnPlay.setTag(R.drawable.ic_media_play_light);

        timerRecord = new CountDownTimer(600000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                Date date = new Date(millisUntilFinished);
                DateFormat formatter = new SimpleDateFormat("ss:SS", Locale.ENGLISH);
                txtTimer.setText(formatter.format(date));
            }

            @Override
            public void onFinish() {
                stopRecording();
                btnRecord.setImageResource(R.drawable.microphone);
                btnRecord.setTag(R.drawable.microphone);

                changeTrackButtonsVisibility(true);
            }
        };

        btnRecord.setOnClickListener(view -> {
            toggleRecordButton();
        });

        btnPlay.setOnClickListener(view -> {
            togglePlayButton();
        });

        btnDelete.setOnClickListener(view -> {
            resetTimer();
            changeTrackButtonsVisibility(false);
            stopPlaying();
        });

        btnUpload.setOnClickListener(view -> {
            VoiceMessageRepository voiceMessageRepository = new VoiceMessageRepository();
            Call<ResponseBody> call = voiceMessageRepository.sendVoice(pathToSaveTrack);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Message succesfully sent to Farshid!", Toast.LENGTH_SHORT).show();
                        resetTimer();
                        changeTrackButtonsVisibility(false);
                        stopPlaying();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("Result", response.message());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    Log.e("Result", t.getLocalizedMessage());
                }
            });
        });
    }

    private void toggleRecordButton() {
        if (btnRecord.getTag().equals(R.drawable.microphone)) {
            prepareRecording(() -> {
                btnRecord.setImageResource(R.drawable.ic_media_pause_light);
                btnRecord.setTag(R.drawable.ic_media_pause_light);

                changeTrackButtonsVisibility(false);
            });
        } else {
            stopRecording();

            btnRecord.setImageResource(R.drawable.microphone);
            btnRecord.setTag(R.drawable.microphone);

            changeTrackButtonsVisibility(true);
        }
    }

    private void togglePlayButton() {
        if (btnPlay.getTag().equals(R.drawable.ic_media_play_light)) {
            playLastStoredAudioMusic();
            mediaPlayerPlaying();

            btnPlay.setImageResource(R.drawable.ic_media_pause_light);
            btnPlay.setTag(R.drawable.ic_media_pause_light);
        } else {
            stopPlaying();
        }
    }

    private void stopPlaying() {
        stopAudioPlay();

        btnPlay.setImageResource(R.drawable.ic_media_play_light);
        btnPlay.setTag(R.drawable.ic_media_play_light);
    }

    private void changeTrackButtonsVisibility(boolean visible) {
        if (visible) {
            btnPlay.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnPlay.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
    }

    private void startTimer() { timerRecord.start(); }

    private void stopTimer() {
        timerRecord.pause();
    }

    private void resetTimer() {
        timerRecord.cancel();
        txtTimer.setText("60:00");
    }

    // Recording
    private void prepareRecording(CompletionHandler callback) {
        if (checkPermission()) {
            try {
                String absolutePath = getExternalCacheDir().getAbsolutePath();
                pathToSaveTrack = absolutePath + "/istgaah_" + tempFileName + ".mp3";
            } catch (NullPointerException error) {
                error.printStackTrace();
            }

            if (startRecording()) {
                callback.complete();
            }

        } else {
            requestPermission();
        }
    }

    private void stopRecording() {
        mediaRecorder.stop();
        stopTimer();
    }

    private boolean startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setAudioEncodingBitRate(16);
        mediaRecorder.setAudioSamplingRate(44100);
        mediaRecorder.setOutputFile(pathToSaveTrack);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            startTimer();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Playback
    private void playLastStoredAudioMusic(){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(pathToSaveTrack);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            stopPlaying();
        });

        mediaPlayer.start();
        btnRecord.setEnabled(false);
    }

    private void mediaPlayerPlaying(){
        if(!mediaPlayer.isPlaying()){
            stopAudioPlay();
        }
    }

    private void stopAudioPlay(){
        btnRecord.setEnabled(true);
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Permission Control
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    public boolean checkPermission() {
        int result_record = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result_record == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toggleRecordButton();
            }
        }
    }
}
