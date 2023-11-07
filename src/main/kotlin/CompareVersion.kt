import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.github.g00fy2.versioncompare.Version
import io.ktor.client.request.*
import io.ktor.http.*
import utils.HttpUtil
import kotlin.system.exitProcess

/*
 * CompareVersion.kt
 * KotlinPlayground
 *
 * Created by Qian Qian "Cubik" on Tuesday Oct. 31.
 */

enum class VersionStatus {
    NOT_RELEASED,
    UP_TO_DATE,
    OUTDATED,
    ERROR
}

fun compareVersion(
    currentVersion: String,
    currentStage: String,
    comparedVersion: String,
    comparedStage: String
): VersionStatus {
    return try {
        val current = Version("$currentVersion-$currentStage", true)
        val compared = Version("$comparedVersion-$comparedStage", true)
        when {
            current < compared -> VersionStatus.OUTDATED
            current > compared -> VersionStatus.NOT_RELEASED
            else -> VersionStatus.UP_TO_DATE
        }
    } catch (e: Exception) {
        println(e)
        VersionStatus.ERROR
    }
}

fun getGitHubLatestRelease(
    repo: String
): Pair<String, String> {
    val url = "https://api.github.com/repos/$repo/releases"

    val response = HttpUtil.GetRequests.getBody(url) {
        accept(ContentType("application", "vnd.github+json"))
        headers {
            append("X-GitHub-Api-Version", "2022-11-28")
        }
    }

    val release = (Parser.default().parse(StringBuilder(response)) as JsonArray<JsonObject>)[0]
    val tagName = release.string("tag_name")?.substring(1)?.split("-")

    val version = tagName?.get(0) ?: "1.0.0"
    val stage = tagName?.get(1) ?: "stable"
    return (version to stage)
}

fun main(args: Array<String>) {
    val currentVersion: String?
    val currentStage: String?
    val latestVersion: String?
    val latestStage: String?

    println("CompareVersion.kt")
    print("Do you want to compare with a GitHub release? (y/n) ")
    val input = readlnOrNull()

    when (input) {
        "nt" -> {
            currentVersion = "1.0.0"
            currentStage = "stable"
            val status = compareVersion(currentVersion, currentStage, "exception", "exception")
            when (status) {
                VersionStatus.NOT_RELEASED -> println("The current version is not released yet.")
                VersionStatus.UP_TO_DATE -> println("The current version is up to date.")
                VersionStatus.OUTDATED -> println("The current version is outdated.")
                VersionStatus.ERROR -> println("Version parsing error.")
            }
            exitProcess(1)
        }
        "y" -> {
            print("Please enter the current version of the \"software\" (e.g. 1.0.0): ")
            currentVersion = readlnOrNull()
            print("Please enter the current stage of the \"software\" (e.g. stable): ")
            currentStage = readlnOrNull()
            print("Please enter the GitHub repo name (e.g. Cubik65536/KotlinPlayground): ")
            val repo = readlnOrNull()
            if (repo == null) {
                println("Invalid repo name.")
                exitProcess(1)
            }
            val (githubVersion, githubStage) = getGitHubLatestRelease(repo)
            latestVersion = githubVersion
            latestStage = githubStage
        }
        else -> {
            print("Please enter the current version of the \"software\" (e.g. 1.0.0): ")
            currentVersion = readlnOrNull()
            print("Please enter the current stage of the \"software\" (e.g. stable): ")
            currentStage = readlnOrNull()
            print("Please enter the latest version number (e.g. 1.0.0): ")
            latestVersion = readlnOrNull()
            print("Please enter the latest stage (e.g. stable): ")
            latestStage = readlnOrNull()
        }
    }

    val status = compareVersion(currentVersion!!, currentStage!!, latestVersion!!, latestStage!!)
    when (status) {
        VersionStatus.NOT_RELEASED -> println("The current version is not released yet.")
        VersionStatus.UP_TO_DATE -> println("The current version is up to date.")
        VersionStatus.OUTDATED -> println("The current version is outdated.")
        VersionStatus.ERROR -> println("Version parsing error.")
    }
    exitProcess(0)
}
