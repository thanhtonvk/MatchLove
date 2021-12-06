package com.tondz.matchlove.Activity.User.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.Model.ChatTemp;
import com.tondz.matchlove.R;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import static com.tondz.matchlove.Activity.User.ChatActivity.linearLayout;
import static com.tondz.matchlove.Activity.User.ChatActivity.prepareMediaPlayer;
import static com.tondz.matchlove.Activity.User.ChatActivity.setSeekBarTouch;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0, MSG_TYPE_RIGHT = 1, CALL_TYPE_LEFT = 2, CALL_TYPE_RIGHT = 3, MUSIC_TYPE_LEFT = 4, MUSIC_TYPE_RIGHT = 5, VIDEO_TYPE_LEFT = 6, VIDEO_TYPE_RIGHT = 7;
    private Context context;
    private List<ChatTemp> mChats;

    public ChatAdapter(Context context, List<ChatTemp> mChats) {
        this.mChats = mChats;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case MSG_TYPE_LEFT:
                view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
                break;
            case MSG_TYPE_RIGHT:
                view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
                break;
            case CALL_TYPE_LEFT:
                view = LayoutInflater.from(context).inflate(R.layout.call_item_left, parent, false);
                break;
            case CALL_TYPE_RIGHT:
                view = LayoutInflater.from(context).inflate(R.layout.call_item_right, parent, false);
                break;
            case MUSIC_TYPE_LEFT:
                view = LayoutInflater.from(context).inflate(R.layout.music_item_left, parent, false);
                break;
            case MUSIC_TYPE_RIGHT:
                view = LayoutInflater.from(context).inflate(R.layout.music_item_right, parent, false);
                break;
            case VIDEO_TYPE_LEFT:
                view = LayoutInflater.from(context).inflate(R.layout.video_item_left, parent, false);
                break;
            case VIDEO_TYPE_RIGHT:
                view = LayoutInflater.from(context).inflate(R.layout.video_item_right, parent, false);
                break;
        }
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ChatTemp chat = mChats.get(position);
        holder.show_message.setText(chat.getMessage());
        try {
            if (holder.show_message.getText().toString().contains("REQUESTCALL")) {
                holder.btn_accept_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        videoCall(holder.show_message.getText().toString().split("_")[0]);
                    }
                });
            }
            else if(holder.show_message.getText().toString().contains("REQUESTVIDEO")){
                holder.btn_accept_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playVideo(holder.show_message.getText().toString().split(";")[0]);
                    }
                });
            }else if(holder.show_message.getText().toString().contains("REQUESTMUSIC")){
                holder.btn_accept_music.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        linearLayout.setVisibility(View.VISIBLE);
                        String link = holder.show_message.getText().toString().split(";")[0];
                        prepareMediaPlayer(link);
                        setSeekBarTouch();
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mChats.get(position).getSender().equals(Common.account.getId())) {
            if (mChats.get(position).getMessage().contains("REQUESTCALL")) {
                return CALL_TYPE_RIGHT;
            } else if (mChats.get(position).getMessage().contains("REQUESTMUSIC")) {
                return MUSIC_TYPE_RIGHT;
            } else if (mChats.get(position).getMessage().contains("REQUESTVIDEO")) {
                return VIDEO_TYPE_RIGHT;
            } else {
                return MSG_TYPE_RIGHT;
            }
        } else {
            if (mChats.get(position).getMessage().contains("REQUESTCALL")) {
                return CALL_TYPE_LEFT;
            } else if (mChats.get(position).getMessage().contains("REQUESTMUSIC")) {
                return MUSIC_TYPE_LEFT;
            } else if (mChats.get(position).getMessage().contains("REQUESTVIDEO")) {
                return VIDEO_TYPE_LEFT;
            } else {
                return MSG_TYPE_LEFT;
            }
        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public Button btn_accept_call;
        public Button btn_accept_music;
        public Button btn_accept_video;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            btn_accept_call = itemView.findViewById(R.id.btn_accept_call);
            btn_accept_music = itemView.findViewById(R.id.btn_accept_music);
            btn_accept_video = itemView.findViewById(R.id.btn_accept_video);
        }
    }

    //set Video
    private void playVideo(String id) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_playvideo);
        YouTubePlayerView youTubePlayerView = dialog.findViewById(R.id.youtube_play);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(id, 0);
                super.onReady(youTubePlayer);
            }
        });
        dialog.show();
    }

    private void videoCall(String idRoom) {
        try {
            URL server = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(server)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(idRoom)
                .setWelcomePageEnabled(false).build();
        JitsiMeetActivity.launch(context.getApplicationContext(), options);
    }

}
