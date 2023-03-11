package model.adt;

import exception.MyException;

import java.util.Enumeration;
import java.util.Map;

public interface MyIDictionary<K, V> {
    V put(K key, V value);
    V lookup(K key) throws MyException;
    boolean isVarDef(K key);
    void update(K key, V value);
    void remove(K key);
    Map<K, V> getMap();
    void setMap(Map<K, V> map);
    MyIDictionary<K, V> copy();
}
