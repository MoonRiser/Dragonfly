package com.example.dragonfly.bean;


import java.util.List;

public class Categories {

    private boolean error;
    private List<Results> results;

    public class Results {
        private String _id;
        private String en_name;
        private String name;
        private int rank;

        @Override
        public String toString() {
            return "Results{" +
                    "_id='" + _id + '\'' +
                    ", en_name='" + en_name + '\'' +
                    ", name='" + name + '\'' +
                    ", rank=" + rank +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "Categories{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}