package com.github.comtihon.catcherintellijplugin.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.github.comtihon.catcherintellijplugin.services.CatcherSdkService

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<CatcherSdkService>()
    }
}
