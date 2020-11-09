package com.github.comtihon.catcherintellijplugin.language

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable;
import javax.swing.*;

class CatcherLang private constructor(): Language("Catcher") {
    companion object CatcherLang {
        val INSTANCE = CatcherLang()
    }
}

object CatcherIcon {  // TODO icon!
    val FILE: Icon = IconLoader.getIcon("/icons/jar-gray.png")
}

class CatcherFileType private constructor() : LanguageFileType(CatcherLang.INSTANCE) {

    companion object CatcherFileType {
        val INSTANCE = CatcherFileType()
    }

    @NotNull
    @Override
    override fun getName(): String {
        return "Catcher Test File"
    }

    @NotNull
    @Override
    override fun getDescription(): String {
        return "Catcher test or inventory file"
    }

    @NotNull
    @Override
    override fun getDefaultExtension(): String {
        return "xml"
    }

    @Nullable
    @Override
    override fun getIcon(): Icon? {
        return CatcherIcon.FILE
    }
}

