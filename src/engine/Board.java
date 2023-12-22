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

    /**
     * Constructeur de la classe board, initialise le plateau
     */
    public Board() {
        restBoard();
    }

    /**
     * Méthode qui initialise le plateau en construisant les 64 cases du plateau
     */
    private void initBoard(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                board[i][j] = new Square(i, j);
            }
        }
    }

    /**
     * Reset le plateau, reviens à l'état initial
     */
    public void restBoard() {
        turn = 0;
        lastPiecePlayed = null;
        castlingSquares = null;
        enPassantSquare = null;
        initBoard();
    }

    /**
     * Méthode qui place les pièces à leurs positions initiales
     */
    public void setStartingPiecePosition() {
        PlayerColor playerColor = PlayerColor.WHITE;
        int line = 0;
        int pawn = 1;

        for (int i = 0; i < 2; ++i) {

            board[0][line].setPiece(new Rook(playerColor, board[0][line], this));
            board[1][line].setPiece(new Knight(playerColor, board[1][line], this));
            board[2][line].setPiece(new Bishop(playerColor, board[2][line], this));
            board[3][line].setPiece(new Queen(playerColor, board[3][line], this));
            board[4][line].setPiece(new King(playerColor, board[4][line], this));
            board[5][line].setPiece(new Bishop(playerColor, board[5][line], this));
            board[6][line].setPiece(new Knight(playerColor, board[6][line], this));
            board[7][line].setPiece(new Rook(playerColor, board[7][line], this));
            kings[i] = (King)board[4][line].getPiece();

            for (int k = 0; k < SIZE; ++k) {
                board[k][pawn].setPiece(new Pawn(playerColor, board[k][pawn], this));
            }

            playerColor = PlayerColor.BLACK;
            line = 7;
            pawn = 6;
        }
    }

    /**
     * Place une pièce sur un carré
     * @param p la pièce
     * @param to le carré de destination
     */
    public void setPiece(Piece p, Square to) {
        to.setPiece(p);
        p.setSquare(to);
    }

    /**
     * Supprime une pièce, autrement dit la pièce n'est sur plus aucun carré
     * @param from le carré à libérer
     */
    public void removePiece(Square from) {
        from.setPiece(null);
    }

    /**
     * Déplace une pièce d'un carré à l'autre, si le carré de destination est occupé,
     * sa pièce est mangée
     * @param p la pièce à déplacer
     * @param to le carré de destination
     */
    public void movePiece(Piece p, Square to) {
        removePiece(p.getSquare());
        setPiece(p, to);
    }

    /**
     * Méthode qui retourne une case du board
     * @param x coordonnée X de la case
     * @param y coordonnée Y de la case
     * @return case aux coordonnées (X,Y)
     */
    public Square getSquare(int x, int y) {
        return board[x][y];
    }

    /**
     * Méthode pour determiner à qui s'est le tour de jouer
     * @return la couleur du joueur à qui c'est le tour
     */
    protected PlayerColor colorTurn(){
        return turn % 2 == 0 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }


}
