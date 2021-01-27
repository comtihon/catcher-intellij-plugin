package com.github.comtihon.catcherintellijplugin.project.ui.panels

import com.intellij.openapi.projectRoots.Sdk
import javax.swing.JPanel

class DockerPanel : SdkSelectionPanel {

    var dockerImage = "comtihon/catcher:latest"

    fun create(): JPanel {
        return JPanel()
    }

    override fun getSelectedSdk(): Sdk? {
        TODO("Not yet implemented")
    }
}