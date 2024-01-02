package engine;

import chess.ChessController;
import chess.ChessView;

public class GameManager implements ChessController {
    private Board board;
    private ChessView chessView;

    private boolean newGamePressed = false; // pour savoir si le bouton a été pressé
    private boolean displayMsg = false; // pour savoir si on affiche un message ou pas

    @Override
    public void start(ChessView view) {
        this.board = new Board();
        this.chessView = view;
        view.startView();
    }

    /**
     * Démarre ou redémmare une nouvelle partie d'échec
     */
    @Override
    public void newGame() {
        newGamePressed = true;
        displayMsg = false;
        board.restBoard();
        placePieces();
    }

    /**
     * Méthode privée qui place toutes les pièces à leur place initiale pour le début d'une partie
     */
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

    /**
     * Méthode privée pour le déplacement d'une pièce
     * @param from carré de départ
     * @param to carré d'arrivée
     */
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
     * Suppression d'une pièce
     * @param from le carré de la pièce a supprimer
     */
    private void removePiece(Square from) {
        //suppression de l'affichage de la pièce
        chessView.removePiece(from.getX(), from.getY());

        //suppression de la pièce du board
        board.removePiece(from);
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
        if(!newGamePressed) return false;
        boolean valid = false;

        Square from = board.getSquare(fromX, fromY);
        Square to = board.getSquare(toX, toY);

        if(board.move(from, to)) { //si le move est valide on rentre dans le if
            placePiece(from, to); //déplacement de la pièce

            // TODO gestion de la promotion
            // TODO gestion de la prise en passsant
            // TODO gestion du roque
            // TODO gestion des actions à faire à la fin d'un tour

            //on stocke la dernière pièce jouée
            board.setLastPiecePlayed(to.getPiece());

            //increment du tour
            board.setTurn(board.getTurn() + 1);

            //on regarde s'il y a un échec au début du tour de l'autre joueur
            displayMsg = board.isChecked();
            valid = true;
        }
        return false;
    }
}
