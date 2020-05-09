package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource;

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
                "0 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "1 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "2 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "3 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "4 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "5 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "6 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "7 Harold & Kumar Go to White Castle"
        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        add(new Dvd(
                imageUpInSmoke,
                "8 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "9 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "10 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "11 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "12 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "13 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "14 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "15 Harold & Kumar Go to White Castle"
        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        add(new Dvd(
                imageUpInSmoke,
                "16 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "17 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "18 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "19 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "20 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "21 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "22 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "23 Harold & Kumar Go to White Castle"
        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        add(new Dvd(
                imageUpInSmoke,
                "24 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "25 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "26 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "27 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "28 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "29 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "30 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "31 Harold & Kumar Go to White Castle"
        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        add(new Dvd(
                imageUpInSmoke,
                "32 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "33 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "34 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "35 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "36 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "37 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "38 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "39 Harold & Kumar Go to White Castle"
        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        add(new Dvd(
                imageUpInSmoke,
                "40 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "41 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "42 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "43 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "44 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "45 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "46 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "47 Harold & Kumar Go to White Castle"
        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        add(new Dvd(
                imageUpInSmoke,
                "48 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "49 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "50 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "51 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "52 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "53 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "54 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "55 Harold & Kumar Go to White Castle"
        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        add(new Dvd(
                imageUpInSmoke,
                "56 Up in Smoke"
        ));
        add(new Dvd(
                imageFastTimeAtRidgemontHigh,
                "57 Fast Time at Ridgemont High"
        ));
        add(new Dvd(
                imageDazedAndConfused,
                "58 Dazed and Confused"
        ));
        add(new Dvd(
                imageFriday,
                "59 Friday"
        ));
        add(new Dvd(
                imageHalfBaked,
                "60 Half Baked"
        ));
        add(new Dvd(
                imageJayAndSilentBobStrikeBack,
                "61 Jay and Silent Bob Strike Back"
        ));
        add(new Dvd(
                imageGrandmasBoy,
                "62 Grandma's Boy"
        ));
        add(new Dvd(
                imageHaroldAndKumarGoToWhiteCastle,
                "63 Harold & Kumar Go to White Castle"
        ));

        Log.d(MainActivity.DEBUG_TAG, "DvdList constructor FINISHED");
    }

}