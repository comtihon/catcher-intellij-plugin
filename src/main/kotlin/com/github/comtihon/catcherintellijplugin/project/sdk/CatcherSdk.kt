package com.github.comtihon.catcherintellijplugin.project.sdk

import com.intellij.openapi.projectRoots.*
import org.jdom.Element
import com.intellij.openapi.projectRoots.SdkModificator

import com.intellij.openapi.projectRoots.SdkModel

import com.intellij.openapi.projectRoots.Sdk




class CatcherSdk(name: String): SdkType(name) {
    override fun saveAdditionalData(additionalData: SdkAdditionalData, additional: Element) {
        TODO("Not yet implemented")
    }

    override fun suggestHomePath(): String? {
        TODO("Not yet implemented")
    }

    override fun isValidSdkHome(path: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String?): String {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun getPresentableName(): String {
        return name
    }
}