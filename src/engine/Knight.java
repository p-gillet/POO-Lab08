/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;
import java.awt.*;

/**
 * Classe représentant le cavalier
 */
public class Knight extends Piece implements DistanceGetter {

    /**
     * Constructeur de cavalier
     * @param color couleur de la pièce
     * @param square case sur laquelle se trouve la pièce
     * @param board plateau de jeu sur lequel la pièce se trouve
     */
    public Knight(Square square, PlayerColor color, Board board){
        super(color, square, board);
        this.ignoresCollision = true;
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propres au cavalier
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    protected boolean isValidMove(Square target) {
        Point dist = getDistance(this.getSquare(), target);
        return dist.getX() * dist.getY() == 2;
    }

    /**
     * Méthode qui retourne le type de la pièce
     * @return retourne le type enum KNIGHT
     */
    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    /**
     * Méthode retournant le nom de la pièce
     * @return nom de la pièce
     */
    public String textValue(){ return "Knight";}

    /**
     * Méthode toString de la classe
     * @return chaine de caractère
     */
    public String toString(){ return textValue();}
}
