package com.techware.react

import com.techware.utilities.Descriptor
import com.techware.utilities.Type
import java.io.File
import java.lang.StringBuilder

class GenerateReactJS {
    companion object {
        const val REACT_FILES = "./src/main/resources/react"
        const val COMPONENTS = "/src/components"
        const val REDUCERS = "/src/reducers"
        const val ACTIONS = "/src/actions"
        const val UTILITIES = "/src/utilities"

        fun generateReactJS(fileName:String, className: String, packageName: String, targetFolder: String) {
            val descriptorList = Descriptor.getDescriptorFromFile(fileName)
            require(descriptorList.isNotEmpty()) {"descriptorList is empty"}

            generateReactJSEdit(className, targetFolder, descriptorList)

            generateReactJSTable(className, targetFolder, descriptorList)

            generateReactJSReducers(className, targetFolder)

            generateReactJSActions(className, targetFolder)
        }

        fun generateReactJSEdit(className: String, targetFolder: String ,descriptorList: List<Type>) {
            val pathEdit = "$targetFolder/$COMPONENTS/${className.capitalize()}/${className.capitalize()}Edit"
            File(pathEdit).mkdirs()
            // generate index file
            val indexFileName = "$pathEdit/index.js"
            val indexEdit =GenerateReactJS.generatedEditIndex(className)
            File(indexFileName).writeText(indexEdit)

            // generate edit file
            val sb = StringBuilder()
            sb.appendln(GenerateReactJSEdit.generatedImport(className))
            sb.appendln(GenerateReactJSEdit.generateConstructor(descriptorList))
            sb.appendln(GenerateReactJSEdit.generateComponentDidMount(className,descriptorList))
            sb.appendln(GenerateReactJSEdit.generateComponentHandleSubmit(className,descriptorList))
            sb.appendln(GenerateReactJSEdit.generateComponentRender(className,descriptorList))
            sb.appendln(GenerateReactJSEdit.generateComponentForm(className,descriptorList))
            val edit = sb.toString()
            val editFileName = "$pathEdit/${className.capitalize()}Edit.js"
            File(editFileName).writeText(edit)
        }

        fun generateReactJSTable(className: String, targetFolder: String ,descriptorList: List<Type>) {
            // generate table file
            val pathTable = "$targetFolder/$COMPONENTS/${className.capitalize()}/${className.capitalize()}Table"
            File(pathTable).mkdirs()

            // generate index file
            val indexTableFileName = "$pathTable/index.js"
            val indexTable =GenerateReactJS.generateTableIndex(className)
            File(indexTableFileName).writeText(indexTable)

            val sbt = StringBuilder()
            sbt.appendln( GenerateReactJS.generateComponentTableHeader(className))
            sbt.appendln(GenerateReactJS.generateComponentTableFields(descriptorList))
            sbt.appendln(GenerateReactJS.generateComponentTableTrailer(className))

            val table = sbt.toString()
            val tableFileName =  "$pathTable/${className.capitalize()}Table.js"
            File(tableFileName).writeText(table)

            val css = GenerateReactJS.generateTableCss()
            val cssFileName ="$pathTable/${className.capitalize()}Table.css"
            File(cssFileName).writeText(css)
        }

        fun generateReactJSReducers(className: String, targetFolder: String ) {
            val reducers = generateReducers(className)
            val pathReducer = "$targetFolder/$REDUCERS/"
            File(pathReducer).mkdirs()
            val reducerFileName = "$pathReducer/${className.capitalize()}Reducer.js"
            File(reducerFileName).writeText(reducers)
        }

        fun generateReactJSActions(className: String, targetFolder: String ) {
            val actionTypes = generateActionTypes(className)
            val actions = generateAction(className)
            val pathTable = "$targetFolder/$ACTIONS/"
            File(pathTable).mkdirs()

            val actionTypeFileName = "$pathTable/${className}_types.js"
            File(actionTypeFileName).writeText(actionTypes)

            val actionFileName ="$pathTable/${className}.js"
            File(actionFileName).writeText(actions)
        }

        fun generateReducerIndex(classNameList: List<String>): String {
            val sb = StringBuilder()
            sb.appendln("import {combineReducers} from 'redux';")
            sb.appendln("import notifyMessageReducer from './notifyMessageReducer';")
            classNameList.map {
                sb.appendln("import ${it.capitalize()}Reducer from './${it.capitalize()}Reducer';")
            }

            sb.appendln("export default combineReducers({")
            sb.appendln("    notifyMessageReducer: notifyMessageReducer,")
            classNameList.map {
                sb.appendln("    ${it}Reducer: ${it.capitalize()}Reducer,")
            }
            sb.appendln("});")
            return sb.toString()
        }

        fun generateActionReducerIndex(targetFolder: String, classNameList: List<String>) {
            val actionIndex = GenerateReactJS.generateActionIndex(classNameList)
            val pathAction = "$targetFolder/$ACTIONS"
            File(pathAction).mkdirs()

            val actionIndexFileName ="$pathAction/index.js"
            File(actionIndexFileName).writeText(actionIndex)

            val reduceIndex = GenerateReactJS.generateReducerIndex(classNameList)
            val pathReducer = "$targetFolder/$REDUCERS/"
            File(pathReducer).mkdirs()

            val reducerIndexFileName = "$pathReducer/index.js"
            File(reducerIndexFileName).writeText(reduceIndex)

            val apiEndpoint = GenerateReactJS.generateApiEndpoints(classNameList)
            val pathapiEndpoint = "$targetFolder/$UTILITIES/"
            File(pathapiEndpoint).mkdirs()

            val apiEndpointFileName = "$pathapiEndpoint/apiEndpoints.js"
            File(apiEndpointFileName).writeText(apiEndpoint)

            val constants = GenerateReactJS.generateConstants(classNameList)
            val pathContants = "$targetFolder/$UTILITIES/"
            File(pathContants).mkdirs()

            val constantsFileName = "$pathContants/constants.js"
            File(constantsFileName).writeText(constants)

            val layout = GenerateReactJS.generateLayout(classNameList)
            val pathLayout ="$targetFolder/$COMPONENTS/Layout"
            File(pathLayout).mkdirs()
            val layoutFileName = "$pathLayout/Layout.js"
            File(layoutFileName).writeText(layout)

        }


        fun copyFiles(targetFolder: String) {
            File(REACT_FILES).copyRecursively(File(targetFolder), true)
        }

        fun generatedEditIndex(typeName: String): String {
            val index = "export {default} from './${typeName.capitalize()}Edit';"
            return index
        }

        fun generateTableIndex(typeName: String): String {
            val index = "export {default} from './${typeName.capitalize()}Table';"
            return index
        }


        fun generateTableCss(): String {
            val css =
                ".delete  {\n" +
                        "    color: red;\n" +
                        "}\n" +
                        ".edit  {\n" +
                        "    color: #fd7e14;\n" +
                        "}\n" +
                        ".view {\n" +
                        "    color: #3292a6;\n" +
                        "}"
            return css
        }

        fun generateActionTypes(typeName: String): String {
            var actionType = "export const ${typeName.toUpperCase()}_ACTIONS = {\n" +
                    "    VIEW_${typeName.toUpperCase()}: 'VIEW_${typeName.toUpperCase()}',\n" +
                    "    EDIT_${typeName.toUpperCase()}: 'EDIT_${typeName.toUpperCase()}',\n" +
                    "    NEW_${typeName.toUpperCase()}: 'NEW_${typeName.toUpperCase()}',\n" +
                    "    DELETE_${typeName.toUpperCase()}: 'DELETE_${typeName.toUpperCase()}',\n" +
                    "\n" +
                    "    FETCH_${typeName.toUpperCase()}_START: 'FETCH_${typeName.toUpperCase()}_START',\n" +
                    "    FETCH_${typeName.toUpperCase()}_SUCCESS: 'FETCH_${typeName.toUpperCase()}_SUCCESS',\n" +
                    "    FETCH_${typeName.toUpperCase()}_FAIL: 'FETCH_${typeName.toUpperCase()}_FAIL',\n" +
                    "\n" +
                    "    UPDATE_${typeName.toUpperCase()}_START: 'UPDATE_${typeName.toUpperCase()}_START',\n" +
                    "    UPDATE_${typeName.toUpperCase()}_SUCCESS: 'UPDATE_${typeName.toUpperCase()}_SUCCESS',\n" +
                    "    UPDATE_${typeName.toUpperCase()}_FAIL: 'UPDATE_${typeName.toUpperCase()}_FAIL',\n" +
                    "\n" +
                    "    ADD_${typeName.toUpperCase()}_START: 'ADD_${typeName.toUpperCase()}_START',\n" +
                    "    ADD_${typeName.toUpperCase()}_SUCCESS: 'ADD_${typeName.toUpperCase()}_SUCCESS',\n" +
                    "    ADD_${typeName.toUpperCase()}_FAIL: 'ADD_${typeName.toUpperCase()}_FAIL',\n" +
                    "\n" +
                    "    DELETE_${typeName.toUpperCase()}_START: 'DELETE_${typeName.toUpperCase()}_START',\n" +
                    "    DELETE_${typeName.toUpperCase()}_SUCCESS: 'DELETE_${typeName.toUpperCase()}_SUCCESS',\n" +
                    "    DELETE_${typeName.toUpperCase()}_FAIL: 'DELETE_${typeName.toUpperCase()}_FAIL'\n" +
                    "}"
            return actionType
        }

        fun generateAction(typeName: String): String {
            val actions =
                "import Axios, { ApiEndpoints, Constants } from '../utilities';\n" +
                        "import { ${typeName.toUpperCase()}_ACTIONS } from '../actions/${typeName}_types';\n" +
                        "import {NOTIFY_MESSAGE_ACTIONS} from '../actions/notifyMessage_types';\n" +
                        "\n" +
                        "export const get${typeName.capitalize()} = (page, limit, searchText) => async dispatch => {\n" +
                        "    try {        \n" +
                        "        // console.log(res);\n" +
                        "       \n" +
                        "        const _page = page ? Math.round(page): 1;\n" +
                        "        const _limit = limit? limit: Constants.itemsPerPage; // Maximum per page\n" +
                        "        //  console.log('page',page,'limit',limit,'_page',_page,'_limit', _limit)\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.FETCH_${typeName.toUpperCase()}_START\n" +
                        "        });\n" +
                        "        const url = searchText \n" +
                        "            ? `\${ApiEndpoints.${typeName}}?_page=\${_page}&_limit=\${_limit}&q=\${searchText}` \n" +
                        "            : `\${ApiEndpoints.${typeName}}?_page=\${_page}&_limit=\${_limit}`\n" +
                        "        const res = await Axios.get(url);\n" +
                        "\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.FETCH_${typeName.toUpperCase()}_SUCCESS,\n" +
                        "            payload: res.data\n" +
                        "        });\n" +
                        "\n" +
                        "    } catch (e) {\n" +
                        "        console.error(e);\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.FETCH_${typeName.toUpperCase()}_FAIL,\n" +
                        "            payload: e\n" +
                        "        })        \n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "export const view${typeName.capitalize()} = (${typeName}) => async dispatch => {\n" +
                        "    dispatch({\n" +
                        "        type: ${typeName.toUpperCase()}_ACTIONS.VIEW_${typeName.toUpperCase()},\n" +
                        "        payload: ${typeName}\n" +
                        "    });\n" +
                        "}\n" +
                        "export const edit${typeName.capitalize()} = (${typeName}) => async dispatch => {\n" +
                        "    dispatch({\n" +
                        "        type: ${typeName.toUpperCase()}_ACTIONS.EDIT_${typeName.toUpperCase()},\n" +
                        "        payload: ${typeName}\n" +
                        "    });\n" +
                        "}\n" +
                        "export const new${typeName.capitalize()} = () => async dispatch => {\n" +
                        "    dispatch({\n" +
                        "        type: ${typeName.toUpperCase()}_ACTIONS.NEW_${typeName.toUpperCase()},\n" +
                        "    });\n" +
                        "}\n" +
                        "\n" +
                        "export const update${typeName.capitalize()} = (${typeName}) => async dispatch => {\n" +
                        "    try {        \n" +
                        "        // console.log(res);\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_START\n" +
                        "        });\n" +
                        "        \n" +
                        "        const res = await Axios.put(`\${ApiEndpoints.${typeName}}/\${${typeName}.id}`, ${typeName});\n" +
                        "\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_SUCCESS,\n" +
                        "            payload: res.data\n" +
                        "        });\n" +
                        "        const messagePayload = getMessagePayload(\"${typeName.capitalize()} Updated\", `ID: \${${typeName}.id}`, \"INFO\");\n" +
                        "        dispatch({\n" +
                        "            type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO,\n" +
                        "            payload: messagePayload\n" +
                        "        });\n" +
                        "    } catch (e) {\n" +
                        "        console.error(e);\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_FAIL,\n" +
                        "            payload: e\n" +
                        "        })        \n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "export const add${typeName.capitalize()} = (${typeName}) => async dispatch => {\n" +
                        "    try {        \n" +
                        "        console.log(${typeName});\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_START\n" +
                        "        });\n" +
                        "        const res = await Axios.post(ApiEndpoints.${typeName}, ${typeName});\n" +
                        "\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_SUCCESS,\n" +
                        "            payload: res.data\n" +
                        "        });\n" +
                        "        const messagePayload = getMessagePayload(\"${typeName.capitalize()} Added\", `ID: \${res.data.id}`, \"INFO\")\n" +
                        "        dispatch({\n" +
                        "            type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO,\n" +
                        "            payload: messagePayload\n" +
                        "        });\n" +
                        "    } catch (e) {\n" +
                        "        console.error('add${typeName.capitalize()} ',e);\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_FAIL,\n" +
                        "            payload: e\n" +
                        "        })        \n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const getMessagePayload =(messageHeader, messageBody, messageType) =>{\n" +
                        "   const notifyMessage = {\n" +
                        "        show: true,\n" +
                        "        hide: messageType === \"INFO\" ? true: false,\n" +
                        "        messageHeader: messageHeader,\n" +
                        "        messageBody: messageBody,\n" +
                        "        messageType: messageType\n" +
                        "    }\n" +
                        "    return notifyMessage\n" +
                        "}\n" +
                        "\n" +
                        "export const delete${typeName.capitalize()} = (${typeName}) => async dispatch => {\n" +
                        "    try {        \n" +
                        "        // console.log(res);\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.DELETE_${typeName.toUpperCase()}_START\n" +
                        "        });\n" +
                        "        const res = await Axios.delete(`\${ApiEndpoints.${typeName}}/\${${typeName}.id}`);\n" +
                        "\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.DELETE_${typeName.toUpperCase()}_SUCCESS,\n" +
                        "            payload: ${typeName}\n" +
                        "        });\n" +
                        "        const messagePayload = getMessagePayload(\"${typeName.capitalize()} Deleted\", `ID: \${${typeName}.id}`, \"INFO\");\n" +
                        "        dispatch({\n" +
                        "            type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO,\n" +
                        "            payload: messagePayload\n" +
                        "        });\n" +
                        "    } catch (e) {\n" +
                        "        console.error(e);\n" +
                        "        dispatch({\n" +
                        "            type: ${typeName.toUpperCase()}_ACTIONS.DELETE_${typeName.toUpperCase()}_FAIL,\n" +
                        "            payload: e\n" +
                        "        })        \n" +
                        "    }\n" +
                        "}"

            return actions
        }

        fun generateActionIndex(actionNames: List<String>): String {
            val sb = StringBuilder()
            sb.appendln("export {notifyInfo, notifyError, notifyWarning, notifyClear} from './notifyMessage'")
            actionNames.map {
                sb.appendln("export{get${it.capitalize()}, view${it.capitalize()}, edit${it.capitalize()}, update${it.capitalize()}, add${it.capitalize()}, delete${it.capitalize()}, new${it.capitalize()}} from './${it}'")
            }
            return sb.toString()
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
            sb.append("    typesList: ['")
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
                    "                <Row>\n" +
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


        fun generateReducers(typeName: String): String {
            val reducer =
                        "import { ${typeName.toUpperCase()}_ACTIONS } from '../actions/${typeName}_types';\n" +
                        "\n" +
                        "const initialState = {\n" +
                        "    loading: false,\n" +
                        "    action: null,\n" +
                        "    ${typeName}List: null,\n" +
                        "    ${typeName}: null,\n" +
                        "    error: null\n" +
                        "}\n" +
                        "\n" +
                        "const processStart = (state, action) => {\n" +
                        "    return { ...state, loading: true }\n" +
                        "}\n" +
                        "\n" +
                        "const processFail = (state, action) => {\n" +
                        "\n" +
                        "    console.error('fail',action.payload);\n" +
                        "\n" +
                        "    return {\n" +
                        "// TODO\n" +
                        "        ...state, error: {\n" +
                        "            ...action.payload\n" +
                        "        }, loading: false\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const fetch${typeName.capitalize()}ListSuccess = (state, action) => {\n" +
                        "\n" +
                        "    let ids = state.${typeName}List? state.${typeName}List.map(p=>p.id):[];\n" +
                        "    // console.log('ids',ids);\n" +
                        "    const newRecords = action.payload.filter((e)=>{return ids.indexOf(e.id) < 0});\n" +
                        "    // console.log(newRecords);\n" +
                        "    const ${typeName}List = !state.${typeName}List? [...action.payload]:[...state.${typeName}List,...newRecords]\n" +
                        "\n" +
                        "    return {\n" +
                        "        ...state, \n" +
                        "        ${typeName}List: [\n" +
                        "            ...${typeName}List\n" +
                        "        ], loading: false\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const view${typeName.capitalize()} = (state, action) => {\n" +
                        "    return {\n" +
                        "        ...state,\n" +
                        "        action: action.type,\n" +
                        "        ${typeName}: {\n" +
                        "            ...action.payload\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const new${typeName.capitalize()} = (state, action) => {\n" +
                        "    return {\n" +
                        "        ...state,\n" +
                        "        action: action.type,\n" +
                        "        ${typeName}: {\n" +
                        "            id: 0,\n" +
                        "            firstname: \"\",\n" +
                        "            lastname: \"\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const update${typeName.capitalize()} = (state, action) => {\n" +
                        "    return {\n" +
                        "        ...state,\n" +
                        "        loading: false,\n" +
                        "        action: action.type,\n" +
                        "        ${typeName}: {...action.payload},\n" +
                        "        ${typeName}List: state.${typeName}List.map(\n" +
                        "            p => p.id === action.payload.id \n" +
                        "            ? p= action.payload\n" +
                        "            : p\n" +
                        "        )\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const add${typeName.capitalize()} = (state, action) => {\n" +
                        "    return {\n" +
                        "        ...state,\n" +
                        "        action: action.type,\n" +
                        "        loading: true,\n" +
                        "        ${typeName}: {...action.payload},\n" +
                        "        ${typeName}List: [\n" +
                        "            action.payload,...state.${typeName}List\n" +
                        "        ], \n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const delete${typeName.capitalize()} = (state, action) => {\n" +
                        "    // console.log(action.payload)\n" +
                        "    const new${typeName.capitalize()}List = [...state.${typeName}List];\n" +
                        "    const index = new${typeName.capitalize()}List.findIndex(p=>p.id === action.payload.id);\n" +
                        "    new${typeName.capitalize()}List.splice(index,1);\n" +
                        "    return {\n" +
                        "        ...state,\n" +
                        "        action: action.type,\n" +
                        "        loading: true,\n" +
                        "        ${typeName}List:new${typeName.capitalize()}List,\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const reducer = (state = initialState, action) => {\n" +
                        "    switch (action.type) {\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_START:\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_START:\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.FETCH_${typeName.toUpperCase()}_START:\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.DELETE_${typeName.toUpperCase()}_START:\n" +
                        "            return processStart(state, action);\n" +
                        "\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_FAIL:\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_FAIL:\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.FETCH_${typeName.toUpperCase()}_FAIL:\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.DELETE_${typeName.toUpperCase()}_FAIL:\n" +
                        "                return processFail(state, action); \n" +
                        "\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.FETCH_${typeName.toUpperCase()}_SUCCESS:\n" +
                        "            return fetch${typeName.capitalize()}ListSuccess(state, action);\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.VIEW_${typeName.toUpperCase()}:\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.EDIT_${typeName.toUpperCase()}:\n" +
                        "            return view${typeName.capitalize()}(state, action);\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.NEW_${typeName.toUpperCase()}:\n" +
                        "            return new${typeName.capitalize()}(state, action);\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_SUCCESS:\n" +
                        "            return add${typeName.capitalize()}(state, action);\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_SUCCESS:\n" +
                        "            return update${typeName.capitalize()}(state, action);\n" +
                        "        case ${typeName.toUpperCase()}_ACTIONS.DELETE_${typeName.toUpperCase()}_SUCCESS:\n" +
                        "            return delete${typeName.capitalize()}(state, action);\n" +
                        "        default:\n" +
                        "            return state;\n" +
                        "    }\n" +
                        "}\n" +
                        "export default reducer;"

            return reducer
        }

        fun generateComponentTableHeader(typeName: String): String {
            val component =
                        "import React, { Component } from 'react'\n" +
                        "import { Redirect } from 'react-router-dom';\n" +
                        "import { connect } from 'react-redux';\n" +
                        "import { Container, Row, Col, Button } from 'react-bootstrap';\n" +
                        "import ConfirmModal from '../../ConfirmModal';\n" +
                        "import BootstrapTable from 'react-bootstrap-table-next';\n" +
                        "import paginationFactory from 'react-bootstrap-table2-paginator';\n" +
                        "import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';\n" +
                        "import 'react-bootstrap-table-next/dist/react-bootstrap-table2.css';\n" +
                        "import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';\n" +
                        "import './${typeName.capitalize()}Table.css'\n" +
                        "import dayjs from 'dayjs';\n" +
                        "import numeral from 'numeral';\n" +
                        "import  { Constants } from '../../../utilities';\n" +
                        "\n" +
                        "import { ${typeName.toUpperCase()}_ACTIONS } from '../../../actions/${typeName}_types';\n" +
                        "import { get${typeName.capitalize()}, view${typeName.capitalize()}, edit${typeName.capitalize()}, new${typeName.capitalize()}, delete${typeName.capitalize()}, notifyInfo, notifyError, notifyWarning } from '../../../actions'\n" +
                        "\n" +
                        "\n" +
                        "/**\n" +
                        " * This uses https://react-bootstrap-table.github.io/react-bootstrap-table2/docs/getting-started.html\n" +
                        " */\n" +
                        "\n" +
                        "export class ${typeName.capitalize()}Table extends Component {\n" +
                        "    constructor(props){\n" +
                        "        super(props);\n" +
                        "        this.searchText = React.createRef();\n" +
                        "    }\n" +
                        "    state = {\n" +
                        "        action: null,\n" +
                        "        showConfirmModal: false,\n" +
                        "        confirmModalHeader: \"\",\n" +
                        "        confirmModalBody:\"\", \n" +
                        "        ${typeName}:null\n" +
                        "    }\n" +
                        "    componentDidMount() {\n" +
                        "        this.props.get${typeName.capitalize()}();\n" +
                        "    }\n" +
                        "    deleteHandle = (data) =>{\n" +
                        "        // console.log(data);\n" +
                        "        this.setState({showConfirmModal: true,\n" +
                        "            ${typeName}:data,\n" +
                        "            confirmModalHeader: 'Delete record ?',\n" +
                        "            confirmModalBody: `ID: \${data.id}`});\n" +
                        "    }\n" +
                        "    onConfirmDelete=(confirmDelete)=>{\n" +
                        "        this.setState({showConfirmModal: false});\n" +
                        "        const {${typeName}} = this.state;\n" +
                        "        if(confirmDelete) {\n" +
                        "            this.props.delete${typeName.capitalize()}(${typeName});\n" +
                        "            this.setState({${typeName}:null});\n" +
                        "        }\n" +
                        "    }\n" +
                        "    addHandle = () =>{\n" +
                        "        // console.log(data);\n" +
                        "        this.props.new${typeName.capitalize()}();\n" +
                        "        this.setState({action: ${typeName.toUpperCase()}_ACTIONS.NEW_${typeName.toUpperCase()}});\n" +
                        "    }\n" +
                        "    editHandle = (data) =>{\n" +
                        "        // console.log(data);\n" +
                        "        this.props.edit${typeName.capitalize()}(data);\n" +
                        "        this.setState({action: ${typeName.toUpperCase()}_ACTIONS.EDIT_${typeName.toUpperCase()}});\n" +
                        "    }\n" +
                        "    viewHandle = (data) =>{\n" +
                        "        //  console.log(data);\n" +
                        "        this.props.view${typeName.capitalize()}(data);\n" +
                        "        this.setState({action: ${typeName.toUpperCase()}_ACTIONS.VIEW_${typeName.toUpperCase()}});\n" +
                        "    }\n" +
                        "\n" +
                        "    onPageChange =(page, sizePerPage) =>{\n" +
                        "        // console.log(\"page\", page,\"sizePerpage\",sizePerPage)\n" +
                        "        this.fetchMorePages(page,sizePerPage);\n" +
                        "    }\n" +
                        "    onSizePerPageChange =(page, sizePerPage) =>{\n" +
                        "        // console.log(\"page\", page,\"sizePerpage\",sizePerPage)\n" +
                        "        this.fetchMorePages(page,sizePerPage);\n" +
                        "    }\n" +
                        "\n" +
                        "    fetchMorePages=(page, sizePerPage)=>{\n" +
                        "        const {${typeName}List} = this.props;\n" +
                        "        const totalPages = Math.round(${typeName}List.length / sizePerPage);\n" +
                        "        const isFetchingMorePages = page >= totalPages  ? true: false; \n" +
                        "        // console.log('totalPages', totalPages,'isFetchingMorePages',isFetchingMorePages )\n" +
                        "        if(isFetchingMorePages){\n" +
                        "            this.props.get${typeName.capitalize()}(page+1,sizePerPage);\n" +
                        "        }\n" +
                        "    }\n" +
                        "    \n" +
                        "    onSearch = ()=>{\n" +
                        "        const searchText = this.searchText.current.props.searchText;\n" +
                        "        this.props.get${typeName.capitalize()}(1, Constants.itemsPerPage, searchText);\n" +
                        "    }\n" +
                        "    onClearSearch=() =>{\n" +
                        "        console.log(\"Clear Search\");\n" +
                        "        this.props.get${typeName.capitalize()}();\n" +
                        "    }\n" +
                        "\n" +
                        "    render() {\n" +
                        "        const ${typeName}List = this.props.${typeName}List;\n" +
                        "        if (!${typeName}List) {\n" +
                        "            return null\n" +
                        "        }\n" +
                        "        // console.log(${typeName}List);\n" +
                        "\n" +
                        "        const {action} = this.state\n" +
                        "        let redirect ='';\n" +
                        "        switch(action) {\n" +
                        "            case ${typeName.toUpperCase()}_ACTIONS.NEW_${typeName.toUpperCase()}: \n" +
                        "                redirect = <Redirect push to={'/${typeName}Edit'}/>\n" +
                        "                break;\n" +
                        "            case ${typeName.toUpperCase()}_ACTIONS.EDIT_${typeName.toUpperCase()}: \n" +
                        "                redirect = <Redirect push to={'/${typeName}Edit'}/>\n" +
                        "                break;\n" +
                        "            case ${typeName.toUpperCase()}_ACTIONS.VIEW_${typeName.toUpperCase()}: \n" +
                        "                redirect = <Redirect push to={'/${typeName}Edit'}/>\n" +
                        "                break;\n" +
                        "            case ${typeName.toUpperCase()}_ACTIONS.DELETE_${typeName.toUpperCase()}: \n" +
                        "                redirect = <Redirect push to={'/${typeName}Delete'}/>\n" +
                        "                break;\n" +
                        "            default: \n" +
                        "                redirect =null;\n" +
                        "                break;\n" +
                        "        }\n"

            return component
        }

        fun generateComponentTableFields(fieldTypes:List<Type>): String {
            val sb =  StringBuilder()
            sb.appendln("const columns = [")

            fieldTypes.map {
                when(it.fieldType) {
                    "string"->{
                        sb.appendln("            { dataField: '${it.fieldName}', text: '${it.fieldName.capitalize()}', sort: true },")
                    }
                    "date"->{
                        sb.appendln("            { dataField: '${it.fieldName}', text: '${it.fieldName.capitalize()}', sort: true },")
                    }
                    "boolean"->{
                        sb.appendln("            { dataField: '${it.fieldName}', text: '${it.fieldName}', sort: true,\n" +
                                "            formatter: (cell, row)=>{return ( \n" +
                                "                <>\n" +
                                "                <div className=\"checkbox disabled text-center\">\n" +
                                "                <label  >\n" +
                                "                    <input type=\"checkbox\" checked={row.${it.fieldName}} disabled/>\n" +
                                "                </label>\n" +
                                "                </div>\n" +
                                "                </>\n" +
                                "            )}, \n" +
                                "            },\n" +
                                "        \n" +
                                "            ")
                    }
                    "instant"->{
                        sb.appendln("            { dataField: '${it.fieldName}', text: '${it.fieldName}', sort: true,\n" +
                                "                formatter: (cell, row)=> { return (\n" +
                                "                    <>\n" +
                                "                    <div className='text-center'>\n" +
                                "                        {dayjs(row.${it.fieldName}).format('DD/MM/YYYY HH:mm:ss')}\n" +
                                "                    </div>\n" +
                                "                    </>\n" +
                                "                )}\n" +
                                "            },")
                    }
                    "double"->{
                        sb.appendln("            { dataField: '${it.fieldName}', text: '${it.fieldName}', sort: true, \n" +
                                "                formatter: (cell, row)=> {return (\n" +
                                "                    <>\n" +
                                "                    <div className='text-right'>\n" +
                                "                        {numeral(row.${it.fieldName}).format('\$0,0.00')}\n" +
                                "                    </div>\n" +
                                "                    </>\n" +
                                "                )}\n" +
                                "\n" +
                                "            },")
                    }
                    else ->{
                        sb.appendln("            { dataField: '${it.fieldName}', text: '${it.fieldName.capitalize()}', sort: true },")
                    }
                }
            }

            return sb.toString()

        }

        fun generateComponentTableTrailer(typeName: String): String {
            val component =
                        "            { dataField: 'df1', text: 'Action', isDummyField: true,\n" +
                        "                 formatter: (cell, row)=>{return ( \n" +
                        "                     <>\n" +
                        "                    <i data-action='delete' className=\"delete far fa-trash-alt\"></i>\n" +
                        "                    <i data-action='edit' className=\"edit ml-2 fas fa-pencil-alt\"></i>\n" +
                        "                    <i data-action='view' className=\"view ml-2 far fa-eye\"></i>\n" +
                        "                    </>\n" +
                        "                    )},\n" +
                        "                events: {\n" +
                        "                    onClick: (e, column, columnIndex, row, rowIndex) => {\n" +
                        "                        switch(e.target && e.target.dataset.action){\n" +
                        "                            case 'delete': {\n" +
                        "                                this.deleteHandle(row);\n" +
                        "                                break;\n" +
                        "                            }\n" +
                        "                            case 'edit': {\n" +
                        "                                this.editHandle(row);\n" +
                        "                                break;\n" +
                        "                            }\n" +
                        "                            case 'view': {\n" +
                        "                                this.viewHandle(row);\n" +
                        "                                break;\n" +
                        "                            }\n" +
                        "                            default: break;//console.error(`invalid action: \${e.eventData.target.dataset.action}`)\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "        const defaultSorted = [{ dataField: 'firstname', order: 'desc' }];\n" +
                        "\n" +
                        "        const pagination = paginationFactory({\n" +
                        "            page: 1,\n" +
                        "            sizePerPage: 5,\n" +
                        "            lastPageText: '>>',\n" +
                        "            firstPageText: '<<',\n" +
                        "            nextPageText: '>',\n" +
                        "            prePageText: '<',\n" +
                        "            showTotal: true,\n" +
                        "            alwaysShowAllBtns: true,\n" +
                        "            onPageChange: (page,sizePerPage)=>this.onPageChange(page,sizePerPage),\n" +
                        "            onSizePerPageChange: (page,sizePerPage) => this.onSizePerPageChange(sizePerPage,page),\n" +
                        "        });\n" +
                        "        const { SearchBar, ClearSearchButton } = Search;\n" +
                        "        // const { ExportCSVButton } = CSVExport;\n" +
                        "        const MyExportCSV = (props) => {\n" +
                        "            const handleClick = () => {\n" +
                        "              props.onExport();\n" +
                        "            };\n" +
                        "            return (\n" +
                        "                <button className=\"btn btn-success\" onClick={handleClick}>Export to CSV</button>\n" +
                        "            );\n" +
                        "          };\n" +
                        "\n" +
                        "          const showConfirmModal = this.state.showConfirmModal === true\n" +
                        "            ? \n" +
                        "                <ConfirmModal show={this.state.showConfirmModal} \n" +
                        "                    handleClose={this.onConfirmDelete}\n" +
                        "                     confirmHeader={this.state.confirmModalHeader}\n" +
                        "                     confirmBody={this.state.confirmModalBody} />  \n" +
                        "\n" +
                        "            : null\n" +
                        "\n" +
                        "        return (\n" +
                        "            <>\n" +
                        "            {redirect}\n" +
                        "            \n" +
                        "                <Container>\n" +
                        "                    {showConfirmModal}\n" +
                        "                    <h1>List ${typeName.capitalize()}</h1>\n" +
                        "                     <ToolkitProvider\n" +
                        "                        bootstrap4\n" +
                        "                        keyField='id'\n" +
                        "                        data={${typeName}List}\n" +
                        "                        columns={columns}\n" +
                        "                        search\n" +
                        "                    >\n" +
                        "                        {\n" +
                        "                            props => (\n" +
                        "                                <>\n" +
                        "                                    <Row>\n" +
                        "                                        <Col>\n" +
                        "                                            <MyExportCSV {...props.csvProps} />\n" +
                        "                                        </Col>\n" +
                        "                                        <Col>\n" +
                        "                                            <SearchBar {...props.searchProps} ref={this.searchText} />\n" +
                        "                                            <ClearSearchButton {...props.searchProps} onClick={()=>this.onClearSearch} />\n" +
                        "                                            <Button className=\"btn btn-success\" onClick={this.onSearch} >Search</Button>\n" +
                        "                                        </Col>\n" +
                        "                                        <Col className=\"text-right\">\n" +
                        "                                            <Button className=\"btn btn-success\" onClick={this.addHandle}>New</Button>\n" +
                        "                                        </Col>\n" +
                        "                                    </Row>\n" +
                        "                                    \n" +
                        "\n" +
                        "                                    <BootstrapTable\n" +
                        "                                        defaultSorted={defaultSorted}\n" +
                        "                                        pagination={pagination}\n" +
                        "                                        {...props.baseProps}\n" +
                        "                                    />\n" +
                        "                                </>\n" +
                        "                            )\n" +
                        "                        }\n" +
                        "                    </ToolkitProvider>\n" +
                        "                </Container>\n" +
                        "            </>\n" +
                        "        )\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "const mapStateToProps = state => ({\n" +
                        "    ${typeName}List: state.${typeName}Reducer.${typeName}List,\n" +
                        "    loading: state.${typeName}Reducer.loading,\n" +
                        "    action: state.${typeName}Reducer.action,\n" +
                        "})\n" +
                        "\n" +
                        "export default connect(mapStateToProps, { get${typeName.capitalize()}, view${typeName.capitalize()}, edit${typeName.capitalize()}, new${typeName.capitalize()}, delete${typeName.capitalize()}, notifyInfo, notifyError, notifyWarning  })(${typeName.capitalize()}Table);\n"
            return component
        }
    }
}