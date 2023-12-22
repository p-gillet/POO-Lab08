package engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    PlayerColor color;
    Square square;
    Board board;

    Piece(PlayerColor color, Square square, Board board){
        this.color = color;
        this.square = square;
        this.board = board;
    }

    public abstract boolean canMove(Square dest);

    public void setSquare(Square square) {
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }

    public abstract PieceType getType();

    public PlayerColor getColor() {
        return color;
    }

    protected boolean checkCollision(Square dest){
        int srcX = square.getX();
        int srcY = square.getY();
        int destX = dest.getX();
        int destY = dest.getY();

        int xDirection = destX-srcX;
        int yDirection = destY-srcY;

        //distance maximale
        int distance = Math.max(Math.abs(xDirection), Math.abs(yDirection));

        //normalisation de la direction x (soit -1, 0 ou 1)
        if(xDirection != 0){
            xDirection = xDirection / Math.abs(xDirection);
        }

        //normalisation de la direction y (soit -1, 0 ou 1)
        if(yDirection != 0){
            yDirection = yDirection / Math.abs(yDirection);
        }

        //vérifie si le chemin est libre autrement dit, que
        //chaque carré entre le carré de départ et d'arrivée est inoccupé
        for(int i = 1; i < distance; i++){
            srcX += xDirection;
            srcY += yDirection;
            if(board.getSquare(srcX, srcY).isOccupied()){
                return true;
            }
        }
        return false;
    }
}
