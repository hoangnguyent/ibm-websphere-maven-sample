package uk.co.sample.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.sample.exception.Information;

public class Result implements Serializable {

    private static final long       serialVersionUID = -758073018699993133L;
    private final List<Information> infos            = new ArrayList<>();
    private final List<Information> warns            = new ArrayList<>();

    //***** injection field *****
    //***** constructor *****
    //***** public method *****

    public void add(Result other) {
        infos.addAll(other.getInfos());
        warns.addAll(other.getWarns());
    }

    public void addInfo(String key, Object... parameters) {
        infos.add(new Information(key, parameters));
    }

    public void addWarn(String key, Object... parameters) {
        warns.add(new Information(key, parameters));
    }

    public boolean hasInfo() {
        return !infos.isEmpty();
    }

    public boolean hasWarn() {
        return !warns.isEmpty();
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

    public List<Information> getInfos() {
        return infos;
    }

    public List<Information> getWarns() {
        return warns;
    }

}
