package com.logbook.wira.flipviewpager.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wira on 1/10/16.
 */
public class Makanan {
    private int icon;
    private String name;
    private int background;
    private List<String> interests = new ArrayList<>();

    public Makanan(int icon, String name, int background, String... interest) {
        this.icon = icon;
        this.name = name;
        this.background = background;
        interests.addAll(Arrays.asList(interest));
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getBackground() {
        return background;
    }

    public List<String> getInterests() {
        return interests;
    }
}
