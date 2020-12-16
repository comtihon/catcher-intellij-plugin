package com.github.comtihon.catcherintellijplugin.project

import com.github.comtihon.catcherintellijplugin.project.ui.NewSDKDialog
import com.github.comtihon.catcherintellijplugin.services.SdkService
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.projectRoots.SdkType
import com.intellij.ui.layout.panel
import javax.swing.ComboBoxModel
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent


class WizardStep(private val sdkService: SdkService) : ModuleWizardStep() {
    var comboBoxModel: ComboBoxModel<String> = DefaultComboBoxModel(emptyArray())

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
        comboBoxModel = DefaultComboBoxModel(comboBoxItems)
        return panel {
            row {
                label("Project SDK:")
                comboBox(
                    comboBoxModel,
                    { comboBoxModel.selectedItem as String? ?: comboBoxModel.getElementAt(0) },
                    { comboBoxModel.selectedItem = it })
                    .component.addActionListener {
                        if (comboBoxModel.selectedItem == "+ Add new") {
                            sdkService.addNewSdk()
                            NewSDKDialog().showAndGet()  // TODO after sdk was created it should be automatically selected here.
                        }
                        // TODO save selection somewhere?
                    }
            }
        }
    }

    override fun updateDataModel() {
        // TODO select sdk?
    }

    override fun validate(): Boolean {
        // TODO show notification "Please select SDK"
        return comboBoxModel.selectedItem != "+ Add new"
    }
}
