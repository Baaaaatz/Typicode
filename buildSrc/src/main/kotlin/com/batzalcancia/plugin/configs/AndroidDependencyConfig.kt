package com.batzalcancia.plugin.configs

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.batzalcancia.dependencies.Dependencies


internal fun Project.configureDependencies()  = dependencies {
    add("implementation", Dependencies.KOTLIN)

    add("implementation", Dependencies.NAVIGATION_FRAGMENT_KTX)
    add("implementation", Dependencies.NAVIGATION_UI_KTX)


    add("implementation", Dependencies.LIFECYCLE_EXTENSIONS)
    add("implementation", Dependencies.LIFECYCLE_VIEWMODEL_KTX)
    add("implementation", Dependencies.LIFECYCLE_LIVEDATA_KTX)

    add("implementation", Dependencies.APPCOMPAT)
    add("implementation", Dependencies.CORE_KTX)
    add("implementation", Dependencies.ACTIVITY_KTX)
    add("implementation", Dependencies.FRAGMENT_KTX)
    add("implementation", Dependencies.DATASTORE)
    add("implementation", Dependencies.ROOM)
    add("implementation", Dependencies.ROOM_COROUTINE)
    add("implementation", Dependencies.ROOM_KAPT)

    add("implementation", Dependencies.MATERIAL_COMPONENTS)
    add("implementation", Dependencies.SWIPE_REFRESH_LAYOUT)
    add("implementation", Dependencies.CONSTRAINT_LAYOUT)

    add("implementation", Dependencies.COROUTINES)
    add("implementation", Dependencies.COROUTINES_PLAY_SERVICES)
    add("implementation", Dependencies.COIL)

    add("implementation", Dependencies.PAGING)

    add("implementation", Dependencies.SHIMMER)

    add("implementation", Dependencies.RETROFIT)
    add("implementation", Dependencies.RETROFIT_CONVERTER)

    add("implementation", Dependencies.OKHTTP)
    add("implementation", Dependencies.OKHTTP_INTERCEPTOR)

    add("implementation", Dependencies.DAGGER_HILT)
    add("implementation", Dependencies.DAGGER_HILT_LIFECYCLE)
    add("implementation", Dependencies.DAGGER_HILT_KAPT)

    add("implementation", Dependencies.DAGGER_HILT_VIEWMODEL)
    add("implementation", Dependencies.DAGGER_HILT_VIEWMODEL_KAPT)
}

