package de.z0rdak.yawp;

import de.z0rdak.yawp.config.ConfigRegistry;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(YetAnotherWorldProtector.MODID)
public class YetAnotherWorldProtector {
    public static final String MODID = "yawp";
    public static final String MODID_LONG = "Yet Another World Protector";
    public static final Logger LOGGER = LogManager.getLogger("YAWP");

    public YetAnotherWorldProtector() {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> ConfigRegistry::register);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            LOGGER.info(Component.translatableWithFallback("loading.client.info", "You are loading %s on a client. %s is a server-side only mod. The client only provides Internationalization.",MODID_LONG, MODID.toUpperCase()).getString());
        });

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (s, b) -> true));
    }
}
