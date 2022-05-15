package com.gomezdevlopment.androidtest.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChatApiCall {

    //http://dev.rapptrlabs.com/Tests/scripts/
    // chat_log.php
    String apiString = "chat_log.php";
    @GET(apiString)
    Call<ChatLogModel> getData();
}
