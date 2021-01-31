package com.github.comtihon.catcherintellijplugin.services

import com.github.comtihon.catcherintellijplugin.exceptions.ExecutionFailedException
import com.github.comtihon.catcherintellijplugin.project.sdk.CatcherSdkType
import com.github.comtihon.catcherintellijplugin.services.tool.SystemTool
import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.projectRoots.Sdk
import org.slf4j.LoggerFactory

class SdkService {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val sdkTable: ProjectJdkTable = ProjectJdkTable.getInstance()

    fun getAllSdks(): List<Sdk> {
        return sdkTable.getSdksOfType(CatcherSdkType.getInstance())
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