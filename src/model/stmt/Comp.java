package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.type.Type;

public class Comp implements IStmt{
    IStmt first;
    IStmt second;

    public Comp(IStmt s1, IStmt s2) {
        first = s1;
        second = s2;
    }

    public IStmt getFirst(){
        return first;
    }
    public IStmt getSecond(){
        return second;
    }

    public PrgState execute(PrgState state)  throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(second);
        stk.push(first);
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        return second.typecheck(first.typecheck(typeEnv));
    }

    public String toString()  {
        return first.toString() + second.toString();
    }

}
