package org.isec.pa.ecossistema.model.data;

public class Field {
    private IElemento[][] grid;
    private final int width;
    private final int height;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new IElemento[height][width];
    }

    // Check if a position is within the maze bounds
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    // Place an element at a specified position
    public void placeElement(IElemento elemento, int x, int y) {
        if (isValidPosition(x, y)) {
            grid[y][x] = elemento;
        }
    }

    // Retrieve an element from a specific position
    public IElemento getElementAt(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[y][x];
        }
        return null;
    }

    // Remove an element from a specific position
    public void removeElement(int x, int y) {
        if (isValidPosition(x, y)) {
            grid[y][x] = null;
        }
    }

    // Move an element from one position to another
    public void moveElement(int fromX, int fromY, int toX, int toY) {
        if (isValidPosition(fromX, fromY) && isValidPosition(toX, toY) && grid[fromY][fromX] != null) {
            grid[toY][toX] = grid[fromY][fromX];
            grid[fromY][fromX] = null;
        }
    }

}

