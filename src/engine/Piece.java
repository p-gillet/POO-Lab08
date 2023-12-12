package engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    PieceType type;
    PlayerColor color;
    Piece(PieceType type, PlayerColor color){
        this.type = type;
        this.color = color;
    }
}
