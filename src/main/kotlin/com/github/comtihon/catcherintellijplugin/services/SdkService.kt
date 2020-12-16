package com.github.comtihon.catcherintellijplugin.services

import com.github.comtihon.catcherintellijplugin.exceptions.ExecutionFailedException
import com.intellij.openapi.projectRoots.SdkType
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException

class SdkService {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getAllSdks(): List<SdkType> {
        return emptyList()
    }

    fun addNewSdk() {

    }

    fun searchForSystemInstalled(): String? {
        return try {
            "catcher -v".runCommand()
        } catch (e: ExecutionFailedException) {
            log.debug("Got $e while checking if catcher installed locally")
            null
        }
    }

    fun installCatcherLocally(): String? {
        return try {
            "python -m pip install catcher".runCommand()
            null
        } catch (e: ExecutionFailedException) {
            log.error("Got $e while installing catcher locally.")
            e.message
        }
    }

    fun String.runCommand(workingDir: File = File(".")): String {
        return runCommand(this.split("\\s".toRegex()), workingDir)
    }

    private fun runCommand(command: List<String>, workingDir: File = File(".")): String {
        var proc: Process? = null
        try {
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