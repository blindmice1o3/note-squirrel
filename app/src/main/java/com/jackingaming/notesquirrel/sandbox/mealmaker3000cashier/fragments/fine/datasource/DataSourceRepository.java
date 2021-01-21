package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource;

import java.util.List;

public interface DataSourceRepository {
    List<String> retrieveDataSourceForQuadrant1();
    List<String> retrieveDataSourceForQuadrant2();
    List<String> retrieveDataSourceForQuadrant3();
    List<String> retrieveDataSourceForQuadrant4();
}
