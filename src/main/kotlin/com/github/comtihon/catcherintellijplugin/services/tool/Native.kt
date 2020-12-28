package com.github.comtihon.catcherintellijplugin.services.tool

import com.github.comtihon.catcherintellijplugin.exceptions.ExecutionFailedException

/**
 * This package is not recommended. Please, use conda instead.
 */
class Native(private val executable: String = "python") : SystemTool() {

    // TODO search for python system executable (python vs python3)

    override fun version(): String {
        return executable.runCommand()
    }

    @Throws(ExecutionFailedException::class)
    override fun install() {
        try {
            "$executable -m pip install catcher".runCommand()
        } catch (e: ExecutionFailedException) {
            if (e.message!!.contains("No module named pip")) {
                throw ExecutionFailedException(
                    e.message + "\nPlease check " +
                            "https://packaging.python.org/tutorials/installing-packages/ for pip installation info."
                )
            } else {
                throw ExecutionFailedException(
                    e.message +
                            "\nTry running 'pip install catcher' or 'pip3 install catcher' in your terminal."
                )
            }
        }
    }

    // TODO testme
    @Throws(ExecutionFailedException::class)
    override fun execute(command: String): String {
        return command.replaceExecutable("python", executable)
            .runCommand()
    }

    @Throws(ExecutionFailedException::class)
    override fun listInstallations(): List<String> {
        return listOf("catcher -v".runCommand())  // native can have only 1 catcher installed
    }

    /**
     * this = pip install catcher
     * from pip
     * to pip3
     * result = pip3 install catcher
     */
    private fun String.replaceExecutable(from: String, to: String): String {
        return when {
            this.startsWith(from) -> "$to ${this.substring(this.indexOf(' '))}"
            else -> this
        }
    }
}