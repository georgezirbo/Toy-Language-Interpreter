package model.exp;

import exception.MyException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value v) {
        e = v;
    }

    public Value eval (MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> heaptbl) throws MyException {
        if(e.getType().equals(new BoolType()) && e.getType().equals(new IntType()))
            throw new MyException("The given value has a different type that the ones implemented");
        else
            return e;
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        return e.getType();
    }
    @Override
    public String toString() {
        return e.toString();
    }
}
