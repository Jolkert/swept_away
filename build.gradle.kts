import net.msrandom.minecraftcodev.runs.MinecraftRunConfiguration

// ig cloche is busted (https://github.com/terrarium-earth/cloche/issues/157) -morgan 2026-07-29
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

plugins {
	id("earth.terrarium.cloche") version "0.19.13"
}

group = "dev.jolkert"
version = "2.0.1"

repositories {
	cloche.main()
	cloche.librariesMinecraft()
	mavenCentral()
	cloche {
		mavenFabric()
		mavenNeoforgedMeta()
		mavenNeoforged()
		mavenParchment()
	}

	maven("https://maven.terraformersmc.com/") { name = "Terraformers" } // Mod Menu
}

cloche {
	metadata {
		modId = "swept_away"
		name = "Swept Away"
		description = "Removes sweeping from unenchanted swords."
		icon = "assets/swept_away/icon.png"
		author("jolkert")
		license = "GPL-3.0"
	}

	common {}

	val latestVersion = "26.2" // Fabric Only
	val ltsVersion = "1.21.1" // Fabric & Neoforge

	val commonLts = common("common:$ltsVersion") {
		metadata {
			mixins.from("src/common/$ltsVersion/main/swept_away.common-$ltsVersion.mixins.json")
		}
	}

	// ------------------------------------------
	// | FABRIC (https://fabricmc.net/develop/) |
	// ------------------------------------------
	fabric("fabric:$latestVersion") {
		minecraftVersion = latestVersion
		loaderVersion = "0.19.3"
		includedClient()

		metadata {
			entrypoint("main", "dev.jolkert.sweptaway.fabric.SweptAwayFabric")
			mixins.from("src/fabric/$latestVersion/main/swept_away.fabric-$latestVersion.mixins.json")
		}

		val modMenuVersion = "20.0.1"
		dependencies {
			fabricApi("0.156.0")
			runtimeOnly("com.terraformersmc:modmenu:$modMenuVersion")
		}

		runs {
			server()
			client() {
				setUsernameAndUuid()
			}
		}
	}

	fabric("fabric:$ltsVersion") {
		dependsOn(commonLts)

		minecraftVersion = ltsVersion
		loaderVersion = "0.19.3"
		includedClient()
		mappings {
			official()
			parchment("2024.11.17") // https://parchmentmc.org/docs/getting-started.html
		}

		metadata {
			entrypoint("main", "dev.jolkert.sweptaway.fabric.SweptAwayFabric")
		}

		val modMenuVersion = "11.0.4"
		dependencies {
			fabricApi("0.116.15")
			modImplementation("com.terraformersmc:modmenu:$modMenuVersion")
		}

		runs {
			server()
			client() {
				setUsernameAndUuid()
			}
		}

	}

	// -------------------------------------
	// | NEOFORGE (https://neoforged.net/) |
	// -------------------------------------
	neoforge {
		dependsOn(commonLts)

		minecraftVersion = ltsVersion
		loaderVersion = "21.1.244"
		mappings {
			official()
			parchment("2024.11.17") // https://parchmentmc.org/docs/getting-started.html
		}

		runs {
			server()
			client() {
				setUsernameAndUuid()
			}
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
