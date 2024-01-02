package engine;

import java.awt.*;

public class Square {
    private final Point coord;

    private Piece piece; // pièce sur la case


    /**
     * Constructeur à deux paramètres de la classe Square
     * @param x coordonn&eacute;e x de la case
     * @param y coordonn&eacute;e y de la case
     */
    public Square(int x, int y){
        this.coord = new Point(x, y);
        piece = null;
    }

    /**
     * Constructeur à trois paramètres de la classe Square
     * @param x coordonn&eacute;e x de la case
     * @param y coordonn&eacute;e y de la case
     * @param piece pi&egrave;ce sur la case
     */
    public Square(int x, int y, Piece piece){
        this(x, y);
        this.piece = piece;
    }

    public int getX() {
        return coord.x;
    }

    public void setX(int x) {
        coord.x = x;
    }

    public int getY() {
        return coord.y;
    }

    public void setY(int y) {
        coord.y = y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isOccupied(){
        return this.piece != null;
    }
}
