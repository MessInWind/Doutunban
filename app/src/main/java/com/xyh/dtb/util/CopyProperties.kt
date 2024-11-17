package com.xyh.dtb.util

import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty

inline fun <reified T : Any> copyProperties(source: T, target: T) {
    val sourceProperties = source::class.memberProperties
    val targetProperties = target::class.memberProperties.associateBy { it.name }

    for (property in sourceProperties) {
        val targetProperty = targetProperties[property.name] ?: continue
        if (targetProperty is KMutableProperty<*>) {
            val value = property.getter.call(source)
            targetProperty.setter.call(target, value)
        }
    }
}