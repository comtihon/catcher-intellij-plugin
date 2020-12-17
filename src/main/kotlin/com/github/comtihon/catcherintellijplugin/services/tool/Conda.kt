package com.github.comtihon.catcherintellijplugin.services.tool

class Conda(private val env: String = "catcher", private val python: String = "3.7") : SystemTool() {

    override fun install() {
        // TODO determine target platform and try to install conda https://docs.conda.io/en/latest/miniconda.html
        "conda create -n $env --copy -y python=$python".runCommand()
    }

    override fun execute(command: String): String {
        return runCommand(arrayListOf("bash", "-c", "source activate $env && $command"))
    }

    override fun listInstallations(): List<String> {
        val envs: String = "conda env list".runCommand()
        // TODO parse and iterate over envs line by line. Find catcher in each
        return emptyList()
    }
}