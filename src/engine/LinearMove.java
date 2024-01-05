/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

/**
 * Interface pour les mouvements linéaires
 */
public interface LinearMove {
    /**
     * Méthode qui retourne un bool pour savoir si une case ciblée est sur la même ligne de celle actuelle
     * @param from la case actuelle
     * @param to la case cible
     * @return true si la case ciblée est sur la même ligne, sinon false
     */
    default boolean isOnLine(Square from, Square to){
        return from.getX() == to.getX() || from.getY() == to.getY();
    }
}
