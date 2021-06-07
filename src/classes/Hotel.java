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

    /* Automated controller starts here */
    public void startController() {
        Controller controller = new Controller();
        controller.startListeningToMotion(floors);
    }

    public void display(List<Floor> floorList) {
        Floor floorObj = new Floor();
        for (Floor floor : floorList) {
            floorObj.displayFloor(floor);
            System.out.println("\n");
        }
    }
}
