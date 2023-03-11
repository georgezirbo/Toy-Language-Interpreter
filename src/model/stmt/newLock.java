package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyILockTable;
import model.adt.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class newLock implements IStmt{
    String var;

    public newLock(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return " newLock(" + var + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heaptbl =  state.getHeapTable();
        MyILockTable<Integer, Integer> locktbl = state.getLockTable();
        locktbl.put(locktbl.get_next_address(), -1);
        if(symtbl.isVarDef(var)){
            if(symtbl.lookup(var).getType().equals(new IntType())){
                symtbl.update(var, new IntValue(locktbl.get_next_address() - 1));
            } else throw new MyException("The variable does not evaluate to a IntValue");

        } else throw new MyException("The variable is not defined in the SymTable");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.isVarDef(var) && typeEnv.lookup(var).equals(new IntType())){
            return typeEnv;
        } else throw new MyException("The variable is not defined in the SymTable or the variable does not evaluate to a IntValue");

    }
}
