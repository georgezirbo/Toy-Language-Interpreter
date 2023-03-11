package model.adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyList<T> implements MyIList<T>{
    private List<T> out;
    public MyList(){
        out = new Vector<T>();
    }
    public void add(T elem){
        out.add(elem);
    }
    public String toString(){
        return out.toString();
    }

    public List<T> getList() {
        return out;
    }
}
