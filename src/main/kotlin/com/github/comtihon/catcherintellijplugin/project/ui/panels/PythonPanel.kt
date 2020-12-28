package com.github.comtihon.catcherintellijplugin.project.ui.panels

import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.CollectionComboBoxModel
import com.intellij.ui.components.JBList
import com.intellij.ui.layout.panel
import com.jetbrains.python.sdk.PythonSdkType
import com.jetbrains.python.sdk.add.PyAddSdkDialog
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class PythonPanel {

    fun create(): JPanel {
        val sdkTable = ProjectJdkTable.getInstance()
        val pySdks = sdkTable.getSdksOfType(PythonSdkType.getInstance())
        val sdkNames = pySdks
            .map { it.name }
            .toMutableList()
        sdkNames.add("+ Add new")
        val pythonSdkModel = CollectionComboBoxModel(sdkNames)
        val pythonSdks = ComboBox(pythonSdkModel)
        pythonSdks.addActionListener {
            if (pythonSdks.selectedItem == "+ Add new") {  // add new python sdk
                PyAddSdkDialog.show(null, null, pySdks) { et ->
                    if (et != null) {
                        WriteAction.run<Exception> { sdkTable.addJdk(et) }
                        pySdks.add(et)
                        sdkNames.add(0, et.name)
                        pythonSdkModel.update()
                    }
                }
            } else {  // use selected python sdk
                // TODO validate?
            }
        }
        val catcherVersion = JTextField("Latest")
        val stepList = JBList<JComponent>()
        // TODO on ok press catcher should try to install. Notification should be shown (Events)
        return panel {
            row {
                row {
                    label("Python SDK")
                    pythonSdks()
                }
                row {
                    label("Catcher Version")
                    catcherVersion()
                }
                row {
                    label("External Steps")
                    stepList()
                }
            }
        }
    }
}