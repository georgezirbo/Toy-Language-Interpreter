package model.stmt;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.PrgState;
import model.exp.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class closeFile implements IStmt{
    Exp exp;

    public closeFile(Exp e){
        exp = e;
    }

    public PrgState execute(PrgState state) throws MyException {
        //retrieve used adts
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> filetbl = state.getFileTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();

        Value val = exp.eval(symtbl, heaptbl);
        //check if the result of the expression is of StringType
        if(val.getType().equals(new StringType())){
            //check if the variable is in filetable
            if(filetbl.isVarDef((StringValue) val)){
                BufferedReader br = filetbl.lookup((StringValue) val);
                try{
                    br.close();
                    filetbl.remove((StringValue)val);
                } catch (Exception e){
                    throw new MyException("CloseFile: Error when trying to close the BufferedReader");
                }
            }
            else throw new MyException("CloseFile: The file name does not exist in the fileTabe");
        }
        else throw new MyException ("CloseFile: The value of the expression is not of StringType");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if(type instanceof StringType){
            return typeEnv;
        }
        else
            throw new MyException("[T] CloseFile: The value resulted from evaultion of expression is not of string type");
    }

    public String toString(){
        return " close("+ exp.toString() + ");";
    }
}
