package sample.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SearchParametr {
    private Integer code;
    private String name;

    public SearchParametr(Integer code, String name) {
        this.code = code;
        this.name = name;

    }

    public String toString() {
        return this.name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
