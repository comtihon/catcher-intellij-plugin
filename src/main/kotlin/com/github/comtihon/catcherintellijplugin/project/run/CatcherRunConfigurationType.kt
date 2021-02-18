package com.github.comtihon.catcherintellijplugin.project.run

import com.github.comtihon.catcherintellijplugin.core.Icons
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import javax.swing.Icon

class CatcherRunConfigurationType : ConfigurationType {
    override fun getDisplayName(): String {
        return "Catcher"
    }

    override fun getConfigurationTypeDescription(): String {
        return "Run Catcher test suite"
    }

    override fun getIcon(): Icon {
        return Icons.SmallIcon
    }

    override fun getId(): String {
        return "CatcherRunConfiguration"
    }

    override fun getConfigurationFactories(): Array<ConfigurationFactory> {
        TODO("Not yet implemented")
    }
}