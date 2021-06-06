package classes;

import java.util.List;

public class Floor {
    private String floorId;
    private List<Corridor> corridors;

    public String getFloorId() {
        return this.floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public List<Corridor> getCorridors() {
        return this.corridors;
    }

    public void setCorridors(List<Corridor> corridors) {
        this.corridors = corridors;
    }

}
