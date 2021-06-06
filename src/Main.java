import constants.*;
import utils.InputValueCheck;

import java.util.ArrayList;
import java.util.List;
import constants.*;
import classes.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputValueCheck inputValueCheck = new InputValueCheck();
        Scanner sc = new Scanner(System.in);

        List<Floor> floors = new ArrayList<Floor>();

        System.out.println("Please enter number of floors: ");
        Integer noOfFloors = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

        for (Integer floorCount = 1; floorCount <= noOfFloors; noOfFloors++) {
            Floor floorObj = new Floor();

            System.out.println("Please enter number of main corridors: ");
            Integer noOfMainCorridor = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

            List<Corridor> corridorsList = new ArrayList<Corridor>();
            for (Integer mainCorridorCount = 1; mainCorridorCount <= noOfMainCorridor; mainCorridorCount++) {
                List<Equipment> mainCorridorEquipments = getEquipments(StateType.ON);
                Corridor mainCorridor = new Corridor();
                mainCorridor.setCorridorId(mainCorridorCount.toString());
                mainCorridor.setEquipments(mainCorridorEquipments);
                mainCorridor.setCorridorType(CorridorType.MAIN_CORRIDOR);
                corridorsList.add(mainCorridor);
            }

            System.out.println("Please enter number of sub corridors: ");
            Integer noOfSubCorridors = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

            for (Integer subCorridorCount = 1; subCorridorCount <= noOfSubCorridors; subCorridorCount++) {
                List<Equipment> subCorridorEquipments = getEquipments(StateType.OFF);
                Corridor subCorridor = new Corridor();
                subCorridor.setCorridorId(subCorridorCount.toString());
                subCorridor.setEquipments(subCorridorEquipments);
                subCorridor.setCorridorType(CorridorType.SUB_CORRIDOR);
                corridorsList.add(subCorridor);
            }

            floorObj.setFloorId(floorCount.toString());
            floorObj.setCorridors(corridorsList);
            floors.add(floorObj);
        }

    }

    // private static Floor getFloor(String floorId, Corridor mainCorridor, Corridor
    // subCorridor1, Corridor subCorridor2) {
    // List<Corridor> corridors = new LinkedList<>();
    // corridors.add(mainCorridor1);
    // corridors.add(subCorridor1);
    // corridors.add(subCorridor2);

    // return new Floor(floorId, corridors);
    // }

    private static List<Equipment> getEquipments(StateType state) {
        PowerConsumption lightPowerConsumption = new PowerConsumption();
        lightPowerConsumption.setValue(Constant.LIGHT_POWER_CONSUMPTION);
        PowerConsumption acPowerConsumption = new PowerConsumption();
        acPowerConsumption.setValue(Constant.AC_POWER_CONSUMPTION);

        // Equipment corridorLight = new Equipment(LIGHT_BULB, state,
        // lightPowerConsumption);

        Equipment corridorLight = new Equipment();
        corridorLight.setType(EquipmentType.LIGHT);
        corridorLight.setState(state);
        corridorLight.setPowerConsumption(lightPowerConsumption);

        // AC should be in ON state for first time

        // Equipment corridorAc = new Equipment(AIR_CONDITIONER, ON,
        // acPowerConsumption);

        Equipment corridorAc = new Equipment();
        corridorAc.setType(EquipmentType.AIR_CONDITIONER);
        corridorAc.setState(StateType.ON);
        corridorAc.setPowerConsumption(acPowerConsumption);

        List<Equipment> corridorEquipments = new ArrayList<Equipment>();
        corridorEquipments.add(corridorLight);
        corridorEquipments.add(corridorAc);

        return corridorEquipments;
    }
}