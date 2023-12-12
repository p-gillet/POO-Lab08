package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends Piece {
    public Pawn(PlayerColor color){
        super(PieceType.PAWN, color);
    }
}
