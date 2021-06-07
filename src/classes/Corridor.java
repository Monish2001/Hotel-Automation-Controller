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

    public Integer powerConsumption(Corridor corridor) {
        Integer corriodPowerConsumption = 0;
        for (Equipment equipment : equipments) {
            corriodPowerConsumption += equipment.getPowerConsumption().getValue();
        }
        return corriodPowerConsumption;
    }

    public void changeStateOfAEquipmentInACorridor(Corridor corridor, EquipmentType equipmentType,
            StateType stateType) {
        Equipment equipmentObj = new Equipment();
        for (Equipment equipment : corridor.getEquipments()) {
            if (equipment.getType().equals(equipmentType)) {
                equipmentObj.toggleState(equipment, stateType);
            }
        }
    }

    public void displayCorridor(Corridor corridor) {
        Equipment equipmentObj = new Equipment();
        System.out.println(corridor.getCorridorType() + " " + corridor.getCorridorId() + ":");
        for (Equipment equipment : corridor.getEquipments()) {
            equipmentObj.displayEquipment(equipment);
        }
    }
}
