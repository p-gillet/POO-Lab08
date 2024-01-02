package engine;

public interface DiagonalMove {
    /**
     * Méthode qui retourne un bool pour savoir si une case ciblée est sur la diagonale de celle actuelle
     * @param from la case actuelle
     * @param to la case cible
     * @return true si la case ciblée est sur la diagonale, sinon false
     */
    default boolean isOnDiagonal(Square from, Square to){
        return from.getX() - from.getY() == to.getX() - to.getY() ||
                from.getX() + from.getY() == to.getX() + to.getY();
    }
}
