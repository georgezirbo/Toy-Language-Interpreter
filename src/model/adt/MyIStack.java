package model.adt;

import exception.MyException;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T> {
    void push(T elem);
    T pop() throws MyException;
    boolean isEmpty();
    List<T> getList();
    List<T> getReversed();
}
