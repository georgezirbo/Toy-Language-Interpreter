package model.exp;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.Type;
import model.value.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String s) {
        id = s;
    }

    public Value eval(MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> heaptbl) throws MyException
    {
        if(!symtbl.isVarDef(id))
            throw new MyException("The given variable is not defined");
        return symtbl.lookup(id);
    }
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        return typeEnv.lookup(id);
    }
    public String toString(){
        return id.toString();
    }

}
