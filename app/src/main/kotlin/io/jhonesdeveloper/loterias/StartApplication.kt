package io.jhonesdeveloper.loterias

import io.jhonesdeveloper.loterias.cdi.Container
import javafx.application.Application
import javafx.stage.Stage

class StartApplication : Application() {

    override fun start(primaryStage: Stage) {
        try {
            Container.init(primaryStage, parameters)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(StartApplication::class.java, *args)
}