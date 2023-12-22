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

    private void placePiece(Square from, Square to) {
        //affichage de la pièce sur la case d'arrivée
        chessView.putPiece( from.getPiece().getType(), from.getPiece().getColor(),
                to.getX(), to.getY() );

        //suppression de l'affichage de la pièce sur la case de départ
        chessView.removePiece(from.getX(), from.getY());

        //déplacement en lui-même
        board.movePiece(from.getPiece(), to);
    }

    /**
     * Redefinition de la méthode move() de l'interface ChessController
     * Si le mouvement est valide déplace la pièce et return true, false sinon
     * et aucun mouvement n'est effectué
     * @param fromX coordonnée x de départ
     * @param fromY coordonnée y de départ
     * @param toX coordonnée x d'arrivée
     * @param toY coordonnée y d'arrivée
     * @return true si le mouvement est effectué false sinon
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        // TODO
        return false;
    }
}
