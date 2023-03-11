package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.Value;

public class VarDecl implements IStmt{
    String name;
    Type type;

    public VarDecl(String n, Type t) {
        name = n;
        type = t;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        symTbl.put(name, type.defaultValue());
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Object typ;
        typeEnv.put(name,type);
        return typeEnv;
    }

    public String toString(){
        return " " + type.toString()+ " " + name.toString()  + ";";
     }
}
