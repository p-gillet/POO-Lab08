package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class King extends Piece implements DistanceCheck{
    
    /**
     * Constructeur de roi
     * @param color couleur de la pièce
     * @param square case sur laquelle se trouve la pièce
     * @param board plateau de jeu sur lequel la pièce se trouve
     */
    public King(Square square, PlayerColor color, Board board){
        super(color, square, board);
        ignoresCollision = true;
    }

    /**
     * Méthode qui retourne si le déplacement est légal ou non par rapport aux déplacements propre au roi
     * @param target case cible
     * @return true si le déplacement est légal sinon false
     */
    @Override
    protected boolean isValidMove(Square target) {
        if(nbMove == 0 && checkCastle(target)){
            return true;
        }

        Point dist = getDistance(this.getSquare(), target);
        return (dist.getX() <= 1) && (dist.getY() <= 1);
    }

    /**
     * Méthode qui retourne si le roi peut effectuer un roque petit ou grand
     * @param target case cible
     * @return true si le roque est possible sinon false
     */
    private boolean checkCastle(Square target){
        Point dist = getTrueDistance(this.getSquare(), target);

        //il faut que le déplacement soit de 2 soit à gauche soit à droite
        if(dist.y != 0 || Math.abs(dist.x) != 2){
            return false;
        }

        //recuperation de la tour concernée
        boolean rightSide = dist.x > 0;
        int rookXpos = rightSide ? getSquare().getX() + 3 : getSquare().getX() - 4;
        Square rookSquare = getBoard().getSquare(rookXpos, getSquare().getY());
        Piece rook = rookSquare.getPiece();

        //verification que le roque peut avoir lieu
        if(rook == null || !(rook instanceof Rook) ||
                rook.nbMove > 0 || rook.checkCollision(getSquare())){
            return false;
        }

        //verification que le chemin emprunté par le roi lors du roque n'est pas menacé
        int xpos = rightSide ? 1 : -1;
        Square toCheck;
        for(int i = 0; i < 3; ++i){
            toCheck = getBoard().getSquare(getSquare().getX() + (i * xpos), getSquare().getY());
            if(getBoard().isSquareAttacked(toCheck)){
                return false;
            }
        }

        //stockage des carrés concernés par le déplacement de la tour
        rookXpos = rightSide ? getSquare().getX() + 1 : getSquare().getX() -1;
        rookSquare = getBoard().getSquare(rookXpos, getSquare().getY());

        getBoard().setCastlingSquares(new Square[]{rook.getSquare(), rookSquare});

        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    public String textValue(){ return "King";}

    public String toString(){ return textValue();}
}
