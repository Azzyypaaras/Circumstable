package azzy.fabric.circumstable.util.interaction;

public interface HeatHolder {

    double getHeat();

    void moveHeat(double change);

    double getArea();

    default boolean forceArea(){
        return false;
    }
}
