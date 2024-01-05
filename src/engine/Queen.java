/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

/**
 * Classe représentant la reine
 */
public class Queen extends Piece implements DiagonalMove, LinearMove{

    /**
     * Constructeur de reine
     * @param color couleur de la pièce
     * @param square case sur laquelle se trouve la pièce
     * @param board plateau de jeu sur lequel la pièce se trouve
     */
    public Queen(Square square, PlayerColor color, Board board){
        super(color, square, board);
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propre à la reine
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    protected boolean isValidMove(Square target) {
        return (isOnLine(this.getSquare(), target) || isOnDiagonal(this.getSquare(), target));
    }

    /**
     * Méthode qui retourne le type de la pièce
     * @return retourne le type enum QUEEN
     */
    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    /**
     * Méthode retournant le nom de la pièce
     * @return nom de la pièce
     */
    public String textValue(){ return "Queen";}

    /**
     * Méthode toString de la classe
     * @return chaine de caractère
     */
    public String toString(){ return textValue();}
}
