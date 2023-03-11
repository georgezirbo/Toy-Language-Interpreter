package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.exp.Exp;
import model.exp.ValueExp;
import model.type.Type;
import model.value.Value;

public class Assign implements IStmt{
    String id;
    Exp exp;
    public Assign(String s, Exp e) {
        id = s;
        exp = e;
    }

    public PrgState execute(PrgState state) throws MyException {
        //retrieve used adts
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();

        //check if id is already defined in SymTable
        if (symtbl.isVarDef(id)) {
            Value val = exp.eval(symtbl, heaptbl);
            Type typId = symtbl.lookup(id).getType();
            //check if the expression has the same type as the variable id
            if ((val.getType()).equals(typId))
                symtbl.update(id, val);
            else
                throw new MyException("AssignStmt: Declared type of variable " + id + " and type of the assigned expression do not match");
        } else throw new MyException("AssignStmt: The variable " + id + " was not declared in the SymTable");
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("[T] AssignStmt: Right hand side and left hand side have different types ");
    }

    public String toString(){
        return " " + id + " = "+ exp.toString() + ";";
    }
}
