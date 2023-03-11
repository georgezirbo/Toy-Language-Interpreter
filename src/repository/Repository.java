package repository;

import exception.MyException;
import model.adt.*;
import model.stmt.Comp;
import model.stmt.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class Repository implements IRepository{
    private String logPath;
    private List<PrgState> repo;
    public Repository(PrgState state, String path){
        repo = new Vector<PrgState>();
        repo.add(state);
        logPath = path;
    }
    public List<PrgState> getPrgList(){
        return repo;
    }
    public void clearFile() throws MyException {
        try {
            new PrintWriter(logPath).close();
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        repo = prgList;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logPath, true)));
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        logFile.append(state.toString());
        logFile.flush();
        logFile.close();
    }
    public int size(){
        return repo.size();
    }

    public PrgState get(int i) {
        if(0 <= i && i < repo.size())
            return repo.get(i);
        return null;
    }

    @Override
    public boolean isEmpty() {
        return repo.isEmpty();
    }
}
