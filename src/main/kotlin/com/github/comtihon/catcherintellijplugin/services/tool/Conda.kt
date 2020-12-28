package com.github.comtihon.catcherintellijplugin.services.tool

import com.github.comtihon.catcherintellijplugin.exceptions.ExecutionFailedException

class Conda(var env: String = "catcher", var python: String = "3.7") : SystemTool() {

    override fun install() {
        // TODO determine target platform and try to install conda https://docs.conda.io/en/latest/miniconda.html
        "conda create -n $env --copy -y python=$python".runCommand()
    }

    override fun execute(command: String): String {
        return runCommand(arrayListOf("bash", "-c", "source activate $env && $command"))
    }

    @Throws(ExecutionFailedException::class)
    override fun listInstallations(): List<String> {
        try {
            val envs: String = "conda env list".runCommand()
        } catch (e: ExecutionFailedException) {
            if (e.message!!.contains("No such file or directory")) {
                throw ExecutionFailedException(
                    e.message + "\nPlease install conda " +
                            "https://docs.conda.io/en/latest/miniconda.html"
                )
            } else {
                throw e
            }
        }
        // TODO parse and iterate over envs line by line. Find catcher in each
        return emptyList()
    }
}