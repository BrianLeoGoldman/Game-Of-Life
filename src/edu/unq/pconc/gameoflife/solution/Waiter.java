package edu.unq.pconc.gameoflife.solution;

class Waiter {

    private int finished;
    private int total;

    Waiter(int total){
        this.finished = 0;
        this.total = total;
    }

    synchronized void threadFinished(){
        finished++;
        notifyAll();
    }

    synchronized void standBy() {
        while (finished < total){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        finished = 0;
    }
}
