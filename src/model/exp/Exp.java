package model.exp;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.Type;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> heaptbl) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    String toString();
}
