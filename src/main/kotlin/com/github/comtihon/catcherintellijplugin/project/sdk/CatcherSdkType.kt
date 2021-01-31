package com.github.comtihon.catcherintellijplugin.project.sdk

import com.github.comtihon.catcherintellijplugin.core.Icons
import com.github.comtihon.catcherintellijplugin.project.ui.NewSDKDialog
import com.intellij.openapi.projectRoots.*
import com.intellij.util.Consumer
import org.jdom.Element
import javax.swing.Icon
import javax.swing.JComponent


class CatcherSdkType : SdkType("Catcher") {

    override fun saveAdditionalData(additionalData: SdkAdditionalData, additional: Element) {
        if (additionalData is CatcherSdkAdditionalData) {
            additionalData.save(additional)
        }
    }

    override fun loadAdditionalData(currentSdk: Sdk, additional: Element): SdkAdditionalData? {
        return CatcherSdkAdditionalData.load(additional)
    }

    override fun suggestHomePath(): String? {
        TODO("Not yet implemented")
    }

    override fun isValidSdkHome(path: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String?): String {
        TODO("Not yet implemented")
        // TODO detect sdk version from sdk home (path to python)
    }

    override fun getIcon(): Icon {
        return Icons.SmallIcon
    }

    override fun supportsCustomCreateUI(): Boolean {
        return true
    }

    override fun showCustomCreateUI(
        sdkModel: SdkModel,
        parentComponent: JComponent,
        sdkCreatedCallback: Consumer<in Sdk>
    ) {
        NewSDKDialog().showAndGet()
    }

    override fun setupSdkPaths(sdk: Sdk, sdkModel: SdkModel): Boolean {
        val modificator = sdk.sdkModificator
        modificator.versionString = getVersionString(sdk)
        modificator.commitChanges() // save
        return true
    }

    override fun createAdditionalDataConfigurable(
        sdkModel: SdkModel,
        sdkModificator: SdkModificator
    ): AdditionalDataConfigurable? {
        return null
    }

    override fun getPresentableName(): String {
        return name
    }

    companion object {
        fun getInstance(): CatcherSdkType {
            return findInstance(CatcherSdkType::class.java)
        }
    }
}