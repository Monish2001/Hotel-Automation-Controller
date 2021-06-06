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
        do {
            System.out.println("Please enter 1 if motion detected else 0: ");
            Integer inputMotion = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

            switch (inputMotion) {
                case 0: {
                    motionReaction(floors, false);
                    break;
                }

                case 1: {
                    motionReaction(floors, true);
                    break;
                }

                default: {
                    // Do nothing for now
                    System.out.println("Please enter a valid input");
                    break;
                }
            }
        } while (true);
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
                    totalPowerConsumedByThisFloorBeforeMotion = floorObj.totalPowerConsumptionOfAFloor(floor);

                    Floor currentClassObj = new Floor();
                    List<Integer> noOfMainAndSubCorridors = currentClassObj.noOfMainAndSubCorridors(floorObj);
                    Integer noOfMainCorridor = noOfMainAndSubCorridors.get(0);
                    Integer noOfSubCorridor = noOfMainAndSubCorridors.get(1);

                    List<Corridor> corridorList = floor.getCorridors();
                    if (noOfSubCorridor == 1) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorId().equals(subCorridorNo.toString())) {
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

                    else if (noOfSubCorridor > 1) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorId().equals(subCorridorNo.toString())) {
                                if (totalPowerConsumedByThisFloorBeforeMotion
                                        + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                                            StateType.ON);
                                } else {

                                }
                            }
                        }
                    }

                    if (totalPowerConsumedByThisFloorBeforeMotion
                            + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {

                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorId().equals(subCorridorNo.toString())) {
                                corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                                        StateType.ON);

                            }
                        }
                    }

                    else if (totalPowerConsumedByThisFloorBeforeMotion
                            + Constant.LIGHT_POWER_CONSUMPTION > maxPowerLimitOfThisFloor) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)) {

                            }
                        }
                    }

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
