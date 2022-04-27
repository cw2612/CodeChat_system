// **********************************************
// |docname| - Define plugin application services
// **********************************************
// This defines a single plugin component for the entire application. See the `docs <https://plugins.jetbrains.com/docs/intellij/plugin-services.html>`__.
package com.github.bjones1.intellij.codechat.services

import com.github.bjones1.intellij.codechat.MyBundle
import com.github.bjones1.intellij.codechat.gen_java.CodeChat_ServicesConstants
import com.github.bjones1.intellij.codechat.gen_java.EditorPlugin
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.TSocket

@Service
class MyApplicationService: Disposable {
    private var transport: TSocket? = null
    var client: EditorPlugin.Client? = null
        private set
        get() {
            if (transport == null) {
                // Start the server, in case it's not running. First, get the path to the server's executable.
                val codeChatServerExe = PropertiesComponent.getInstance()
                    .getValue("com.github.bjones1.intellij.codechat.CodeChatBinaryPath", "CodeChat_Server")
                println(codeChatServerExe)
                // TODO: Run the server.

                // Connect to the server. See Thrift's `Java tutorial <https://thrift.apache.org/tutorial/java.html>`_ and the corresponding `tutorial files on GitHub <https://github.com/apache/thrift/tree/master/lib/java>`_.
                transport = TSocket("localhost", CodeChat_ServicesConstants.THRIFT_PORT.toInt())
                transport!!.open()
            }
            val protocol: TProtocol = TBinaryProtocol(transport)
            return EditorPlugin.Client(protocol)
        }

    init {
        println(MyBundle.message("applicationService"))
    }

    override fun dispose() {
        transport?.close()
    }
}
