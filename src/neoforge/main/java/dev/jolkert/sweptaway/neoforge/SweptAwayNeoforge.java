package dev.jolkert.sweptaway.neoforge;

import dev.jolkert.sweptaway.SweptAway;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

@Mod(SweptAway.MOD_ID)
//@EventBusSubscriber
public class SweptAwayNeoforge
{
	public SweptAwayNeoforge()
	{
		SweptAway.init();
	}

}
