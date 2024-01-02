package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Knight extends Piece implements DistanceCheck {
    public Knight(PlayerColor color, Square square, Board board){
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

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
