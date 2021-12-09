package models;

import java.util.List;

public class DataBase<T> {
    private List<T> data;


    public DataBase(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
