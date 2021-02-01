package com.github.comtihon.catcherintellijplugin.services

import com.github.comtihon.catcherintellijplugin.project.sdk.CatcherSdkType
import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.projectRoots.Sdk
import org.slf4j.LoggerFactory

// TODO remove me?
class SdkService {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val sdkTable: ProjectJdkTable = ProjectJdkTable.getInstance()

    fun getAllSdks(): List<Sdk> {
        return sdkTable.getSdksOfType(CatcherSdkType.getInstance())
    }

    fun addNewSdk() {

    }
}