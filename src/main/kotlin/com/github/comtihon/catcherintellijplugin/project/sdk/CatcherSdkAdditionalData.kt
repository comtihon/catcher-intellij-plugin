package com.github.comtihon.catcherintellijplugin.project.sdk

import com.intellij.openapi.projectRoots.SdkAdditionalData
import org.jdom.Element

// TODO catcher & modules versions?
// TODO isInstalled flag/status?
class CatcherSdkAdditionalData(private var pythonSdk: String?, val dockerImage: String?) : SdkAdditionalData {
    fun save(rootElement: Element) {
        if (pythonSdk != null)
            rootElement.setAttribute("ASSOCIATED_PYTHON_SDK", pythonSdk!!)
    }



    companion object {
        fun load(element: Element?): CatcherSdkAdditionalData? {
            if (element != null) {
                val sdkName = element.getAttributeValue("ASSOCIATED_PYTHON_SDK")
                if(sdkName != null) {
                    return CatcherSdkAdditionalData(sdkName, null)
                }
            }
            return null
        }
    }
}
