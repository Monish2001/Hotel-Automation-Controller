package classes;

import constants.*;

public class Movement {
    private Floor floor;
    private Corridor corridor;
    private MovementType type;
    private Controller controller;

    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Corridor getCorridor() {
        return this.corridor;
    }

    public void setCorridor(Corridor corridor) {
        this.corridor = corridor;
    }

    public MovementType getType() {
        return this.type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public Controller getController() {
        return this.controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
