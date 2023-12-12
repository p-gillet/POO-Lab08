package engine;

public class Square {
    private int x; // coordonnée x de la case
    private int y; // coordonnée y de la case
    private Piece piece; // pièce sur la case

    /**
     * Constructeur à deux param&egrave;tres de la classe Square
     * @param x coordonn&eacute;e x de la case
     * @param y coordonn&eacute;e y de la case
     */
    public Square(int x, int y){
        this.x = x;
        this.y = y;
        piece = null;
    }

    /**
     * Constructeur à trois param&egrave;tres de la classe Square
     * @param x coordonn&eacute;e x de la case
     * @param y coordonn&eacute;e y de la case
     * @param piece pi&egrave;ce sur la case
     */
    public Square(int x, int y, Piece piece){
        this(x, y);
        this.piece = piece;
    }
}
