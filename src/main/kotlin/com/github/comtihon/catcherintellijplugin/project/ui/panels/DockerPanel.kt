package com.github.comtihon.catcherintellijplugin.project.ui.panels

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.ui.layout.panel
import javax.swing.JLabel
import javax.swing.JPanel

class DockerPanel : SdkSelectionPanel {

    var dockerImage = "comtihon/catcher:latest"

    fun create(): JPanel {
        val stubPanel = createStub()
        stubPanel.isEnabled = false
        // TODO optional dependency and https://plugins.jetbrains.com/docs/intellij/plugin-extension-points.html?from=jetbrains.org#using-extension-points
        if(!PluginManagerCore.isPluginInstalled(PluginId.findId("Docker")!!))
            return stubPanel

        return panel {
            row {
                row {
                    label("Python SDK")
//                    pythonSdks()
                }
                row {
                    label("Catcher Version")
//                    catcherVersion()
                }
                row {
                    label("External Steps")
//                    stepList()
                }
            }
        }
    }

    private fun createStub(): JPanel {
        return panel {
            row {
                label("Docker plugin required")
                browserLink(
                    "Install",
                    "https://plugins.jetbrains.com/plugin/7724-docker"
                )
            }
        }
    }

    override fun getSelectedSdk(): Sdk? {
        TODO("Not yet implemented")
    }
}