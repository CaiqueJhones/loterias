package io.jhonesdeveloper.loterias.fx

import io.jhonesdeveloper.loterias.cdi.StartupScene
import io.jhonesdeveloper.loterias.fx.main.MainView
import javafx.scene.Scene
import javafx.stage.Stage
import javax.enterprise.event.Observes
import javax.inject.Inject

class Startup {

    @Inject lateinit var mainView: MainView

    fun initialize(@Observes @StartupScene stage: Stage) {
        try {
            val scene = Scene(mainView.view)

            stage.scene = scene
            stage.show()
        } catch (e: Exception) {

        }
    }
}