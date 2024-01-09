/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

/**
 * Classe représentant le fou
 */
public class Bishop extends Piece implements DiagonalMove {

    /**
     * Constructeur de fou
     * @param square la case de départ
     * @param color la couleur de la pièce
     * @param board le plateau de jeu
     */
    public Bishop(Square square, PlayerColor color, Board board){
        super(color, square, board);
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propre au fou
     * @param target la case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    protected boolean isValidMove(Square target) {
        return isOnDiagonal(this.getSquare(), target);
    }

    /**
     * Méthode qui retourne le type de la pièce
     * @return retourne le type enum BISHOP
     */
    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }

    /**
     * Méthode retournant le nom de la pièce
     * @return nom de la pièce
     */
    public String textValue(){ return "Bishop";}

    /**
     * Méthode toString de la classe
     * @return chaine de caractère
     */
    public String toString(){ return textValue();}
}
