package org.example;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.boot.ansi.AnsiColor;

public class Main {
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String WHITE = "\u001B[37m";
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        if(loadGame().isEmpty()) {
            System.out.print("Quelle sera la taille du plateau ? ");
            int nbColumn = scanner.nextInt();
            while (nbColumn < 3) {
                System.out.print("Impossible de creer un plateau plus petit que 3." + NEW_LINE + "Quelle sera la taille du plateau ? ");
                nbColumn = scanner.nextInt();
            }
            ArrayList<String> newArray = createArray(nbColumn);
            displayArray(newArray, Optional.empty());
            playMatch(scanner, newArray);
        } else {
            ArrayList<String> newArray = loadGame();
            displayArray(newArray, Optional.empty());
            playMatch(scanner, newArray);
        }
    }
    public static ArrayList<String> createArray(int n){
        int nbCells = n*n;
        ArrayList<String> array = new ArrayList<String>();
        for (int i=1;i < nbCells + 1;i++){
            array.add(String.valueOf(i));}
        return array;
    }
    public static Void displayArray(ArrayList<String> array, Optional<ArrayList<Integer>> winningCase){
        int nbCells = array.size();
        int n = (int) Math.sqrt(nbCells);
        ArrayList<Integer> optionalListe = new ArrayList<>();
        if(winningCase.isPresent()){
            optionalListe = winningCase.get();
        }
        System.out.print(NEW_LINE + optionalListe + NEW_LINE);
        for (int i = 1;i < nbCells + 1;i++){

           if(optionalListe.contains(i-1)) {
                if (i == 1) {
                    if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE +"| " + GREEN_BOLD + array.get(i - 1));
                    } else {
                        System.out.print(WHITE + "|  " + GREEN_BOLD + array.get(i - 1) + " ");
                    }
                } else if ((i - 1) % n == 0 && i != 1) {
                    if (array.get(i - 1).length() < 2) {
                        System.out.print(WHITE + "|" + NEW_LINE + "|  " + GREEN_BOLD + array.get(i - 1) + " ");
                    } else if (array.get(i - 1).length() > 2) {
                        System.out.print(WHITE + "|" + NEW_LINE + "| " + GREEN_BOLD + array.get(i - 1));
                    } else {
                        System.out.print(WHITE + "|" + NEW_LINE + "| " + GREEN_BOLD + array.get(i - 1) + " ");
                    }
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
                        System.out.print(GREEN_BOLD + array.get(i - 1) + " ");
                    }}

            } else {
               if (i == 1) {
                   if (array.get(i - 1).length() > 2) {
                       System.out.print(WHITE + "| " + array.get(i - 1));
                   } else {
                       System.out.print(WHITE +"|  " + array.get(i - 1) + " ");
                   }
               } else if ((i - 1) % n == 0 && i != 1) {
                   if (array.get(i - 1).length() < 2) {
                       System.out.print(WHITE + "|" + NEW_LINE + "|  " + array.get(i - 1) + " ");
                   } else if (array.get(i - 1).length() > 2) {
                       System.out.print(WHITE + "|" + NEW_LINE + "| " + array.get(i - 1));
                   } else {
                       System.out.print(WHITE +"|" + NEW_LINE + "| " + array.get(i - 1) + " ");
                   }
               } else if (i == nbCells) {
                   if (array.get(i - 1).length() < 2) {
                       System.out.print(" " + WHITE + array.get(i - 1) + " |");
                   } else if (array.get(i - 1).length() > 2) {
                       System.out.print(WHITE + array.get(i - 1) + "|");
                   } else {
                       System.out.print(WHITE + array.get(i - 1) + " |");
                   }
               } else {
                   if (array.get(i - 1).length() < 2) {
                       System.out.print(" " + WHITE + array.get(i - 1) + " ");
                   } else if (array.get(i - 1).length() > 2) {
                       System.out.print(WHITE + array.get(i - 1));
                   } else {
                       System.out.print(WHITE + array.get(i - 1) + " ");
                   }
               }

           }
        }






        return null;
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
    public static String playMatch(Scanner scanner, ArrayList<String> newArray) throws IOException {

        long nbO = newArray.stream().filter(x->x.equals("O")).count();
        long nbX = newArray.stream().filter(x->x.equals("X")).count();
        long nbTurn = nbO+nbX;
        while (nbTurn < newArray.size()) {
            if(nbTurn==0 || nbO == nbX) {
                System.out.print(NEW_LINE + "Premier joueur" + System.getProperty("line.separator") + "Quelle case voulez-vous cocher ?");
                int selectFirst = scanner.nextInt();
                verifyFreeCase(newArray, selectFirst, scanner);
                displayArray(addXorO(newArray, selectFirst, ">x<"), Optional.empty());
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                int confirmation = scanner.nextInt();
                while (confirmation != selectFirst) {
                    verifyFreeCase(newArray, confirmation, scanner);
                    displayArray(addXorO(newArray, confirmation, ">x<"), Optional.empty());
                    System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                    int newSelect = scanner.nextInt();
                    if (newSelect == confirmation) {
                        selectFirst = confirmation;
                    }
                    confirmation = newSelect;
                }
                newArray = addXorO(newArray, selectFirst, "X");
                scanner.nextLine();
                if(verifyLinesVertical(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !");
                    removeSave();
                    displayArray(newArray,Optional.of(winningLineVertical(newArray,"X")));
                    return "X win";
                } else if(verifyLinesHorizontal(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !");
                    removeSave();
                    displayArray(newArray,Optional.of(winningLineHorizontal(newArray,"O")));
                    return "X win";
                } else if(verifyFirstLineDiagonal(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !");
                    removeSave();
                    displayArray(newArray,Optional.of(winningLineDiagonal1(newArray,"O")));
                    return "X win";
                } else if (verifySecondLineDiagonal(newArray, "X", squareRoot(newArray.size()))){
                    System.out.print("Le premier joueur a gagné !");
                    removeSave();
                    displayArray(newArray,Optional.of(winningLineDiagonal2(newArray,"X")));
                    return "X win";
                }
                displayArray(newArray, Optional.empty());
                nbTurn++;
                nbX++;
                saveGame(newArray, 1);
            }
            System.out.print(System.getProperty("line.separator") + "Deuxieme joueur" + System.getProperty("line.separator") + "Quelle case voulez-vous cocher ?");
            int selectSecond = scanner.nextInt();
            //saveGame(scanner);
            verifyFreeCase(newArray, selectSecond, scanner);
            displayArray(addXorO(newArray, selectSecond, ">o<"), Optional.empty());
            System.out.print(System.getProperty("line.separator") + "S'agit_il bien de cette case ?");
            int confirmationSecond = scanner.nextInt();
            while(confirmationSecond!=selectSecond){
                verifyFreeCase(newArray, confirmationSecond, scanner);
                displayArray(addXorO(newArray, confirmationSecond, ">o<"), Optional.empty());
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                int newSelect = scanner.nextInt();
                if(newSelect == confirmationSecond){
                    selectSecond=confirmationSecond;}
                confirmationSecond=newSelect;
            }
            newArray = addXorO(newArray, selectSecond, "O");
            if(verifyLinesVertical(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !");
                removeSave();
                displayArray(newArray,Optional.of(winningLineVertical(newArray,"O")));
                return "O win";
            } else if(verifyLinesHorizontal(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !");
                removeSave();
                displayArray(newArray,Optional.of(winningLineHorizontal(newArray,"O")));
                return "O win";
            } else if(verifyFirstLineDiagonal(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !");
                removeSave();
                displayArray(newArray,Optional.of(winningLineDiagonal1(newArray,"O")));
                return "O win";
            } else if (verifySecondLineDiagonal(newArray, "O", squareRoot(newArray.size()))){
                System.out.print("Le deuxième joueur a gagné !");
                removeSave();
                displayArray(newArray,Optional.of(winningLineDiagonal2(newArray,"O")));
                return "O win";
            }
            displayArray(newArray, Optional.empty());
            nbTurn++;
            nbO++;
        }
        removeSave();
        System.out.print(NEW_LINE + "Match nul, personne ne remporte cette partie.");
        return null;
    }
    public static void verifyFreeCase(ArrayList<String> newArray, int select, Scanner scanner){
        while(newArray.get(select-1).equals("O") | newArray.get(select-1).equals("X")){
            System.out.print("Case déjà prise." + NEW_LINE + "Selectionner une autre case ");
            select = scanner.nextInt();
        };
    }
    public static Boolean lineIsWin(ArrayList<String> array, String OorX) throws IOException {
        int nbCells = array.size();
        int n = (int) Math.sqrt(nbCells);
      //  int possibility = n - 3 + n + 5;
        if (verifyLinesVertical(array, OorX, n) || verifyFirstLineDiagonal(array, OorX, n) || verifySecondLineDiagonal(array, OorX, n) || verifyLinesHorizontal(array, OorX, n)
        ){
            return true;
        }
        return false;
    }
    public static Boolean verifyFirstLineDiagonal(ArrayList<String> array, String OorX, int n){
        for(int i = 0; i < (n*(n-1))-1; i+=(n+1)){
            if(array.get(i) == array.get(i+n+1) && array.get(i)==OorX){
            } else {
                return false;
            }
        }
        return true;
    }
    public static Boolean verifySecondLineDiagonal(ArrayList<String> array, String OorX, int n){
        for(int i = n-1; i < n*(n-1); i+=(n-1)){
            if(array.get(i) == array.get(i+n-1) && array.get(i)==OorX){
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
                if(array.get(j+i) == array.get(j+i+1) && array.get(j+i) == OorX) {
                    cumul++;
                    if(cumul == n-1){   //j == n-1 &&
                        return true;}
                } else if(i == n*(n-1)) {
                    return false;
                }
                else{
                    cumul=0;
                }
            }
        }
        return false;
    }


    public static Boolean verifyLinesVertical(ArrayList<String> array, String OorX, int n){
        for(int i = 0; i < n;i++){
            int cumul = 0;
            for(int j = 0; j < ((n-1)*n); j+=n){
                if(array.get(j+i) == array.get(j+n+i) && array.get(j+i) == OorX) {
                    cumul++;
                    if(j == (n-2)*n && cumul==n-1){
                        return true;}
                } else if(i == n-1) {
                    return false;
                } else{
                    cumul=0;
                }
            }
        }
        return false;
    }

    public static Void saveGame(ArrayList<String> arrayToSave, int player) throws IOException {
            File newFile = new File("save.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(newFile, arrayToSave);
            return null;
    }
    public static ArrayList<String> loadGame() throws IOException {
        File newFile = new File("save.json");
        ObjectMapper objectMapper = new ObjectMapper();
       // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ArrayList<String> myDataList = objectMapper.readValue(newFile, new TypeReference<ArrayList<String>>() {
        });
        if(myDataList.isEmpty()){
            return new ArrayList<>();
        } else {
            return myDataList;
        }
    }
    public static Void removeSave() throws IOException {
        List<String> emptyList = new ArrayList<>();
        File newFile = new File("save.json");
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(newFile, emptyList);
        return null;
    }
public static ArrayList<Integer> winningLineVertical(ArrayList<String> array, String XorO){
        int nbCells = array.size();
        int n = squareRoot(nbCells);
        int verticalWinningLine=0;
    if (verifyLinesVertical(array, XorO, n)){
        for(int i = 0; i < n;i++){
            int cumul = 0;
            for(int j = 0; j < ((n-1)*n); j+=n){
                if(array.get(j+i) == array.get(j+n+i) && array.get(j+i) == XorO) {
                    cumul++;
                    if(j == (n-2)*n && cumul==n-1){
                        verticalWinningLine = i;}
                } else{ cumul=0;
                }}}}
    ArrayList<Integer> arrToReturn = new ArrayList<>();
    for(int i=verticalWinningLine; i<n*(n-1)+2; i+=n){
        arrToReturn.add(i);
    }
    System.out.print(arrToReturn);
    return arrToReturn;
}

    public static ArrayList<Integer> winningLineHorizontal(ArrayList<String> array, String XorO) {
        int nbCells = array.size();
        int n = squareRoot(nbCells);
        int horizontalWinningLine=0;
        for(int i = 0; i < (n*(n-1)+1);i+=n){
            int cumul = 0;
            for(int j = 0; j < n-1;j++){
                if(array.get(j+i) == array.get(j+i+1) && array.get(j+i) == XorO) {
                    cumul++;
                    if(cumul == n-1){
                        horizontalWinningLine=i;}
                } else{
                    cumul=0;
                }
            }
        }
        ArrayList<Integer> arrToReturn = new ArrayList<>();
        for(int i=horizontalWinningLine; i<n+horizontalWinningLine; i++){
            arrToReturn.add(i);
        }
        return arrToReturn;
    }
    public static ArrayList<Integer> winningLineDiagonal1(ArrayList<String> array, String XorO) {
        int nbCells = array.size();
        int n = squareRoot(nbCells);

        ArrayList<Integer> arrToReturn = new ArrayList<>();
        for(int i=0; i<n*n; i+=n+1){
            arrToReturn.add(i);
        }
        System.out.print(arrToReturn);
        return arrToReturn;
    }
    public static ArrayList<Integer> winningLineDiagonal2(ArrayList<String> array, String XorO) {
        int nbCells = array.size();
        int n = squareRoot(nbCells);

        ArrayList<Integer> arrToReturn = new ArrayList<>();
        for(int i=n-1; i<n*(n-1)+1; i+=n-1){
            arrToReturn.add(i);
        }
        System.out.print(arrToReturn);
        return arrToReturn;
    }
//racine carré
public static int squareRoot(int n){
        return (int) Math.sqrt(n);
}
}