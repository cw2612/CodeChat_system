package com.github.bjones1.intellijaplugin.services

import com.intellij.openapi.project.Project
import com.github.bjones1.intellijaplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
