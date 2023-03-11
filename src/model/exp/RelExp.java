package model.exp;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelExp implements Exp{
    Exp exp1;
    Exp exp2;
    int op;

    public RelExp(String o, Exp e1, Exp e2) {
        switch (o) {
            case "<" -> op = 1;
            case "<=" -> op = 2;
            case "==" -> op = 3;
            case "!=" -> op = 4;
            case ">" -> op = 5;
            case ">=" -> op = 6;
        }
        exp1 = e1;
        exp2 = e2;
    }
    public Value eval(MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> heaptbl) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(symtbl, heaptbl);
        if (v1.getType().equals(new IntType())) {
            v2 = exp2.eval(symtbl, heaptbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                switch (op) {
                    case 1:
                        return new BoolValue(n1 < n2);
                    case 2:
                        return new BoolValue(n1 <= n2);
                    case 3:
                        return new BoolValue(n1 == n2);
                    case 4:
                        return new BoolValue(n1 != n2);
                    case 5:
                        return new BoolValue(n1 > n2);
                    case 6:
                        return new BoolValue(n1 >= n2);
                }
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1, t2;
        t1 = exp1.typecheck(typeEnv);
        t2 = exp2.typecheck(typeEnv);
        if(t1.equals(new IntType())){
            if(t2.equals(new IntType())){
                return new BoolType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("second operand is not an integer");
    }

    @Override
    public String toString(){
        return switch (op) {
            case 1 -> exp1.toString() + " < " + exp2.toString();
            case 2 -> exp1.toString() + " <= " + exp2.toString();
            case 3 -> exp1.toString() + " == " + exp2.toString();
            case 4 -> exp1.toString() + " != " + exp2.toString();
            case 5 -> exp1.toString() + " > " + exp2.toString();
            case 6 -> exp1.toString() + " >= " + exp2.toString();
            default -> new String("");
        };
    }
}
