package com.github.comtihon.catcherintellijplugin.services

import com.github.comtihon.catcherintellijplugin.exceptions.ExecutionFailedException
import com.github.comtihon.catcherintellijplugin.services.tool.SystemTool
import com.intellij.openapi.projectRoots.SdkType
import org.slf4j.LoggerFactory

class SdkService {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getAllSdks(): List<SdkType> {
        return emptyList()
    }

    fun addNewSdk() {

    }

    fun searchForInstallations(tool: SystemTool): List<String>? {
        return try {
            tool.listInstallations()
        } catch (e: ExecutionFailedException) {
            log.debug("Got $e while checking if catcher installed locally")
            null
        }
    }

    fun installCatcher(tool: SystemTool): String? {
        return try {
            tool.install()
            null
        } catch (e: ExecutionFailedException) {
            log.error("Got $e while installing catcher locally.")
            e.message
        }
    }
}