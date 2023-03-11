package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    String val;
    public StringValue(String v){
        val = v;
    }
    public String getVal(){
        return val;
    }
    public String toString(){
        return  val;
    }
    public Type getType() {
        return new StringType();
    }
    public boolean equals(StringValue v){
        return val.equals(v.getVal());
    }
}
