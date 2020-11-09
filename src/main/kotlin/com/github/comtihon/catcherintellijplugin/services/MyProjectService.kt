package com.github.comtihon.catcherintellijplugin.services

import com.intellij.openapi.project.Project
import com.github.comtihon.catcherintellijplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
