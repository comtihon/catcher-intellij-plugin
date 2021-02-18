package com.github.comtihon.catcherintellijplugin.project.ui.panels

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.ui.layout.panel
import javax.swing.JPanel
import javax.swing.JTextField

class DockerPanel : SdkSelectionPanel {

    fun create(): JPanel {
        if (!PluginManagerCore.isPluginInstalled(PluginId.findId("Docker")!!)) {
            val stubPanel = createStub()
            stubPanel.isEnabled = false
            return stubPanel
        }

        return panel {
            row {
                row {
                    label("Docker image")
                    JTextField("comtihon/catcher:latest")
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