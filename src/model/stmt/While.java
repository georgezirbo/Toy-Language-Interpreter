package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyStack;
import model.adt.PrgState;
import model.exp.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class While implements IStmt{
    Exp exp;
    IStmt stmt;

    public While(Exp expression, IStmt statement){
        exp = expression;
        stmt = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();
        if(! (exp.eval(symtbl, heaptbl).getType() instanceof BoolType)){
            throw new MyException("While: The value of the result after evaluating the expression is not a bool type");
        }
        BoolValue val = (BoolValue)exp.eval(symtbl, heaptbl);
        if(val.getVal()){
            List<IStmt> stack = state.getStk().getList();
            stack.add(this);
            stack.add(stmt);
            Stack<IStmt> newstack = new Stack<>();
            newstack.addAll(stack);
            state.setStk(new MyStack<IStmt>(newstack));
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = exp.typecheck(typeEnv);
        stmt.typecheck(typeEnv);
        if(t1 instanceof BoolType){
            return typeEnv;
        }
        else throw new MyException("[T] While: The value of the result after evaluating the expression is not a bool type");
    }

    public String toString(){
        return " while (" + exp.toString() + ") {" + stmt.toString() + "};";
    }

}
