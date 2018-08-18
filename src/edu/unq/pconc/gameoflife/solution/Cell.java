package edu.unq.pconc.gameoflife.solution;

public class Cell {

  final short col;
  final short row;
  private boolean alive;

  Cell(int col, int row) {
    this.col = (short)col;
    this.row = (short)row;
    alive = false;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Cell) )
      return false;
    return col==((Cell)o).col && row==((Cell)o).row;
  }

  public String toString() {

    return "Cell at ("+col+", "+row+")";
  }

  void setAlive(boolean state) {
    this.alive = state;
  }

  boolean alive() {
    return alive;
  }
}
