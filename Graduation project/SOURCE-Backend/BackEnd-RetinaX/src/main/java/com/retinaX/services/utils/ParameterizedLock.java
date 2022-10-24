package com.retinaX.services.utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ParameterizedLock {

    private int parameter;
    private ReentrantLock lock;
    private Condition condition;

    public ParameterizedLock() {
        this.lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void await(int parameter) throws InterruptedException, IllegalAccessError {
        setParameter(parameter);
        condition.await();
    }

    public void awake(int parameter) {
        if (this.parameter == parameter) {
            condition.signalAll();
        }
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    private void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
