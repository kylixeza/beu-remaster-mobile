pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://jitpack.io")
    }
}

rootProject.name = "Beu Skripsi"
include(":app")
include(":common")
include(":core")
include(":splash")
include(":onboard")
include(":router")
include(":auth")
include(":main")
include(":home")
include(":camera")
include(":profile")
