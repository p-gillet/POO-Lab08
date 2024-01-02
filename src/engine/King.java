package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class King extends Piece implements DistanceCheck{
    public King(PlayerColor color, Square square, Board board){
        super(color, square, board);
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propre au roi
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    protected boolean isValidMove(Square target) {
        if(nbMove == 0 && checkCastle(target)){
            return true;
        }

        Point dist = getDistance(this.getSquare(), target);
        return (dist.getX() <= 1) && (dist.getY() <= 1);
    }

    /**
     * Méthode qui retourne si le roi peut effectuer un roque petit ou grand
     * @param target case cible
     * @return true si le roque est possible sinon false
     */
    private boolean checkCastle(Square target){
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
