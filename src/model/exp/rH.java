package model.exp;

import exception.MyException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class rH  implements Exp {
    Exp exp;
    public rH(Exp expression){exp = expression;}
    @Override
    public Value eval(MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> heaptbl) throws MyException {
        Value value = exp.eval(symtbl, heaptbl);
        if(!(value instanceof RefValue))
            throw new MyException("After the evaluation of the expression "+ exp.toString() + " the result type is not a RefValue");
        if(!heaptbl.isVarDef(((RefValue)value).getAddr()))
            throw new MyException("The expression " + exp +" with the value: " + value + " does not point to any variable in HeapTable");
        return heaptbl.lookup(((RefValue)value).getAddr());
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type type= exp.typecheck(typeEnv);
        if (type instanceof RefType) {
            RefType reft =(RefType) type;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
