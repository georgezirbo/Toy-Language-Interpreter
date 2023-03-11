package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;
    public RefValue(int addr, Type type){
        address = addr;
        locationType = type;
    }
    public int getAddr() {return address;}
    public Type getType() { return new RefType(locationType);}
    public Type getLocationType(){return locationType;}
    public String toString(){return "(" + address + ", " + locationType.toString() + ")";}
}
