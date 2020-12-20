package com.techware.react

import java.io.File
import java.lang.StringBuilder

class GenerateReactJSCommon {
    companion object{

        fun generateLayoutFile(classNameList: List<String>, targetFolder: String) {
            val layout = GenerateReactJSCommon.generateLayout(classNameList)
            val pathLayout = "$targetFolder/${ReactJSConstants.COMPONENTS}/Layout"
            File(pathLayout).mkdirs()
            val layoutFileName = "$pathLayout/Layout.js"
            File(layoutFileName).writeText(layout)
        }

        fun generateConstantsFile(classNameList: List<String>, targetFolder: String) {
            val constants = GenerateReactJSCommon.generateConstants(classNameList)
            val pathContants = "$targetFolder/${ReactJSConstants.UTILITIES}/"
            File(pathContants).mkdirs()

            val constantsFileName = "$pathContants/constants.js"
            File(constantsFileName).writeText(constants)
        }

        fun generateApiEndpointFile(
            classNameList: List<String>,
            targetFolder: String
        ) {
            val apiEndpoint = GenerateReactJSCommon.generateApiEndpoints(classNameList)
            val pathapiEndpoint = "$targetFolder/${ReactJSConstants.UTILITIES}/"
            File(pathapiEndpoint).mkdirs()

            val apiEndpointFileName = "$pathapiEndpoint/apiEndpoints.js"
            File(apiEndpointFileName).writeText(apiEndpoint)
        }

        fun generateApiEndpoints(actionNames: List<String>): String {
            val sb = StringBuilder()
            sb.appendln("export const ApiEndpoints= {")
            actionNames.map {
                sb.appendln("    ${it}: '/${it}',")
            }
            sb.appendln("}")
            return sb.toString()
        }

        fun generateConstants(actionNames: List<String>): String {
            val sb = StringBuilder()
            sb.appendln("export const Constants= {\n" +
                    "    itemsPerPage: 50,")
            sb.append("    typesList: [")
            actionNames.mapIndexed{i,e-> if(i<actionNames.size-1) {
                sb.append("'$e',")
            } else {
                sb.append("'$e'")
            }}
            sb.appendln("]\n" +
                    "}")
            return sb.toString()
        }

        fun generateLayout(actionNames: List<String>): String {
            val sb = StringBuilder()
            sb.appendln("import React, { Component } from 'react';\n" +
                    "import TopNav from '../TopNav';\n" +
                    "import Footer from '../Footer';\n" +
                    "import { Route, Switch } from 'react-router-dom';\n" +
                    "import { connect } from 'react-redux';\n" +
                    "import { NotifyMessage } from '../NotifyMessage/NotifyMessage';\n" +
                    "import { notifyClear } from '../../actions';\n" +
                    "import LeftSide from '../LeftSide/LeftSide';\n" +
                    "import { Row, Col, } from 'react-bootstrap';")
            actionNames.map{
                sb.appendln("import ${it.capitalize()}Edit from '../${it.capitalize()}/${it.capitalize()}Edit';")
                sb.appendln("import ${it.capitalize()}Table from '../${it.capitalize()}/${it.capitalize()}Table';")
            }

            sb.appendln("\n" +
                    "class Layout extends Component {\n" +
                    "\n" +
                    "    onClose = () => {\n" +
                    "        this.props.notifyClear();\n" +
                    "    }\n" +
                    "\n" +
                    "    render() {\n" +
                    "        const notifyMessage = this.props.notifyMessage;\n" +
                    "        // console.log(notifyMessage);\n" +
                    "\n" +
                    "        return (\n" +
                    "            <div>\n" +
                    "                <TopNav />\n" +
                    "                <NotifyMessage onClose={this.onClose} notifyMessage={notifyMessage} />\n" +
                    "\n" +
                    "                <Row  className=\"no-gutters\">\n" +
                    "                    <Col md={1}>\n" +
                    "                        <LeftSide />\n" +
                    "                    </Col>\n" +
                    "                    <Col md={11}>\n" +
                    "                         <Switch>")
            actionNames.mapIndexed { index, s ->
                if (index == 0) {
                    sb.appendln("                            <Route path='/' exact component={${s.capitalize()}Table} />")
                    sb.appendln(
                        "                            <Route path='/${s}Table' exact component={${s.capitalize()}Table} />\n" +
                                "                            <Route path='/${s}Edit' exact component={${s.capitalize()}Edit} onClose={this.onClose} notifyMessage={notifyMessage} />\n"
                    )
                } else {
                    sb.appendln(
                        "                            <Route path='/${s}Table' exact component={${s.capitalize()}Table} />\n" +
                                "                            <Route path='/${s}Edit' exact component={${s.capitalize()}Edit} onClose={this.onClose} notifyMessage={notifyMessage} />\n"
                    )
                }


            }
            sb.appendln(
                "                        </Switch>\n" +
                        "                    </Col>\n" +
                        "                </Row>\n" +
                        "\n" +
                        "                <Footer />\n" +
                        "            </div>\n" +
                        "        )\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const mapStateToProps = state => ({\n" +
                        "    notifyMessage: state.notifyMessageReducer.notifyMessage,\n" +
                        "})\n" +
                        "\n" +
                        "export default connect(mapStateToProps, { notifyClear })(Layout);"
            )

            return sb.toString()
        }

    }
}