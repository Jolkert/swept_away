package dev.jolkert.sweptaway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class SweptAway
{
	public static final String MOD_ID = "swept_away";
	public static final Logger LOGGER = LoggerFactory.getLogger(SweptAway.MOD_ID);


	public static ColorMap COLOR_MAP;

	public static void init()
	{
		SweptAway.LOGGER.info("Initializing {}...", SweptAway.MOD_ID);
	}


}
