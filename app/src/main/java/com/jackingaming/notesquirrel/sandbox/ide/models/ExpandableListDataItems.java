package com.jackingaming.notesquirrel.sandbox.ide.models;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataItems {
    private static final String TAG = "ExpandableListDataItems";
    public static final String ROOT_DIRECTORY_NAME = "java";

    private static void createDirectoryIfNotExist(File directory) {
        if (!directory.exists()) {
            Log.i(TAG, "Directory does not exist, creating: " + directory.getPath());
            directory.mkdirs();
        }
    }

    private static List<String> directoriesAsString = new ArrayList<>();
    private static void initDirectoriesAsString() {
        directoriesAsString.add("scenes");
        directoriesAsString.add("entities");
        directoriesAsString.add("tiles");
    }

    public static HashMap<String, List<String>> getData(Context context) {
        // DIRECTORIES
        File rootDirectory = new File(context.getExternalFilesDir(null), ROOT_DIRECTORY_NAME);

        initDirectoriesAsString();

        for (String directoryName : directoriesAsString) {
            File file = new File(rootDirectory, directoryName);
            createDirectoryIfNotExist(file);
        }
        File secondLevelSubdirectory01 = new File(rootDirectory, "scenes");
        createDirectoryIfNotExist(secondLevelSubdirectory01);
        File secondLevelSubdirectory02 = new File(rootDirectory, "entities");
        createDirectoryIfNotExist(secondLevelSubdirectory02);
        File secondLevelSubdirectory03 = new File(rootDirectory, "tiles");
        createDirectoryIfNotExist(secondLevelSubdirectory03);

        // FILES
        String textToWrite =
                "public class GameboyColor {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello, world!\");\n" +
                        "    }\n" +
                        "}";
        File gameboyColorFile = new File(rootDirectory, "GameboyColor.txt");
        try {
            boolean createNew = gameboyColorFile.createNewFile();
            if (createNew) {
                Log.i(TAG, "The file was successfully created.");
            } else {
                Log.i(TAG, "The file already exists.");
            }

            FileWriter writer = new FileWriter(gameboyColorFile);
            writer.write(textToWrite);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> directories = new ArrayList<String>();
        List<String> files = new ArrayList<String>();
        List<String> directoriesAndFiles = new ArrayList<String>();
        for (File file : rootDirectory.listFiles()) {
            if (file.isDirectory()) {
                String directoryName = file.getName();
                directories.add(directoryName);
            } else if (file.isFile()) {
                int lengthOfDotTxtPrefix = 4;
                int indexWithoutDotTxtPrefix = file.getName().length() - lengthOfDotTxtPrefix;
                String fileNameWithoutDotTxtPrefix = file.getName().substring(0, indexWithoutDotTxtPrefix);
                files.add(fileNameWithoutDotTxtPrefix);
            }
        }
        directoriesAndFiles.addAll(directories);
        directoriesAndFiles.addAll(files);


        HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();

        // As we are populating List of fruits, vegetables and nuts, using them here
        // We can modify them as per our choice.
        // And also choice of fruits/vegetables/nuts can be changed
        List<String> fruits = new ArrayList<String>();
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Guava");
        fruits.add("Papaya");
        fruits.add("Pineapple");

        List<String> vegetables = new ArrayList<String>();
        vegetables.add("Tomato");
        vegetables.add("Potato");
        vegetables.add("Carrot");
        vegetables.add("Cabbage");
        vegetables.add("Cauliflower");

        List<String> nuts = new ArrayList<String>();
        nuts.add("Cashews");
        nuts.add("Badam");
        nuts.add("Pista");
        nuts.add("Raisin");
        nuts.add("Walnut");

        // Fruits are grouped under Fruits Items. Similarly the rest two are under
        // Vegetable Items and Nuts Items respecitively.
        // i.e. expandableDetailList object is used to map the group header strings to
        // their respective children using an ArrayList of Strings.
        expandableDetailList.put("Fruits Items", fruits);
        expandableDetailList.put("Vegetable Items", vegetables);
        expandableDetailList.put("Nuts Items", nuts);
        expandableDetailList.put(ROOT_DIRECTORY_NAME, directoriesAndFiles);
        return expandableDetailList;
    }
}