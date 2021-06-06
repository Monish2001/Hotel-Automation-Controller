package classes;

import java.util.List;

public class Controller {
    private List<Movement> movements;
    private List<Floor> floors;

    public List<Movement> getMovements() {
        return this.movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    // private static final Boolean SHOULD_LOG = true;

    public Controller(List<Movement> movements) {
        this.movements = movements;
    }
}
