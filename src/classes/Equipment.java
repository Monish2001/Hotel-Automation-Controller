package classes;

import constants.*;

public class Equipment {
    private EquipmentType type;
    private StateType state;
    private PowerConsumption powerConsumption;

    public EquipmentType getType() {
        return this.type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public StateType getState() {
        return this.state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public PowerConsumption getPowerConsumption() {
        return this.powerConsumption;
    }

    public void setPowerConsumption(PowerConsumption powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public void toggleState(Equipment equipment, StateType stateType) {
        equipment.setState(stateType);
    }

    public void displayEquipment(Equipment equipment) {
        System.out.println(equipment.getType() + " : " + equipment.getState());
    }

}