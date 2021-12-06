package com.tondz.matchlove.Activity.User;

import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.Admin.Adapter.VideoListAdapter;
import com.tondz.matchlove.Activity.User.Adapter.ChatAdapter;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.MatchDBContext;
import com.tondz.matchlove.FirebaseContext.MusicDBContext;
import com.tondz.matchlove.FirebaseContext.VideoDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.Model.ChatTemp;
import com.tondz.matchlove.Model.Match;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.Model.Video;
import com.tondz.matchlove.R;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static LinearLayout linearLayout;
    private static MediaPlayer mediaPlayer;
    private static TextView tv_duration_music;
    Button btn_back, btn_videocall, btn_menu, btn_playmusic, btn_add, btn_emoji, btn_send;
    TextView tv_name_music;
    public static TextView tv_current_music;
    public static SeekBar seekBar_music;
    EditText edt_content;
    //Temp
    DatabaseReference reference;
    Intent intent;
    RecyclerView recyclerView;
    List<ChatTemp> chatList;
    ChatAdapter chatAdapter;
    public static String REQUEST = "";
    String userID;
    AccountDBContext dbContext;
    TextView tv_name;
    ImageView img_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        handler = new Handler();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        seekBar_music.setMax(100);
        //temp
        intent = getIntent();
        userID = intent.getStringExtra("userID");
        onClick();
        reference = FirebaseDatabase.getInstance().getReference("User").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessage(Common.account.getId(), userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //show mess
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadInfor();
    }

    private void initView() {
        linearLayout = findViewById(R.id.layout_playmusic);
        btn_back = findViewById(R.id.btn_back);
        btn_videocall = findViewById(R.id.btn_videocall);
        btn_menu = findViewById(R.id.btn_menu);
        btn_playmusic = findViewById(R.id.btn_playmusic);
        btn_add = findViewById(R.id.btn_add);
        btn_emoji = findViewById(R.id.btn_emoji);
        tv_name_music = findViewById(R.id.tv_name_music);
        tv_current_music = findViewById(R.id.tv_current_music);
        tv_duration_music = findViewById(R.id.tv_duration_music);
        seekBar_music = findViewById(R.id.seekbar_music);
        btn_send = findViewById(R.id.btn_send);
        edt_content = findViewById(R.id.edt_content);
        recyclerView = findViewById(R.id.lv_chat);
        dbContext = new AccountDBContext();
        tv_name = findViewById(R.id.tv_name);
        img_avatar = findViewById(R.id.img_avatar);

    }

    private void loadInfor() {
        tv_name.setText(Common.AccountChat.getFullName());
        String string = Common.AccountChat.getAvatar();
        if (!string.equals("")) {
            dbContext.getStorageReference().child(Common.AccountChat.getId()).child(string).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(ChatActivity.this).load(uri).into(img_avatar);
                }
            });
        }
    }

    private void onClick() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edt_content.getText().toString();
                if (!message.equals("")) {
                    sendMessage(Common.account.getId(), userID, message);
                } else {
                    Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
                }
                edt_content.setText("");
            }
        });
        btn_videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REQUEST = Common.account.getId() + userID + "_REQUESTCALL";
                sendMessage(Common.account.getId(), userID, REQUEST);
                videoCall(REQUEST.split("_")[0]);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd();
            }
        });
        btn_playmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    btn_playmusic.setBackgroundResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    btn_playmusic.setBackgroundResource(R.drawable.ic_pause);
                    updateSeekBar();
                }
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

    }
    private void dialog(){
        Dialog dialog = new Dialog(ChatActivity.this);
        dialog.setContentView(R.layout.dialog_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_name = dialog.findViewById(R.id.tv_name);
        ImageView img_avatar  = dialog.findViewById(R.id.img_avatar);
        ImageView btn_videocall = dialog.findViewById(R.id.btn_videocall);
        ImageView btn_profile = dialog.findViewById(R.id.btn_profile);
        ImageView btn_block = dialog.findViewById(R.id.btn_block);
        tv_name.setText(Common.AccountChat.getFullName());
        String string = Common.AccountChat.getAvatar();
        if (!string.equals("")) {
            dbContext.getStorageReference().child(Common.AccountChat.getId()).child(string).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(ChatActivity.this).load(uri).into(img_avatar);
                }
            });
        }
        btn_videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REQUEST = Common.account.getId() + userID + "_REQUESTCALL";
                sendMessage(Common.account.getId(), userID, REQUEST);
                videoCall(REQUEST.split("_")[0]);
                dialog.dismiss();
            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatActivity.this,ViewProfileActivity.class));
            }
        });
        btn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void block(){
        MatchDBContext matchDBContext = new MatchDBContext(ChatActivity.this);
        matchDBContext.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()
                     ) {
                    Match match = dataSnapshot.getValue(Match.class);
                    if((match.getAccount1().getId().contains(Common.account.getId())&&match.getAccount2().getId().contains(Common.AccountChat.getId())||
                            (match.getAccount2().getId().contains(Common.account.getId())&&match.getAccount1().getId().contains(Common.AccountChat.getId()))))
                    {
                        match.setBlocked(true);
                        matchDBContext.Update(match);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Custom dialog add
    private void dialogAdd() {
        Dialog dialog = new Dialog(ChatActivity.this);
        dialog.setContentView(R.layout.dialog_more_chat);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Button btn_music = dialog.findViewById(R.id.btn_music);
        Button btn_video = dialog.findViewById(R.id.btn_video);
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialogListMusic();
            }
        });
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialogListVideo();
            }
        });
    }

    Music music;
    ListView lvMusic;
    AutoCompleteTextView autoCompleteTextView;

    //custom dialogMusic
    private void dialogListMusic() {
        Dialog dialog = new Dialog(ChatActivity.this);
        dialog.setContentView(R.layout.dialog_list_music);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        lvMusic = dialog.findViewById(R.id.lv_music);
        autoCompleteTextView = dialog.findViewById(R.id.autoComplete_music);
        loadImage();
        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                linearLayout.setVisibility(View.VISIBLE);
                music = musicList.get(i);
                tv_name_music.setText(music.getName());
                REQUEST = music.getLink() + ";REQUESTMUSIC";
                sendMessage(Common.account.getId(), userID, REQUEST);
                prepareMediaPlayer(music.getLink());
                setSeekBarTouch();
            }
        });
    }

    MusicDBContext musicDBContext;
    List<Music> musicList;

    private void loadImage() {
        musicDBContext = new MusicDBContext();
        musicDBContext.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                musicList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Music music = dataSnapshot.getValue(Music.class);
                    musicList.add(music);
                }
                ArrayAdapter<Music> musicArrayAdapter = new ArrayAdapter<>(ChatActivity.this, android.R.layout.simple_list_item_1, musicList);
                lvMusic.setAdapter(musicArrayAdapter);
                autoCompleteTextView.setAdapter(musicArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    Video video;
    ListView lvVideo;
    AutoCompleteTextView auto_video;
    private void dialogListVideo() {
        Dialog dialog = new Dialog(ChatActivity.this);
        dialog.setContentView(R.layout.dialog_list_video);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lvVideo = dialog.findViewById(R.id.lv_video);
        auto_video = dialog.findViewById(R.id.autoComplete_video);
        setVideoList();
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                linearLayout.setVisibility(View.VISIBLE);
                video = videoList.get(i);
                REQUEST = video.getId() + ";REQUESTVIDEO";
                sendMessage(Common.account.getId(), userID, REQUEST);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    VideoDBContext videoDBContext;
    List<Video> videoList;
    VideoListAdapter videoListAdapter;
    private void setVideoList() {
        videoDBContext= new VideoDBContext();
        videoDBContext.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videoList = new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()
                     ) {
                    Video video= dataSnapshot.getValue(Video.class);
                    videoList.add(video);
                }
                videoListAdapter = new VideoListAdapter(ChatActivity.this,videoList);
                lvVideo.setAdapter(videoListAdapter);
                auto_video.setAdapter(videoListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String reciever, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", reciever);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(String myID, String userID) {
        chatList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatTemp chat = dataSnapshot.getValue(ChatTemp.class);
                    if (chat.getReciever().equals(myID) && chat.getSender().equals(userID) || chat.getReciever().equals(userID) && chat.getSender().equals(myID)) {
                        chatList.add(chat);
                        chatAdapter = new ChatAdapter(ChatActivity.this, chatList);
                        recyclerView.setAdapter(chatAdapter);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        JitsiMeetActivity.launch(getApplicationContext(), options);
    }

    Handler handler;

    //set play music
    public static String miliSecondsToTime(long miliSeconds) {
        String timerString = "";
        String secondStirng;
        int hour = (int) (miliSeconds / (1000 * 60 * 60));
        int minutes = (int) (miliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((miliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hour > 0) {
            timerString = hour + ":";
        }
        if (seconds < 10) {
            secondStirng = "0" + seconds;
        } else {
            secondStirng = "" + seconds;
        }
        timerString = timerString + minutes + ":" + secondStirng;
        return timerString;

    }

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            seekBar_music.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            tv_current_music.setText(miliSecondsToTime(currentDuration));
        }
    };

    public static void prepareMediaPlayer(String link) {
        String mediaUrl = link;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(mediaUrl);
            mediaPlayer.prepare();
            tv_duration_music.setText(miliSecondsToTime(mediaPlayer.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setSeekBarTouch() {
        seekBar_music.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                tv_current_music.setText(miliSecondsToTime(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
    }
}