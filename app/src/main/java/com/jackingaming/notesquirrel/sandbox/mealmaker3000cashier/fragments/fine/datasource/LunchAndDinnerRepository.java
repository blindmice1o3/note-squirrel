package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource;

import java.util.ArrayList;
import java.util.List;

public class LunchAndDinnerRepository
        implements DataSourceRepository {
    @Override
    public List<String> retrieveDataSourceForQuadrant1() {
        // BURGERS
        List<String> dataSourceQuadrant1 = new ArrayList<String>();
        dataSourceQuadrant1.add("Bac Sws Btry Jack #1");
        dataSourceQuadrant1.add("Buttery Jack #2");
        dataSourceQuadrant1.add("Srd Jack #3");
        dataSourceQuadrant1.add("EMPTY");

        dataSourceQuadrant1.add("Jumbo Cheese #4");
        dataSourceQuadrant1.add("Jumbo Jack #4");
        dataSourceQuadrant1.add("Double Jack #5");
        dataSourceQuadrant1.add("Ult CB #6");

        dataSourceQuadrant1.add("Bac Ult CB #7");
        dataSourceQuadrant1.add("Spicy Sriracha #8");
        dataSourceQuadrant1.add("BLT Chz Bgr Choice");
        dataSourceQuadrant1.add("BBQ Bacon CB Choice");

        dataSourceQuadrant1.add("Jr Jumbo Jack");
        dataSourceQuadrant1.add("Jr Jumbo Jack Chz");
        dataSourceQuadrant1.add("Jr Bac CB");
        dataSourceQuadrant1.add("EMPTY");

        dataSourceQuadrant1.add("Ham Burger");
        dataSourceQuadrant1.add("Cheese Burger");
        dataSourceQuadrant1.add("Grilled Chz");
        dataSourceQuadrant1.add("Breakfast Jack");
        return dataSourceQuadrant1;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant2() {
        // CHICKEN
        List<String> dataSourceQuadrant2 = new ArrayList<String>();
        dataSourceQuadrant2.add("Spicy Chk #10");
        dataSourceQuadrant2.add("Spicy Chk Chz #10");
        dataSourceQuadrant2.add("Srd Grl Chk Club #11");
        dataSourceQuadrant2.add("Home Style Chk #12");

        dataSourceQuadrant2.add("Crsp Chk Strips #13");
        dataSourceQuadrant2.add("Nuggets (10) #14");
        dataSourceQuadrant2.add("Chicken Fajita #15");
        dataSourceQuadrant2.add("Chk Teri Bowl #16");

        dataSourceQuadrant2.add("Spicy Chk Strips Choice");
        dataSourceQuadrant2.add("Really Big Chkn Choice");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("EMPTY");

        dataSourceQuadrant2.add("Chicken Sand");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("EMPTY");

        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("Fish Sandwich");
        dataSourceQuadrant2.add("EMPTY");
        return dataSourceQuadrant2;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant3() {
        // TACOS
        List<String> dataSourceQuadrant3 = new ArrayList<String>();
        dataSourceQuadrant3.add("2 Tacos");
        dataSourceQuadrant3.add("Monster Taco");
        dataSourceQuadrant3.add("Bac Rnch Monster Taco");
        dataSourceQuadrant3.add("Nacho Monster Taco");

        dataSourceQuadrant3.add("3 Taco Combo");
        dataSourceQuadrant3.add("Monster Choice Cmb");
        dataSourceQuadrant3.add("Bacon Nacho Tacos");
        dataSourceQuadrant3.add("EMPTY");
        return dataSourceQuadrant3;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant4() {
        // SALADS
        List<String> dataSourceQuadrant4 = new ArrayList<String>();
        dataSourceQuadrant4.add("Club w/ Julienne");
        dataSourceQuadrant4.add("Grl Juli Chk Salad");
        dataSourceQuadrant4.add("Southwest w/ Julienne");
        dataSourceQuadrant4.add("Side Salad");
        return dataSourceQuadrant4;
    }
}
