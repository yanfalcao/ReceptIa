pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ReceptIa"
include(":app")
include(":core:model")
include(":core:database")
include(":core:data")
include(":core:network")
include(":core:designsystem")
include(":feature:recipecatalog")
include(":feature:avatar")
include(":feature:home")
include(":feature:description")
include(":feature:createrecipe")
include(":core:authentication")
include(":feature:login")
include(":feature:splash")
include(":feature:panehomedescription")
include(":feature:panecatalogdescription")
