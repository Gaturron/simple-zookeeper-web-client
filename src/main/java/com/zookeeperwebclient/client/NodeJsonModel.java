package com.zookeeperwebclient.client;

import org.apache.zookeeper.data.Stat;

import java.util.Map;

public class NodeJsonModel {

    private Map<String, Map<String, String>> childrens;
    private Map<String, Map<String, String>> aclNodes;
    private String data;
    private Stat2 stat;

    public Stat2 getStat() {
        return stat;
    }

    public void setStat(Stat2 stat) {
        this.stat = stat;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String,  Map<String, String>> getChildrens() {
        return childrens;
    }

    public void setChildrens(Map<String,  Map<String, String>> childrens) {
        this.childrens = childrens;
    }

    public String getData() {
        return data;
    }

    public Map<String, Map<String, String>> getAclNodes() {
        return aclNodes;
    }

    public void setAclNodes(Map<String, Map<String, String>> aclNodes) {
        this.aclNodes = aclNodes;
    }

    public boolean contains(String s){
        boolean res = false;
        res = res || data.contains(s);
        res = res || stat.getCtimeLocalDateTime().contains(s);
        res = res || stat.getCzxid().contains(s);
        res = res || stat.getEphemeralOwner().contains(s);

        return res;
    }
}
