package com.social.chenl.entity.inc;

import java.io.Serializable;

/**
 * author : happyc
 * e-mail : bafs.jy@live.com
 * time   : 2019/04/21
 * desc   :
 * version: 1.0
 */
public class Episode implements Serializable {

    /**
     * title : 第01集
     * url : https://pan.jiningwanjun.com/v/mXi9aNbF
     */
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
