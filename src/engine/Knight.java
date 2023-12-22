package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece implements DistanceCheck {
    public Knight(PlayerColor color, Square square, Board board){
        super(color, square, board);
        this.ignoresCollision = true;
    }

    @Override
    public boolean canMove(Square dest) {
        return false;
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propres au cavalier
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    protected boolean isValidMove(Square target) {
        int distance = getDistance(this.square, target);
        return distance == 2;
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
