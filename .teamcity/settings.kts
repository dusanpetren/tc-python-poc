import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.python
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {

    buildType(Cicd)

    subProject(AoC2022Kotlin)
}

object Cicd : BuildType({
    name = "CICD"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        python {
            environment = venv {
            }
            command = pytest {
            }
        }
        python {
            environment = venv {
            }
            command = file {
                filename = "main.py"
            }
        }
        dockerCommand {
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
            }
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})


object AoC2022Kotlin : Project({
    name = "AoC 2022 Kotlin"

    vcsRoot(AoC2022Kotlin_HttpsGithubComDusanpetrenAoC2022KotlinRefsHeadsMain)

    buildType(AoC2022Kotlin_Build)
})

object AoC2022Kotlin_Build : BuildType({
    name = "Build"

    vcs {
        root(AoC2022Kotlin_HttpsGithubComDusanpetrenAoC2022KotlinRefsHeadsMain)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object AoC2022Kotlin_HttpsGithubComDusanpetrenAoC2022KotlinRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/dusanpetren/AoC-2022-Kotlin#refs/heads/main"
    url = "https://github.com/dusanpetren/AoC-2022-Kotlin"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "dusanpetren"
        password = "credentialsJSON:78fceeaa-b8a7-408c-a626-2afd78ca8237"
    }
})
