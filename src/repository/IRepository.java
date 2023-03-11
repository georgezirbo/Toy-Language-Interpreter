package repository;

import exception.MyException;
import model.adt.PrgState;

import java.util.List;

public interface IRepository {
    void logPrgStateExec(PrgState state) throws MyException;
    void clearFile() throws MyException;
    void setPrgList(List<PrgState> prgList);
    List<PrgState> getPrgList();
    int size();
    PrgState get(int i);
    boolean isEmpty();

}
