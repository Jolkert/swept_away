package dev.jolkert.sweptaway;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ColorMap
{
	private final HashMap<UUID, Integer> backingMap;
	private final Path filePath;

	public ColorMap(Path filePath)
	{
		this.filePath = filePath;
		this.backingMap = readColorFile(filePath);
	}

	public void setPlayerColor(UUID uuid, int color)
	{
		this.backingMap.put(uuid, color);
		writeColorFile();
	}

	public void clearPlayerColor(UUID uuid)
	{
		this.backingMap.remove(uuid);
		writeColorFile();
	}

	public int getPlayerColor(UUID uuid)
	{
		return this.backingMap.getOrDefault(uuid, -1);
	}

	private void writeColorFile()
	{
		JsonObject root = new JsonObject();
		for (Map.Entry<UUID, Integer> entry : this.backingMap.entrySet())
		{
			root.addProperty(entry.getKey().toString(), entry.getValue());
		}

		try
		{
			Files.writeString(this.filePath, new GsonBuilder().setPrettyPrinting().create().toJson(root));
		}
		catch (IOException e)
		{
			SweptAway.LOGGER.error("Failed to write player colors file!", e);
		}
	}

	private static @Nonnull HashMap<UUID, Integer> readColorFile(Path path)
	{
		try
		{
			if (Files.notExists(path))
			{
				return new HashMap<>();
			}

			JsonObject root = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
			Map<String, JsonElement> rawMap = root.asMap();

			HashMap<UUID, Integer> newMap = new HashMap<>(rawMap.size());
			for (Map.Entry<String, JsonElement> entry : rawMap.entrySet())
			{
				UUID uuid = tryParseUuid(entry.getKey());
				Integer color = tryGetInt(entry.getValue());

				if (uuid != null && color != null)
				{
					newMap.put(uuid, color);
				}
			}
			return newMap;

		}
		catch (IOException e)
		{
			SweptAway.LOGGER.error("Failed to read player colors file!", e);
			return new HashMap<>();
		}
	}

	private static @Nullable UUID tryParseUuid(String string)
	{
		try
		{
			return UUID.fromString(string);
		}
		catch (IllegalArgumentException e)
		{
			return null;
		}
	}

	private static @Nullable Integer tryGetInt(JsonElement element)
	{
		try
		{
			return element.getAsInt();
		}
		catch (UnsupportedOperationException e)
		{
			return null;
		}
	}
}
