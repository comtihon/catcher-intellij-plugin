package com.github.comtihon.catcherintellijplugin.project

import com.github.comtihon.catcherintellijplugin.services.SdkService
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.roots.ModifiableRootModel
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable


class CatcherModuleBuilder : ModuleBuilder() {

    override fun setupRootModel(@NotNull model: ModifiableRootModel) {}

    override fun getModuleType(): CatcherModuleType {
        return CatcherModuleType.getInstance()
    }

    @Nullable
    override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?): ModuleWizardStep? {
        return WizardStep(ServiceManager.getService(SdkService::class.java))
    }
}