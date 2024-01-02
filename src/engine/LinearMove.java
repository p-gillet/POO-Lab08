package engine;

public interface LinearMove {
    default boolean isOnline(Square from, Square to){
        return from.getX() == to.getX() || from.getY() == to.getY();
    }
}
