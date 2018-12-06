// *****************************************
// |docname| - This is an Extension
// *****************************************
const vscode = require('vscode');
var tools = require('../Thrift/JavaScript_Client/Client');
var subscription;
var panel;
function activate(context) {
    console.log('Congratulations, your extension "CodeChat" is now active!');
    let disposable = vscode.commands.registerCommand('extension.sayHello', function () {
        vscode.window.showInformationMessage('Hello Codechat');
        // Create and show panel
        // ---------------------
        panel = vscode.window.createWebviewPanel('CodeChat', "CodeChat", vscode.ViewColumn.One, { });
        // Calling function from Client.js named myfunc
        // --------------------------------------------
        tools.Clientfunc(panel.webview);
    });

    context.subscriptions.push(disposable);
    // Taking the event from webview so that it can be used in OnDidChangeTextDocument
    // ---------------------------------------------------------------------------------
    var listener = function(event) {
        console.log("It happened", event);
        tools.Clientfunc(panel.webview);
    };
    // Starts listening for the `event <https://code.visualstudio.com/docs/extensionAPI/vscode-api#Event>`_.
    // ---------------------------------------------------------------------------------------------------------
    subscription = vscode.workspace.onDidChangeTextDocument(listener);
}

exports.activate = activate;
function deactivate() {
    subscription.dispose(); // stop listening
}
exports.deactivate = deactivate;







