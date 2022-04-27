// **********************************************
// |docname| - Define plugin application services
// **********************************************
// This defines a single plugin component for the entire application. See the `docs <https://plugins.jetbrains.com/docs/intellij/plugin-services.html>`__.
package com.github.bjones1.intellij.codechat.services

import com.github.bjones1.intellij.codechat.MyBundle

class MyApplicationService {

    init {
        println(MyBundle.message("applicationService"))
    }
}
