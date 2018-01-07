package io.jhonesdeveloper.loterias.cdi

import javafx.application.Application.Parameters

import javax.enterprise.inject.Produces
import javax.inject.Singleton

@Singleton
class ApplicationParametersProducer {

    @get:Produces
    var parameters: Parameters? = null
}
