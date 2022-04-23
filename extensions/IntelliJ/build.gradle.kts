// *******************************
// |docname| - Gradle build script
// *******************************
// See the `Gradle docs <https://docs.gradle.org/current/userguide/tutorial_using_tasks.html>`_. This was mostly taken from the IntelliJ template plugin.

import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.4.0"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "1.3.1"
    // Gradle Qodana Plugin
    id("org.jetbrains.qodana") version "0.1.13"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

// Configure project's dependencies
repositories {
    mavenCentral()
}

dependencies {
    // The Apache Thrift Java libraries are required. Pull the `compiled library <https://search.maven.org/artifact/org.apache.thrift/libthrift>`_ from the `central Maven repository <https://search.maven.org/>`_. The other option is to build these libraries manually, following the `Thrift Java build procedure <https://github.com/apache/thrift/blob/master/lib/java/README.md#building-thrift-with-gradle-without-cmakeautoconf>`_.
    implementation("org.apache.thrift:libthrift:0.16.0") {
        // However, this by default will reference logging that produces the error ``Caused by: java.lang.LinkageError: loader constraint violation: when resolving method 'org.slf4j.ILoggerFactory org.slf4j.impl.StaticLoggerBinder.getLoggerFactory()' the class loader com.intellij.ide.plugins.cl.PluginClassLoader @794e9ee9 of the current class, org/slf4j/LoggerFactory, and the class loader com.intellij.util.lang.PathClassLoader @4bbfb90a for the method's defining class, org/slf4j/impl/StaticLoggerBinder, have different Class objects for the type org/slf4j/ILoggerFactory used in the signature (org.slf4j.LoggerFactory is in unnamed module of loader com.intellij.ide.plugins.cl.PluginClassLoader @794e9ee9, parent loader 'bootstrap'; org.slf4j.impl.StaticLoggerBinder is in unnamed module of loader com.intellij.util.lang.PathClassLoader @4bbfb90a, parent loader 'platform')``. To fix this, exclude the offending library, which appeared in the "External libraries" folder of the Project task pane.
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

// Configure Gradle Qodana Plugin - read more: https://github.com/JetBrains/gradle-qodana-plugin
qodana {
    cachePath.set(projectDir.resolve(".qodana").canonicalPath)
    reportPath.set(projectDir.resolve("build/reports/inspections").canonicalPath)
    saveReport.set(true)
    showReport.set(System.getenv("QODANA_SHOW_REPORT")?.toBoolean() ?: false)
}

tasks {
    // Set the JVM compatibility versions
    properties("javaVersion").let {
        withType<JavaCompile> {
            sourceCompatibility = it
            targetCompatibility = it
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = it
        }
    }

    wrapper {
        gradleVersion = properties("gradleVersion")
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
            projectDir.resolve("README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run { markdownToHTML(this) }
        )

        changeNotes.set("<p>See the <a href=\"https://codechat-system.readthedocs.io/en/latest/docs/CHANGELOG.html\">CodeChat System changelog</a>.</p>")
    }

    // Configure UI tests plugin
    // Read more: https://github.com/JetBrains/intellij-ui-test-robot
    runIdeForUiTests {
        systemProperty("robot-server.port", "8082")
        systemProperty("ide.mac.message.dialogs.as.sheets", "false")
        systemProperty("jb.privacy.policy.text", "<!--999.999-->")
        systemProperty("jb.consents.confirmation.enabled", "false")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getenv("PUBLISH_TOKEN"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first()))
    }
}
