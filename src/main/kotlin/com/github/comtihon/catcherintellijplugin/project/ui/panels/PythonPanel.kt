package com.github.comtihon.catcherintellijplugin.project.ui.panels

import com.github.comtihon.catcherintellijplugin.project.sdk.CatcherSdkAdditionalData
import com.github.comtihon.catcherintellijplugin.project.sdk.CatcherSdkType
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkType
import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.CollectionComboBoxModel
import com.intellij.ui.components.JBList
import com.intellij.ui.layout.panel
import com.jetbrains.python.sdk.PythonSdkType
import com.jetbrains.python.sdk.add.PyAddSdkDialog
import org.jetbrains.annotations.Nullable
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class PythonPanel : SdkSelectionPanel {

    private val sdkTable: ProjectJdkTable = ProjectJdkTable.getInstance()
    private var pythonSdkModel: CollectionComboBoxModel<String>? = null

    init {
        val pySdks = sdkTable.getSdksOfType(PythonSdkType.getInstance())
        val sdkNames = pySdks
            .map { it.name }
            .toMutableList()
        sdkNames.add("+ Add new")
        pythonSdkModel = CollectionComboBoxModel(sdkNames)
    }

    val catcherVersion = JTextField("Latest")


    fun create(): JPanel {
        val pySdks: MutableList<Sdk> = sdkTable.getSdksOfType(PythonSdkType.getInstance())

        val pythonSdks = ComboBox(pythonSdkModel!!)
        pythonSdks.addActionListener {
            if (pythonSdks.selectedItem == "+ Add new") {  // add new python sdk
                PyAddSdkDialog.show(null, null, pySdks) { et ->
                    if (et != null) {
                        WriteAction.run<Exception> { sdkTable.addJdk(et) }
                        pySdks.add(et)
                        pythonSdkModel!!.add(0, et.name)
                        pythonSdks.selectedItem = et.name
                    }
                }
            } else {  // use selected python sdk
                // TODO get installed catcher and update latest version + installed libraries
            }
        }
        val stepList = JBList<JComponent>()
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

    override fun getSelectedSdk(): Sdk? {
        // get connected python sdk or return null, as catcher is based on python
        val pythonSdk: @Nullable Sdk = sdkTable.findJdk(pythonSdkModel!!.selectedItem.toString()) ?: return null
        val catcherSdkType = SdkType.findInstance(CatcherSdkType::class.java)
        val catcherSdk =  ProjectJdkImpl(catcherSdkType.name, catcherSdkType) // TODO version?
        catcherSdk.sdkAdditionalData = CatcherSdkAdditionalData(pythonSdk, null)
        return catcherSdk;
    }
}