import QtQuick 2.0
import Sailfish.WebView 1.0
import Sailfish.WebEngine 1.0

Item {

    id: idAgentBlock

    signal completed()
    property var stateResponse: ({})

    function clear() {
        idAgentBlock.stateResponse = {}
    }

    function run(method, result, error) {
        if (method.indexOf("return") === -1) {
            webview.runJavaScript("return " + method, function(key) {
                idAgentBlock.stateResponse[key] = [result, error]
            });
        } else {
            webview.runJavaScript(method, result, error);
        }
    }

    WebView {
        id: webview
        height: 100
        width: parent.width
        privateMode: true
        url: "../js-webpack/index.html"
        visible: false

        onViewInitialized: {
            webview.loadFrameScript(Qt.resolvedUrl("../js-webpack/framescript.js"));
            webview.addMessageListener("webview:action")
        }

        onRecvAsyncMessage: {
            switch (message) {
            case "webview:action":
                try {
                    if (data.caller === 'init') {
                        idAgentBlock.completed()
                    } else if (idAgentBlock.stateResponse[data.caller] !== undefined) {
                        if (JSON.stringify(data.response) !== '{}') {
                            if (data.response.indexOf("Error") !== -1) {
                                idAgentBlock.stateResponse[data.caller][1](data.response)
                            } else {
                                idAgentBlock.stateResponse[data.caller][0](data.response)
                            }
                            console.debug(JSON.stringify(data))
                        }
                    }
                } catch (e) {
                    console.log(e)
                }
                break
            }
       }

        Component.onCompleted: {
            WebEngineSettings.javascriptEnabled = true
            WebEngineSettings.popupEnabled = false
            WebEngineSettings.setPreference("security.disable_cors_checks", true, WebEngineSettings.BoolPref)
         }
    }
}

