package io.jhonesdeveloper.loterias.cdi

import javax.inject.Qualifier
import kotlin.annotation.Retention

@Qualifier
@Target(AnnotationTarget.FIELD,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class StartupScene
