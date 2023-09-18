package org.example;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.boot.ansi.AnsiColor;

public class Main {
    public static final String NEW_LINE = System.getProperty("line.separator");
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
            displayArray(newArray);
            playMatch(scanner, newArray);
        } else {
            ArrayList<String> newArray = loadGame();
            displayArray(newArray);
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
    public static Void displayArray(ArrayList<String> array){
        int nbCells = array.size();
        int n = (int) Math.sqrt(nbCells);

        for (int i = 1;i < nbCells + 1;i++){
            if(i==1){
                if(array.get(i-1).length()>2){
                    System.out.print("| " + array.get(i-1));
                }else{
                    System.out.print("|  " + array.get(i-1) + " ");
                }}
            else if((i-1) % n == 0 && i != 1){
                if(array.get(i-1).length()<2){
                    System.out.print("|" + NEW_LINE + "|  " + array.get(i-1) + " ");
                } else if(array.get(i-1).length()>2){
                    System.out.print("|" + NEW_LINE + "| " + array.get(i-1));
                } else {System.out.print("|" + NEW_LINE + "| " + array.get(i-1) + " ");}
            } else if(i==nbCells){
                if(array.get(i-1).length()<2){
                    System.out.print(" " + array.get(i-1) + " |");
                }else if(array.get(i-1).length()>2){
                    System.out.print(array.get(i-1) + "|");
                } else{System.out.print(array.get(i-1) + " |");}
            } else {
                if(array.get(i-1).length()<2){
                    System.out.print(" " + array.get(i-1) + " ");
                }else if(array.get(i-1).length()>2){
                    System.out.print(array.get(i-1));
                } else{System.out.print(array.get(i-1) + " ");}
            }}
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
                displayArray(addXorO(newArray, selectFirst, ">x<"));
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                int confirmation = scanner.nextInt();
                while (confirmation != selectFirst) {
                    verifyFreeCase(newArray, confirmation, scanner);
                    displayArray(addXorO(newArray, confirmation, ">x<"));
                    System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                    int newSelect = scanner.nextInt();
                    if (newSelect == confirmation) {
                        selectFirst = confirmation;
                    }
                    confirmation = newSelect;
                }
                newArray = addXorO(newArray, selectFirst, "X");
                scanner.nextLine();
                displayArray(newArray);
                if (lineIsWin(newArray, "X")) {
                    System.out.print("Le premier joueur a gagné !");
                    removeSave();
                    return "X win";
                }
                ;
                nbTurn++;
                nbX++;
                saveGame(newArray, 1);
            }
            System.out.print(System.getProperty("line.separator") + "Deuxieme joueur" + System.getProperty("line.separator") + "Quelle case voulez-vous cocher ?");
            int selectSecond = scanner.nextInt();
            //saveGame(scanner);
            verifyFreeCase(newArray, selectSecond, scanner);
            displayArray(addXorO(newArray, selectSecond, ">o<"));
            System.out.print(System.getProperty("line.separator") + "S'agit_il bien de cette case ?");
            int confirmationSecond = scanner.nextInt();
            while(confirmationSecond!=selectSecond){
                verifyFreeCase(newArray, confirmationSecond, scanner);
                displayArray(addXorO(newArray, confirmationSecond, ">o<"));
                System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
                int newSelect = scanner.nextInt();
                if(newSelect == confirmationSecond){
                    selectSecond=confirmationSecond;}
                confirmationSecond=newSelect;
            }
            newArray = addXorO(newArray, selectSecond, "O");
            displayArray(newArray);
            if(lineIsWin(newArray,"O")){
                System.out.print("Le deuxième joueur a gagné !");
                removeSave();
                return "O win";
            };
            nbTurn++;
            nbO++;
        }
        removeSave();
        return null;
    }
    public static void verifyFreeCase(ArrayList<String> newArray, int select, Scanner scanner){
        while(newArray.get(select-1).equals("O") | newArray.get(select-1).equals("X")){
            System.out.print("Case déjà prise."+NEW_LINE+"Selectionner une autre case ");
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
        System.out.println(NEW_LINE+"Le joueur " + OorX + " a gagné !"+NEW_LINE+ "Premiere diagonale");
        return true;
    }
    public static Boolean verifySecondLineDiagonal(ArrayList<String> array, String OorX, int n){
        for(int i = n-1; i < n*(n-2)+1; i+=(n-1)){
            if(array.get(i) == array.get(i+n-1) && array.get(i)==OorX){
            } else {
                return false;
            }
        }
        System.out.print(NEW_LINE+"Le joueur " + OorX + " a gagné !"+NEW_LINE+ "Deuxieme diagonale");
        return true;
    }
    public static Boolean verifyLinesHorizontal(ArrayList<String> array, String OorX, int n){
        for(int i = 0; i < (n*(n-1)+1);i+=n){
            int cumul = 0;
            for(int j = 0; j < n-1;j++){
                if(array.get(j+i) == array.get(j+i+1) && array.get(j+i) == OorX) {
                    cumul++;
                    if(cumul == n-1){   //j == n-1 &&
                        System.out.print(NEW_LINE+ AnsiColor.BRIGHT_GREEN +"Le joueur " + OorX + " a gagné !"+NEW_LINE+ "ligne horizontale "+(i+1));
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
                        System.out.print(NEW_LINE+"Le joueur " + OorX + " a gagné !"+NEW_LINE+ "ligne verticale "+(i+1));
                        return true;}
                } else if(i == n-1) {
                    return false;
                } else{
//                    i++;
//                    j=0;
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

}