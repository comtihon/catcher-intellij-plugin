package com.github.comtihon.catcherintellijplugin.project.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import javax.swing.JComponent

class ModulesSelectionDialog(val selectedSteps: List<String>) : DialogWrapper(true) {


    override fun createCenterPanel(): JComponent {
        return panel {

        }
    }
}