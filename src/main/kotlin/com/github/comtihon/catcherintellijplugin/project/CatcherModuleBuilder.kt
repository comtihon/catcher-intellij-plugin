package com.github.comtihon.catcherintellijplugin.project

import com.github.comtihon.catcherintellijplugin.project.sdk.CatcherSdkType
import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.Disposable
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.util.Pair
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File
import java.util.*


class CatcherModuleBuilder : ModuleBuilder(), SourcePathsBuilder {

    private var mySourcePaths: MutableList<Pair<String, String>> = mutableListOf()

    override fun setupRootModel(model: ModifiableRootModel) {
        val contentEntry = doAddContentEntry(model)
        if (contentEntry != null) {
            for (sourcePath in sourcePaths) {
                val first = sourcePath.first
                File(first).mkdirs()
                val sourceRoot = LocalFileSystem.getInstance()
                    .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(first))
                if (sourceRoot != null) {
                    contentEntry.addSourceFolder(sourceRoot, false, sourcePath.second!!)
                }
            }
        }
    }

    override fun getModuleType(): CatcherModuleType {
        return CatcherModuleType.getInstance()
    }

    override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?): ModuleWizardStep {
        return ProjectJdkForModuleStep(context!!, CatcherSdkType.getInstance())
    }

    override fun getSourcePaths(): MutableList<Pair<String, String>> {
        if (mySourcePaths.isEmpty()) {
            val paths: MutableList<Pair<String, String>> = ArrayList()
            paths.add(ensureSourcePath("tests"))
            paths.add(ensureSourcePath("resources"))
            paths.add(ensureSourcePath("reports"))
            paths.add(ensureSourcePath("steps"))
            paths.add(ensureSourcePath("inventory"))
            return paths
        }
        return mySourcePaths
    }

    override fun setSourcePaths(sourcePaths: MutableList<Pair<String, String>>?) {
        mySourcePaths = if (sourcePaths != null) ArrayList(sourcePaths) else mutableListOf()
    }

    override fun addSourcePath(sourcePathInfo: Pair<String, String>?) {
        if (sourcePathInfo != null) mySourcePaths.add(sourcePathInfo)
    }

    private fun ensureSourcePath(dir: String): Pair<String, String> {
        val path = contentEntryPath + File.separator + dir
        File(path).mkdirs()
        return Pair.create(path, "")
    }
}