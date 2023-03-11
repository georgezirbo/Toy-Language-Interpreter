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
import java.io.FileReader;

public class openFile implements IStmt {
    Exp exp;
    public openFile(Exp e){
        exp = e;
    }


    public PrgState execute(PrgState state) throws MyException {
        //retrieve used adts
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader>  filetbl = state.getFileTable();
        MyIHeap<Integer, Value> heaptbl = state.getHeapTable();

        Value val = exp.eval(symtbl, heaptbl);
        Type type = val.getType();
        //check if filepath is string
        if(!val.getType().equals(new StringType())){
            throw new MyException("openFile: The type of the filepath is not String");
        }
        //check if filepath is not already in filetable
        if(filetbl.isVarDef((StringValue)val)){
            throw new MyException("openFile: The introduced filepath already existing in the FileTable");
        }

        try{
            BufferedReader br = new BufferedReader(new FileReader(val.toString()));

            filetbl.put((StringValue) val, br);
        } catch (Exception e) {
            throw new MyException("openFile: File does not exist or other IO error occured");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString(){
        return " openFile(" + exp.toString() + ");";
    }
}
