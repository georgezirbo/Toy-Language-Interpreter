package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.PrgState;
import model.exp.Exp;
import model.exp.VarExp;
import model.type.Type;
import model.value.Value;

public class Print implements IStmt{
    Exp exp;

    public Print(Exp e) {
        exp = e;
    }


    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();
        MyIList<Value> out =state.getOut();

        out.add(exp.eval(symtbl, heaptbl));
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString(){
        return " print("+exp.toString()+");";
    }
}
