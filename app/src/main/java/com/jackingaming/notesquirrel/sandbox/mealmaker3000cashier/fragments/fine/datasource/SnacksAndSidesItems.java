package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource;

import java.util.ArrayList;
import java.util.List;

public class SnacksAndSidesItems
        implements DataSourceRepository {
    @Override
    public List<String> retrieveDataSourceForQuadrant1() {
        // DESSERTS
        List<String> dataSourceQuadrant1 = new ArrayList<String>();
        dataSourceQuadrant1.add("EMPTY");
        dataSourceQuadrant1.add("Cheese Cake");
        dataSourceQuadrant1.add("Choc Overload");
        dataSourceQuadrant1.add("5 Mini Churros");

        dataSourceQuadrant1.add("EMPTY");
        dataSourceQuadrant1.add("Donut Holes");
        dataSourceQuadrant1.add("EMPTY");
        dataSourceQuadrant1.add("EMPTY");

        return dataSourceQuadrant1;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant2() {
        // POPULAR SIDES
        List<String> dataSourceQuadrant2 = new ArrayList<String>();
        dataSourceQuadrant2.add("Fries");
        dataSourceQuadrant2.add("Curly Fries");
        dataSourceQuadrant2.add("Wedges");
        dataSourceQuadrant2.add("Onion Rings");

        dataSourceQuadrant2.add("Single Taco");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("4 Burger Dippers");
        dataSourceQuadrant2.add("Bacon Cheddar Wedges");

        return dataSourceQuadrant2;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant3() {
        // EMPTY
        List<String> dataSourceQuadrant3 = new ArrayList<String>();

        return dataSourceQuadrant3;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant4() {
        // FINGER FOODS
        List<String> dataSourceQuadrant4 = new ArrayList<String>();
        dataSourceQuadrant4.add("EMPTY");
        dataSourceQuadrant4.add("1 Crsp Chk Strip");
        dataSourceQuadrant4.add("1 Egg Roll");
        dataSourceQuadrant4.add("3 Jal Poppers");

        dataSourceQuadrant4.add("5 Chicken Nuggets");
        dataSourceQuadrant4.add("4 Crsp Chk Strips");
        dataSourceQuadrant4.add("3 Egg Rolls");
        dataSourceQuadrant4.add("7 Jal Poppers");

        dataSourceQuadrant4.add("10 Chicken Nuggets");
        dataSourceQuadrant4.add("1 Spcy Chk Strip");
        dataSourceQuadrant4.add("EMPTY");
        dataSourceQuadrant4.add("EMPTY");
        return dataSourceQuadrant4;
    }
}
