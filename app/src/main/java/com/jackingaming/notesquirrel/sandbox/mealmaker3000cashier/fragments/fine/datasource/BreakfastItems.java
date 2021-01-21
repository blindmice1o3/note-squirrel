package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource;

import java.util.ArrayList;
import java.util.List;

public class BreakfastItems
        implements DataSourceRepository {

    @Override
    public List<String> retrieveDataSourceForQuadrant1() {
        // COMBOS
        List<String> dataSourceQuadrant1 = new ArrayList<String>();
        dataSourceQuadrant1.add("Suprm Crosnt #21");
        dataSourceQuadrant1.add("Ssg Crosnt #22");
        dataSourceQuadrant1.add("Grd Ssg Burrito #23");
        dataSourceQuadrant1.add("Meat Lvr Burrito #24");

        dataSourceQuadrant1.add("SEC Bisc #25");
        dataSourceQuadrant1.add("BEC Bisc #25");
        dataSourceQuadrant1.add("Loaded Bfst Sand #26");
        dataSourceQuadrant1.add("Extreme Ssg #27");

        dataSourceQuadrant1.add("Ult Bfst #28");
        dataSourceQuadrant1.add("Grl Srd Swiss #29");
        dataSourceQuadrant1.add("Bfst Platter #30");
        dataSourceQuadrant1.add("EMPTY");
        return dataSourceQuadrant1;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant2() {
        // ADDITIONAL ITEMS
        List<String> dataSourceQuadrant2 = new ArrayList<String>();
        dataSourceQuadrant2.add("Breakfast Jack");
        dataSourceQuadrant2.add("Sausage Bfst Jack");
        dataSourceQuadrant2.add("Bacon Bfst Jack");
        dataSourceQuadrant2.add("EMPTY");

        dataSourceQuadrant2.add("Srd Bfst Sand");
        dataSourceQuadrant2.add("Hmst Chk Bisc");
        dataSourceQuadrant2.add("Mini Pancakes");
        dataSourceQuadrant2.add("EMPTY");

        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("Donut Holes");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("EMPTY");
        return dataSourceQuadrant2;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant3() {
        // EXTRAS
        List<String> dataSourceQuadrant3 = new ArrayList<String>();
        dataSourceQuadrant3.add("Hash Browns");
        dataSourceQuadrant3.add("Bacon");
        dataSourceQuadrant3.add("Sausage");
        dataSourceQuadrant3.add("Ham");

        dataSourceQuadrant3.add("Croissant");
        dataSourceQuadrant3.add("Biscuit");
        dataSourceQuadrant3.add("Tortilla");
        dataSourceQuadrant3.add("EMPTY");

        dataSourceQuadrant3.add("Shell Egg");
        dataSourceQuadrant3.add("Scramble Egg");
        dataSourceQuadrant3.add("Egg White");
        dataSourceQuadrant3.add("EMPTY");
        return dataSourceQuadrant3;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant4() {
        // BREAKFAST DRINKS
        List<String> dataSourceQuadrant4 = new ArrayList<String>();
        dataSourceQuadrant4.add("Coffee");
        dataSourceQuadrant4.add("Lrg Coffee");
        dataSourceQuadrant4.add("Decaf Coffee");
        dataSourceQuadrant4.add("Lrg Decaf Coffee");

        dataSourceQuadrant4.add("Orange Juice");
        dataSourceQuadrant4.add("Apple Juice");
        dataSourceQuadrant4.add("Milk");
        dataSourceQuadrant4.add("EMPTY");
        return dataSourceQuadrant4;
    }
}
