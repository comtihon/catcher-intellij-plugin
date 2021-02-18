package com.github.comtihon.catcherintellijplugin.project.activity

import com.github.comtihon.catcherintellijplugin.services.CatcherSdkService
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class ProjectStartupActivity: StartupActivity {
    override fun runActivity(project: Project) {
        project.getService(CatcherSdkService::class.java).ensureSdkVersion()
    }
}