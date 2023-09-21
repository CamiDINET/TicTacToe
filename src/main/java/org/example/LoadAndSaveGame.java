package org.example;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.PlayGame.NEW_LINE;

public class LoadAndSaveGame {
    public static int saveGame(ArrayList<String> arrayToSave, Scanner scanner) throws IOException {
        String scanToReturn;
        boolean scanIsInteger = false;
        while(!scanIsInteger){
            scanToReturn = scanner.nextLine();
            if(scanToReturn.toLowerCase().equals("s")){
                File newFile = new File("save.json");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(newFile, arrayToSave);
                System.out.print(NEW_LINE + "Partie sauvegardée.");
            } else {
                try {
                    return Integer.parseInt(scanToReturn);
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez saisir un nombre ou la lettre 's' pour sauvegarder.");
                    scanner.nextLine();
            }
        }

    }
        return 0;
}




//                if(!scanner.hasNextInt()){
//                System.out.print("Merci de saisir un nombre !");
//                scanToReturn = scanner.nextLine();
//            } else {
//                scanIsInteger = true;
//            }
//        }
//        return Integer.parseInt(scanToReturn);





    public static ArrayList<String> loadGame() throws IOException {
        File newFile = new File("save.json");
        ObjectMapper objectMapper = new ObjectMapper();
        if(!newFile.exists()){
            newFile.createNewFile();
            objectMapper.writeValue(newFile, new ArrayList<>());
        }

        // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ArrayList<String> myDataList = objectMapper.readValue(newFile, new TypeReference<ArrayList<String>>() {});
        if(myDataList.isEmpty()){
            return new ArrayList<>();
        } else {
            return myDataList;
        }
    }
    public static void removeSave() throws IOException {
        List<String> emptyList = new ArrayList<>();
        File newFile = new File("save.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(newFile, emptyList);
    }

}
