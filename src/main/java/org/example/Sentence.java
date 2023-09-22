package org.example;

import static org.example.PlayGame.NEW_LINE;

public class Sentence {

   public static void playerSelectCase(){
       System.out.print(NEW_LINE + "Quelle case voulez-vous cocher ?");
   }
    public static void playerConfirmSelect(){
        System.out.print(NEW_LINE + "S'agit_il bien de cette case ?");
    }

    public static void playerWin(String player){
        System.out.print("Le " + player + " joueur a gagné !"+NEW_LINE);
    }

}
