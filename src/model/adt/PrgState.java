package model.adt;

import exception.MyException;
import model.stmt.Comp;
import model.stmt.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
    private MyIStack<IStmt> stack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heapTable;
    private MyILockTable<Integer, Integer> lockTable;
    private int id;

    public int getId(){return id;}
    private static AtomicInteger nextPrg = new AtomicInteger(1);

    public static int getnextId(){
        return nextPrg.getAndIncrement();
    }

    public PrgState(IStmt stmt){
        stack = new MyStack<>();
        symTable = new MyDictionary<String, Value>();
        out = new MyList<Value>();
        stack.push(stmt);
        fileTable = new MyDictionary<StringValue, BufferedReader>();
        heapTable = new MyHeap<Integer, Value> ();
        lockTable = new MyLockTable<>();
        id = getnextId();
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> o, MyIDictionary<StringValue, BufferedReader> filetbl, MyIHeap<Integer, Value> heaptbl, MyILockTable<Integer, Integer> locktbl, IStmt originalPrg) {
        stack = stk;
        symTable = symtbl;
        out = o;
        stack.push(originalPrg);
        fileTable = filetbl;
        heapTable = heaptbl;
        lockTable = locktbl;
        id = getnextId();
    }

    public MyILockTable<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public void setLockTable(MyILockTable<Integer, Integer> lockTable) {
        this.lockTable = lockTable;
    }

    public MyIStack<IStmt> getStk() {
        return stack;
    }
    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }
    public MyIList<Value> getOut() {
        return out;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public MyIHeap<Integer, Value> getHeapTable(){return heapTable;}
    public void setStk(MyIStack<IStmt> stk) {
        stack = stk;
    }
    public void setSymTable(MyIDictionary<String, Value> tbl) {
        symTable = tbl;
    }
    public void setOut(MyIList<Value> o) {
        out = o;
    }
    public void setFileTable(MyIDictionary<StringValue, BufferedReader> tbl) {
        fileTable = tbl;
    }
    public void setHeapTable(MyIHeap<Integer, Value> tbl) {
        heapTable = tbl;
    }

    public boolean isNotCompleted() {
        return !stack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if(stack.isEmpty())
            throw  new MyException("prgstate stack is empty");
        IStmt  crtStmt = stack.pop();
        return crtStmt.execute(this);
    }


    public String toString() {
        String ExeStack = "[" + id + "] ExeStack:\n", SymTable = "[" + id + "] SymTable:\n", Out =  "Out:", FileTable = "FileTable:\n", HeapTable = "HeapTable:\n";

        //ExeStack
        List<IStmt> stmts = stack.getReversed();
        int size = stmts.size();
        for(int i = 0; i<size; i++){
            if(stmts.get(i) instanceof Comp){
                Comp stmt = (Comp) stmts.get(i);
                stmts.remove(i);
                stmts.add(i, stmt.getFirst());
                stmts.add(i+1, stmt.getSecond());
                size++;
            }
            ExeStack += " " + stmts.get(i).toString() +"\n";
        }

        //SymTable
        for( String key : symTable.getMap().keySet()){
            SymTable += " " + key + " -> " + symTable.getMap().get(key).toString() + ";\n";
        };

        //Out
        for(Value val: out.getList()){
            Out += " " + val.toString() ;
        }

        //FileTable
        for (StringValue str : fileTable.getMap().keySet()){
            FileTable += " " + str + "\n";
        }

        //HeapTable
        for(int key : heapTable.getMap().keySet()){
            HeapTable += " " + key + " -> " + heapTable.getMap().get(key).toString() + ";\n";
        };

        //LockTable
        String LockTable = "\nLockTable:\n";
        for(int key : lockTable.getMap().keySet()){
            LockTable += " " + key + " -> " + lockTable.getMap().get(key).toString() + ";\n";
        };

        return ExeStack + SymTable + HeapTable + FileTable + Out + LockTable + "\n\n";
    }
}

