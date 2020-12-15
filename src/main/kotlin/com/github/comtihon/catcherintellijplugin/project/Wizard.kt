package com.github.comtihon.catcherintellijplugin.project

import com.github.comtihon.catcherintellijplugin.services.SdkService
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.projectRoots.SdkType
import com.intellij.ui.layout.panel
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent
import javax.swing.JOptionPane


class WizardStep(private val sdkService: SdkService) : ModuleWizardStep() {
    override fun getComponent(): JComponent {
        // TODO construct window with:
        // select catcher sdk
        // use catcher-in-docker or conda-catcher or native one
        // docker) download catcher image
        // conda-catcher) add env with catcher
        // native) point to the catcher executable if not found

        val installedSdks: List<SdkType> = sdkService.getAllSdks()
        val sdkNames: MutableList<String> = installedSdks.map { it.name }.toMutableList()
        sdkNames.add("+ Add new")
        val comboBoxItems: Array<String> = sdkNames.toTypedArray()
        val comboBoxModel = DefaultComboBoxModel(comboBoxItems)
        return panel {
            row {
                label("Project SDK:")
                comboBox<String>(
                    comboBoxModel,
                    { comboBoxModel.selectedItem as String? ?: comboBoxModel.getElementAt(0) },
                    { comboBoxModel.selectedItem = it })
                    .component.addActionListener {
                        if (comboBoxModel.selectedItem == "+ Add new") {
                            sdkService.addNewSdk() // TODO popup?
                        }
                        // TODO save selection somewhere?
                    }
            }
        }
    }

    override fun updateDataModel() {
        // TODO select sdk?
        // TODO do not finish wizard without sdk selection!
    }
}
