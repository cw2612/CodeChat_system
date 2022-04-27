// **********************************************
// |docname| - Define plugin application services
// **********************************************
// This defines an application-wide service for this plugin. See the `docs <https://plugins.jetbrains.com/docs/intellij/plugin-services.html>`__. It provides access to ``client``, the Thrift client, starting the CodeChat Server and opening a Thrift network connection as needed; it also disposes of ``client`` on shutdown.
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
import org.jetbrains.annotations.NonNls

// The name of the persistence key where the ``codeChatServerExecutable`` is stored.
@NonNls
private const val CODECHAT_SERVER_EXECUTABLE_KEY = "com.github.bjones1.intellij.codechat.CodeChatBinaryPath"

@Service
class ApplicationService: Disposable {
    // The Thrift network connection to the CodeChat Server.
    private var transport: TSocket? = null

    // The Thrift client to invoke functions on the CodeChat Server.
    var client: EditorPlugin.Client? = null
        get() {
            // If the Thrift transport isn't running, start it. TODO: receive some sort of callback when an error occurs, when the connection is closed, etc.
            if (transport == null) {
                // Start the server, in case it's not running. First, get the path to the server's executable.
                println(codeChatServerExecutable)
                // TODO: Run the server.

                // Connect to the server. See Thrift's `Java tutorial <https://thrift.apache.org/tutorial/java.html>`_ and the corresponding `tutorial files on GitHub <https://github.com/apache/thrift/tree/master/lib/java>`_.
                transport = TSocket("localhost", CodeChat_ServicesConstants.THRIFT_PORT.toInt())
                transport!!.open()
            }
            if (field == null) {
                val protocol: TProtocol = TBinaryProtocol(transport)
                field = EditorPlugin.Client(protocol)
            }
            return field
        }
        private set

    // The path to the CodeChat Server executable.
    var codeChatServerExecutable: String
        get() = PropertiesComponent.getInstance().getValue(CODECHAT_SERVER_EXECUTABLE_KEY, "CodeChat_Server")
        set(value) = PropertiesComponent.getInstance().setValue(CODECHAT_SERVER_EXECUTABLE_KEY, value)

    init {
        println(MyBundle.message("applicationService"))
    }

    override fun dispose() {
        transport?.close()
    }
}
