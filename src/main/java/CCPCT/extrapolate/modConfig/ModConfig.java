package CCPCT.extrapolate.modConfig;

import CCPCT.extrapolate.Extrapolate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

    public boolean modEnabled = false;
    public float extraWeight = 0f;
    public boolean disableSmooth = false;
    public boolean onlyHitbox = false;
    public boolean renderPhysics = false;
    public boolean debug = false;

    public static ModConfig get() {
        if (INSTANCE==null)
            INSTANCE = new ModConfig();
        return INSTANCE;
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static ModConfig INSTANCE;


    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve(Extrapolate.MOD_ID+".json");

    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                INSTANCE = GSON.fromJson(Files.newBufferedReader(CONFIG_PATH), ModConfig.class);
            } else {
                INSTANCE = new ModConfig();
                save();
            }
        } catch (IOException e) {
            INSTANCE = new ModConfig();
        }
    }

    public static void save() {
        try {
            Files.writeString(CONFIG_PATH, GSON.toJson(get()));
        } catch (IOException e) {
            System.err.println("Unable to save config for "+Extrapolate.MOD_ID);
        }
    }
}