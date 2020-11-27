package com.polovni_automobili.auta.dto;

import java.util.ArrayList;
import java.util.List;

public class Entities<T> {

    private List<T> entities = new ArrayList<T>();
    private long total;

    public Entities() {
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "Entities{" +
                "entities=" + entities +
                ", total=" + total +
                '}';
    }
}
