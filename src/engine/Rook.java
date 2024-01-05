/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

/**
 * Classe représentant la tour
 */
public class Rook extends Piece implements LinearMove {

    /**
     * Constructeur de tour
     * @param color couleur de la pièce
     * @param square case sur laquelle se trouve la pièce
     * @param board plateau de jeu sur lequel la pièce se trouve
     */
    public Rook(Square square, PlayerColor color, Board board){
        super(color, square, board);
    }

    /**
     * Méthode qui retourne le type de la pièce
     * @return retourne le type enum ROOK
     */
    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propre à la tour
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    public boolean isValidMove(Square target) {
        return isOnLine(this.getSquare(), target);
    }

    /**
     * Méthode retournant le nom de la pièce
     * @return nom de la pièce
     */
    public String textValue(){ return "Rook";}

    /**
     * Méthode toString de la classe
     * @return chaine de caractère
     */
    public String toString(){ return textValue();}
}
