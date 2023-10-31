import kotlin.system.exitProcess

/*
 * CompareVersion.kt
 * KotlinPlayground
 *
 * Created by Qian Qian "Cubik" on Tuesday Oct. 31.
 */

fun compareVersion(version: String, latest: String) {

}

fun main(args: Array<String>) {
    println("CompareVersion.kt")
    println("Do you want to compare with a GitHub release? (y/n)")
    val input = readlnOrNull()
    if (input == "y") {
        println("Please enter the GitHub repo name (e.g. Cubik65536/KotlinPlayground): ")
        val repo = readlnOrNull()
        println("Please enter the version to compare with (e.g. v1.0.0): ")
        val version = readlnOrNull()
        if (repo == null || version == null) {
            println("Invalid input.")
            exitProcess(1)
        }
    } else {
        println("Please enter the latest version (e.g. v1.0.0): ")
        val latest = readlnOrNull()
        println("Please enter the version to compare with (e.g. v1.0.0): ")
        val version = readlnOrNull()
        if (latest == null || version == null) {
            println("Invalid input.")
            exitProcess(1)
        }
    }
 }
