package com.tondz.matchlove.FirebaseContext;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.Model.Album;
import com.tondz.matchlove.Model.Location;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.Model.Video;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static Account account;
    public static Video video;
    public static Music music;
    public static int maxSpace = 10;
    public static boolean isNu=true;
    public static  boolean isNam=false;
    public static  int ageFrom = 18;
    public static  int ageTo = 30;
    public  static List<Account>accountList;
    public static  int indexAccount=0;
    public static Location yourLatLng;
    public static Account AccountChat;
    public static Album album;


    public static void showDialogImage(ImageView img, Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_image_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView imageView = dialog.findViewById(R.id.img_slider);
        Drawable drawable = img.getDrawable();
        imageView.setImageDrawable(drawable);
        dialog.show();
    }
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public static int calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }
}
