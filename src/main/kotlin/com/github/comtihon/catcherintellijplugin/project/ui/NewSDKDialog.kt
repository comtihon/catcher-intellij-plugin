package com.github.comtihon.catcherintellijplugin.project.ui

import com.github.comtihon.catcherintellijplugin.services.SdkService
import com.github.comtihon.catcherintellijplugin.services.tool.Native
import com.github.comtihon.catcherintellijplugin.services.tool.SystemTool
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JTextField

class NewSDKDialog : DialogWrapper(true) {
    init {
        title = "Set up a new Catcher SDK"
        init()
    }

    var dockerImage = "comtihon/catcher:latest"

    private fun installLocally(row: Row) {
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
                    installButton.addActionListener {
                        onInstallPress(localVersionField, systemTool, installButton)
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

    override fun createCenterPanel(): JComponent {
        val dockerImageField = JTextField("some string")
        // TODO memorize catcher installation on ok pressed.
        return panel {
            row {
                label("Where to use Catcher from?")
                buttonGroup {
                    row {
                        radioButton(
                            "System",
                            "Use Catcher from System's Python (installs if necessary)"
                        )
                        hideableRow("Settings") {
                            installLocally(this)
                        }
                    }
                    row {
                        radioButton(
                            "Venv",
                            "Use Python virtual environment and install Catcher"
                        )
                        hideableRow("Settings") {
                            noteRow("Use System's Python and install Catcher")
                        }
                    }
                    row {
                        radioButton(
                            "Conda",
                            "Use Conda virtual environment and install Catcher"
                        )
                        hideableRow("Settings") {
                            noteRow("Use System's Python and install Catcher")
                        }
                    }
                    row {
                        radioButton(
                            "Docker", "Use Catcher docker image with all dependencies " +
                                    "preinstalled. This require docker service installed locally. Catcher's latest image" +
                                    "will be downloaded"
                        )
                        hideableRow("Settings") {
                            label("Docker image")
                            dockerImageField()
                        }
                    }
                }
            }
        }
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