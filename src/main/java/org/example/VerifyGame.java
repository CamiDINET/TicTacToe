package org.example;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import static org.example.PlayGame.NEW_LINE;
import static org.example.PlayGame.squareRoot;

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


    public static boolean verifyLinesVertical(ArrayList<String> array, String OorX, int n){
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

    public static boolean verifyWinningLineOnEveryLines(ArrayList<String> newArray, String XorO) throws IOException {
        String player = "";
        if(XorO.equals("X")){
            player = "premier";
        } else {
            player= "deuxième";
        }
        if(verifyLinesVertical(newArray, XorO, squareRoot(newArray.size()))){
            System.out.print("Le " + player + " joueur a gagné !" + NEW_LINE);
            LoadGame.removeSave();
            PlayGame.displayArray(newArray,Optional.of(WinningGame.winningLineVertical(newArray,"O")));
            return true;
        } else if(verifyLinesHorizontal(newArray, XorO, squareRoot(newArray.size()))){
            System.out.print("Le " + player + " joueur a gagné !" + NEW_LINE);
            LoadGame.removeSave();
            PlayGame.displayArray(newArray,Optional.of(WinningGame.winningLineHorizontal(newArray,"O")));
            return true;
        } else if(verifyFirstLineDiagonal(newArray, XorO, squareRoot(newArray.size()))){
            System.out.print("Le " + player + " joueur a gagné !" + NEW_LINE);
            LoadGame.removeSave();
            PlayGame.displayArray(newArray,Optional.of(WinningGame.winningLineDiagonal1(newArray,"O")));
            return true;
        } else if (VerifyGame.verifySecondLineDiagonal(newArray, XorO, squareRoot(newArray.size()))){
            System.out.print("Le " + player + " joueur a gagné !" + NEW_LINE);
            LoadGame.removeSave();
            PlayGame.displayArray(newArray,Optional.of(WinningGame.winningLineDiagonal2(newArray,"O")));
            return true;
        }
        else {
            return false;
        }
    }
    public static int verifyFreeCase(ArrayList<String> newArray, int select, Scanner scanner){
        while(newArray.get(select-1).equals("O") | newArray.get(select-1).equals("X")){
            System.out.print("Case déjà prise." + NEW_LINE + "Selectionner une autre case ");
            select = scanner.nextInt();
        };
        return select;
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
    public static int verifyInputOrSaveGame(ArrayList<String> arrayToSave, Scanner scanner) throws IOException {
        Scanner scan = new Scanner(System.in);
        String scanToReturn;
        boolean scanIsInteger = false;
        while (!scanIsInteger) {
            scanToReturn = scan.nextLine();
                if (scanToReturn.equalsIgnoreCase("S")) {
                    File newFile = new File("save.json");
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(newFile, arrayToSave);
                    System.out.print(NEW_LINE + "Partie sauvegardée.");
                } else if (scanToReturn.matches("^-?\\d+$")) {
                    return Integer.parseInt(scanToReturn);
                } else {
                    System.out.println("Saisie invalide. Veuillez réessayer.");
                }
            }
    return 0;
    }

    public static int reverifyInputOrSaveGame(ArrayList<String> array, Scanner scanner, String XorO, int selectFirst){
        int confirmation = scanner.nextInt();

        while (confirmation != selectFirst) {
            VerifyGame.verifyFreeCase(array, confirmation, scanner);
            PlayGame.displayArray(PlayGame.addXorO(array, confirmation, ">" + XorO + "<"), Optional.empty());
            System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
            int newSelect = scanner.nextInt();
            if (newSelect == confirmation) {
                selectFirst = confirmation;
            }
             confirmation = newSelect;
            return confirmation;
        }
        return 0;
    }
}
