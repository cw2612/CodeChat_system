// ******************************************
// |docname| - Define plugin project services
// ******************************************
// This defines a single plugin component per project. See the `docs <https://plugins.jetbrains.com/docs/intellij/plugin-services.html>`__.
package com.github.bjones1.intellij.codechat.services

import com.github.bjones1.intellij.codechat.MyBundle
import com.intellij.openapi.project.Project
import org.apache.thrift.TException
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.TSocket
import org.apache.thrift.transport.TTransport
import com.github.bjones1.intellij.codechat.gen_java.CodeChat_ServicesConstants.THRIFT_PORT
import com.github.bjones1.intellij.codechat.gen_java.EditorPlugin


class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))

        try {
            // See Thrift's `Java tutorial <https://thrift.apache.org/tutorial/java.html>`_ and the corresponding `tutorial files on GitHub <https://github.com/apache/thrift/tree/master/lib/java>`_.
            val transport: TTransport
            transport = TSocket("localhost", THRIFT_PORT.toInt())
            transport.open()
            val protocol: TProtocol = TBinaryProtocol(transport)
            val client: EditorPlugin.Client = EditorPlugin.Client(protocol)
            client.ping()
            transport.close()
        } catch (x: TException) {
            x.printStackTrace()
        }
    }
}
