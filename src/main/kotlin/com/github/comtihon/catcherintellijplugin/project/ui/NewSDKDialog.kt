package com.github.comtihon.catcherintellijplugin.project.ui

import com.github.comtihon.catcherintellijplugin.services.SdkService
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import javax.swing.JComponent
import javax.swing.JTextField

enum class SdkGrounding {
    SYSTEM, DOCKER, VENV, CONDA
}

class NewSDKDialog : DialogWrapper(true) {
    init {
        title = "Set up a new Catcher SDK"
        init()
    }

    var dockerImage = "comtihon/catcher:latest"

    private fun installLocally(row: Row) {
        val service: SdkService = ServiceManager.getService(SdkService::class.java)
        val installedLocally = service.searchForSystemInstalled()
        val localVersionField = JTextField(installedLocally ?: "latest")
        if (installedLocally == null) {
            row.row {
                row {
                    label("Version to install")
                    localVersionField()
                }
                row {
                    button("Install", actionListener = {
                        onInstallPress(localVersionField.text, localVersionField)
                    })
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

    private fun onInstallPress(version: String, versionComponent: JComponent) {
        println("install catcher $version")
        val service: SdkService = ServiceManager.getService(SdkService::class.java)
        val errorLogs = service.installCatcherLocally()
        if (errorLogs != null) {
            JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(
                "Can't install catcher:$version\n$errorLogs" +
                        "\nTry running 'pip install catcher' or 'pip3 install catcher' in your terminal.",
                MessageType.ERROR, null
            ).createBalloon().showInCenterOf(versionComponent)
        }
        println("got $errorLogs")
        // TODO notify user in case of failure!
    }

}