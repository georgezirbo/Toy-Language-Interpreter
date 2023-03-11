package model.exp;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(char o, Exp exp1, Exp exp2) {
        switch (o) {
            case '+' -> op = 1;
            case '-' ->op = 2;
            case '*' -> op = 3;
            case '/' -> op = 4;
        }
        e1 = exp1;
        e2 = exp2;
    }

    public Value eval(MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> heaptbl) throws MyException {
        Value v1, v2;
        v1 = e1.eval(symtbl, heaptbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(symtbl, heaptbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op==1)  return new IntValue(n1+n2);
                if (op ==2)  return new IntValue(n1-n2);
                if(op==3)  return new IntValue(n1*n2);
                if(op==4)
                    if(n2==0) throw new MyException("Division by zero");
                    else
                        return new IntValue(n1/n2);
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
        return null; //does not get executed
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    public String toString(){
        return switch (op) {
            case 1 -> e1.toString() + " + " + e2.toString();
            case 2 -> e1.toString() + " - " + e2.toString();
            case 3 -> e1.toString() + " * " + e2.toString();
            case 4 -> e1.toString() + " / " + e2.toString();
            default -> new String("");
        };
    }
}
