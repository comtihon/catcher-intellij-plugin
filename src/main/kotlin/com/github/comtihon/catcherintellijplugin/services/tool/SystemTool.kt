package com.github.comtihon.catcherintellijplugin.services.tool

import com.github.comtihon.catcherintellijplugin.exceptions.ExecutionFailedException
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException

abstract class SystemTool {
    private val log = LoggerFactory.getLogger(this::class.java)

    open fun version(): String {
        return try {
            execute("python --version")
        } catch (e: ExecutionFailedException) {
            "not installed"
        }
    }

    abstract fun install()

    abstract fun execute(command: String): String

    abstract fun listInstallations(): List<String>

    fun String.runCommand(workingDir: File = File(".")): String {
        return runCommand(this.split("\\s".toRegex()), workingDir)
    }

    fun runCommand(command: List<String>, workingDir: File = File(".")): String {
        var proc: Process? = null
        try {
            log.debug(command.joinToString { " " })
            proc = ProcessBuilder(command)
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()
            proc.waitFor()
            if (proc.exitValue() != 0)
                throw ExecutionFailedException(proc!!.errorStream.bufferedReader().readText())
            if (proc.errorStream.available() != 0) {
                log.warn(proc.errorStream.bufferedReader().readText())
            }
            return proc.inputStream.bufferedReader().readText()
        } catch (e: IOException) {
            throw ExecutionFailedException(proc?.errorStream?.bufferedReader()?.readText() ?: e.localizedMessage)
        }
    }
}