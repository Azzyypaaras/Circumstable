package azzy.fabric.circumstable.registry.damagetypes;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class SoundRegistry {

    public static final SoundEvent WHIRL = register("industrial_whirl", new SoundEvent(new Identifier(MOD_ID, "machine.generic.whirl")));

    public static void init(){
    }

    private static SoundEvent register(String name, SoundEvent event){
        return Registry.register(Registry.SOUND_EVENT, new Identifier(MOD_ID, name), event);
    }
}
