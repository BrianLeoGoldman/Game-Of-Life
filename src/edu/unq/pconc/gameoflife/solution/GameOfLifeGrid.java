package edu.unq.pconc.gameoflife.solution;

import edu.unq.pconc.gameoflife.CellGrid;

import java.awt.*;

public class GameOfLifeGrid implements CellGrid {

    private Buffer buffer = new Buffer(50);
    private Waiter waiter;
    private ThreadPool pool;
    private int cellRows;
    private int cellCols;
    private int generations;
    private Cell[][] currentGrid;
    private Cell[][] nextGrid;

    /**
     * Contructs a GameOfLifeGrid.
     *
     * @param cellCols number of columns
     * @param cellRows number of rows
     */
    public GameOfLifeGrid() {
        this.cellCols = 0;
        this.cellRows = 0;
        this.waiter = new Waiter(cellCols);
        this.pool = new ThreadPool(buffer, this);
        currentGrid = new Cell[cellCols][cellRows];
        nextGrid = new Cell[cellCols][cellRows];
        for ( int c=0; c<cellCols; c++)
            for ( int r=0; r<cellRows; r++ )
                currentGrid[c][r] = new Cell( c, r );
        for ( int c=0; c<cellCols; c++)
            for ( int r=0; r<cellRows; r++ )
                nextGrid[c][r] = new Cell( c, r );

    }

    int getCellCols() {
        return cellCols;
    }

    int getCellRows(){
        return cellRows;
    }

    @Override
    public boolean getCell(int col, int row) {
        return currentGrid[col][row].alive();
    }

    @Override
    public void setCell(int col, int row, boolean state) {
        this.currentGrid[col][row].setAlive(state);

    }

    @Override
    public Dimension getDimension() {
        return new Dimension( cellCols, cellRows );
    }

    @Override
    public void resize(int cellColsNew, int cellRowsNew) {
        if ( cellCols==cellColsNew && cellRows==cellRowsNew )
            return;
        currentGrid = new Cell[cellColsNew][cellRowsNew];
        nextGrid = new Cell[cellColsNew][cellRowsNew];
        for ( int c=0; c<cellColsNew; c++)
            for ( int r=0; r<cellRowsNew; r++ )
                currentGrid[c][r] = new Cell( c, r );
        for ( int c=0; c<cellColsNew; c++)
            for ( int r=0; r<cellRowsNew; r++ )
                nextGrid[c][r] = new Cell( c, r );
        cellCols = cellColsNew;
        cellRows = cellRowsNew;
        waiter = new Waiter(cellColsNew);
    }

    @Override
    public void clear() {
        generations = 0;
        waiter = new Waiter(cellCols);
        pool.newWaiter(waiter);
        for ( int c=0; c<cellCols; c++)
            for ( int r=0; r<cellRows; r++ )
                currentGrid[c][r] = new Cell( c, r );
        for ( int c=0; c<cellCols; c++)
            for ( int r=0; r<cellRows; r++ )
                nextGrid[c][r] = new Cell( c, r );

    }

    @Override
    public void setThreads(int threads) {
        buffer.empty();
        pool.closeThreads();
        pool.setThreads(threads, this.waiter);
        pool.start();

    }

    @Override
    public int getGenerations() {
        return generations;
    }

    @Override
    public void next() {
        generations++;
        this.fillBuffer();
        this.waiter.standBy();
        this.swap();
    }

    private void swap() {
        Cell[][] oldCurrent = this.currentGrid;
        this.currentGrid = this.nextGrid;
        this.nextGrid = oldCurrent;
    }

    private void fillBuffer(){
        for ( int c=0; c<cellCols; c++){
            Region region = new Region(cellCols);
            for ( int r=0; r<cellRows; r++ ){
                Cell cell = currentGrid[c][r];
                region.add(cell);

            }
            buffer.addElement(region);
        }

    }

    void setStateOfCell(int col, int row, boolean state) {
        nextGrid[col][row].setAlive(state);
    }
}

