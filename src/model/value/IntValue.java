package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value{
    int val;
    public IntValue(int v){
        val = v;
    }
    public int getVal(){
        return val;
    }
    public String toString(){
        return Integer.toString(val);
    }
    public Type getType() { return new IntType();}
    public boolean equals(IntValue v){
        return val == v.getVal();
    }
}
