package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class PlayGame {
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String WHITE = "\u001B[37m";
    public static void playMatch(Scanner scanner, ArrayList<String> newArray) throws IOException {

        long nbO = newArray.stream().filter(x->x.equals("O")).count();
        long nbX = newArray.stream().filter(x->x.equals("X")).count();
        long nbTurn = nbO+nbX;
        while (nbTurn < newArray.size()) {
            if(nbTurn==0 || nbO == nbX) {
                System.out.print(NEW_LINE + "Premier joueur");
                System.out.print(NEW_LINE + "Quelle case voulez-vous cocher ?");
                int selectFirst = VerifyGame.verifyInputOrSaveGame(newArray, scanner);
                while(newArray.get(selectFirst-1).equals("O") | newArray.get(selectFirst-1).equals("X")){
                    System.out.print("Case déjà prise." + NEW_LINE + "Selectionner une autre case ");
                    selectFirst = scanner.nextInt();
                }
                displayArray(addXorO(newArray, selectFirst, ">x<"), Optional.empty());
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");

                int confirmation = VerifyGame.verifyInputOrSaveGame(newArray, scanner);
                while (confirmation != selectFirst) {
                    confirmation = VerifyGame.verifyFreeCase(newArray, confirmation, scanner);
                    displayArray(addXorO(newArray, confirmation, ">x<"), Optional.empty());
                    System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                    int newSelect = scanner.nextInt();
                    if (newSelect == confirmation) {
                        selectFirst = confirmation;
                    }
                    confirmation = newSelect;
                }
                newArray = addXorO(newArray, selectFirst, "X");
                //scanner.nextLine();
               if (VerifyGame.verifyWinningLineOnEveryLines(newArray,"X")){
                   scanner.close();
                   return;
               }
                displayArray(newArray, Optional.empty());
                nbTurn++;
                nbX++;
            }
            System.out.print(NEW_LINE + "Deuxième joueur");
            System.out.print(NEW_LINE + "Quelle case voulez-vous cocher ?");
            int selectSecond = VerifyGame.verifyInputOrSaveGame(newArray, scanner);
            while(newArray.get(selectSecond-1).equals("O") | newArray.get(selectSecond-1).equals("X")){
                System.out.print("Case déjà prise." + NEW_LINE + "Selectionner une autre case ");
                selectSecond = scanner.nextInt();
            }
            VerifyGame.verifyFreeCase(newArray, selectSecond, scanner);
            displayArray(addXorO(newArray, selectSecond, ">o<"), Optional.empty());
            System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
            int confirmationSecond = VerifyGame.verifyInputOrSaveGame(newArray, scanner);
            while(confirmationSecond!=selectSecond){
                confirmationSecond = VerifyGame.verifyFreeCase(newArray, confirmationSecond, scanner);
                displayArray(addXorO(newArray, confirmationSecond, ">o<"), Optional.empty());
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                int newSelect = scanner.nextInt();
                if(newSelect == confirmationSecond){
                    selectSecond=confirmationSecond;}
                confirmationSecond=newSelect;
            }
            newArray = addXorO(newArray, selectSecond, "O");
          if(VerifyGame.verifyWinningLineOnEveryLines(newArray,"O")){
              scanner.close();
              return;
          }
            displayArray(newArray, Optional.empty());
            nbTurn++;
            nbO++;
        }
        LoadGame.removeSave();
        System.out.print(NEW_LINE + "Match nul, personne ne remporte cette partie.");
    }

    public static void displayArray(ArrayList<String> array, Optional<ArrayList<Integer>> winningCase){
        int nbCells = array.size();
        int n = (int) Math.sqrt(nbCells);
        String space = " ";
        String pipe = "|";
        ArrayList<Integer> optionalListe = new ArrayList<>();
        if(winningCase.isPresent()){
            optionalListe = winningCase.get();
        }
        for (int i = 1;i < nbCells + 1;i++){
            if(optionalListe.contains(i-1)) {
                if (i == 1) {
                    if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE +"| " + GREEN_BOLD + array.get(i - 1));
                    } else {
                        System.out.print(WHITE + "|  " + GREEN_BOLD + array.get(i - 1) + " ");}
                } else if ((i - 1) % n == 0 && i != 1) {
                    if (array.get(i - 1).length() < 2) {
                        System.out.print(WHITE + "|" + NEW_LINE + "|  " + GREEN_BOLD + array.get(i - 1) + " ");
                    } else if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE + "|" + NEW_LINE + "| " + GREEN_BOLD + array.get(i - 1));
                    } else {
                        System.out.print(WHITE + "|" + NEW_LINE + "| " + GREEN_BOLD + array.get(i - 1) + " ");}
                } else if (i == nbCells) {
                    if (array.get(i - 1).length() < 2) {
                        System.out.print(" " + GREEN_BOLD + array.get(i - 1)+ WHITE + " |");
                    } else if (array.get(i - 1).length() > 2) {
                        System.out.print(GREEN_BOLD + array.get(i - 1)+ WHITE + "|");
                    } else {
                        System.out.print(GREEN_BOLD + array.get(i - 1)+ WHITE + " |");}
                } else {
                    if (array.get(i - 1).length() < 2) {
                        System.out.print(" " + GREEN_BOLD + array.get(i - 1) + " ");
                    } else if (array.get(i - 1).length() > 2) {
                        System.out.print(GREEN_BOLD + array.get(i - 1));
                    } else {
                        System.out.print(GREEN_BOLD + array.get(i - 1) + " ");}}
            } else {
                if (i == 1) {
                    if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE + "| " + array.get(i - 1));
                    } else {
                        System.out.print(WHITE +"|  " + array.get(i - 1) + " ");}
                } else if ((i - 1) % n == 0 && i != 1) {
                    if (array.get(i - 1).length() < 2) {
                        System.out.print(WHITE + "|" + NEW_LINE + "|  " + array.get(i - 1) + " ");
                    } else if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE + "|" + NEW_LINE + "| " + array.get(i - 1));
                    } else {
                        System.out.print(WHITE +"|" + NEW_LINE + "| " + array.get(i - 1) + " ");}
                } else if (i == nbCells) {
                    if (array.get(i - 1).length() < 2) {
                        System.out.print(" " + WHITE + array.get(i - 1) + " |");
                    } else if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE + array.get(i - 1) + "|");
                    } else {
                        System.out.print(WHITE + array.get(i - 1) + " |");}
                } else {
                    if (array.get(i - 1).length() < 2) {
                        System.out.print(" " + WHITE + array.get(i - 1) + " ");
                    } else if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE + array.get(i - 1));
                    } else {
                        System.out.print(WHITE + array.get(i - 1) + " ");
                    }}}}

    }

    public static ArrayList<String> addXorO(ArrayList<String> arrayToModified, int index, String XorO){
        ArrayList<String> newArr = new ArrayList<>();
        for(int i = 0; i<arrayToModified.size();i++){
            if(i!=index-1){
                newArr.add(arrayToModified.get(i));
            } else{
                newArr.add(XorO);
            }
        }
        return newArr;
    }
    public static int squareRoot(int n){
        return (int) Math.sqrt(n);
    }
}
