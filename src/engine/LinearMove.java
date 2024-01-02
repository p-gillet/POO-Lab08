package engine;

public interface LinearMove {
    /**
     * Méthode qui retourne un bool pour savoir si une case ciblée est sur la même ligne de celle actuelle
     * @param from la case actuelle
     * @param to la case cible
     * @return true si la case ciblée est sur la même ligne, sinon false
     */
    default boolean isOnline(Square from, Square to){
        return from.getX() == to.getX() || from.getY() == to.getY();
    }
}
