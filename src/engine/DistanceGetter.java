/**
 * @author Louis Haye
 * @author Paul Gillet
 */

package engine;

import java.awt.*;

/**
 * Interface qui permet de calculer les distances x et y entre deux cases
 */
public interface DistanceGetter {

    /**
     * Méthode qui retourne un point qui a comme coordonnée :
     * - x qui est égal à la valeur absolue de la différence entre la coordonnée x
     *   de la case actuelle et celle de la case cible
     * - y qui est égal à la valeur absolue de la différence entre la coordonnée y
     *   de la case actuelle et celle de la case cible
     * @param from la case actuelle
     * @param to la case ciblée
     * @return un Point (x;y) avec les distances x et y en paramètres
     */
    default Point getDistance(Square from, Square to){
        int x = from.getX() - to.getX();
        int y = from.getY() - to.getY();
        return new Point(Math.abs(x), Math.abs(y));
    }

    /**
     * Méthode qui retourne un point qui a comme coordonnée :
     * - x qui est égal à la différence entre la coordonnée x de la case cible et celle de la case actuelle
     * - y qui est égal à la différence entre la coordonnée y de la case cible et celle de la case actuelle
     * @param from la case actuelle
     * @param to la case ciblée
     * @return un Point (x;y) avec les distances x et y en paramètres
     */
    default Point getTrueDistance(Square from, Square to) {
        int x = to.getX() - from.getX();
        int y = to.getY() - from.getY();
        return new Point(x, y);
    }
}
