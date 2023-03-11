package model.adt;

import exception.MyException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MyDictionary<K,V> implements MyIDictionary<K,V> {
    protected Map<K, V> map;

    public MyDictionary() {
        map = new ConcurrentHashMap<>();
    }
    public MyDictionary(Map<K, V> newmap) {
        map = newmap;
    }
    public MyDictionary<K, V> copy(){
        return new MyDictionary(map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public V lookup(K key) throws MyException {
        V v = map.get(key);
        if (v == null)
            throw new MyException("Variable not declared");
        return v;
    }

    public boolean isVarDef(K key) {
        return map.get(key) != null;
    }

    public void update(K key, V value) {
        map.put(key, value);//????
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    public String toString() {
        return map.toString();
    }

    public Map<K, V> getMap(){
        return map;
    }

    public void setMap(Map<K, V> newmap) {
        map = newmap;
    }

}
