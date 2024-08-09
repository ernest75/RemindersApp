pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RemindersApp"
include(":app")
include(":core:view")
include(":feature:reminders")
include(":feature:reminderdetails")
include(":feature:remindercreation")
include(":core:database")
include(":domain:local")
include(":domain:data")
include(":testutils")
