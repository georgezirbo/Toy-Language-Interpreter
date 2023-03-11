package model.adt;

import exception.MyException;

import java.util.Map;

public interface MyIHeap <K, V>{
    int get_next_address();
    V put(K key, V value);
    V lookup(K key) throws MyException;
    boolean isVarDef(K key);
    void update(K key, V value);
    void remove(K key);
    Map<K, V> getMap();
    void setMap(Map<K, V> newmap);
}
