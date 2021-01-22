package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource;

import java.util.ArrayList;
import java.util.List;

public class EmptyRepository
        implements DataSourceRepository {
    @Override
    public List<String> retrieveDataSourceForQuadrant1() {
        // EMPTY
        List<String> dataSourceQuadrant1 = new ArrayList<String>();
        return dataSourceQuadrant1;
    }

    @Override
    public List<String> retrieveDataSourceForQuadrant2() {
        // EMPTY
        List<String> dataSourceQuadrant2 = new ArrayList<String>();
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
        // EMPTY
        List<String> dataSourceQuadrant4 = new ArrayList<String>();
        return dataSourceQuadrant4;
    }
}
