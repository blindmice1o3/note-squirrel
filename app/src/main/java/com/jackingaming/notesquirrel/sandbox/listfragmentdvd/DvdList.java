package com.jackingaming.notesquirrel.sandbox.listfragmentdvd;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.util.ArrayList;

public class DvdList extends ArrayList<Dvd> {

    public DvdList(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "DvdList constructor START");

        Bitmap imageCompilation = BitmapFactory.decodeResource(resources, R.drawable.stoner_movies_on_netflix);

        Bitmap imageUpInSmoke = Bitmap.createBitmap(imageCompilation, 0, 0, 204, 308);
        Bitmap imageFastTimeAtRidgemontHigh = Bitmap.createBitmap(imageCompilation, 207, 0, 225, 307);
        Bitmap imageDazedAndConfused = Bitmap.createBitmap(imageCompilation, 436, 0, 204, 307);
        Bitmap imageFriday = Bitmap.createBitmap(imageCompilation, 646, 0, 195, 306);
        Bitmap imageHalfBaked = Bitmap.createBitmap(imageCompilation, 0, 312, 204, 321);
        Bitmap imageJayAndSilentBobStrikeBack = Bitmap.createBitmap(imageCompilation, 209, 312, 219, 321);
        Bitmap imageGrandmasBoy = Bitmap.createBitmap(imageCompilation, 436, 312, 205, 321);
        Bitmap imageHaroldAndKumarGoToWhiteCastle = Bitmap.createBitmap(imageCompilation, 642, 312, 199, 321);

        add(new Dvd(
                imageUpInSmoke,
                "Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "Harold & Kumar Go to White Castle"
        ));

        Log.d(MainActivity.DEBUG_TAG, "DvdList constructor FINISHED");
    }

}