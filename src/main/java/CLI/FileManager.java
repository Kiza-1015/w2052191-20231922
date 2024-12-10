package CLI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileManager {
    private static final String configFile = "config.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveConfiguration(Configuration config){
        try{
            Writer writer = new FileWriter(configFile);
            gson.toJson(config, writer);
            writer.close();

            System.out.println("Successfully saved the configuration");
        }catch(IOException e){
            System.out.println("Failed to save configuration: " + e.getMessage());;
        }
    }

    public static Configuration getConfiguration(){
        try(FileReader reader = new FileReader(configFile)){
            return gson.fromJson(reader, Configuration.class);
        }catch (IOException e){
            System.out.println("Failed to load the configuration: " + e.getMessage());;
            return null;
        }
    }
}

