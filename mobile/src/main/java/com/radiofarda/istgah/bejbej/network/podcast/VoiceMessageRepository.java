package com.radiofarda.istgah.bejbej.network.podcast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Farshad on 3/5/17.
 */

public class VoiceMessageRepository {
    public Call<ResponseBody> sendVoice(String filePath) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.bejbej.ca/rpf/")
                .build();

        VoiceMessageService voiceMessageService = retrofit.create(VoiceMessageService.class);

        File file = new File(filePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("vmf", file.getName(), requestFile);
        RequestBody command =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, "voice_message");
        RequestBody quality =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, "L");
        return voiceMessageService.sendMessage(command, quality, body);
    }
}
