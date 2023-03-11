package model.adt;

import model.value.StringValue;

public class MyFileTable<K, V> extends MyDictionary<K, V>{

    public String toString(){
        StringBuilder str= new StringBuilder("{");
        for(K key : map.keySet()){
            str.append(key.toString()).append(",\n");
        }
        if (!map.isEmpty())
            str = new StringBuilder(str.substring(0, str.length() - 2));
        str.append("}\n");
        return str.toString();
    }
}
