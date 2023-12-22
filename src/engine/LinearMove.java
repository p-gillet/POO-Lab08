package engine;

public interface LinearMove {
    default boolean isOnline(Square src, Square dest){
        return src.getX() == dest.getX() || src.getY() == dest.getY();
    }
}
