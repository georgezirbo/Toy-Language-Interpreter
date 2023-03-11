package model.exp;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    int op;

    @Override
    public String toString() {
        return switch (op) {
            case 1 -> e1.toString() + " && " + e2.toString();
            case 2 -> e1.toString() + " || " + e2.toString();
            default -> "";
        };
    }
    public Value eval(MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> heaptbl) throws MyException
    {
        Value v1, v2;
        v1 = e1.eval(symtbl, heaptbl);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(symtbl, heaptbl);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if(op==1)  return new BoolValue(n1 && n2);
                if(op==2)  return new BoolValue(n1 || n2);

            }
            else
                throw new MyException("second operand is not a boolean");
        }
        else
            throw new MyException("first operand is not a boolean");
        return v1; //should not get executed
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);
        if(t1.equals(new BoolType())){
            if(t2.equals(new BoolType()))
                return new BoolType();
            else
                throw new MyException("second operand is not a boolean");
        }
        else
            throw new MyException("first operand is not a boolean");
    }


}


