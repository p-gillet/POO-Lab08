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

            board[0][line].setPiece(new Rook(board[0][line], playerColor, this));
            board[1][line].setPiece(new Knight(board[1][line], playerColor, this));
            board[2][line].setPiece(new Bishop(board[2][line], playerColor, this));
            board[3][line].setPiece(new Queen(board[3][line], playerColor, this));
            board[4][line].setPiece(new King(board[4][line], playerColor, this));
            board[5][line].setPiece(new Bishop(board[5][line], playerColor, this));
            board[6][line].setPiece(new Knight(board[6][line], playerColor, this));
            board[7][line].setPiece(new Rook(board[7][line], playerColor, this));
            kings[i] = (King)board[4][line].getPiece();

            for (int k = 0; k < SIZE; ++k) {
                board[i][pawn].setPiece(new Pawn(playerColor, board[k][pawn], this));
            }

            playerColor = PlayerColor.BLACK;
            line = 7;
            pawn = 6;
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
