package com.github.comtihon.catcherintellijplugin.project

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.ui.ComboBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SpringLayout


class WizardStep : ModuleWizardStep() {
    override fun getComponent(): JComponent {
        // TODO construct window with:
        // select catcher sdk
        // use catcher-in-docker or conda-catcher or native one
        // docker) download catcher image
        // conda-catcher) add env with catcher
        // native) point to the catcher executable if not found
        val layout = SpringLayout()
        val mainPanel = JPanel()
        mainPanel.layout = layout

        val comboBoxPane = JPanel() //use FlowLayout

        val comboBoxItems = arrayOf("Catcher", "+ Add new")
        val cb: ComboBox<*> = ComboBox<Any?>(comboBoxItems)
        cb.isEditable = false
        comboBoxPane.add(cb)
        val sdkLabel = JLabel("Project SDK:")
        mainPanel.add(sdkLabel)
        mainPanel.add(JLabel("Test2"))
        mainPanel.add(comboBoxPane)

        layout.putConstraint(SpringLayout.WEST, sdkLabel, 5, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, sdkLabel, 5, SpringLayout.NORTH, mainPanel);

        return mainPanel
    }

    override fun updateDataModel() {
        //todo update model according to UI
    }
}
