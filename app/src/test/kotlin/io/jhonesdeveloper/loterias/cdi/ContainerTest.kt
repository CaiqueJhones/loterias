package io.jhonesdeveloper.loterias.cdi

import javafx.application.Application
import javafx.stage.Stage
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.enterprise.event.Observes

@Suppress("UNUSED_PARAMETER")
open private class MockObservable {
    fun init(@Observes @StartupScene stage: Stage){}
}

class ContainerTest {

    @Test
    fun testInit() {
        val observable = mock(MockObservable::class.java)
        val stage = mock(Stage::class.java)
        Container.init(stage, mock(Application.Parameters::class.java))
        verify(observable).init(stage)
    }
}