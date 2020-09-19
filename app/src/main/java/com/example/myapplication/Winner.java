package com.example.myapplication;

import java.util.Date;

public class Winner {

    private String name = "";
    private int player_number = 0;
    private int numOfMoves = 99;
    private double lat = 0;
    private double lon = 0;
    private String time = "";

    public Winner() {
        this.time = java.text.DateFormat.getDateTimeInstance().format(new Date());
    }

    public Winner(String name, int player_number, int numOfMoves, double lat, double lon, String time) {
        this.name = name;
        this.player_number = player_number;
        this.numOfMoves = numOfMoves;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Winner setName(String name) {
        this.name = name;
        return this;
    }

    public int getPlayer_number() {
        return player_number;
    }

    public Winner setPlayer_number(int player_number) {
        this.player_number = player_number;
        return this;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }

    public Winner setNumOfMoves(int numOfMoves) {
        this.numOfMoves = numOfMoves;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Winner setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Winner setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Winner setTime(String time) {
        this.time = time;
        return this;
    }
}
