package com.github.comtihon.catcherintellijplugin.services

import com.github.comtihon.catcherintellijplugin.Catcher
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(Catcher.message("projectService", project.name))
    }
}
