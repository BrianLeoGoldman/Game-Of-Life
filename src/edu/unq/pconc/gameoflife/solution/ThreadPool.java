package edu.unq.pconc.gameoflife.solution;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {

    private List<Worker> workerList;
    private int threads;
    private Buffer buffer;
    private GameOfLifeGrid game;

    ThreadPool(Buffer buffer, GameOfLifeGrid game){
        this.workerList = new ArrayList<>();
        this.buffer = buffer;
        this.game = game;
        this.threads = 0;

    }

    void setThreads(int threads, Waiter waiter){
        this.threads = threads;
        for (int i = 0; i < this.threads; ++i)
        {
            Worker worker = new Worker(this.buffer, this.game, i, waiter);
            workerList.add(worker);
        }
    }

    void start() {
        for (Worker aWorker : workerList) {
            aWorker.start();
        }
    }

    void closeThreads() {
        if(this.threads > 0){
            for (Worker aWorker : workerList) {
                aWorker.finishProcessing();
            }
            workerList.clear();
        }
    }

    public void printWorkers() {
        for (Worker aWorker : workerList) {
            aWorker.printWork();
        }
    }

    void newWaiter(Waiter waiter) {
        for (Worker aWorker : workerList) {
            aWorker.newWaiter(waiter);
        }

    }
}


