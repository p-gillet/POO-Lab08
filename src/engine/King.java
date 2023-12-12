package engine;

import chess.PieceType;
import chess.PlayerColor;

public class King extends Piece {
    public King(PlayerColor color){
        super(PieceType.KING, color);
    }
}
