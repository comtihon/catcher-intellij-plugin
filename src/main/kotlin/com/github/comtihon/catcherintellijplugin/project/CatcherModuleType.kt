package com.github.comtihon.catcherintellijplugin.project

import com.github.comtihon.catcherintellijplugin.core.Icons
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import org.jetbrains.annotations.NotNull
import javax.swing.Icon


private const val ID = "CATCHER_MODULE_TYPE"


object CatcherModuleType : ModuleType<CatcherModuleBuilder>(ID) {

    fun getInstance(): CatcherModuleType {
        return ModuleTypeManager.getInstance().findByID(ID) as CatcherModuleType
    }

    @NotNull
    override fun createModuleBuilder(): CatcherModuleBuilder {
        return CatcherModuleBuilder()
    }

    @NotNull
    override fun getName(): String {
        return "Catcher Test"
    }

    @NotNull
    override fun getDescription(): String {
        return "Create a Catcher E2E test project"
    }

    @NotNull
    override fun getNodeIcon(b: Boolean): Icon {
        return Icons.SmallIcon
    }

    @NotNull
    override fun createWizardSteps(@NotNull wizardContext: WizardContext,
                                   @NotNull moduleBuilder: CatcherModuleBuilder,
                                   @NotNull modulesProvider: ModulesProvider): Array<ModuleWizardStep> {
        return super.createWizardSteps(wizardContext, moduleBuilder, modulesProvider)
    }
}