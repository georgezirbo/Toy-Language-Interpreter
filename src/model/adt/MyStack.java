package model.adt;

import exception.MyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> stk;
    public MyStack(){
        stk = new Stack<T>();
    }

    public MyStack(Stack<T> stack){
        stk = stack;
    }

    public void push(T elem) {
        stk.push(elem);
    }

    public T pop() throws MyException {
        if(stk.empty())
            throw new MyException("The Stack is empty, therefore can't use pop method.\n");
        return stk.pop();
    }

    public boolean isEmpty() {
        return stk.empty();
    }

    public List<T> getList(){
        return new ArrayList<T>(stk);
    }

    public List<T> getReversed() {
        List<T> l = new ArrayList<T>(stk);
        Collections.reverse(l);
        return l;
    }
    public String toString(){
        return stk.toString();
    }
}
