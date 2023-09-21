package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static final String NEW_LINE = System.getProperty("line.separator");
  //  public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
  //  public static final String WHITE = "\u001B[37m";
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        if(LoadAndSaveGame.loadGame().isEmpty()) {
            System.out.print("Quelle sera la taille du plateau ? ");
            int nbColumn = VerifyGame.verifyInputInteger(scanner);
            while (nbColumn < 3) {
                System.out.print("Impossible de creer un plateau plus petit que 3." + NEW_LINE + "Quelle sera la taille du plateau ? ");
                nbColumn = scanner.nextInt();
            }
            ArrayList<String> newArray = createArray(nbColumn);
            PlayGame.displayArray(newArray, Optional.empty());
            PlayGame.playMatch(scanner, newArray);
        } else {
            ArrayList<String> arrayToLoad = LoadAndSaveGame.loadGame();
            PlayGame.displayArray(arrayToLoad, Optional.empty());
            PlayGame.playMatch(scanner, arrayToLoad);
        }
    }
    public static ArrayList<String> createArray(int n){
        int nbCells = n*n;
        ArrayList<String> array = new ArrayList<String>();
        for (int i=1;i < nbCells + 1;i++){
            array.add(String.valueOf(i));}
        return array;
    }

//racine carré
public static int squareRoot(int n){
        return (int) Math.sqrt(n);
}
}