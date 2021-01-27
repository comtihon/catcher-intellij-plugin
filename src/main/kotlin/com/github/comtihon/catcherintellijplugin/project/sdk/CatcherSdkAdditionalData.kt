package com.github.comtihon.catcherintellijplugin.project.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkAdditionalData

class CatcherSdkAdditionalData(public val pythonSdk: Sdk?, public val dockerImage: String?) : SdkAdditionalData