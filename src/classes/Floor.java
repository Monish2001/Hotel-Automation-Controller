package classes;

import java.util.ArrayList;
import java.util.List;

import constants.CorridorType;

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

    /*
     * This function is used to return the maximum power limit of a particular floor
     */
    public Integer maxPowerLimit(Floor floorObj) {
        Integer noOfMainCorridor = 0;
        Integer noOfSubCorridor = 0;
        List<Integer> noOfMainAndSubCorridors = floorObj.noOfMainAndSubCorridors(floorObj);
        noOfMainCorridor = noOfMainAndSubCorridors.get(0);
        noOfSubCorridor = noOfMainAndSubCorridors.get(1);
        Integer maxPowerConsumptionLimit = (noOfMainCorridor * 15) + (noOfSubCorridor * 10);
        return maxPowerConsumptionLimit;
    }

    public Integer totalPowerConsumptionOfAFloor(Floor floorObj) {
        Corridor corridorObj = new Corridor();
        Integer totalPowerConsumption = 0;
        for (Corridor corridor : floorObj.getCorridors()) {
            totalPowerConsumption += corridorObj.powerConsumption(corridor);
        }
        return totalPowerConsumption;
    }

    public List<Integer> noOfMainAndSubCorridors(Floor floorObj) {
        List<Integer> mainAndSubCorridors = new ArrayList<Integer>();
        Integer mainCorridorCount = 0;
        Integer subCorridorCount = 0;
        for (Corridor corridor : floorObj.getCorridors()) {
            if (corridor.getCorridorType().equals(CorridorType.MAIN_CORRIDOR)) {
                mainCorridorCount += 1;
            } else {
                subCorridorCount += 1;
            }
        }
        mainAndSubCorridors.add(mainCorridorCount);
        mainAndSubCorridors.add(subCorridorCount);
        return mainAndSubCorridors;

    }

    public void displayFloor(Floor floorObj) {
        Corridor corridorObj = new Corridor();
        System.out.println("Floor no: " + floorObj.getFloorId());
        for (Corridor corridor : floorObj.getCorridors()) {
            corridorObj.displayCorridor(corridor);
        }
    }
}
