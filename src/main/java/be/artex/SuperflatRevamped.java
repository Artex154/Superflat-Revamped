package be.artex;

import be.artex.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperflatRevamped implements ModInitializer {
	public static final String MODID = "superflat_revamped";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
	}
}