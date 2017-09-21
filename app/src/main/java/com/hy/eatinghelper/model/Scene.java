/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.hy.eatinghelper.model;

/**
 * Created by huyin on 2017/9/12.
 */

public class Scene {

    private int id;

    private String name;

    public Scene(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
