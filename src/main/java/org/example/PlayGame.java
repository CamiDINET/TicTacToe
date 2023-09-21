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
                System.out.print(NEW_LINE + "Premier joueur" + NEW_LINE + "Quelle case voulez-vous cocher ?");


                int selectFirst = LoadAndSaveGame.saveGame(newArray, scanner);
                VerifyGame.verifyFreeCase(newArray, selectFirst, scanner);
                displayArray(addXorO(newArray, selectFirst, ">x<"), Optional.empty());
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");

                int confirmation = scanner.nextInt();

                while (confirmation != selectFirst) {
                    VerifyGame.verifyFreeCase(newArray, confirmation, scanner);
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
                if(VerifyGame.verifyLinesVertical(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !"+NEW_LINE);
                    LoadAndSaveGame.removeSave();
                    displayArray(newArray,Optional.of(WinningGame.winningLineVertical(newArray,"X")));
                    return;
                } else if(VerifyGame.verifyLinesHorizontal(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !"+NEW_LINE);
                    LoadAndSaveGame.removeSave();
                    displayArray(newArray,Optional.of(WinningGame.winningLineHorizontal(newArray,"O")));
                    return;
                } else if(VerifyGame.verifyFirstLineDiagonal(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !"+NEW_LINE);
                    LoadAndSaveGame.removeSave();
                    displayArray(newArray,Optional.of(WinningGame.winningLineDiagonal1(newArray,"O")));
                    return;
                } else if (VerifyGame.verifySecondLineDiagonal(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !"+NEW_LINE);
                    LoadAndSaveGame.removeSave();
                    displayArray(newArray,Optional.of(WinningGame.winningLineDiagonal2(newArray,"X")));
                    return;
                }
                displayArray(newArray, Optional.empty());
                nbTurn++;
                nbX++;
            }
            System.out.print(NEW_LINE + "Deuxieme joueur" + NEW_LINE + "Quelle case voulez-vous cocher ?");


            int selectSecond = LoadAndSaveGame.saveGame(newArray, scanner);
            VerifyGame.verifyFreeCase(newArray, selectSecond, scanner);
            displayArray(addXorO(newArray, selectSecond, ">o<"), Optional.empty());
            System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
            int confirmationSecond = scanner.nextInt();
            while(confirmationSecond!=selectSecond){
                VerifyGame.verifyFreeCase(newArray, confirmationSecond, scanner);
                displayArray(addXorO(newArray, confirmationSecond, ">o<"), Optional.empty());
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                int newSelect = scanner.nextInt();
                if(newSelect == confirmationSecond){
                    selectSecond=confirmationSecond;}
                confirmationSecond=newSelect;
            }
            newArray = addXorO(newArray, selectSecond, "O");

            if(VerifyGame.verifyLinesVertical(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !"+NEW_LINE);
                LoadAndSaveGame.removeSave();
                displayArray(newArray,Optional.of(WinningGame.winningLineVertical(newArray,"O")));
                return;
            } else if(VerifyGame.verifyLinesHorizontal(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !"+NEW_LINE);
                LoadAndSaveGame.removeSave();
                displayArray(newArray,Optional.of(WinningGame.winningLineHorizontal(newArray,"O")));
                return;
            } else if(VerifyGame.verifyFirstLineDiagonal(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !"+NEW_LINE);
                LoadAndSaveGame.removeSave();
                displayArray(newArray,Optional.of(WinningGame.winningLineDiagonal1(newArray,"O")));
                return;
            } else if (VerifyGame.verifySecondLineDiagonal(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !"+NEW_LINE);
                LoadAndSaveGame.removeSave();
                displayArray(newArray,Optional.of(WinningGame.winningLineDiagonal2(newArray,"O")));
                return;
            }
            displayArray(newArray, Optional.empty());
            nbTurn++;
            nbO++;
        }
        LoadAndSaveGame.removeSave();
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
