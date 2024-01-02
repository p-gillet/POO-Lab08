package engine;

public interface DiagonalMove {
    default boolean isOnDiagonal(Square from, Square to){
        return from.getX() - from.getY() == to.getX() - to.getY() ||
                from.getX() + from.getY() == to.getX() + to.getY();
    }
}
