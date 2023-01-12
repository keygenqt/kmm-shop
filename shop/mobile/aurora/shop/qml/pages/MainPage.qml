import QtQuick 2.0
import Sailfish.Silica 1.0
import "../components" as Components

Page {
    id: homePage

    readonly property color borderColorBlock: idApp.getPerceptualBrightness(idApp.colors.highlightDarkColor) < 500
                                              ? "#444444" : idApp.colors.highlightDarkColor

    ListModel {
        id: categoryModel
    }

    QtObject {
        id: state
        property var response
        property string error: ""
        property bool loading: true
        function clear() {
            response = undefined; error = ""; loading = true; categoryModel.clear()
        }
    }

    function update() {
        // clear state
        state.clear()
        // run query
        agent.run(
            "kmm.Requests.get.categoriesPublished()",
            function(result) {
                try {
                    var list = JSON.parse(result)
                    state.response = list
                    for (var index = 0; index < list.length; index++) {
                        list[index]['bg'] = "../icons/cat_bg_" +(index + 1)+ ".svg"
                        if (index == 3) break
                        categoryModel.append(list[index])
                    }
                } catch (e) {
                    state.error = error
                }
                state.loading = false
            },
            function(error) {
                state.error = error
                state.loading = false
            }
        )
    }

    onStatusChanged: {
        if (status == PageStatus.Active) {
            // clear agent kmm
            agent.clear()
            // run query
            if (!Boolean(state.response)) {
                homePage.update()
            }
        }
    }

    Components.AppPage {
        id: idAppPage
        header: qsTr("Майшоп")
        menuDisabled: state.loading
        selectedPage: "itemMenuHome"
        fixed: state.loading
        menuUpdate: function () {
            homePage.update()
        }

        Rectangle {
            height: idInfoBlock.height + appTheme.paddingLarge
            width: parent.width
            color: 'transparent'

            Rectangle {
                color: 'white'
                anchors.fill: parent
                radius: appTheme.shapesLarge
            }

            Rectangle {
                color: idApp.colors.highlightDarkColor
                anchors.fill: parent
                radius: appTheme.shapesLarge
                opacity: 0.7
                border.width: 1
                border.color: homePage.borderColorBlock
            }

            Column {
                id: idInfoBlock
                width: parent.width - appTheme.paddingLarge * 2
                anchors.horizontalCenter: parent.horizontalCenter
                anchors.top: parent.top
                anchors.topMargin: appTheme.paddingLarge

                Row {
                    width: parent.width
                    spacing: appTheme.paddingSmall

                    Column {
                        width: parent.width - 180
                        spacing: appTheme.paddingSmall

                        Label {
                            text: qsTr("В этом сезоне найди лучшее 🔥")
                            color: "white"
                            bottomPadding: 4
                            font.pixelSize: appTheme.fontSizeBody2
                        }

                        Column {
                            width: parent.width
                            spacing: appTheme.paddingLarge

                            Text {
                                width: parent.width
                                text: qsTr("Коллекции для вашего стиля")
                                color: "white"
                                wrapMode: Text.WordWrap
                                font.pixelSize: appTheme.fontSizeH4
                            }

                            Components.AppButton {
                                text: qsTr("Начните поиск")
                                onClick: pageStack.push(Qt.resolvedUrl("CatalogPage.qml"), {menu: false, index: 1})
                                padding: appTheme.paddingMedium
                                background: 'black'
                            }
                        }
                    }

                    Image {
                        source: Qt.resolvedUrl("../icons/girl.png")
                        fillMode: Image.PreserveAspectFit
                        anchors.verticalCenter: parent.verticalCenter
                        width: 180
                        height: 180
                        anchors.bottom: parent.bottom
                    }
                }

                Rectangle{
                    width: parent.width
                    height: appTheme.paddingLarge
                    color: 'transparent'
                }
            }
        }

        Components.AppBlock {
            visible: state.error !== "" || state.loading
            width: parent.width
            height: idAppPage.pageH - idInfoBlock.height - appTheme.paddingMedium - appTheme.paddingLarge
            backgroundColor: idApp.colors.highlightDarkColor
            borderColor: homePage.borderColorBlock

            Components.BlockLoading {
               visible: state.loading
            }

            Components.BlockError {
                visible: state.error !== ""
            }
        }

        Components.AppBlock {
            id: contentBlock
            width: parent.width
            backgroundColor: idApp.colors.highlightDarkColor
            visible: state.response !== undefined
            borderColor: homePage.borderColorBlock

            Column {
                width: parent.width
                spacing: appTheme.paddingLarge

                Row {
                    width: parent.width
                    spacing: 0

                    Label {
                        id: allLabel
                        text: qsTr("Топ категорий")
                        font.pixelSize: appTheme.fontSizeH5
                        color: "white"
                    }

                    Rectangle {
                        color: 'transparent'
                        height: 1
                        width: parent.width - allLabel.width - allButton.width
                    }

                    Components.AppButton {
                        id: allButton
                        text: qsTr("Все")
                        onClick: pageStack.push(Qt.resolvedUrl("CatalogPage.qml"), {menu: false, index: 0})
                        padding: appTheme.paddingMedium
                        background: 'white'
                    }
                }

                Repeater {
                    model: categoryModel
                    delegate: Components.AppBlock {
                        width: parent.width
                        backgroundColor: "white"
                        bgSource: bg

                        Label {
                            text: name
                            font.pixelSize: appTheme.fontSizeBody2
                            bottomPadding: 5
                            color: idApp.colors.highlightDarkColor
                        }

                        Column {
                            width: parent.width
                            spacing: appTheme.paddingLarge

                            Text {
                                width: parent.width
                                text: desc
                                wrapMode: Text.WordWrap
                                font.pointSize: appTheme.fontSizeBody1
                            }

                            Components.AppButton {
                                text: qsTr("Смотреть")
                                iconEnd: "image://theme/icon-m-enter-next"
                                onClick: pageStack.push(Qt.resolvedUrl("ProductsPage.qml"))
                                padding: appTheme.paddingMedium
                            }
                        }
                   }
               }
            }
        }

    }
}
