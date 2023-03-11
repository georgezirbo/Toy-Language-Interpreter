package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.PrgState;
import model.type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
