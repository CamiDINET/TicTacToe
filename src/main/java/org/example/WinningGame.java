package org.example;

import java.util.ArrayList;

public class WinningGame {
    public static int squareRoot(int n){
        return (int) Math.sqrt(n);
    }

    public static ArrayList<Integer> winningLineVertical(ArrayList<String> array, String XorO){
        int nbCells = array.size();
        int n = squareRoot(nbCells);
        int verticalWinningLine=0;
       // if (VerifyGame.verifyLinesVertical(array, XorO, n)){
            for(int i = 0; i < n;i++){
                int cumul = 0;
                for(int j = 0; j < ((n-1)*n); j+=n){
                    if(array.get(j+i) == array.get(j+n+i) && array.get(j+i) == XorO) {
                        cumul++;
                        if(j == (n-2)*n && cumul==n-1){
                            verticalWinningLine = i;}
                    } else{ cumul=0;
                    }}}
        ArrayList<Integer> arrToReturn = new ArrayList<>();
        for(int i=verticalWinningLine; i<n*n; i+=n){
            arrToReturn.add(i);
        }
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
        return arrToReturn;
    }
    public static ArrayList<Integer> winningLineDiagonal2(ArrayList<String> array, String XorO) {
        int nbCells = array.size();
        int n = squareRoot(nbCells);

        ArrayList<Integer> arrToReturn = new ArrayList<>();
        for(int i=n-1; i<n*(n-1)+1; i+=n-1){
            arrToReturn.add(i);
        }
        return arrToReturn;
    }
}
