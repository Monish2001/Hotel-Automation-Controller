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
                    totalPowerConsumedByThisFloorBeforeMotion = floorObj.totalPowerConsumptionOfAFloor(floor);

                    Floor currentClassObj = new Floor();
                    List<Integer> noOfMainAndSubCorridors = currentClassObj.noOfMainAndSubCorridors(floor);
                    Integer noOfMainCorridor = noOfMainAndSubCorridors.get(0);
                    Integer noOfSubCorridor = noOfMainAndSubCorridors.get(1);

                    List<Corridor> corridorList = floor.getCorridors();
                    if (noOfSubCorridor == 1) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)
                                    && corridor.getCorridorId().equals(subCorridorNo.toString())) {
                                if (totalPowerConsumedByThisFloorBeforeMotion
                                        + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                                            StateType.ON);
                                    break;
                                } else {
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor,
                                            EquipmentType.AIR_CONDITIONER, StateType.OFF);
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                                            StateType.ON);

                                    break;
                                }
                            }
                        }
                    }

                    else if (noOfSubCorridor > 1) {
                        switchOffACInACorridorandTurnOnBulb(floor, maxPowerLimitOfThisFloor,
                                totalPowerConsumedByThisFloorBeforeMotion, subCorridorNo);

                    }
                    break;

                }

            }

        } else if (condition == false) {
            Integer maxPowerLimitOfThisFloor = 0;
            Integer totalPowerConsumedByThisFloorAfterNoMotion = 0;

            for (Floor floor : floors) {
                if (floor.getFloorId().equals(floorNo.toString())) {
                    maxPowerLimitOfThisFloor = floorObj.maxPowerLimit(floor);

                    Floor currentClassObj = new Floor();
                    List<Integer> noOfMainAndSubCorridors = currentClassObj.noOfMainAndSubCorridors(floor);
                    Integer noOfMainCorridor = noOfMainAndSubCorridors.get(0);
                    Integer noOfSubCorridor = noOfMainAndSubCorridors.get(1);

                    List<Corridor> corridorList = floor.getCorridors();
                    if (noOfSubCorridor == 1) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)
                                    && corridor.getCorridorId().equals(subCorridorNo.toString())) {
                                corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT,
                                        StateType.OFF);
                                totalPowerConsumedByThisFloorAfterNoMotion = floorObj
                                        .totalPowerConsumptionOfAFloor(floor);
                                if (totalPowerConsumedByThisFloorAfterNoMotion
                                        + Constant.AC_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                                    corridorObj.changeStateOfAEquipmentInACorridor(corridor,
                                            EquipmentType.AIR_CONDITIONER, StateType.ON);
                                }
                                break;
                            }
                        }
                    }

                }
            }

        }

    }

    public void switchOffACInACorridorandTurnOnBulb(Floor floor, Integer maxPowerLimitOfThisFloor,
            Integer totalPowerConsumedByThisFloorBeforeMotion, Integer subCorridorNo) {
        List<Corridor> corridorList = floor.getCorridors();
        // List<Corridor> subCorridors =
        Corridor corridorObj = new Corridor();
        for (Corridor corridor : corridorList) {
            if (corridor.getCorridorId().equals(subCorridorNo.toString())
                    && corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)) {
                if (totalPowerConsumedByThisFloorBeforeMotion
                        + Constant.LIGHT_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                    corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT, StateType.ON);
                } else {
                    boolean returnVal = turnOffAcInACorridor(corridorList, subCorridorNo);
                    if (returnVal == true) {
                        corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT, StateType.ON);
                    } else {
                        System.out.println(
                                "Cannot turn on bulb here, as limit will be exceeded and no ac can be turned of here!!");
                    }
                }
            }
        }
    }

    public boolean turnOffAcInACorridor(List<Corridor> corridorList, Integer subCorridorNo) {
        for (Corridor corridor : corridorList) {
            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)
                    && !corridor.getCorridorId().equals(subCorridorNo.toString())) {
                List<Equipment> equipmentList = corridor.getEquipments();
                for (Equipment equipment : equipmentList) {
                    if (equipment.getType().equals(EquipmentType.LIGHT) && equipment.getState().equals(StateType.OFF)) {
                        for (Equipment equipment2 : equipmentList) {
                            if (equipment2.getType().equals(EquipmentType.AIR_CONDITIONER)
                                    && equipment2.getState().equals(StateType.ON)) {
                                equipment2.setState(StateType.OFF);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
