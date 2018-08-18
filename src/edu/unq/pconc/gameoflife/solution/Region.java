package edu.unq.pconc.gameoflife.solution;


class Region {

    private Cell[] containers;
    private int add;
    private int take;
    private int counter;

    Region(int size){
        this.containers = new Cell[size];
        this.add = 0;
        this.take = 0;
        this.counter = 0;
    }

    void add(Cell cell){
        containers[add] = cell;
        add++;
        counter++;
    }

    boolean hasElements(){
        return counter > 0;
    }

    Cell next(){
        Cell elem = containers[take];
        take++;
        counter--;
        return elem;

    }
}
