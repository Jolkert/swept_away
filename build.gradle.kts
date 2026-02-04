import net.msrandom.minecraftcodev.runs.MinecraftRunConfiguration
import org.gradle.api.file.DuplicatesStrategy

plugins {
	id("earth.terrarium.cloche") version "0.17.7"
	kotlin("jvm") version "2.2.20"
}

repositories {
	cloche.librariesMinecraft()

	mavenCentral()

	cloche {
		main()

		mavenFabric()
		mavenNeoforgedMeta()
		mavenNeoforged()
	}
}

cloche {
	minecraftVersion = "1.21.1"

	metadata {
		modId = "swept_away"
		name = "Swept Away"
		license = "GPL-3.0"
		description = "Remove sweeping from unenchanted swords!"
		icon = "assets/swept_away/icon.png"

		author("jolkert")
	}

	mappings {
		official()
		parchment("2024.11.17")
	}

	neoforge {
		loaderVersion = "21.1.135"
		metadata {
			mixins.from("src/common/swept_away.mixins.json")
		}

		data()

		runs {
			server {
				args("nogui")
			}
			client {
				setUsernameAndUuid()
			}
			data()
		}
	}

	fabric {
		loaderVersion = "0.16.10"

		metadata {
			entrypoint("main", "dev.jolkert.sweptaway.fabric.SweptAwayFabric");
			mixins.from("src/common/swept_away.mixins.json")
		}

		data()
		client {
			tasks.named<Jar>(sourceSet.jarTaskName) {
				duplicatesStrategy = DuplicatesStrategy.INCLUDE
			}
		}

		dependencies {
			fabricApi("0.115.2")
		}

		runs {
			server()
			client {
				setUsernameAndUuid()
			}
			data()
		}
	}
}


fun MinecraftRunConfiguration.setUsernameAndUuid()
{
	val username = System.getenv("MC_USER")
	if (!username.isNullOrBlank())
	{
		args("--username", username)
	}

	val uuid = System.getenv("MC_UUID")
	if (!uuid.isNullOrBlank())
	{
		args("--uuid", uuid)
	}
}
