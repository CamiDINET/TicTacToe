package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class VerifyGame {
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static Boolean verifyFirstLineDiagonal(ArrayList<String> array, String OorX, int n){
        for(int i = 0; i < (n*(n-1))-1; i+=(n+1)){
            if(array.get(i).equals(array.get(i+n+1)) && array.get(i).equals(OorX)){
            } else {
                return false;
            }
        }
        return true;
    }
    public static Boolean verifySecondLineDiagonal(ArrayList<String> array, String OorX, int n){
        for(int i = n-1; i < n*(n-1); i+=(n-1)){
            if(array.get(i).equals(array.get(i+n-1)) && array.get(i).equals(OorX)){
            } else {
                return false;
            }
        }
        return true;
    }
    public static Boolean verifyLinesHorizontal(ArrayList<String> array, String OorX, int n){
        for(int i = 0; i < (n*(n-1)+1);i+=n){
            int cumul = 0;
            for(int j = 0; j < n-1;j++){
                if(array.get(j+i).equals(array.get(j+i+1)) && array.get(j+i).equals(OorX)) {
                    cumul++;
                    if(cumul == n-1){
                        return true;}
                } else if(i == n*(n-1)) {
                    return false;
                }
                else{
                    cumul=0;
                }}}
        return false;
    }


    public static Boolean verifyLinesVertical(ArrayList<String> array, String OorX, int n){
        for(int i = 0; i < n;i++){
            int cumul = 0;
            for(int j = 0; j < ((n-1)*n); j+=n){
                if(array.get(j+i).equals(array.get(j+n+i)) && array.get(j+i).equals(OorX)) {
                    cumul++;
                    if(j == (n-2)*n && cumul==n-1){
                        return true;}
                } else if(i == n-1) {
                    return false;
                } else{
                    cumul=0;
                }}}
        return false;
    }

    public static Boolean verifyEveryLines(){
        return true;
    }
    public static void verifyFreeCase(ArrayList<String> newArray, int select, Scanner scanner){
        while(newArray.get(select-1).equals("O") | newArray.get(select-1).equals("X")){
            System.out.print("Case déjà prise." + NEW_LINE + "Selectionner une autre case ");
            select = scanner.nextInt();
        };
    }
    public static int verifyInputInteger(Scanner scanner){
        String scanToReturn = scanner.nextLine();
        while(true){
        try {
            return Integer.parseInt(scanToReturn);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez saisir un nombre ou la lettre 's' pour sauvegarder.");
            scanToReturn = scanner.nextLine();
        }}
       // return 0;
    }

}
