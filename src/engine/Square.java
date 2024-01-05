/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import java.awt.*;

/**
 * Classe représentant une case
 */
public class Square {
    private final Point coord; // coordonnée de la case

    private Piece piece; // pièce sur la case

    /**
     * Constructeur à deux paramètres de la classe Square
     * @param x coordonnée x de la case
     * @param y coordonnée y de la case
     */
    public Square(int x, int y){
        this.coord = new Point(x, y);
        piece = null;
    }

    /**
     * Constructeur à trois paramètres de la classe Square
     * @param x coordonnée x de la case
     * @param y coordonnée y de la case
     * @param piece pi&egrave;ce sur la case
     */
    public Square(int x, int y, Piece piece){
        this(x, y);
        this.piece = piece;
    }

    /**
     * Méthode qui retourne la coordonnée x de la case
     * @return coordonnée x de la case
     */
    public int getX() {
        return coord.x;
    }

    /**
     * Méthode qui retourne la coordonnée y de la case
     * @return coordonnée y de la case
     */
    public int getY() {
        return coord.y;
    }

    /**
     * Méthode qui retourne la pièce sur la case
     * @return pièce sur la case
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Méthode qui affecte une pièce sur la case
     * @param piece pièce à affecter sur la case
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Méthode qui retourne si la case est occupée ou non
     * @return true si la case est occupée sinon false
     */
    public boolean isOccupied(){
        return this.piece != null;
    }
}
