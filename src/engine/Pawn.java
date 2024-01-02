package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pawn extends Piece implements LinearMove, DiagonalMove, DistanceCheck {
    private final int goesUp; // 1 si le pion va vers le haut, -1 s'il va vers le bas
    private boolean enPassantVictim; // true si le pion est une victime de la prise en passant
    public Pawn(PlayerColor color, Square square, Board board){
        super(color, square, board);
        goesUp = color == PlayerColor.WHITE ? 1 : -1;
        enPassantVictim = false;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    public boolean isAhead(Square target){
        switch (this.getColor()) {
            case WHITE -> {
                return this.getSquare().getY() < target.getY();
            }
            case BLACK -> {
                return this.getSquare().getY() > target.getY();
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public boolean isValidMove(Square target) {
        if (isOnline(this.getSquare(), target)) {
            Point dist = getDistance(this.getSquare(), target);
            if ((dist.x == 0 && (this.nbMove == 0 && (dist.y == 1 || dist.y == 2)) || dist.y == 1)) {
                return isAhead(target);
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(Square target) {
        if (target.isOccupied()) {
            Point dist = getDistance(this.getSquare(), target);
            return isOnDiagonal(this.getSquare(), target) &&
                    isAhead(target) &&
                    (dist.getX() == 1 && dist.getY() == 1);

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

        return dist.getY() == goesUp && (dist.getX() == 1 || dist.getX() == -1);
    }
}