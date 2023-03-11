package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.PrgState;
import model.type.Type;

public class Nop implements IStmt{
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    public String toString(){
        return " nop;";
    }
}
