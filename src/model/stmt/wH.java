package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.PrgState;
import model.exp.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;


public class wH implements IStmt{
    String var;
    Exp exp;

    public wH(String variable, Exp expression){
        var = variable;
        exp = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();
        if(! symtbl.isVarDef(var))
            throw new MyException("wH: The variable " + var + "was not declared in the SymTable");
        if(! (symtbl.lookup(var) instanceof RefValue))
            throw new MyException("wH: The variable " + var + "is not a RefValue");
        RefValue  ref = (RefValue) symtbl.lookup(var);
        if(! heaptbl.isVarDef(ref.getAddr()))
            throw new MyException("wH: The address of the variable " + var + "(#" + ref.getAddr() + ") was not declared in the SymTable");
        Value value = exp.eval(symtbl, heaptbl);
        if(! value.getType().equals(ref.getLocationType()))
            throw new MyException("wH: After the evaluation of the expression "+ exp.toString() + " the result type does not match the type of RefValue " + var + " which points to a " + ref.getLocationType());
        heaptbl.update(ref.getAddr(), value);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = typeEnv.lookup(var);
        if(t1 instanceof RefType)
            return typeEnv;
        else throw new MyException("[T] wH: The variable " + var + " is not a RefValue");
    }

    public String toString(){
        return " wH(" + var + ", " + exp.toString() + ");";
    }
}
