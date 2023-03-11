package model.adt;

import exception.MyException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyHeap<K, V> implements MyIHeap<K, V>{
    private Map<K, V> map;
    private AtomicInteger next_address =  new AtomicInteger(1);

    public MyHeap() {
        map = new ConcurrentHashMap<>();
    }

    public synchronized int get_next_address(){
            return next_address.get();
    }

    public synchronized V put(K key, V value) {
        Lock lock = new ReentrantLock();
        lock.lock();
        next_address.getAndIncrement();
        lock.unlock();
        return map.put(key, value);

    }

    public synchronized V lookup(K key) throws MyException {
        Lock lock = new ReentrantLock();
        lock.lock();
        V v = map.get(key);
        if (v == null)
            throw new MyException("Variable not declared");
        lock.unlock();
        return v;
    }

    public synchronized boolean  isVarDef(K key) {
        return map.get(key) != null;
    }

    public synchronized void  update(K key, V value) {
        Lock lock = new ReentrantLock();
        lock.lock();
        map.put(key, value);
        lock.unlock();
    }

    @Override
    public synchronized void remove(K key) {
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
