package com.github.comtihon.catcherintellijplugin.project.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.jetbrains.python.packaging.PyPackageManagerImpl

class MyPyPackageManagerImpl(sdk: Sdk) : PyPackageManagerImpl(sdk) {
}