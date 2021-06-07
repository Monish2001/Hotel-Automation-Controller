package classes;

import java.util.List;

import constants.Constant;
import constants.CorridorType;
import constants.EquipmentType;
import constants.StateType;
import utils.InputValueCheck;

public class Controller {

    InputValueCheck inputValueCheck = new InputValueCheck();

    public void startListeningToMotion(List<Floor> floors) {
        boolean input = true;
        do {
            System.out.println("******************CONTROLLER MENU******************");
            System.out.println("1.MOVEMENT \n 2.NO MOVEMENT \n 3.EXIT ");
            Integer inputMotion = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());
            Hotel hotel = new Hotel();

            switch (inputMotion) {
                case 1: {
                    motionReaction(floors, true);
                    System.out.println(Constant.EQUIPMENTS_STATE_AFTER_MOVEMENT);
                    hotel.display(floors);
                    break;
                }

                case 2: {
                    motionReaction(floors, false);
                    System.out.println(Constant.EQUIPMENTS_STATE_AFTER_REST);
                    hotel.display(floors);
                    break;
                }

                case 3: {
                    input = false;
                    break;
                }

                default: {
                    // Do nothing for now
                    System.out.println("Please enter a valid input!!");
                    break;
                }
            }
        } while (input == true);
    }

    public void motionReaction(List<Floor> floors, boolean condition) {
        // boolean condition;

        System.out.println("Please enter the floor no: ");

        Integer floorNo = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

        System.out.println("Please enter the corridor no: ");
        Integer subCorridorNo = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

        Floor floorObj = new Floor();
        Corridor corridorObj = new Corridor();
        if (condition == true) {
            Integer maxPowerLimitOfThisFloor = 0;
            Integer totalPowerConsumedByThisFloorBeforeMotion = 0;

            for (Floor floor : floors) {
                if (floor.getFloorId().equals(floorNo.toString())) {
                    maxPowerLimitOfThisFloor = floorObj.maxPowerLimit(floor);
                    System.out.println("Max power limi: " + maxPowerLimitOfThisFloor);
                    totalPowerConsumedByThisFloorBeforeMotion = floorObj.totalPowerConsumptionOfAFloor(floor);
                    System.out.println("Max power limi: " + maxPowerLimitOfThisFloor);
                    System.out.println("total pow consumend: " + totalPowerConsumedByThisFloorBeforeMotion);

                    Floor currentClassObj = new Floor();
                    List<Integer> noOfMainAndSubCorridors = currentClassObj.noOfMainAndSubCorridors(floor);
                    Integer noOfMainCorridor = noOfMainAndSubCorridors.get(0);
                    Integer noOfSubCorridor = noOfMainAndSubCorridors.get(1);
                    System.out.println("No of main and sub cori : " + noOfMainCorridor + " " + noOfSubCorridor);

                    List<Corridor> corridorList = floor.getCorridors();
                    if (noOfSubCorridor == 1) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)
                                    && corridor.getCorridorId().equals(subCorridorNo.toString())) {
                                if (totalPowerConsumedByThisFloorBeforeMotion
                                        + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                                            StateType.ON);
                                } else {
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                                            StateType.ON);
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor,
                                            EquipmentType.AIR_CONDITIONER, StateType.OFF);
                                }
                            }
                        }
                    }

                    // else if (noOfSubCorridor > 1) {
                    // for (Corridor corridor : corridorList) {
                    // if (corridor.getCorridorId().equals(subCorridorNo.toString())) {
                    // if (totalPowerConsumedByThisFloorBeforeMotion
                    // + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                    // corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                    // StateType.ON);
                    // } else {

                    // }
                    // }
                    // }
                    // }

                    // if (totalPowerConsumedByThisFloorBeforeMotion
                    // + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {

                    // for (Corridor corridor : corridorList) {
                    // if (corridor.getCorridorId().equals(subCorridorNo.toString())) {
                    // corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                    // StateType.ON);

                    // }
                    // }
                    // }

                    // else if (totalPowerConsumedByThisFloorBeforeMotion
                    // + Constant.LIGHT_POWER_CONSUMPTION > maxPowerLimitOfThisFloor) {
                    // for (Corridor corridor : corridorList) {
                    // if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)) {

                    // }
                    // }
                    // }

                    break;

                }

            }

        } else if (condition == false) {
            // first turn off the light for particular corridor and then check by adding
            // power consumption to it , if it is less than or equal to maxLimit then turn
            // on ac

        }

    }

    public void switchOffACInACorridorandTurnOnBulb(Floor floor, Integer maxPowerLimitOfThisFloor,
            Integer totalPowerConsumedByThisFloorBeforeMotion) {
        List<Corridor> corridorList = floor.getCorridors();
        Corridor corridorObj = new Corridor();
        for (Corridor corridor : corridorList) {
            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)) {
                if (totalPowerConsumedByThisFloorBeforeMotion
                        + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                    corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT, StateType.ON);
                } else {

                }
            }
        }
    }
}
