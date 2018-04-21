package com.zookeeperwebclient.client;

import java.util.Map;

public class FindJsonModel {

    private Map<String, String> occurency;
    private String url;

    public Map<String, String> getOccurency() {
        return occurency;
    }

    public void setOccurency(Map<String, String> occurency) {
        this.occurency = occurency;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
