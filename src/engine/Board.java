package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Board {

    /* nombres de lignes/colonnes du plateau */
    public final static int SIZE = 8;

    /* le plateau en lui-même (8 x 8) */
    private final Square[][] board = new Square[SIZE][SIZE];

    /* tableau pour stocker les 2 rois */
    private final King[] kings = new King[2];

    /* pour stocker le tour */
    private int turn;

    /* pour stocker la dernière pièce jouée */
    private Piece lastPiecePlayed;

    /* pour stocker les carrés concernés par un roque */
    private Square[] castlingSquares;

    /* pour stocker un carré concerné par un déplacement en passant*/
    private Square enPassantSquare;

    public Board() {
        restBoard();
    }

    /**
     * Méthode qui initialise le plateau en construisant
     * les 64 cases du plateau
     */
    private void initBoard(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                board[i][j] = new Square(i, j);
            }
        }
    }

    public void restBoard() {
        turn = 0;
        lastPiecePlayed = null;
        castlingSquares = null;
        enPassantSquare = null;
        initBoard();
    }

    public void setBoard() {
        PlayerColor playerColor = PlayerColor.WHITE;
        int line = 0;
        int pawn = 1;

        for (int i = 0; i < SIZE; ++i) {

            for (int i = 0; i < SIZE; ++i) {
                board[i][pawn].setPiece(new Pawn());
            }

            playerColor = PlayerColor.BLACK;
            // changer line et pawn
        }


    }

    public void setPiece(Piece p, Square to) {
        to.setPiece(p);
        p.setSquare(to);
    }

    public void removePiece(Square from) {
        from.setPiece(null);
    }
}
