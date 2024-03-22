package sk.tuke.kpi.oop.game;

public interface Switchable {
    /**
     * switches on the controlled device
     */
    void turnOn();

    /**
     * switches off the controlled device
     */
    void turnOff();

    /**
     * @return a value that represents the state of the device (true - the device is on, false - the device is off)
     */
    boolean isOn();

    /**
     * updates the animation
     */
    void updateAnimation();
}
