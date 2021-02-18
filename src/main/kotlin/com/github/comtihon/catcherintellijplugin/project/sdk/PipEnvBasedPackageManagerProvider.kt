package com.github.comtihon.catcherintellijplugin.project.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.jetbrains.python.packaging.PyPackageManager
import com.jetbrains.python.packaging.PyPackageManagerProvider

class PipEnvBasedPackageManagerProvider : PyPackageManagerProvider {
    override fun tryCreateForSdk(sdk: Sdk): PyPackageManager {
        return MyPyPackageManagerImpl(sdk)
    }
}