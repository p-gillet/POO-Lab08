package engine;

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
        resetBoard();
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
    public void resetBoard() {
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
                board[k][pawn].setPiece(new Pawn(board[k][pawn], playerColor, this));
            }

            playerColor = PlayerColor.BLACK;
            line = 7;
            pawn = 6;
        }
    }

    /**
     * Méthode qui permet de vérifier si un mouvement est valide ou pas
     * @param from carré de départ
     * @param to carré d'arrivée
     * @return true si le mouvement est possible, false sinon
     */
    public boolean move(Square from, Square to){
        //si le carré de départ n'a pas de pièce => false
        if(from.getPiece() == null){
            return false;
        }

        //si le carré de départ est occupé par une pièce adverse => false
        if(from.getPiece().getColor() != colorTurn()){
            return false;
        }

        //si on essaie de manger une pièce alliée => false
        if(to.getPiece() != null && to.getPiece().getColor() == from.getPiece().getColor()){
            return false;
        }

        boolean valid = false;

        //test si le déplacement peut être effectué par la pièce sélectionnée
        //si c'est le cas, il faut ensuite tester si le déplacement mettrait le roi allié en échec
        if(from.getPiece().canMove(to)){
            valid = true;

            //on effectue un déplacement factice
            Piece deadPiece = to.getPiece();
            movePieceDummy(from.getPiece(), to);
            if(isChecked()){ //si le roi allié est en échec le déplacement n'est pas valide
                valid = false;
                enPassantSquare = null;
            }

            //dans tous les cas le déplacement est annulé
            movePieceDummy(to.getPiece(), from);
            if(deadPiece != null){
                setPieceDummy(deadPiece, to);
            }
        }
        return valid;
    }

    /**
     * Place une pièce sur un carré, version utilisée lors de la detection d'un échec
     * @param p la pièce
     * @param to le carré de destination
     */
    public void setPieceDummy(Piece p, Square to){
        to.setPiece(p);
        p.setSquare(to, false);
    }

    /**
     * Déplace une pièce d'un carré à l'autre, version utilisée lors de la detection d'un échec
     * @param p la pièce à déplacer
     * @param to le carré de destination
     */
    public void movePieceDummy(Piece p, Square to) {
        removePiece(p.getSquare());
        setPieceDummy(p, to);
    }

    /**
     * Setter de l'attribut castlingSquares
     * @param castlingSquares les carrés concernés par un roque
     */
    public void setCastlingSquares(Square[] castlingSquares) {
        this.castlingSquares = castlingSquares;
    }

    /**
     * Getter de l'attribut castlingSquares
     * @return les carrés concernés par un roque
     */
    public Square[] getCastlingSquares() {
        return castlingSquares;
    }

    /**
     * Setter de l'attribut castlingSquares
     * @param enPassantSquare le carré concerné par une capture en passant
     */
    public void setEnPassantSquare(Square enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
    }

    /**
     * Getter de l'attribut enPassantSquare
     * @return le carré concerné par une capture en passant
     */
    public Square getEnPassantSquare() {
        return enPassantSquare;
    }

    /**
     * Actions à faire à la toute fin d'un tour
     */
    public void beforeNextTurnAction(){
        if(lastPiecePlayed != null){
            //si la pièce est un pion qui pouvait être capturée en passant et que le tour
            //est terminé alors il ne peut plus être capturé en passant
            if(lastPiecePlayed instanceof Pawn pawn && pawn.getEnPassantVictim()){
                pawn.setEnPassantVictim(false);
            }
        }
    }

    /**
     * Méthode pour tester si le roi actuel est en échec
     * @return true si check détecté false sinon
     */
    public boolean isChecked(){
        King kingToCheck;
        if(kings[0].getColor() == colorTurn()){
            kingToCheck = kings[0];
        }else{
            kingToCheck = kings[1];
        }
        return isSquareAttacked(kingToCheck.getSquare());
    }

    /**
     * Méthode pour determiner si un carré est attaqué
     * @param target carré à vérifier
     * @return true si le carré est attaqué false sinon
     */
    public boolean isSquareAttacked(Square target){
        //itération sur toutes les pièces se trouvant sur le board
        for(Square[] row : board){
            for(Square square : row){
                if(square.isOccupied() && square.getPiece().getColor() != colorTurn()){
                    //le pion menace de manière particulière, on le traite séparamment
                    if(square.getPiece() instanceof Pawn pawn){
                        if(pawn.threatensSquare(target)){
                            return true;
                        }
                        //si une pièce adverse peut se déplacer sur le carré testé alors il est menacé
                    } else if (square.getPiece().canMove(target)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si un carré est occupé par un pion pouvant être promu
     * @param square le carré à vérifier
     * @return true si la promotion peut avoir lieu, false sinon
     */
    public boolean canPromote(Square square) {
        if(square.getPiece() instanceof Pawn pawn){
            return pawn.canBePromoted();
        }
        return false;
    }

    /**
     * Méthode pour effectuer une promotion
     * @param square le carré sur lequel la promotion a lieu
     * @return un tableau de pièce contenant les choix possibles de la promotion
     */
    public Piece[] promote(Square square){
        PlayerColor color = square.getPiece().getColor();
        return new Piece[]{
                new Queen(square, color, this),
                new Knight(square, color, this),
                new Rook(square, color, this),
                new Bishop(square, color, this)
        };
    }

    /**
     * Place une pièce sur un carré
     * @param p la pièce
     * @param to le carré de destination
     */
    public void setPiece(Piece p, Square to) {
        to.setPiece(p);
        p.setSquare(to,true);
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

    /**
     * Setter de l'attribut turn
     * @param turn le nouveau tour
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Getter de l'attribut turn
     * @return le tour actuel
     */
    public int getTurn(){
        return turn;
    }

    /**
     * Setter de l'attribut lastPiecePlayed
     * @param lastPiecePlayed la dernière piece jouée
     */
    public void setLastPiecePlayed(Piece lastPiecePlayed) {
        this.lastPiecePlayed = lastPiecePlayed;
    }

    /**
     * Getter de l'attribut lastPiecePlayed
     * @return la dernière pièce jouée
     */
    public Piece getLastPiecePlayed(){return lastPiecePlayed;}
}
