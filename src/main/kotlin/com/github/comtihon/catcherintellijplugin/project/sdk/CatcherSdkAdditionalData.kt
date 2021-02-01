package com.github.comtihon.catcherintellijplugin.project.sdk

import com.intellij.openapi.projectRoots.SdkAdditionalData
import org.jdom.Element

// TODO modules versions?
// TODO isInstalled flag/status?
class CatcherSdkAdditionalData(
    val pythonSdk: String?,
    val dockerImage: String?,
    val catcherActualVersion: String?,
    val catcherDesiredVersion: String?
) : SdkAdditionalData {
    fun save(rootElement: Element) {
        if (pythonSdk != null)
            rootElement.setAttribute("ASSOCIATED_PYTHON_SDK", pythonSdk)
        if (catcherActualVersion != null)
            rootElement.setAttribute("CORE_ACTUAL_VERSION", catcherActualVersion)
        if (catcherDesiredVersion != null)
            rootElement.setAttribute("CORE_DESIRED_VERSION", catcherDesiredVersion)
    }


    companion object {
        fun load(element: Element?): CatcherSdkAdditionalData? {
            if (element != null) {
                val sdkName = element.getAttributeValue("ASSOCIATED_PYTHON_SDK")
                val catcherActualVersion = element.getAttributeValue("CORE_ACTUAL_VERSION")
                val catcherDesiredVersion = element.getAttributeValue("CORE_DESIRED_VERSION")
                if (sdkName != null) {
                    return CatcherSdkAdditionalData(
                        pythonSdk = sdkName,
                        dockerImage = null,
                        catcherActualVersion = catcherActualVersion,
                        catcherDesiredVersion = catcherDesiredVersion
                    )
                }
            }
            return null
        }
    }
}
