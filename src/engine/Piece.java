/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

/**
 * Classe abstraite représentant une pièce quelconque
 */
public abstract class Piece implements ChessView.UserChoice {
    private final PlayerColor color; // couleur de la pièce
    private final Board board; // plateau de jeu sur lequel la pièce se trouve
    private Square square; // case sur laquelle se trouve la pièce
    boolean ignoresCollision; // la pièce, peut-elle ignorer les collisions
    int nbMove; // nombre de déplacements fait par la pièce

    /**
     *  Constructeur de la classe Piece
     * @param square la case sur laquelle se trouve la pièce
     * @param color couleur de la pièce
     * @param board plateau de jeu sur laquelle elle se trouve
     */
    Piece(PlayerColor color, Square square, Board board) throws RuntimeException {
        if (square == null || color == null)
            throw new RuntimeException("Construction de la pièce invalide");

        this.color = color;
        this.square = square;
        this.board = board;
        this.nbMove = 0;
        this.ignoresCollision = false;
    }

    /**
     * Méthode abstraite qui retourne si un déplacement est légal ou non
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    protected abstract boolean isValidMove(Square target);

    /**
     * Méthode qui affecte une case à la pièce
     * @param s case sur laquelle se trouve la pièce
     * @param increase est-ce que le nombre de déplacements doit être incrémenté ou non
     */
    public void setSquare(Square s, boolean increase){
        square = s;
        if(increase){
            ++nbMove;
        }
    }

    /**
     * Méthode qui retourne la case sur laquelle se trouve la pièce
     * @return case sur laquelle se trouve la pièce
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Méthode abstraite qui retourne le type de la pièce
     * @return type de la pièce
     */
    public abstract PieceType getType();

    /**
     * Méthode qui retourne la couleur de la pièce
     * @return couleur de la pièce
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Méthode qui retourne si la pièce entre en collision avec une autre pièce ou non
     * @param target case cible
     * @return true si la pièce entre en collision avec une autre pièce, false si elle ne rencontre aucune pièce
     * ou si elle ignore les collisions
     */
    protected boolean checkCollision(Square target){
        if(ignoresCollision){
            return false;
        }

        int srcX = square.getX();
        int srcY = square.getY();
        int destX = target.getX();
        int destY = target.getY();

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

    /**
     * Méthode qui retourne si la piece peut effectuer le déplacement ou non
     * Un mouvement peut être effectué lorsqu'il est :
     * 1. Légal par rapport aux règles des échecs et
     * 2. Il n'y a pas de collision
     * @return true si le déplacement peut être effectué sinon false
     */
    public boolean canMove(Square to){
        if(!isValidMove(to)){
            return false;
        }
        return !checkCollision(to);
    }

    /**
     * Méthode qui retourne le plateau sur laquelle se trouve la pièce
     * @return plateau sur lequel se trouve la pièce
     */
    public Board getBoard() {
        return board;
    }
}
