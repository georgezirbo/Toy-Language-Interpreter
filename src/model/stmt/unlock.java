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

public class unlock implements IStmt{
    String var;

    public unlock(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return " unlock(" + var +  ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heaptbl =  state.getHeapTable();
        MyILockTable<Integer, Integer> locktbl = state.getLockTable();
        if(symtbl.isVarDef(var) && symtbl.lookup(var).getType().equals(new IntType())){
            int idx = ((IntValue)symtbl.lookup(var)).getVal();
            if(!locktbl.isVarDef(idx))
                throw new MyException("The index is not defined in the LockTable");
            else if(locktbl.lookup(idx) == state.getId()){
                locktbl.update(idx, -1);
            }
        } else throw new MyException("The variable is not defined in the SymTable or the variable does not evaluate to a IntValue");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.isVarDef(var) && typeEnv.lookup(var).equals(new IntType())){
            return typeEnv;
        } else throw new MyException("The variable is not defined in the SymTable or the variable does not evaluate to a IntValue");
    }
}
