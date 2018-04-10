package com.zookeeperwebclient.client;

import org.apache.zookeeper.data.Stat;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Stat2 {

    private DateTimeFormatter format = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    private Stat stat;
    
    public Stat2(Stat stat){
        this.stat = stat;
    }

    public String getCzxid() {
        return ToHex(stat.getCzxid());
    }

    public String getMzxid() {
        return ToHex(stat.getMzxid());
    }
    public String getCtimeLocalDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(stat.getCtime()), ZoneId.systemDefault()).format(format);
    }
    public long getCtime() {
        return stat.getCtime();
    }
    public String getMtimeLocalDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(stat.getMtime()), ZoneId.systemDefault()).format(format);
    }
    public long getMtime() {
        return stat.getMtime();
    }
    public int getVersion() {
        return stat.getVersion();
    }
    public int getCversion() {
        return stat.getCversion();
    }
    public int getAversion() {
        return stat.getAversion();
    }
    public String getEphemeralOwner() {
        return ToHex(stat.getEphemeralOwner());
    }
    public int getDataLength() {
        return stat.getDataLength();
    }
    public int getNumChildren() {
        return stat.getNumChildren();
    }
    public String getPzxid() {
        return ToHex(stat.getPzxid());
    }

    private String ToHex(int i){
        return "0x"+Integer.toHexString(i);
    }

    private String ToHex(long l){
        return "0x"+Long.toHexString(l);
    }
}
