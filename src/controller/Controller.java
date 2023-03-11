package controller;

import exception.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.adt.MyIStack;
import model.adt.*;
import model.stmt.*;
import model.exp.*;
import model.value.*;
import model.type.*;
import repository.IRepository;

import javafx.event.ActionEvent;
import repository.Repository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    public IRepository getRepo(){return repo;}
    private ExecutorService executor;

    public Controller(){};
    public Controller(IRepository r){
        repo = r;
        try {
            repo.clearFile();
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }

    public void oneStepForAllPrg(List<PrgState> prgList){

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(()->{return p.oneStep();}))
                .collect(Collectors.toList());
        try{
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
            prgList.addAll(newPrgList);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        repo.setPrgList(prgList);
    }

    public void oneStep() throws MyException{
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if(!prgList.isEmpty()){
            prgList.get(0).getHeapTable().setMap(
                    safeGarbageCollector(
                            getAddrFromSymTable(prgList.stream()
                                    .map(prg -> {return prg.getSymTable().getMap().values();})
                                    .flatMap(Collection::stream)
                                    .collect(Collectors.toList())),
                            prgList.get(0).getHeapTable().getMap()));
            oneStepForAllPrg(prgList);
        }
        removeCompletedPrg(repo.getPrgList());
        repo.setPrgList(prgList);
        prgList.forEach(p -> {
            try {
                repo.logPrgStateExec(p);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
        executor.shutdownNow();
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
         return inPrgList.stream().
                 filter(PrgState::isNotCompleted).
                 collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->(symTableAddr.contains(e.getKey()) || getAddrFromHeapTable(heap.values()).contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues){
        return heapTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
}
