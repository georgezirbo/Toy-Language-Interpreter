package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.exp.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class If implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public If (Exp e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    public PrgState execute (PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();
        Value cond = exp.eval(symtbl, heaptbl);
        if (!cond.getType().equals(new BoolType()))
            throw new MyException("If: Conditional expression is not boolean");
        else {
            BoolValue c = (BoolValue) cond;
            if(c.getVal())
                stk.push(thenS);
            else
                stk.push(elseS);
        }
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.copy());
            elseS.typecheck(typeEnv.copy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }

    public String toString(){
        return " (IF("+exp.toString()+") THEN ("+thenS.toString()+") ELSE ("+ elseS.toString()+ "));";
    }
}
