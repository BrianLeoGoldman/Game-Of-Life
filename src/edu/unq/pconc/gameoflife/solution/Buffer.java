package edu.unq.pconc.gameoflife.solution;

class Buffer {

    private int put;
    private int take;
    private int counter;
    private int size;
    private Region[] buffer;

    Buffer(int value){
        put = 0;
        take = 0;
        counter = 0;
        size = value;
        buffer = new Region[value];
    }

    synchronized Region nextElement() {
        while(this.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Region res = buffer[take];
        take = (take + 1) % size;
        counter--;
        notifyAll();
        return res;

    }

    synchronized void addElement(Region element) {
        while(this.isFull()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer[put] = element;
        put = (put + 1) % size;
        counter++;
        notifyAll();
    }

    private synchronized boolean isFull() {
        return counter == size;
    }

    private synchronized boolean isEmpty(){
        return counter == 0;
    }

    synchronized void empty() {
        for (int i=0; i<this.size; i++) {
            buffer[i] = null;
        }
        this.put = 0;
        this.take = 0;
        this.counter = 0;
    }
}
