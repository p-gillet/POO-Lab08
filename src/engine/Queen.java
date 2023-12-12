package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece {
    public Queen(PlayerColor color){
        super(PieceType.QUEEN, color);
    }
}
