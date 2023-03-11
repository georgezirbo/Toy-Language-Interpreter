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

public class New implements IStmt{
    String var;
    Exp exp;
    public New(String variable, Exp expression){
        var = variable;
        exp = expression;
    }

    public PrgState execute(PrgState state) throws MyException {
        //retrieve used adts
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heap =  state.getHeapTable();
        //check if variable is defined in SymTable
        if(!symtbl.isVarDef(var))
            throw new MyException("New: The variable " + var + " is not defined in the SymTable");

        //check if var is RefType
        if(!(symtbl.lookup(var).getType() instanceof RefType))
            throw new MyException("New: The variable " + var + " is not RefType, but " + symtbl.lookup(var).getType().toString());

        Value value = exp.eval(symtbl, state.getHeapTable());
        //check if type of the variable matches the type of the expression
        if(!value.getType().equals(((RefValue)symtbl.lookup(var)).getLocationType()))
            throw new MyException("New: After the evaluation of the expression "+ exp.toString() + " the result type does not coincide with the type the variable " + var + " references to");

        symtbl.update(var, new RefValue(heap.get_next_address(), ((RefType) symtbl.lookup(var).getType()).getInner()));
        state.getHeapTable().put(heap.get_next_address(), value);
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typevar = typeEnv.lookup(var);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("[T] NEW: right hand side and left hand side have different types ");
    }

    public String toString(){
        return " new(" + var + ", " + exp.toString() + ");";
    }
}
