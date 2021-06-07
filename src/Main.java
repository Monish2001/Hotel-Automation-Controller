import constants.*;

import utils.InputValueCheck;

import java.util.ArrayList;
import java.util.List;

import classes.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputValueCheck inputValueCheck = new InputValueCheck();
        Scanner sc = new Scanner(System.in);

        List<Floor> floors = new ArrayList<Floor>();

        System.out.println("Please enter number of floors: ");
        Integer noOfFloors = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

        for (Integer floorCount = 1; floorCount <= noOfFloors; floorCount++) {
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

        Hotel hotel = new Hotel();
        hotel.setFloors(floors);
        System.out.println(Constant.INITIAL_STATE_OF_ALL_EQUIPMENTS);
        hotel.display(floors);
        hotel.startController();

    }

    private static List<Equipment> getEquipments(StateType state) {
        Equipment corridorLight = new Equipment();
        corridorLight.setType(EquipmentType.LIGHT);
        corridorLight.setState(state);
        System.out.println("Light : " + Constant.LIGHT_POWER_CONSUMPTION);
        corridorLight.setPowerConsumption(Constant.LIGHT_POWER_CONSUMPTION);

        // AC should be in ON state for first time

        Equipment corridorAc = new Equipment();
        corridorAc.setType(EquipmentType.AIR_CONDITIONER);
        corridorAc.setState(StateType.ON);
        corridorAc.setPowerConsumption(Constant.AC_POWER_CONSUMPTION);

        List<Equipment> corridorEquipments = new ArrayList<Equipment>();
        corridorEquipments.add(corridorLight);
        corridorEquipments.add(corridorAc);

        return corridorEquipments;
    }
}