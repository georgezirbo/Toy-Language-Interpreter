package model.adt;

import exception.MyException;

import java.util.Map;

public interface MyILockTable<K, V> {
    int get_next_address();
    V put(K key, V value);
    V lookup(K key) throws MyException;
    boolean isVarDef(K key);
    void update(K key, V value);
    Map<K, V> getMap();
}

