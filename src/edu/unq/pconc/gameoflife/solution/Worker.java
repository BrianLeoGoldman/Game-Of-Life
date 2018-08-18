package edu.unq.pconc.gameoflife.solution;

public class Worker extends Thread {

    private Buffer buffer;
    private GameOfLifeGrid game;
    private boolean working;
    private int counter;
    private int id;
    private Waiter waiter;

    Worker(Buffer buffer, GameOfLifeGrid game, int number, Waiter waiter){
        this.buffer = buffer;
        this.game = game;
        this.working = true;
        this.counter = 0;
        this.id = number;
        this.waiter = waiter;
    }

    public void run() {
        while(this.working){
            Region elem = buffer.nextElement();
            this.processRegion(elem);
        }

    }

    private void processRegion(Region region) {
        while (region.hasElements()){
            Cell elem = region.next();
            this.processElem(elem);
            counter++;
        }
        waiter.threadFinished();
    }

    private void processElem(Cell elem) {
        int neighbours = this.countNeighbours(elem);
        this.processState(elem, neighbours);
    }

    private void processState(Cell cell, int neighbours) {
        if ( neighbours == 2 ) {
            game.setStateOfCell(cell.col, cell.row, cell.alive());
        }
        if ( neighbours == 3 ) {
            game.setStateOfCell(cell.col, cell.row, true);
        }
        if ( neighbours != 2 && neighbours != 3 ) {
            game.setStateOfCell(cell.col, cell.row, false);
        }

    }

    private int countNeighbours(Cell cell) {
        int counter = 0;
        counter = counter + addNeighbour(cell.col - 1, cell.row - 1);
        counter = counter + addNeighbour(cell.col, cell.row - 1);
        counter = counter + addNeighbour(cell.col + 1, cell.row - 1);
        counter = counter + addNeighbour(cell.col - 1, cell.row);
        counter = counter + addNeighbour(cell.col + 1, cell.row);
        counter = counter + addNeighbour(cell.col - 1, cell.row + 1);
        counter = counter + addNeighbour(cell.col, cell.row + 1);
        counter = counter + addNeighbour(cell.col + 1, cell.row + 1);
        return counter;

    }

    private int addNeighbour(int col, int row) {
        int res = 0;
        if (this.isValidCell(col, row) && game.getCell(col, row)){
            res = 1;
        }
        return res;
    }

    private boolean isValidCell(int col, int row) {
        return (row != -1 && col != -1 && row != game.getCellRows() && col != game.getCellCols());
    }

    void finishProcessing() {
        this.working = false;
    }

    void printWork() {
        System.out.println("Thread " + this.id + " " +
                "has processed " + this.counter + " regions");
        this.counter = 0;
    }

    void newWaiter(Waiter waiter) {
        this.waiter = waiter;
    }
}
