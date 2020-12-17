package com.github.comtihon.catcherintellijplugin.services.tool

class Venv(private val env: String, private val executable: String = "python") : SystemTool() {

    override fun install() {
        "$executable -m pip install --user virtualenv".runCommand()
        "$executable -m venv $env".runCommand()
    }

    override fun execute(command: String): String {
        return runCommand(arrayListOf("bash", "-c", "source $env/bin/activate; $command"))
    }

    /**
     * As it is hard to find all venvs (there is no central storage for them)
     * this method will only find catcher in the current venv specified.
     */
    override fun listInstallations(): List<String> {
        return listOf(runCommand(arrayListOf("bash", "-c", "source $env/bin/activate; catcher -v")))
        // TODO postprocess result to env: catcher_version (or none)
    }
}