package com.techware.react

import java.io.File
import java.lang.StringBuilder

class GenerateReactJSActions {
    companion object {
        fun generateReactJSActions(className: String, targetFolder: String ) {
            val actionTypes = generateActionTypes(className)
            val actions = generateAction(className)
            val pathTable = "$targetFolder/${ReactJSConstants.ACTIONS}/"
            File(pathTable).mkdirs()

            val actionTypeFileName = "$pathTable/${className}_types.js"
            File(actionTypeFileName).writeText(actionTypes)

            val actionFileName ="$pathTable/${className}.js"
            File(actionFileName).writeText(actions)
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

        fun generateActionIndexFile(
            classNameList: List<String>,
            targetFolder: String
        ) {
            val actionIndex = GenerateReactJSActions.generateActionIndex(classNameList)
            val pathAction = "$targetFolder/${ReactJSConstants.ACTIONS}"
            File(pathAction).mkdirs()

            val actionIndexFileName = "$pathAction/index.js"
            File(actionIndexFileName).writeText(actionIndex)
        }
    }
}