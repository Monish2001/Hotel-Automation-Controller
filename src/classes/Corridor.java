package classes;

import constants.*;
import java.util.List;

public class Corridor {
    private String corridorId;
    private List<Equipment> equipments;
    private CorridorType corridorType;

    public String getCorridorId() {
        return this.corridorId;
    }

    public void setCorridorId(String corridorId) {
        this.corridorId = corridorId;
    }

    public List<Equipment> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public CorridorType getCorridorType() {
        return this.corridorType;
    }

    public void setCorridorType(CorridorType corridorType) {
        this.corridorType = corridorType;
    }

}
