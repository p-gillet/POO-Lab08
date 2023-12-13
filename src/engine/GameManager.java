package engine;

import chess.ChessController;
import chess.ChessView;

public class GameManager implements ChessController {
    Board board;
    ChessView chessView;

    @Override
    public void start(ChessView view) {
        this.board = new Board();
        this.chessView = view;
        view.startView();
    }

    @Override
    public void newGame() {
        // TODO
        board.restBoard();
        placePieces();
    }

    private void placePieces() {
        //place les pièces sur le board
        board.setStartingPiecePosition();

        //affichage des pièces
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                Square square = board.getSquare(i, j);
                if (square.getPiece() != null) {
                    chessView.putPiece(square.getPiece().getType(), square.getPiece().getColor(), i, j);
                }
            }
        }
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        // TODO
        return false;
    }


}
