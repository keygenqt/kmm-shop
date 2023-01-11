import QtQuick 2.0
import Sailfish.Silica 1.0

Column {
    id: idMainBlock
    width: parent.width
    spacing: 10

    property bool singleLine: true
    property bool disabled: false
    property string error: ""
    property alias placeholderText: idTextField.placeholderText
    property alias text: idTextField.text

    property color color: "#74828e"
    property color colorBg: "#e5e5e5"
    property color focusColor: "#56b0df"
    property color focusColorBg: "#e5f2f9"
    property color errorColor: "#df5656"
    property color errorColorBg: "#f9e5e5"

    readonly property color nowColor: error.length !== 0 ? errorColor : (idTextField.focus ? focusColor : color)
    readonly property color nowColorBg: error.length !== 0 ? errorColorBg : (idTextField.focus ? focusColorBg : colorBg)

    signal clickedEnter()

    Rectangle {
        id: idRec

        width: parent.width
        height: idTextField.height + 24
        color: 'transparent'
        opacity: idMainBlock.disabled ? 0.6 : 1.0

        TextArea {
            id: idTextField
            color: idMainBlock.error.length === 0 ? 'black' : errorColor
            placeholderColor: nowColor

            labelVisible: false
            anchors.bottom: parent.bottom
            anchors.bottomMargin: 6

            property int radius: appTheme.shapesMedium

            EnterKey.onClicked: {
                if (idMainBlock.singleLine) {
                    idTextField.text = idTextField.text.replace("\n", "")
                    idTextField.cursorPosition = idTextField.text.length
                }
                clickedEnter()
            }

            background: Component {
                Rectangle {
                    width: parent.width
                    height: parent.height + 6
                    color: 'transparent'

                    Item {
                        anchors.topMargin: -18
                        anchors.top: parent.top
                        anchors.left: parent.left
                        anchors.right: parent.right
                        anchors.bottom: parent.bottom

                        Rectangle {
                           color: idMainBlock.nowColorBg
                           radius: idTextField.radius
                           anchors.fill: parent

                           Rectangle {
                              color: idMainBlock.nowColorBg
                              height: idTextField.radius
                              width: idTextField.radius
                              anchors.bottom: parent.bottom
                              anchors.left: parent.left
                           }
                        }

                        Rectangle {
                           color: idMainBlock.nowColor
                           height: 2
                           anchors.bottom: parent.bottom
                           anchors.right: parent.right
                           anchors.left: parent.left
                        }

                        Rectangle {
                           color: idMainBlock.error.length === 0 ? idMainBlock.focusColor : idMainBlock.errorColor
                           height: 4
                           width: idTextField.focus ? parent.width : 0
                           anchors.bottom: parent.bottom
                           anchors.horizontalCenter: parent.horizontalCenter

                           Behavior on width {
                               NumberAnimation {
                                   id: animation
                                   properties: "width";
                                   easing.type: Easing.InOutQuad;
                                   duration: 150
                                   onRunningChanged: {
                                       if (!animation.running) {

                                       }
                                   }
                               }
                           }
                        }
                    }
                }
            }
        }

        MouseArea {
            height: parent.height
            width: parent.width
            visible: idMainBlock.disabled
        }
    }

    Text {
        width: parent.width
        text: error
        color: idMainBlock.errorColor
        wrapMode: Text.WordWrap
        horizontalAlignment: Text.AlignLeft
        font.pixelSize: appTheme.fontSizeCaption
        visible: error.length !== 0
    }
}



