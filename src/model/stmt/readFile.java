package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.PrgState;
import model.exp.Exp;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt{
    Exp exp;
    String var;
    public readFile(Exp e, String n){
        exp = e;
        var = n;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        //retrieve used adts
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> filetbl = state.getFileTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();
        //check if variable is defined
        if(symtbl.isVarDef(var)){
            //check if the type of the variable is IntType
            if(symtbl.lookup(var).getType().equals(new IntType())){
                Value v = exp.eval(symtbl, heaptbl);
                //check if the value is a string
                if(v.getType().equals(new StringType())){
                    //check if there exists a buffer for that specific path in filetable
                    if(filetbl.isVarDef((StringValue) v)){
                        try{
                            BufferedReader br = filetbl.lookup((StringValue) v);
                            String line = br.readLine();
                            IntValue x;
                            if (line == null)
                                x = (IntValue)(new IntType().defaultValue());
                            else {
                                try{
                                    x = new IntValue(Integer.parseInt(line));
                                }
                                catch(NumberFormatException e){
                                    x = new IntValue(0);
                                }
                            }
                            symtbl.update(var, x);
                        } catch (IOException e) {
                            throw new MyException(e.getMessage());
                        }
                    }
                    else throw new MyException("readFile: There is no bufferedReader associated to: " + v);
                }
                else throw new MyException("readFile: The expression: " + exp + "does not evaluate to a string value");
            }
            else throw new MyException("readFile: The varibale name: " + var + "is associated to a non-int value");
        }
        else throw new MyException("readFile: The variable name: " + var + " is not defined");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = typeEnv.lookup(var);
        Type type2 = exp.typecheck(typeEnv);
        if(type1 instanceof IntType){
            if(type2 instanceof StringType){
                return typeEnv;
            }
            else throw new MyException("[T] readFile: The expression: " + exp + " does not evaluate to a string value, but rather to "+ type2.toString());
        }
        else throw new MyException("[T] readFile: The varibale name: " + var + " is associated to a non-integer value");

    }


    public String toString(){
        return " readFile(" + exp.toString() + ", " + var + ");";
    }
}
