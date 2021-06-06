package classes;

import java.util.List;

public class Hotel {
    private List<Floor> floors;

    public List<Floor> getFloors() {
        return this.floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public void startController() {
        Controller controller = new Controller();
        controller.startListeningToMotion(floors);
    }
}
