package com.github.comtihon.catcherintellijplugin.project

import com.github.comtihon.catcherintellijplugin.project.ui.NewSDKDialog
import com.github.comtihon.catcherintellijplugin.services.SdkService
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.layout.panel
import java.awt.Point
import javax.swing.ComboBoxModel
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent
import javax.swing.JPanel


class WizardStep(private val sdkService: SdkService) : ModuleWizardStep() {
    var comboBoxModel: ComboBoxModel<String> = DefaultComboBoxModel(emptyArray())
    var mainPanel: JPanel? = null

    override fun getComponent(): JComponent {
        mainPanel = createPanel()
        return mainPanel!!
    }

    override fun updateDataModel() {
        // TODO select sdk?
        println("Wizard update data model")
    }

    override fun validate(): Boolean {
        if (comboBoxModel.selectedItem == "+ Add new") {  // sdk not selected
            JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("SDK required", MessageType.WARNING, null)
                .createBalloon()
                .show(RelativePoint(mainPanel!!.getComponent(1), Point(5, 5)), Balloon.Position.above)
            return false
        }
        // TODO show notification "Please select SDK"
        return comboBoxModel.selectedItem != "+ Add new"
    }

    private fun createPanel(): JPanel {
        val installedSdks: List<Sdk> = sdkService.getAllSdks()
        val sdkNames: MutableList<String> = installedSdks.map { it.name }.toMutableList()
        sdkNames.add("+ Add new")
        val comboBoxItems: Array<String> = sdkNames.toTypedArray()
        comboBoxModel = DefaultComboBoxModel(comboBoxItems)
        return panel {
            row {
                label("Project SDK:")
                cell {
                    comboBox(  // TODO JdkComboBox
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
    }
}
