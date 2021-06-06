package utils;

import java.util.Scanner;

public class InputValueCheck {
    InputValidation validateString = new InputValidation();
    Scanner sc = new Scanner(System.in);

    public String intCheck() {

        boolean isIntCheckOk = false;
        while (isIntCheckOk == false) {
            String returnStr = validateString.intValidator(sc.nextLine());
            if (returnStr.equals("0")) {
                return "0";
            }
            if (returnStr.equals("invalid input")) {
                isIntCheckOk = false;
                System.out.println("Invalid input !! Please enter input in numbers");

            } else {
                isIntCheckOk = true;
                return returnStr;
            }
        }
        return null;
    }

    public String requiredIntFieldCheck() {

        boolean isRequiredIntFieldCheckIsOk = false;
        InputValueCheck currentClassObj = new InputValueCheck();
        while (isRequiredIntFieldCheckIsOk == false) {
            String intValue = currentClassObj.intCheck();
            if (intValue.equals("0")) {
                isRequiredIntFieldCheckIsOk = false;
                System.out.println("Input field cannot be empty!!");
            } else {
                isRequiredIntFieldCheckIsOk = true;
                return intValue;
            }
        }
        return null;
    }

    public String requiredStringFieldCheck() {
        boolean isRequiredStringFieldCheckIsOk = false;
        while (isRequiredStringFieldCheckIsOk == false) {
            String returnStr = validateString.inputStringValidation(sc.nextLine());
            if (returnStr.equals("Null")) {
                isRequiredStringFieldCheckIsOk = false;
                System.out.println("Input field cannot be empty!!");
            } else {
                isRequiredStringFieldCheckIsOk = true;
                return returnStr;
            }
        }
        return null;
    }

}