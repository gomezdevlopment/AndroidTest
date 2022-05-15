package com.gomezdevlopment.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.gomezdevlopment.androidtest.MainActivity;
import com.gomezdevlopment.androidtest.api.ChatApiCall;
import com.gomezdevlopment.androidtest.api.ChatLogMessageModel;
import com.gomezdevlopment.androidtest.api.ChatLogModel;
import com.gomezdevlopment.androidtest.databinding.ActivityChatBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private ChatAdapter chatAdapter;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter();

        binding.chatRecyclerView.setAdapter(chatAdapter);
        binding.chatRecyclerView.setHasFixedSize(true);
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        List<ChatLogMessageModel> chatList = new ArrayList<>();
        makeApiCall(chatList);

//        List<ChatLogMessageModel> tempList = new ArrayList<>();
//        ChatLogMessageModel chatLogMessageModel = new ChatLogMessageModel();
//        chatLogMessageModel.message = "This is test data. Please retrieve real data.";
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        chatAdapter.setChatLogMessageModelList(tempList);

    }

    private void makeApiCall(List<ChatLogMessageModel> chatList){
        String baseUrl = "http://dev.rapptrlabs.com/Tests/scripts/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ChatApiCall chatApiCall = retrofit.create(ChatApiCall.class);
        Call<ChatLogModel> call = chatApiCall.getData();

        call.enqueue(new Callback<ChatLogModel>() {
            @Override
            public void onResponse(Call<ChatLogModel> call, Response<ChatLogModel> response) {
                if(response.code() != 200){
                    Toast.makeText(binding.getRoot().getContext(), "Check Connection", Toast.LENGTH_SHORT).show();
                }else{
                    for(ChatLogMessageModel chat: response.body().data){
                        System.out.println(chat);
                        chatList.add(chat);
                        chatAdapter.setChatLogMessageModelList(chatList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatLogModel> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
