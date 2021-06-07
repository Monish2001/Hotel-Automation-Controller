package controller;

import java.util.List;

import classes.Corridor;
import classes.Equipment;
import classes.Floor;
import classes.Hotel;
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
            System.out.println("Please enter any one input number: ");
            System.out.println("1.MOVEMENT \n 2.NO MOVEMENT \n 3.EXIT ");
            Integer inputMotion = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());
            Hotel hotel = new Hotel();

            switch (inputMotion) {
                case 1: {
                    /* If movement occurs */
                    motionReaction(floors, true);
                    System.out.println("\n");
                    System.out.println(Constant.EQUIPMENTS_STATE_AFTER_MOVEMENT);
                    hotel.display(floors);
                    break;
                }

                case 2: {
                    /* If there is no movement */
                    motionReaction(floors, false);
                    System.out.println("\n");
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

        System.out.println("Please enter the floor no: ");

        Integer floorNo = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

        System.out.println("Please enter the corridor no: ");
        Integer subCorridorNo = Integer.parseInt(inputValueCheck.requiredIntFieldCheck());

        Floor floorObj = new Floor();
        Corridor corridorObj = new Corridor();

        if (condition == true) {
            Integer maxPowerLimitOfThisFloor = 0;
            Integer totalPowerConsumedByThisFloorBeforeMotion = 0;

            int floorNoMatch = 0;
            for (Floor floor : floors) {
                if (floor.getFloorId().equals(floorNo.toString())) {
                    floorNoMatch = 1;
                    maxPowerLimitOfThisFloor = floorObj.maxPowerLimit(floor);
                    totalPowerConsumedByThisFloorBeforeMotion = floorObj.totalPowerConsumptionOfAFloor(floor);

                    Floor currentClassObj = new Floor();
                    List<Integer> noOfMainAndSubCorridors = currentClassObj.noOfMainAndSubCorridors(floor);
                    // Integer noOfMainCorridor = noOfMainAndSubCorridors.get(0);
                    Integer noOfSubCorridor = noOfMainAndSubCorridors.get(1);

                    List<Corridor> corridorList = floor.getCorridors();

                    /*
                     * If there is only one sub corridor in a given floor then turn on the light in
                     * the given sub floor and turn off AC if the power limit exceeds
                     */
                    int corridorNoMatch = 0;
                    if (noOfSubCorridor == 1) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)
                                    && corridor.getCorridorId().equals(subCorridorNo.toString())) {
                                corridorNoMatch = 1;
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
                        if (corridorNoMatch == 0) {
                            System.out
                                    .println("Subcorridor number didn't match!! Please enter a valid subcorridor no!!");
                        }
                    }
                    /*
                     * If there is more than one sub corridor in a given floor then turn on the
                     * light in the given sub floor and turn off AC in another corridor where light
                     * is turned off already if max power limit exceeds
                     */
                    else if (noOfSubCorridor >= 1) {
                        switchOffACInACorridorandTurnOnBulb(floor, maxPowerLimitOfThisFloor,
                                totalPowerConsumedByThisFloorBeforeMotion, subCorridorNo);

                    }
                    break;

                }
            }
            if (floorNoMatch == 0) {
                System.out.println("Floor no dindn't match!! Please enter a valid floor no!!");
            }

        } else if (condition == false) {
            Integer maxPowerLimitOfThisFloor = 0;
            Integer totalPowerConsumedByThisFloorAfterNoMotion = 0;

            int floorNoMatch = 0;
            for (Floor floor : floors) {
                if (floor.getFloorId().equals(floorNo.toString())) {
                    floorNoMatch = 1;
                    maxPowerLimitOfThisFloor = floorObj.maxPowerLimit(floor);

                    Floor currentClassObj = new Floor();
                    List<Integer> noOfMainAndSubCorridors = currentClassObj.noOfMainAndSubCorridors(floor);
                    // Integer noOfMainCorridor = noOfMainAndSubCorridors.get(0);
                    Integer noOfSubCorridor = noOfMainAndSubCorridors.get(1);

                    List<Corridor> corridorList = floor.getCorridors();
                    int corridorNoMatch = 0;
                    if (noOfSubCorridor == 1) {
                        for (Corridor corridor : corridorList) {
                            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)
                                    && corridor.getCorridorId().equals(subCorridorNo.toString())) {
                                corridorNoMatch = 1;
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
                        if (corridorNoMatch == 0) {
                            System.out
                                    .println("Subcorridor number didn't match!! Please enter a valid subcorridor no!!");
                        }
                        break;
                    }

                    else if (noOfSubCorridor > 1) {
                        switchOfLightInACorridorAndTurnOnAc(floor, maxPowerLimitOfThisFloor, subCorridorNo);
                        break;
                    }

                }

            }
            if (floorNoMatch == 0) {
                System.out.println("Floor no dindn't match!! Please enter a valid floor no!!");
            }

        }

    }

    public void switchOffACInACorridorandTurnOnBulb(Floor floor, Integer maxPowerLimitOfThisFloor,
            Integer totalPowerConsumedByThisFloorBeforeMotion, Integer subCorridorNo) {
        List<Corridor> corridorList = floor.getCorridors();
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

    public void switchOfLightInACorridorAndTurnOnAc(Floor floor, Integer maxPowerLimitOfThisFloor,
            Integer subCorridorNo) {

        List<Corridor> corridorList = floor.getCorridors();
        Corridor corridorObj = new Corridor();
        Integer totalPowerConsumedByThisFloorAfterNoMotion = 0;
        Floor floorObj = new Floor();
        for (Corridor corridor : corridorList) {
            if (corridor.getCorridorId().equals(subCorridorNo.toString())
                    && corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)) {
                corridorObj.changeStateOfAEquipmentInACorridor(corridor, EquipmentType.LIGHT, StateType.OFF);
                totalPowerConsumedByThisFloorAfterNoMotion = floorObj.totalPowerConsumptionOfAFloor(floor);

                if (totalPowerConsumedByThisFloorAfterNoMotion
                        + Constant.AC_POWER_CONSUMPTION <= maxPowerLimitOfThisFloor) {
                    turnOnAcInACorridor(corridorList, subCorridorNo);
                }
            }
        }
    }

    public void turnOnAcInACorridor(List<Corridor> corridorList, Integer subCorridorNo) {
        for (Corridor corridor : corridorList) {
            if (corridor.getCorridorType().equals(CorridorType.SUB_CORRIDOR)
                    && !corridor.getCorridorId().equals(subCorridorNo.toString())) {
                List<Equipment> equipmentList = corridor.getEquipments();
                for (Equipment equipment : equipmentList) {
                    if (equipment.getType().equals(EquipmentType.LIGHT) && equipment.getState().equals(StateType.OFF)) {
                        for (Equipment equipment2 : equipmentList) {
                            if (equipment2.getType().equals(EquipmentType.AIR_CONDITIONER)
                                    && equipment2.getState().equals(StateType.OFF)) {
                                equipment2.setState(StateType.ON);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
