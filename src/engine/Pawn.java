/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;
import java.awt.*;

/**
 * Classe représentant le pion
 */
public class Pawn extends Piece implements LinearMove, DiagonalMove, DistanceCheck {
    private final int goesUp; // 1 si le pion va vers le haut, -1 s'il va vers le bas
    private boolean enPassantVictim; // true si le pion est une victime de la prise en passant

    /**
     * Constructeur de pion
     * @param color couleur de la pièce
     * @param square case sur laquelle se trouve la pièce
     * @param board plateau de jeu sur lequel la pièce se trouve
     */
    public Pawn(Square square, PlayerColor color, Board board){
        super(color, square, board);
        goesUp = color == PlayerColor.WHITE ? 1 : -1;
        enPassantVictim = false;
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propre au pion
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    public boolean isValidMove(Square target) {
        Point dist = getTrueDistance(this.getSquare(), target);

        if (nbMove == 1) {
            ignoresCollision = true;
        }

        if (dist.getY() == goesUp) {
            // vérification si la case n'est pas occupée et que la pièce va tout droit
            if (isOnLine(getSquare(), target) && !target.isOccupied()) {
                return true;
            }

            // vérification si la pièce va en diagonale et que la prise en passant peut se faire
            if (isOnDiagonal(getSquare(), target) && (target.isOccupied() || checkEnPassant(target))) {
                return true;
            }
        }

        // vérification si le pion peut être mangé avec la prise en passant
        if (nbMove == 0 && isOnLine(getSquare(), target) && dist.getY() == goesUp * 2 && dist.getX() == 0) {
            enPassantVictim = true;
            return true;
        }

        return false;
    }

    /**
     * Méthode qui retourne si le pion peut attaquer la case cible ou non
     * @param target case cible
     * @return true si le pion peut attaquer la case cible sinon false
     */
    public boolean threatensSquare(Square target){
        Point dist = getTrueDistance(this.getSquare(), target);

        return dist.getY() == goesUp && (dist.getX() == 1 || dist.getX() == -1);
    }

    /**
     * Méthode qui retourne si le pion peut être promu ou non
     * @return true si le pion peut être promu sinon false
     */
    public boolean canBePromoted(){
        if(goesUp == 1){
            return getSquare().getY() == 7;
        }else {
            return getSquare().getY() == 0;
        }
    }

    /**
     * Méthode qui retourne si le pion peut faire une prise en passant
     * @param target case cible
     * @return true si le pion peut faire la prise en passant sinon false
     */
    private boolean checkEnPassant(Square target){
        int newY = target.getY() - goesUp;

        Square squareBefore = getBoard().getSquare(target.getX(), newY);
        Piece victimPawn = squareBefore.getPiece();

        if(!(victimPawn instanceof Pawn) || !((Pawn)victimPawn).enPassantVictim){
            return false;
        }

        getBoard().setEnPassantSquare(squareBefore);
        return true;
    }

    /**
     * Méthode qui affecte si le pion est une victime de prise en passant ou non
     * @param enPassantVictim true si le pion est une victime de prise en passant sinon false
     */
    public void setEnPassantVictim(boolean enPassantVictim) {
        this.enPassantVictim = enPassantVictim;
    }

    /**
     * Méthode qui retourne si le pion est une victime de prise en passant ou non
     * @return true si le pion est une victime de prise en passant sinon false
     */
    public boolean getEnPassantVictim(){
        return enPassantVictim;
    }

    /**
     * Méthode qui retourne le type de la pièce
     * @return retourne le type enum PAWN
     */
    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    /**
     * Méthode retournant le nom de la pièce
     * @return nom de la pièce
     */
    public String textValue(){ return "Pawn";}

    /**
     * Méthode toString de la classe
     * @return chaine de caractère
     */
    public String toString(){ return textValue();}
}