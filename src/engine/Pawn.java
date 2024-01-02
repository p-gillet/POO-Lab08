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

    //Implémenter DistanceCheck ?
    @Override
    public boolean isValidMove(Square target) {
        if (isOnline(this.getSquare(), target) && this.getSquare().getX() == target.getX()) {
            int dist = Math.abs(this.getSquare().getY() - target.getY());
            if (((this.nbMove == 0 && (dist == 1 || dist == 2)) || dist == 1)) {
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
}