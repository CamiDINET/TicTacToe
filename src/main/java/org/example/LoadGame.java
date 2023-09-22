package org.example;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadGame {





    public static ArrayList<String> loadGame() throws IOException {
        File newFile = new File("save.json");
        ObjectMapper objectMapper = new ObjectMapper();
        if(!newFile.exists()){
            newFile.createNewFile();
            objectMapper.writeValue(newFile, new ArrayList<>());
        }
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
