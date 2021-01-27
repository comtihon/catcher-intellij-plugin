package com.github.comtihon.catcherintellijplugin.project.ui.panels

import com.intellij.openapi.projectRoots.Sdk

interface SdkSelectionPanel {

    fun getSelectedSdk(): Sdk?
}