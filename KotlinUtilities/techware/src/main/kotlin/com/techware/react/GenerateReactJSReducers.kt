package com.techware.react

import java.io.File
import java.lang.StringBuilder

class GenerateReactJSReducers {
    companion object {

        fun generateReactJSReducersFiles(className: String, targetFolder: String) {
            val reducers = generateReducers(className)
            val pathReducer = "$targetFolder/${ReactJSConstants.REDUCERS}/"
            File(pathReducer).mkdirs()
            val reducerFileName = "$pathReducer/${className.capitalize()}Reducer.js"
            File(reducerFileName).writeText(reducers)
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

//        fun generateActionReducerIndexFiles(targetFolder: String, classNameList: List<String>) {
//            GenerateReactJSActions.generateActionIndexFile(classNameList, targetFolder)
//
//            GenerateReactJSReducers.generateReducerIndexFile(classNameList, targetFolder)
//
//            GenerateReactJSCommon.generateApiEndpointFile(classNameList, targetFolder)
//
//            GenerateReactJSCommon.generateConstantsFile(classNameList, targetFolder)
//
//            GenerateReactJSCommon.generateLayoutFile(classNameList, targetFolder)
//        }

        fun generateReducerIndexFile(
            classNameList: List<String>,
            targetFolder: String
        ) {
            val reduceIndex = generateReducerIndex(classNameList)
            val pathReducer = "$targetFolder/${ReactJSConstants.REDUCERS}/"
            File(pathReducer).mkdirs()

            val reducerIndexFileName = "$pathReducer/index.js"
            File(reducerIndexFileName).writeText(reduceIndex)
        }


    }
}