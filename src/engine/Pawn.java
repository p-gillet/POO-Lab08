package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pawn extends Piece implements LinearMove, DiagonalMove, DistanceCheck {
    private final int goesUp; // 1 si le pion va vers le haut, -1 s'il va vers le bas
    private boolean enPassantVictim; // true si le pion est une victime de la prise en passant
    public Pawn(Square square, PlayerColor color, Board board){
        super(color, square, board);
        goesUp = color == PlayerColor.WHITE ? 1 : -1;
        enPassantVictim = false;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isValidMove(Square target) {
        if (nbMove == 1) {
            ignoresCollision = true;
        }
        if (threatensSquare(target)){
            return true;
        } else if (isOnline(this.getSquare(), target)) {
            Point dist = getTrueDistance(this.getSquare(), target);
            return dist.x == 0
                    && ((this.nbMove == 0 && (dist.y == this.goesUp || dist.y == 2 * this.goesUp))
                    || dist.y == 1);
        } else {
            return false;
        }
    }

    /**
     * Méthode qui retourne si le pion peut attaquer la case cible ou non
     * @param target case cible
     * @return true si le pion peut attaquer la case cible sinon false
     */
    public boolean threatensSquare(Square target){
        Point dist = getTrueDistance(this.getSquare(), target);

        return target.isOccupied() && dist.getY() == goesUp && (dist.getX() == 1 || dist.getX() == -1);
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

    public String textValue(){ return "Pawn";}

    public String toString(){ return textValue();}
}