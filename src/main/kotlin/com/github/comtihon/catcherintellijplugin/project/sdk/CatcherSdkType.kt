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

    override fun suggestHomePath(): String {
        return "/usr/bin"
    }

    override fun isValidSdkHome(path: String?): Boolean {
        return true
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String?): String {
        // TODO detect sdk version from sdk home (path to python)
        return "Catcher"
    }

    override fun getVersionString(sdk: Sdk): String? {
        if (sdk.sdkAdditionalData != null && sdk.sdkAdditionalData is CatcherSdkAdditionalData) {
            val version = (sdk.sdkAdditionalData as CatcherSdkAdditionalData).catcherActualVersion
            if (version != null)
                return version
            return (sdk.sdkAdditionalData as CatcherSdkAdditionalData).catcherDesiredVersion  // TODO not installed?
        }
        return super.getVersionString(sdk)
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
        selectedSdk: Sdk?,
        sdkCreatedCallback: Consumer<in Sdk>
    ) {
        NewSDKDialog(sdkCreatedCallback).showAndGet()
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