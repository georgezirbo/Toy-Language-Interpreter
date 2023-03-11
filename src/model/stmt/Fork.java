package model.stmt;

import exception.MyException;
import model.adt.*;
import model.type.Type;

public class Fork implements IStmt{
    private IStmt stmt;

    public Fork(IStmt stmt){
        this.stmt = stmt;
    }

    public PrgState execute(PrgState state) throws MyException {
        return new PrgState(new MyStack<>(), state.getSymTable().copy(),
                state.getOut(), state.getFileTable(), state.getHeapTable(), state.getLockTable(),
                stmt);
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.copy());
        return typeEnv;
    }

    public String toString() {
        return " fork(" + stmt.toString() + ")";
    }
}
