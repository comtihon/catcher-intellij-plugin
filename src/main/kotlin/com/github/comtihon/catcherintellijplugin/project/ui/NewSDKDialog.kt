package com.github.comtihon.catcherintellijplugin.project.ui

import com.github.comtihon.catcherintellijplugin.project.ui.panels.DockerPanel
import com.github.comtihon.catcherintellijplugin.project.ui.panels.PythonPanel
import com.github.comtihon.catcherintellijplugin.services.SdkService
import com.github.comtihon.catcherintellijplugin.services.tool.Native
import com.github.comtihon.catcherintellijplugin.services.tool.SystemTool
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.Splitter
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.layout.Row
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class NewSDKDialog : DialogWrapper(true) {
    init {
        title = "Set up a new Catcher SDK"
        init()
    }


    override fun createCenterPanel(): JComponent {
        val panel = Splitter()
        val sdkPanel = JPanel()
        val pythonPanel = PythonPanel().create()
        val dockerPanel = DockerPanel().create()  // TODO check if docker available
        sdkPanel.add(pythonPanel)
        sdkPanel.add(dockerPanel)
        val optionList = JBList("Python", "Docker")  // TODO add icons.
        panel.firstComponent = optionList
        dockerPanel.isVisible = false
        panel.secondComponent = sdkPanel
        optionList.addListSelectionListener {
            if(optionList.selectedIndex == 0) {
                dockerPanel.isVisible = false
                pythonPanel.isVisible = true
            } else {
                dockerPanel.isVisible = true
                pythonPanel.isVisible = false
            }
        }
        return panel
    }

    override fun doValidate(): ValidationInfo? {
        // TODO correct python sdk should be selected.
        return super.doValidate()
    }

    private fun installLocally(row: Row) {
        // TODO depend on python plugin and try to get configured python?
        val service: SdkService = ServiceManager.getService(SdkService::class.java)
        val systemTool = Native()
        val installedLocally = service.searchForInstallations(systemTool)
        val localVersionField = JTextField(if (installedLocally.isNullOrEmpty()) "latest" else installedLocally[0])
        val installButton = JButton("Install")
        if (installedLocally.isNullOrEmpty()) {
            row.row {
                row {
                    label("Version")
                    localVersionField()
                }
                row {
                    installButton()
                    // TODO show external steps selection on install button
                    installButton.addActionListener {

//
                        // TODO save new python sdk?
//                        onInstallPress(localVersionField, systemTool, installButton)
                    }
                }
            }
        } else {
            row.row {
                row {
                    label("Version found")
                    localVersionField()
                }
                // TODO memorize the selection somehow (local catcher + version). Generate CatcherSdk?
            }
        }
    }


    private fun configureModules() {
        val selected = mutableListOf<String>()
        ModulesSelectionDialog(selected).showAndGet() // TODO ensure selected updated
        // TODO disable this form until selection is closed.
    }

    private fun onInstallPress(
        versionComponent: JTextField,
        systemTool: SystemTool,
        button: JButton
    ) {
        val service: SdkService = ServiceManager.getService(SdkService::class.java)
        val errorLogs = service.installCatcher(systemTool)
        if (errorLogs != null) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(
                "Can't install catcher:${versionComponent.text}\n$errorLogs",
                MessageType.ERROR, null
            ).createBalloon().showInCenterOf(versionComponent)
        } else {
            val installedVersion = service.searchForInstallations(systemTool)!![0]
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(
                "Catcher $installedVersion installed",
                MessageType.INFO, null
            ).createBalloon().showInCenterOf(versionComponent)
            button.text = "Installed"
            button.isEnabled = false
            versionComponent.text = installedVersion
            versionComponent.isEnabled = false
        }
    }

}