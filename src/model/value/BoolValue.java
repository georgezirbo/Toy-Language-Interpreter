package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value{
    boolean val;
    public BoolValue(){
        val = false;
    }
    public BoolValue(boolean v){
        val = v;
    }
    public boolean getVal(){
        return val;
    }
    public String toString(){
        return Boolean.toString(val);
    }
    public Type getType() { return new BoolType();}
    public boolean equals(BoolValue v){
            return val == v.getVal();
    }
}
