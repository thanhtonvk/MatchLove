package com.tondz.matchlove.Activity.User.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.tondz.matchlove.Activity.User.Adapter.PhotoAdapter;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.MatchDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.Model.Match;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;


public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        onClick();
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    ViewPager viewPager;
    CircleIndicator circleIndicator;
    PhotoAdapter photoAdapter;
    Button btn_reload, btn_like, btn_nope;
    TextView tv_nope, tv_like;

    private List<String> loadListImage() {
        List<String> img = new ArrayList<>();
        for (String string : Common.accountList.get(Common.indexAccount).getImages()
        ) {
            if (!string.equals("")) {
                img.add(string);
            }
        }
        return img;
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.viewpager_slider_image);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        btn_like = view.findViewById(R.id.btn_love);
        btn_nope = view.findViewById(R.id.btn_nope);
        btn_reload = view.findViewById(R.id.btn_reload);
        tv_like = view.findViewById(R.id.tv_thich);
        tv_nope = view.findViewById(R.id.tv_nope);
        tv_name = view.findViewById(R.id.tv_name);
        tv_age = view.findViewById(R.id.tv_age);
        tv_hobbies1 = view.findViewById(R.id.tv_hobbies1);
        tv_hobbies2 = view.findViewById(R.id.tv_hobbies2);
        tv_hobbies3 = view.findViewById(R.id.tv_hobbies3);
        tv_space = view.findViewById(R.id.tv_space);
        matchDBContext = new MatchDBContext(getContext());
        random = new Random();
    }

    TextView tv_name, tv_age, tv_hobbies1, tv_hobbies2, tv_hobbies3, tv_space;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadData() {
        Account account = Common.accountList.get(Common.indexAccount);
        tv_name.setText(account.getFullName());
        int yearNow = java.time.LocalDate.now().getYear();
        int yearUser = Integer.parseInt(account.getDateOfBirth().split("-")[Common.account.getDateOfBirth().split("-").length - 1].trim());
        tv_age.setText(yearNow - yearUser + " tuổi");
        if (account.getHobbies() != null) {
            if (account.getHobbies().size() > 0 && account.getHobbies().get(0) != null) {
                tv_hobbies1.setText(account.getHobbies().get(0));
            }
            if (account.getHobbies().size() > 1 && account.getHobbies().get(1) != null) {
                tv_hobbies2.setText(account.getHobbies().get(1));
            }
            if (account.getHobbies().size() > 2 && account.getHobbies().get(2) != null) {
                tv_hobbies3.setText(account.getHobbies().get(2));
            }
        }
        if (account.getLocation() != null && Common.account.getLocation() != null) {
            int space = Common.calculateDistanceInKilometer(account.getLocation().getLatitude(), account.getLocation().getLongtitude(), Common.account.getLocation().getLatitude(), Common.account.getLocation().getLongtitude());
            tv_space.setText("Cách bạn " + space + "km");
        }
        photoAdapter = new PhotoAdapter(getContext(), loadListImage());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    MatchDBContext matchDBContext;

    private void onClick() {
        btn_nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_nope.setVisibility(View.VISIBLE);
                CountDownTimer count = new CountDownTimer(1500, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Common.indexAccount++;
                        if (Common.indexAccount < Common.accountList.size()) {
                            loadData();
                        } else {
                            Common.indexAccount = 0;
                            loadData();
                        }
                        tv_nope.setVisibility(View.GONE);
                    }
                }.start();

            }
        });
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMatch();
                tv_like.setVisibility(View.VISIBLE);
                CountDownTimer countDownTimer = new CountDownTimer(1500, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Common.indexAccount++;
                        if (Common.indexAccount < Common.accountList.size()) {
                            loadData();
                        } else {
                            Common.indexAccount = 0;
                            loadData();
                        }
                        tv_like.setVisibility(View.GONE);
                    }
                }.start();

            }
        });
    }

    Random random;

    public void setMatch() {
        Match match = new Match();
        match.setAccount1(Common.account);
        match.setAccount2(Common.accountList.get(Common.indexAccount));
        match.setIdUser1Accept(true);
        match.setIdUser2Accept(false);
        match.setId(random.nextInt()+"");
        match.setBlocked(false);
        matchDBContext.Update(match);
    }

}