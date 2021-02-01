package com.github.comtihon.catcherintellijplugin.project.ui

import com.github.comtihon.catcherintellijplugin.project.ui.panels.DockerPanel
import com.github.comtihon.catcherintellijplugin.project.ui.panels.PythonPanel
import com.github.comtihon.catcherintellijplugin.project.ui.panels.SdkSelectionPanel
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Splitter
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBList
import com.intellij.util.Consumer
import javax.swing.JComponent
import javax.swing.JPanel

class NewSDKDialog(private val sdkCreatedCallback: Consumer<in Sdk>) : DialogWrapper(true) {
    init {
        title = "Set up a new Catcher SDK"
        init()
    }

    private var activeSelectionPanel: SdkSelectionPanel? = null
    private var selectedSdk: Sdk? = null

    override fun createCenterPanel(): JComponent {
        val panel = Splitter()
        val sdkPanel = JPanel()
        val pythonPanelContainer = PythonPanel()
        val dockerPanelContainer = DockerPanel()
        val pythonPanel = pythonPanelContainer.create()
        val dockerPanel = dockerPanelContainer.create()
        sdkPanel.add(pythonPanel)
        sdkPanel.add(dockerPanel)
        val optionList = JBList("Python", "Docker")  // TODO add icons.
        panel.firstComponent = optionList
        dockerPanel.isVisible = false
        activeSelectionPanel = pythonPanelContainer
        panel.secondComponent = sdkPanel
        optionList.addListSelectionListener {
            if (optionList.selectedIndex == 0) {
                dockerPanel.isVisible = false
                pythonPanel.isVisible = true
                activeSelectionPanel = pythonPanelContainer
            } else {
                dockerPanel.isVisible = true
                pythonPanel.isVisible = false
                activeSelectionPanel = dockerPanelContainer
            }
        }
        return panel
    }

    override fun doValidate(): ValidationInfo? {
        selectedSdk = activeSelectionPanel!!.getSelectedSdk() ?: return ValidationInfo("Sdk is not selected")
        sdkCreatedCallback.consume(selectedSdk)
        return null
    }

}