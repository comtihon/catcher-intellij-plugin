package com.github.comtihon.catcherintellijplugin.services

import com.github.comtihon.catcherintellijplugin.project.sdk.CatcherSdkAdditionalData
import com.intellij.execution.ExecutionException
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.Disposer
import com.jetbrains.python.packaging.PyPackageManagerUI
import com.jetbrains.python.packaging.PyRequirementImpl


/**
 * I check desired catcher sdk vs actual and update/install catcher if necessary
 */
class CatcherSdkService(private val project: Project) {


    /**
     * Ensure sdk latest (or desired) version is installed
     */
    fun ensureSdkVersion() {
        val projectSdk = ProjectRootManager.getInstance(project).projectSdk
        if (projectSdk != null && projectSdk.sdkAdditionalData != null) {
            val additionalData = projectSdk.sdkAdditionalData as CatcherSdkAdditionalData
            val boundedSdk = additionalData.getBoundedSdk()
            println("bounded sdk $boundedSdk")
            if (boundedSdk != null) {
                installCatcherPython(project, additionalData, boundedSdk)
            } // TODO docker
        }
    }

    // TODO not working with Conda (as it calls conda install instead of pip install)
    private fun installCatcherPython(project: Project, additionalData: CatcherSdkAdditionalData, boundedSdk: Sdk) {
        if (additionalData.catcherActualVersion == null || additionalData.catcherActualVersion == "latest") {

            val parent = Disposer.newDisposable()
            // TODO populate PyPackageManagersImpl with custom/provided manager
            Disposer.register(parent, boundedSdk as Disposable)

            val pyMan = PyPackageManagerUI(project, boundedSdk, object : PyPackageManagerUI.Listener {
                override fun started() {}
                override fun finished(exceptions: List<ExecutionException>) {
                    if (exceptions.isEmpty()) {
                        // TODO save actual version
                        println("sdk should be updated")
                    }
                    if (!Disposer.isDisposed(parent))
                        parent.dispose()
                }
            })
            // TODO update
            pyMan.install(
                listOf(PyRequirementImpl("catcher", emptyList(), listOf("catcher"), "")),
                emptyList()
            )
        }
    }

}
