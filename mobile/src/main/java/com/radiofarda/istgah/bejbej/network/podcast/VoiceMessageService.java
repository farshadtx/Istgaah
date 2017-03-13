package com.radiofarda.istgah.bejbej.network.podcast;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Farshad on 3/5/17.
 *
 */

public interface VoiceMessageService {
    @Multipart
    @POST("feed-ext-cmd.php")
    Call<ResponseBody> sendMessage(
            @Part("command") RequestBody command,
            @Part("quality") RequestBody quality,
            @Part MultipartBody.Part file
    );
}
