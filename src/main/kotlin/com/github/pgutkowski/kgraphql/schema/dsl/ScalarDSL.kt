package com.github.pgutkowski.kgraphql.schema.dsl

import com.github.pgutkowski.kgraphql.defaultKQLTypeName
import com.github.pgutkowski.kgraphql.schema.scalar.ScalarCoercion
import kotlin.reflect.KClass


abstract class ScalarDSL<T : Any, Raw : Any>(kClass: KClass<T>, block: ScalarDSL<T, Raw>.() -> Unit) : ItemDSL() {

    companion object {
        const val PLEASE_SPECIFY_COERCION =
                "Please specify scalar coercion object or coercion functions 'serialize' and 'deserialize'"
    }

    var name = kClass.defaultKQLTypeName()

    init {
        block()
    }

    var deserialize : ((Raw) -> T)? = null

    var serialize : ((T) -> Raw)? = null

    var coercion: ScalarCoercion<T, Raw>? = null

    internal fun createCoercion() : ScalarCoercion<T, Raw> {
        return coercion ?: createCoercionFromFunctions()
    }

    abstract protected fun createCoercionFromFunctions() : ScalarCoercion<T, Raw>
}