package engine;

public interface DiagonalMove {
    public default boolean isOnDiagonal(Square src, Square dest){
        return src.getX() - src.getY() == dest.getX() - dest.getY() ||
                src.getX() + src.getY() == dest.getX() + dest.getY();
    }
}
