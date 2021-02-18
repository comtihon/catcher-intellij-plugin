package com.github.comtihon.catcherintellijplugin.project.sdk

import com.intellij.openapi.projectRoots.ProjectJdkTable
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkAdditionalData
import org.jdom.Element

// TODO modules versions?
// TODO isInstalled flag/status?
class CatcherSdkAdditionalData(
    val pythonSdk: Sdk?,
    val dockerImage: String? = null,
    val catcherDesiredVersion: String?,
    val catcherActualVersion: String? = null,
    private val sdkName: String? = null
) : SdkAdditionalData {

    fun save(rootElement: Element) {
        if (pythonSdk != null) {
            rootElement.setAttribute("ASSOCIATED_PYTHON_SDK", pythonSdk.name)
        }
        if (catcherActualVersion != null)
            rootElement.setAttribute("CORE_ACTUAL_VERSION", catcherActualVersion)
        if (catcherDesiredVersion != null)
            rootElement.setAttribute("CORE_DESIRED_VERSION", catcherDesiredVersion)
    }

    fun getBoundedSdk(): Sdk? {
        if (pythonSdk != null)
            return pythonSdk
        // TODO docker image
        if (sdkName != null)
            return ProjectJdkTable.getInstance().findJdk(sdkName)
        return null
    }

    companion object {
        fun load(element: Element?): CatcherSdkAdditionalData? {
            if (element != null) {
                val sdkName = element.getAttributeValue("ASSOCIATED_PYTHON_SDK")
                val catcherActualVersion = element.getAttributeValue("CORE_ACTUAL_VERSION")
                val catcherDesiredVersion = element.getAttributeValue("CORE_DESIRED_VERSION")
                if (sdkName != null) {
                    return CatcherSdkAdditionalData(
                        pythonSdk = null,
                        dockerImage = null,
                        catcherActualVersion = catcherActualVersion,
                        catcherDesiredVersion = catcherDesiredVersion,
                        sdkName = sdkName
                    )
                }
            }
            return null
        }
    }
}
