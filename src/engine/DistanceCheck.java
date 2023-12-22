package engine;

public interface DistanceCheck {
    public default int getDistance(Square src, Square dest){
        return 0;
    }
}
