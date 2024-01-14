/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import chess.PlayerColor;

/**
 * Classe représentant un plateau d'échec virtuel
 */
public class Board {

    /* nombre de lignes/colonnes du plateau */
    private final static int SIZE = 8;

    /* le plateau en lui-même (8 x 8) */
    private final Square[][] board = new Square[SIZE][SIZE];

    /* tableau pour stocker les 2 rois */
    private final King[] kings = new King[2];

    /* pour stocker le tour */
    private int turn;

    /* pour stocker la dernière pièce jouée */
    private Piece lastPiecePlayed;

    /* pour stocker la pièce qui menace le roi */
    private Piece threateningPiece;

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
     * Reset le plateau, retour à l'état initial
     */
    public void resetBoard() {
        turn = 0;
        lastPiecePlayed = null;
        castlingSquares = null;
        enPassantSquare = null;
        initBoard();
    }

    /**
     * Méthode qui place les pièces à leur position initiale
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
     * @param from la case de départ
     * @param to la case d'arrivée
     * @return true si le mouvement est possible, false sinon
     */
    public boolean checkMovement(Square from, Square to){
        if (from.getPiece() == null ||                        // Si la case de départ est vide
                from.getPiece().getColor() != colorTurn() ||  // Si la pièce n'appartient pas au joueur actuel
                (to.getPiece() != null &&                     // Si la case d'arrivée est occupée par une pièce alliée
                        to.getPiece().getColor() == from.getPiece().getColor())) {
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
            if(isCheck()){ //si le roi allié est en échec le déplacement n'est pas valide
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
     * Place une pièce factice sur une case, version utilisée lors de la detection d'un échec
     * @param piece la pièce factice
     * @param to la case de destination
     */
    public void setPieceDummy(Piece piece, Square to){
        to.setPiece(piece);
        piece.setSquare(to, false);
    }

    /**
     * Déplace une pièce d'une case à l'autre, version utilisée lors de la detection d'un échec
     * @param piece la pièce à déplacer
     * @param to la case de destination
     */
    public void movePieceDummy(Piece piece, Square to) {
        removePiece(piece.getSquare());
        setPieceDummy(piece, to);
    }

    /**
     * Setter de l'attribut castlingSquares
     * @param castlingSquares les cases concernées par un roque
     */
    public void setCastlingSquares(Square[] castlingSquares) {
        this.castlingSquares = castlingSquares;
    }

    /**
     * Getter de l'attribut castlingSquares
     * @return les cases concernées par un roque
     */
    public Square[] getCastlingSquares() {
        return castlingSquares;
    }

    /**
     * Setter de l'attribut enPassantSquare
     * @param enPassantSquare la case concernée par une capture en passant
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
            //est terminé alors, il ne peut plus être capturé en passant
            if(lastPiecePlayed instanceof Pawn pawn && pawn.getEnPassantVictim()){
                pawn.setEnPassantVictim(false);
            }
        }
    }

    /**
     * Méthode pour tester si le roi actuel est en échec
     * @return true si échec détecté false sinon
     */
    public boolean isCheck(){
        return isSquareAttacked(getKingToCheck().getSquare());
    }

    /**
     * Méthode retournant le roi du joueur actuel
     * @return roi du joueur actuel
     */
    private King getKingToCheck() {
        return kings[colorTurn().ordinal()];
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
                    //le pion menace de manière particulière, on le traite séparément
                    if(square.getPiece() instanceof Pawn pawn){
                        if(pawn.threatensSquare(target)){
                            return true;
                        }
                        //si une pièce adverse peut se déplacer sur le carré testé alors, il est menacé
                    } else if (square.getPiece().canMove(target)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Méthode pour tester si le roi actuel est en échec et mat
     * Si le roi est en échec, il a trois possibilités de s'en sortir :
     * - Se déplacer sur une case qui n'est pas menacée
     * - La capture de la pièce qui le menace
     * - Le blocage de la pièce qui le menace par une autre pièce alliée
     * Si aucune de ces possibilités n'est possible, alors c'est un échec et mat
     * @return true si échec et mat détecté false sinon
     */
    public boolean isCheckmate() {
        // On vérifie si le roi peut échapper de l'échec
        if (canKingEscapeCheck()) {
            return false;
        }
        // On vérifie si la pièce menaçante peut être capturée
        if (canAnyPieceCaptureThreat()) {
            return false;
        }
        // On vérifie si une pièce alliée peut s'interposer entre le roi et la pièce menaçante
        if (canAnyPieceBlockCheck()) {
            return false;
        }
        return true;
    }

    /**
     * Méthode permettant de vérifier si le roi peut se déplacer pour échapper à l'échec
     * @return true si le roi à au moins une case où il peut se déplacer false sinon
     */
    private boolean canKingEscapeCheck() {
        King king = getKingToCheck();
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                if(checkMovement(king.getSquare(),board[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthode permettant de vérifier si une pièce peut s'interposer entre le roi et la pièce menaçante
     * @return true si une pièce peut s'interposer false sinon
     */
    private boolean canAnyPieceBlockCheck() {
        // Récupérez les cases entre la menace et le roi
        Square[] betweenSquares = getSquaresBetweenThreatAndKing();
        Square[] piecesBetween = new Square[betweenSquares.length];
        // mettre les pièces alliées entre les carrés dans un tableau
        for (int i = 0; i < betweenSquares.length; i++) {
            if (betweenSquares[i].getPiece() != null && betweenSquares[i].getPiece().getColor() == colorTurn()) {
                piecesBetween[i] = betweenSquares[i];
            }
        }

        // vérifier si une pièce alliée peut se déplacer sur une case entre la menace et le roi
        for (Square pieceBetween : piecesBetween) {
            if (pieceBetween != null) {
                for (Square betweenSquare : betweenSquares) {
                    if (betweenSquare != null) {
                        if (checkMovement(pieceBetween, betweenSquare)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Méthode permettant de récupérer les cases entre la menace et le roi
     * @return tableau de cases entre la menace et le roi
     */
    private Square[] getSquaresBetweenThreatAndKing() {
        Square kingsSquare = getKingToCheck().getSquare();
        Square threateningSquare = getThreateningPiece().getSquare();

        int deltaX = kingsSquare.getX() - threateningSquare.getX();
        int deltaY = kingsSquare.getY() - threateningSquare.getY();

        int directionX = Integer.compare(deltaX, 0);
        int directionY = Integer.compare(deltaY, 0);

        int distanceX = Math.abs(deltaX);
        int distanceY = Math.abs(deltaY);

        // Calcul de la direction pour parcourir les cases entre la menace et le roi
        int stepX = (distanceX == 0) ? 0 : directionX;
        int stepY = (distanceY == 0) ? 0 : directionY;

        int totalSteps = Math.max(distanceX, distanceY);
        Square[] squaresBetween = new Square[totalSteps - 1]; // On exclut le carré de la menace et le carré du roi

        for (int i = 1; i < totalSteps; i++) {
            int x = threateningSquare.getX() + i * stepX;
            int y = threateningSquare.getY() + i * stepY;
            squaresBetween[i - 1] = getSquare(x, y);
        }

        return squaresBetween;
    }


    /**
     * Méthode permettant de vérifier si une pièce alliée peut capturer la pièce menaçante
     * @return true si une pièce peut capturer la pièce menaçante false sinon
     */
    private boolean canAnyPieceCaptureThreat() {
        Piece[] pieces = getAlliedPieces(); // on récupère toutes les pièces alliées

        // on teste si au moins une pièce alliée peut capturer la pièce menaçante
        for (Piece piece : pieces) {
            if (piece != null) {
                if (checkMovement(piece.getSquare(), getThreateningPiece().getSquare())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Méthode pour récupérer toutes les pièces alliées
     * @return tableau de pièces alliées
     */
    private Piece[] getAlliedPieces() {
        Piece[] pieces = new Piece[16];
        int index = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Square square = board[i][j];

                if (square.getPiece() != null && square.getPiece().getColor() == colorTurn()) {
                    pieces[index++] = square.getPiece();
                }
            }
        }

        return pieces;
    }

    /**
     * Getter de l'attribut threateningPiece
     * @return la pièce qui menace le roi
     */
    public Piece getThreateningPiece() {
        return threateningPiece;
    }

    /**
     * Setter de l'attribut threateningPiece
     * @param threateningPiece la pièce qui menace le roi
     */
    public void setThreateningPiece(Piece threateningPiece) {
        this.threateningPiece = threateningPiece;
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
     * @param piece la pièce
     * @param to le carré de destination
     */
    public void setPiece(Piece piece, Square to) {
        to.setPiece(piece);
        piece.setSquare(to,true);
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
     * @param piece la pièce à déplacer
     * @param to le carré de destination
     */
    public void movePiece(Piece piece, Square to) {
        removePiece(piece.getSquare());
        setPiece(piece, to);
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

    /**
     * Getter de l'attribut SIZE
     * @return la taille d'une rangée
     */
    public int getSize(){return SIZE;}
}
