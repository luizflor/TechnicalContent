import com.techware.react.*
//import com.techware.react.GenereateReactJSEdit.Companion.getFieldList
import com.techware.utilities.Descriptor
import com.techware.utilities.Type
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.StringBuilder

class GenerateReactJSTest {
    fun getTypeList(): List<Type> {
        val descriptorPerson2Str="id, int\n" +
                "firstname, string\n" +
                "lastname, string\n" +
                "birthdate, date\n" +
                "ismodified, boolean\n" +
                "timestamp, instant\n" +
                "salary, double\n"
        val typeList = Descriptor.Companion.convertDescriptorToTypeList(descriptorPerson2Str)
        return typeList
    }

//    @Test
//    fun generateReactJSFiles() {
//        GenerateReactJS.copyFiles(PATH_DESTINATION_REACTJS)
//    }

    @Test
    fun generateEditIndexTest() {
        val index =GenerateReactJSEdit.generatedEditIndex("person")
//        println(index)
        val expected =
            "export {default} from './PersonEdit';"
        assertEquals(expected,index)
    }

    @Test
    fun generateTableIndexTest() {
        val index =GenerateReactJSTable.generateTableIndex("person")
//        println(index)
        val expected =
            "export {default} from './PersonTable';"
        assertEquals(expected,index)
    }

    @Test
    fun generateFakeActionType() {
        val actionType= GenerateReactJSActions.generateActionTypes("person")
//        println(actionType)

        val actionTypeExpected =
            "export const PERSON_ACTIONS = {\n" +
                    "    VIEW_PERSON: 'VIEW_PERSON',\n" +
                    "    EDIT_PERSON: 'EDIT_PERSON',\n" +
                    "    NEW_PERSON: 'NEW_PERSON',\n" +
                    "    DELETE_PERSON: 'DELETE_PERSON',\n" +
                    "\n" +
                    "    FETCH_PERSON_START: 'FETCH_PERSON_START',\n" +
                    "    FETCH_PERSON_SUCCESS: 'FETCH_PERSON_SUCCESS',\n" +
                    "    FETCH_PERSON_FAIL: 'FETCH_PERSON_FAIL',\n" +
                    "\n" +
                    "    UPDATE_PERSON_START: 'UPDATE_PERSON_START',\n" +
                    "    UPDATE_PERSON_SUCCESS: 'UPDATE_PERSON_SUCCESS',\n" +
                    "    UPDATE_PERSON_FAIL: 'UPDATE_PERSON_FAIL',\n" +
                    "\n" +
                    "    ADD_PERSON_START: 'ADD_PERSON_START',\n" +
                    "    ADD_PERSON_SUCCESS: 'ADD_PERSON_SUCCESS',\n" +
                    "    ADD_PERSON_FAIL: 'ADD_PERSON_FAIL',\n" +
                    "\n" +
                    "    DELETE_PERSON_START: 'DELETE_PERSON_START',\n" +
                    "    DELETE_PERSON_SUCCESS: 'DELETE_PERSON_SUCCESS',\n" +
                    "    DELETE_PERSON_FAIL: 'DELETE_PERSON_FAIL'\n" +
                    "}"
        assertEquals(actionTypeExpected, actionType)
    }

    @Test
    fun generateActionTest() {
        val action = GenerateReactJSActions.generateAction("person")
//        println(action)
        val expected =
            "import Axios, { ApiEndpoints, Constants } from '../utilities';\n" +
                    "import { PERSON_ACTIONS } from '../actions/person_types';\n" +
                    "import {NOTIFY_MESSAGE_ACTIONS} from '../actions/notifyMessage_types';\n" +
                    "\n" +
                    "export const getPerson = (page, limit, searchText) => async dispatch => {\n" +
                    "    try {        \n" +
                    "        // console.log(res);\n" +
                    "       \n" +
                    "        const _page = page ? Math.round(page): 1;\n" +
                    "        const _limit = limit? limit: Constants.itemsPerPage; // Maximum per page\n" +
                    "        //  console.log('page',page,'limit',limit,'_page',_page,'_limit', _limit)\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.FETCH_PERSON_START\n" +
                    "        });\n" +
                    "        const url = searchText \n" +
                    "            ? `\${ApiEndpoints.person}?_page=\${_page}&_limit=\${_limit}&q=\${searchText}` \n" +
                    "            : `\${ApiEndpoints.person}?_page=\${_page}&_limit=\${_limit}`\n" +
                    "        const res = await Axios.get(url);\n" +
                    "\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.FETCH_PERSON_SUCCESS,\n" +
                    "            payload: res.data\n" +
                    "        });\n" +
                    "\n" +
                    "    } catch (e) {\n" +
                    "        console.error(e);\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.FETCH_PERSON_FAIL,\n" +
                    "            payload: e\n" +
                    "        })        \n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "export const viewPerson = (person) => async dispatch => {\n" +
                    "    dispatch({\n" +
                    "        type: PERSON_ACTIONS.VIEW_PERSON,\n" +
                    "        payload: person\n" +
                    "    });\n" +
                    "}\n" +
                    "export const editPerson = (person) => async dispatch => {\n" +
                    "    dispatch({\n" +
                    "        type: PERSON_ACTIONS.EDIT_PERSON,\n" +
                    "        payload: person\n" +
                    "    });\n" +
                    "}\n" +
                    "export const newPerson = () => async dispatch => {\n" +
                    "    dispatch({\n" +
                    "        type: PERSON_ACTIONS.NEW_PERSON,\n" +
                    "    });\n" +
                    "}\n" +
                    "\n" +
                    "export const updatePerson = (person) => async dispatch => {\n" +
                    "    try {        \n" +
                    "        // console.log(res);\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.UPDATE_PERSON_START\n" +
                    "        });\n" +
                    "        \n" +
                    "        const res = await Axios.put(`\${ApiEndpoints.person}/\${person.id}`, person);\n" +
                    "\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.UPDATE_PERSON_SUCCESS,\n" +
                    "            payload: res.data\n" +
                    "        });\n" +
                    "        const messagePayload = getMessagePayload(\"Person Updated\", `ID: \${person.id}`, \"INFO\");\n" +
                    "        dispatch({\n" +
                    "            type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO,\n" +
                    "            payload: messagePayload\n" +
                    "        });\n" +
                    "    } catch (e) {\n" +
                    "        console.error(e);\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.UPDATE_PERSON_FAIL,\n" +
                    "            payload: e\n" +
                    "        })        \n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "export const addPerson = (person) => async dispatch => {\n" +
                    "    try {        \n" +
                    "        console.log(person);\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.ADD_PERSON_START\n" +
                    "        });\n" +
                    "        const res = await Axios.post(ApiEndpoints.person, person);\n" +
                    "\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.ADD_PERSON_SUCCESS,\n" +
                    "            payload: res.data\n" +
                    "        });\n" +
                    "        const messagePayload = getMessagePayload(\"Person Added\", `ID: \${res.data.id}`, \"INFO\")\n" +
                    "        dispatch({\n" +
                    "            type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO,\n" +
                    "            payload: messagePayload\n" +
                    "        });\n" +
                    "    } catch (e) {\n" +
                    "        console.error('addPerson ',e);\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.ADD_PERSON_FAIL,\n" +
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
                    "export const deletePerson = (person) => async dispatch => {\n" +
                    "    try {        \n" +
                    "        // console.log(res);\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.DELETE_PERSON_START\n" +
                    "        });\n" +
                    "        const res = await Axios.delete(`\${ApiEndpoints.person}/\${person.id}`);\n" +
                    "\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.DELETE_PERSON_SUCCESS,\n" +
                    "            payload: person\n" +
                    "        });\n" +
                    "        const messagePayload = getMessagePayload(\"Person Deleted\", `ID: \${person.id}`, \"INFO\");\n" +
                    "        dispatch({\n" +
                    "            type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO,\n" +
                    "            payload: messagePayload\n" +
                    "        });\n" +
                    "    } catch (e) {\n" +
                    "        console.error(e);\n" +
                    "        dispatch({\n" +
                    "            type: PERSON_ACTIONS.DELETE_PERSON_FAIL,\n" +
                    "            payload: e\n" +
                    "        })        \n" +
                    "    }\n" +
                    "}"
        assertEquals(expected, action)
    }

    @Test
    fun generateActionIndexText() {
        val actionIndex = GenerateReactJSActions.generateActionIndex(arrayListOf("person", "person2"))
//        println(actionIndex)
        val expected = "export {notifyInfo, notifyError, notifyWarning, notifyClear} from './notifyMessage'\n" +
                "export{getPerson, viewPerson, editPerson, updatePerson, addPerson, deletePerson, newPerson} from './person'\n" +
                "export{getPerson2, viewPerson2, editPerson2, updatePerson2, addPerson2, deletePerson2, newPerson2} from './person2'\n"

        assertEquals(expected,actionIndex)
    }

    @Test
    fun generateReducerText() {
        val reducer = GenerateReactJSReducers.generateReducers("person")
//        println(reducer)

        val expected =
            "import { PERSON_ACTIONS } from '../actions/person_types';\n" +
                    "\n" +
                    "const initialState = {\n" +
                    "    loading: false,\n" +
                    "    action: null,\n" +
                    "    personList: null,\n" +
                    "    person: null,\n" +
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
                    "const fetchPersonListSuccess = (state, action) => {\n" +
                    "\n" +
                    "    let ids = state.personList? state.personList.map(p=>p.id):[];\n" +
                    "    // console.log('ids',ids);\n" +
                    "    const newRecords = action.payload.filter((e)=>{return ids.indexOf(e.id) < 0});\n" +
                    "    // console.log(newRecords);\n" +
                    "    const personList = !state.personList? [...action.payload]:[...state.personList,...newRecords]\n" +
                    "\n" +
                    "    return {\n" +
                    "        ...state, \n" +
                    "        personList: [\n" +
                    "            ...personList\n" +
                    "        ], loading: false\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "const viewPerson = (state, action) => {\n" +
                    "    return {\n" +
                    "        ...state,\n" +
                    "        action: action.type,\n" +
                    "        person: {\n" +
                    "            ...action.payload\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "const newPerson = (state, action) => {\n" +
                    "    return {\n" +
                    "        ...state,\n" +
                    "        action: action.type,\n" +
                    "        person: {\n" +
                    "            id: 0,\n" +
                    "            firstname: \"\",\n" +
                    "            lastname: \"\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "const updatePerson = (state, action) => {\n" +
                    "    return {\n" +
                    "        ...state,\n" +
                    "        loading: false,\n" +
                    "        action: action.type,\n" +
                    "        person: {...action.payload},\n" +
                    "        personList: state.personList.map(\n" +
                    "            p => p.id === action.payload.id \n" +
                    "            ? p= action.payload\n" +
                    "            : p\n" +
                    "        )\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "const addPerson = (state, action) => {\n" +
                    "    return {\n" +
                    "        ...state,\n" +
                    "        action: action.type,\n" +
                    "        loading: true,\n" +
                    "        person: {...action.payload},\n" +
                    "        personList: [\n" +
                    "            action.payload,...state.personList\n" +
                    "        ], \n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "const deletePerson = (state, action) => {\n" +
                    "    // console.log(action.payload)\n" +
                    "    const newPersonList = [...state.personList];\n" +
                    "    const index = newPersonList.findIndex(p=>p.id === action.payload.id);\n" +
                    "    newPersonList.splice(index,1);\n" +
                    "    return {\n" +
                    "        ...state,\n" +
                    "        action: action.type,\n" +
                    "        loading: true,\n" +
                    "        personList:newPersonList,\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "const reducer = (state = initialState, action) => {\n" +
                    "    switch (action.type) {\n" +
                    "        case PERSON_ACTIONS.UPDATE_PERSON_START:\n" +
                    "        case PERSON_ACTIONS.ADD_PERSON_START:\n" +
                    "        case PERSON_ACTIONS.FETCH_PERSON_START:\n" +
                    "        case PERSON_ACTIONS.DELETE_PERSON_START:\n" +
                    "            return processStart(state, action);\n" +
                    "\n" +
                    "        case PERSON_ACTIONS.UPDATE_PERSON_FAIL:\n" +
                    "        case PERSON_ACTIONS.ADD_PERSON_FAIL:\n" +
                    "        case PERSON_ACTIONS.FETCH_PERSON_FAIL:\n" +
                    "        case PERSON_ACTIONS.DELETE_PERSON_FAIL:\n" +
                    "                return processFail(state, action); \n" +
                    "\n" +
                    "        case PERSON_ACTIONS.FETCH_PERSON_SUCCESS:\n" +
                    "            return fetchPersonListSuccess(state, action);\n" +
                    "        case PERSON_ACTIONS.VIEW_PERSON:\n" +
                    "        case PERSON_ACTIONS.EDIT_PERSON:\n" +
                    "            return viewPerson(state, action);\n" +
                    "        case PERSON_ACTIONS.NEW_PERSON:\n" +
                    "            return newPerson(state, action);\n" +
                    "        case PERSON_ACTIONS.ADD_PERSON_SUCCESS:\n" +
                    "            return addPerson(state, action);\n" +
                    "        case PERSON_ACTIONS.UPDATE_PERSON_SUCCESS:\n" +
                    "            return updatePerson(state, action);\n" +
                    "        case PERSON_ACTIONS.DELETE_PERSON_SUCCESS:\n" +
                    "            return deletePerson(state, action);\n" +
                    "        default:\n" +
                    "            return state;\n" +
                    "    }\n" +
                    "}\n" +
                    "export default reducer;"
        assertEquals(expected,reducer)
    }

    @Test
    fun generateComponentTableHeaderTest() {
        val componentHeader = GenerateReactJSTable.generateComponentTableHeader("person2")
//        println(componentHeader)
        val expected =
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
                            "import './Person2Table.css'\n" +
                            "import dayjs from 'dayjs';\n" +
                            "import numeral from 'numeral';\n" +
                            "import  { Constants } from '../../../utilities';\n" +
                            "\n" +
                            "import { PERSON2_ACTIONS } from '../../../actions/person2_types';\n" +
                            "import { getPerson2, viewPerson2, editPerson2, newPerson2, deletePerson2, notifyInfo, notifyError, notifyWarning } from '../../../actions'\n" +
                            "\n" +
                            "\n" +
                            "/**\n" +
                            " * This uses https://react-bootstrap-table.github.io/react-bootstrap-table2/docs/getting-started.html\n" +
                            " */\n" +
                            "\n" +
                            "export class Person2Table extends Component {\n" +
                            "    constructor(props){\n" +
                            "        super(props);\n" +
                            "        this.searchText = React.createRef();\n" +
                            "    }\n" +
                            "    state = {\n" +
                            "        action: null,\n" +
                            "        showConfirmModal: false,\n" +
                            "        confirmModalHeader: \"\",\n" +
                            "        confirmModalBody:\"\", \n" +
                            "        person2:null\n" +
                            "    }\n" +
                            "    componentDidMount() {\n" +
                            "        this.props.getPerson2();\n" +
                            "    }\n" +
                            "    deleteHandle = (data) =>{\n" +
                            "        // console.log(data);\n" +
                            "        this.setState({showConfirmModal: true,\n" +
                            "            person2:data,\n" +
                            "            confirmModalHeader: 'Delete record ?',\n" +
                            "            confirmModalBody: `ID: \${data.id}`});\n" +
                            "    }\n" +
                            "    onConfirmDelete=(confirmDelete)=>{\n" +
                            "        this.setState({showConfirmModal: false});\n" +
                            "        const {person2} = this.state;\n" +
                            "        if(confirmDelete) {\n" +
                            "            this.props.deletePerson2(person2);\n" +
                            "            this.setState({person2:null});\n" +
                            "        }\n" +
                            "    }\n" +
                            "    addHandle = () =>{\n" +
                            "        // console.log(data);\n" +
                            "        this.props.newPerson2();\n" +
                            "        this.setState({action: PERSON2_ACTIONS.NEW_PERSON2});\n" +
                            "    }\n" +
                            "    editHandle = (data) =>{\n" +
                            "        // console.log(data);\n" +
                            "        this.props.editPerson2(data);\n" +
                            "        this.setState({action: PERSON2_ACTIONS.EDIT_PERSON2});\n" +
                            "    }\n" +
                            "    viewHandle = (data) =>{\n" +
                            "        //  console.log(data);\n" +
                            "        this.props.viewPerson2(data);\n" +
                            "        this.setState({action: PERSON2_ACTIONS.VIEW_PERSON2});\n" +
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
                            "        const {person2List} = this.props;\n" +
                            "        const totalPages = Math.round(person2List.length / sizePerPage);\n" +
                            "        const isFetchingMorePages = page >= totalPages  ? true: false; \n" +
                            "        // console.log('totalPages', totalPages,'isFetchingMorePages',isFetchingMorePages )\n" +
                            "        if(isFetchingMorePages){\n" +
                            "            this.props.getPerson2(page+1,sizePerPage);\n" +
                            "        }\n" +
                            "    }\n" +
                            "    \n" +
                            "    onSearch = ()=>{\n" +
                            "        const searchText = this.searchText.current.props.searchText;\n" +
                            "        this.props.getPerson2(1, Constants.itemsPerPage, searchText);\n" +
                            "    }\n" +
                            "    onClearSearch=() =>{\n" +
                            "        console.log(\"Clear Search\");\n" +
                            "        this.props.getPerson2();\n" +
                            "    }\n" +
                            "\n" +
                            "    render() {\n" +
                            "        const person2List = this.props.person2List;\n" +
                            "        if (!person2List) {\n" +
                            "            return null\n" +
                            "        }\n" +
                            "        // console.log(person2List);\n" +
                            "\n" +
                            "        const {action} = this.state\n" +
                            "        let redirect ='';\n" +
                            "        switch(action) {\n" +
                            "            case PERSON2_ACTIONS.NEW_PERSON2: \n" +
                            "                redirect = <Redirect push to={'/person2Edit'}/>\n" +
                            "                break;\n" +
                            "            case PERSON2_ACTIONS.EDIT_PERSON2: \n" +
                            "                redirect = <Redirect push to={'/person2Edit'}/>\n" +
                            "                break;\n" +
                            "            case PERSON2_ACTIONS.VIEW_PERSON2: \n" +
                            "                redirect = <Redirect push to={'/person2Edit'}/>\n" +
                            "                break;\n" +
                            "            case PERSON2_ACTIONS.DELETE_PERSON2: \n" +
                            "                redirect = <Redirect push to={'/person2Delete'}/>\n" +
                            "                break;\n" +
                            "            default: \n" +
                            "                redirect =null;\n" +
                            "                break;\n" +
                            "        }\n"
        assertEquals(expected,componentHeader)
    }

    @Test
    fun generateComponentTableFieldsTest() {
        val typeList = getTypeList()

        val componentFields = GenerateReactJSTable.generateComponentTableFields(typeList)
//        println(componentFields)

        val expected =
            "const columns = [\n" +
                    "            { dataField: 'id', text: 'Id', sort: true },\n" +
                    "            { dataField: 'firstname', text: 'Firstname', sort: true },\n" +
                    "            { dataField: 'lastname', text: 'Lastname', sort: true },\n" +
                    "            { dataField: 'birthdate', text: 'Birthdate', sort: true },\n" +
                    "            { dataField: 'ismodified', text: 'ismodified', sort: true,\n" +
                    "            formatter: (cell, row)=>{return ( \n" +
                    "                <>\n" +
                    "                <div className=\"checkbox disabled text-center\">\n" +
                    "                <label  >\n" +
                    "                    <input type=\"checkbox\" checked={row.ismodified} disabled/>\n" +
                    "                </label>\n" +
                    "                </div>\n" +
                    "                </>\n" +
                    "            )}, \n" +
                    "            },\n" +
                    "        \n" +
                    "            \n" +
                    "            { dataField: 'timestamp', text: 'timestamp', sort: true,\n" +
                    "                formatter: (cell, row)=> { return (\n" +
                    "                    <>\n" +
                    "                    <div className='text-center'>\n" +
                    "                        {dayjs(row.timestamp).format('DD/MM/YYYY HH:mm:ss')}\n" +
                    "                    </div>\n" +
                    "                    </>\n" +
                    "                )}\n" +
                    "            },\n" +
                    "            { dataField: 'salary', text: 'salary', sort: true, \n" +
                    "                formatter: (cell, row)=> {return (\n" +
                    "                    <>\n" +
                    "                    <div className='text-right'>\n" +
                    "                        {numeral(row.salary).format('\$0,0.00')}\n" +
                    "                    </div>\n" +
                    "                    </>\n" +
                    "                )}\n" +
                    "\n" +
                    "            },\n"
        assertEquals(expected,componentFields)
    }

    @Test
    fun generateComponentTableTrailerTest() {
        val component = GenerateReactJSTable.generateComponentTableTrailer("person2")
//        println(component)
        val expected=
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
                    "                    <h1>List Person2</h1>\n" +
                    "                     <ToolkitProvider\n" +
                    "                        bootstrap4\n" +
                    "                        keyField='id'\n" +
                    "                        data={person2List}\n" +
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
                    "    person2List: state.person2Reducer.person2List,\n" +
                    "    loading: state.person2Reducer.loading,\n" +
                    "    action: state.person2Reducer.action,\n" +
                    "})\n" +
                    "\n" +
                    "export default connect(mapStateToProps, { getPerson2, viewPerson2, editPerson2, newPerson2, deletePerson2, notifyInfo, notifyError, notifyWarning  })(Person2Table);\n"
        assertEquals(expected, component)

    }

    @Test
    fun generateComponentEditTest() {
        val typeName = "person2"
        val typeList = getTypeList()
        val pathEdit = "$PATH_DESTINATION_REACTJS/$COMPONENTS/${typeName.capitalize()}/${typeName.capitalize()}Edit"
        File(pathEdit).mkdirs()

        // generate index file
        val indexFileName = "$pathEdit/index.js"
        val indexEdit =GenerateReactJSEdit.generatedEditIndex(typeName)
        File(indexFileName).writeText(indexEdit)

        // generate edit file
        val sb = StringBuilder()
        sb.appendln(GenerateReactJSEdit.generatedImport(typeName))
        sb.appendln(GenerateReactJSEdit.generateConstructor(typeList))
        sb.appendln(GenerateReactJSEdit.generateComponentDidMount(typeName,typeList))
        sb.appendln(GenerateReactJSEdit.generateComponentHandleSubmit(typeName,typeList))
        sb.appendln(GenerateReactJSEdit.generateComponentRender(typeName,typeList))
        sb.appendln(GenerateReactJSEdit.generateComponentForm(typeName,typeList))
        val edit = sb.toString()
        val editFileName = "$pathEdit/${typeName.capitalize()}Edit.js"
        File(editFileName).writeText(edit)
    }

    @Test
    fun generateComponentTableTest() {
        val typeName = "person2"
        val typeList = getTypeList()
        val pathEdit = "$PATH_DESTINATION_REACTJS/$COMPONENTS/${typeName.capitalize()}/${typeName.capitalize()}Edit"
        File(pathEdit).mkdirs()

        // generate table file
        val pathTable = "$PATH_DESTINATION_REACTJS/$COMPONENTS/${typeName.capitalize()}/${typeName.capitalize()}Table"
        File(pathTable).mkdirs()

        // generate index file
        val indexTableFileName = "$pathTable/index.js"
        val indexTable =GenerateReactJSTable.generateTableIndex(typeName)
        File(indexTableFileName).writeText(indexTable)

        val sbt = StringBuilder()
        sbt.appendln( GenerateReactJSTable.generateComponentTableHeader(typeName))
        sbt.appendln(GenerateReactJSTable.generateComponentTableFields(typeList))
        sbt.appendln(GenerateReactJSTable.generateComponentTableTrailer(typeName))

        val table = sbt.toString()
        val tableFileName =  "$pathTable/${typeName.capitalize()}Table.js"
        File(tableFileName).writeText(table)

        val css = GenerateReactJSTable.generateTableCss()
        val cssFileName ="$pathTable/${typeName.capitalize()}Table.css"
        File(cssFileName).writeText(css)
    }

    @Test
    fun generateReducerIndexTest() {
        val classNames = arrayListOf<String>("person", "person2")
        val reducerIndex = GenerateReactJSReducers.generateReducerIndex(classNames)
//        println(reducerIndex)

        val expected =
            "import {combineReducers} from 'redux';\n" +
                    "import notifyMessageReducer from './notifyMessageReducer';\n" +
                    "import PersonReducer from './PersonReducer';\n" +
                    "import Person2Reducer from './Person2Reducer';\n" +
                    "export default combineReducers({\n" +
                    "    notifyMessageReducer: notifyMessageReducer,\n" +
                    "    personReducer: PersonReducer,\n" +
                    "    person2Reducer: Person2Reducer,\n" +
                    "});\n"
        assertEquals(expected,reducerIndex)
    }

    @Test
    fun generateApiEndpointsTest() {
        val classNames = arrayListOf<String>("person", "person2")
        val endpoint = GenerateReactJSCommon.generateApiEndpoints(classNames)
        //println(endpoint)
        val expected =
            "export const ApiEndpoints= {\n" +
                    "    person: '/person',\n" +
                    "    person2: '/person2',\n" +
                    "}\n"
        assertEquals(expected,endpoint)
    }

    @Test
    fun generateConstantsTest() {
        val classNames = arrayListOf<String>("person", "person2")
        val constants = GenerateReactJSCommon.generateConstants(classNames)
//        println(constants)

        val expected =
            "export const Constants= {\n" +
                    "    itemsPerPage: 50,\n" +
                    "    typesList: ['person','person2']\n" +
                    "}\n"
        assertEquals(expected, constants)

    }

    @Test
    fun generateLayoutTest() {
        val classNames = arrayListOf<String>("person", "person2")
        val layout = GenerateReactJSCommon.generateLayout(classNames)
//        println(layout)

        val expected =
            "import React, { Component } from 'react';\n" +
                    "import TopNav from '../TopNav';\n" +
                    "import Footer from '../Footer';\n" +
                    "import { Route, Switch } from 'react-router-dom';\n" +
                    "import { connect } from 'react-redux';\n" +
                    "import { NotifyMessage } from '../NotifyMessage/NotifyMessage';\n" +
                    "import { notifyClear } from '../../actions';\n" +
                    "import LeftSide from '../LeftSide/LeftSide';\n" +
                    "import { Row, Col, } from 'react-bootstrap';\n" +
                    "import PersonEdit from '../Person/PersonEdit';\n" +
                    "import PersonTable from '../Person/PersonTable';\n" +
                    "import Person2Edit from '../Person2/Person2Edit';\n" +
                    "import Person2Table from '../Person2/Person2Table';\n" +
                    "\n" +
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
                    "                         <Switch>\n" +
                    "                            <Route path='/' exact component={PersonTable} />\n" +
                    "                            <Route path='/personTable' exact component={PersonTable} />\n" +
                    "                            <Route path='/personEdit' exact component={PersonEdit} onClose={this.onClose} notifyMessage={notifyMessage} />\n" +
                    "\n" +
                    "                            <Route path='/person2Table' exact component={Person2Table} />\n" +
                    "                            <Route path='/person2Edit' exact component={Person2Edit} onClose={this.onClose} notifyMessage={notifyMessage} />\n" +
                    "\n" +
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
                    "export default connect(mapStateToProps, { notifyClear })(Layout);\n"
        assertEquals(expected,layout)
    }


    @Test
    fun generateReactJSTest() {
        GenerateReactJS.copyFiles(PATH_DESTINATION_REACTJS)
        GenerateReactJS.generateReactJS(FILE_NAME_PERSON1,"person","", PATH_DESTINATION_REACTJS)
        GenerateReactJS.generateReactJS(FILE_NAME_PERSON2,"person2","", PATH_DESTINATION_REACTJS)
        GenerateReactJS.generateActionReducerIndexFiles(PATH_DESTINATION_REACTJS, arrayListOf("person","person2"))
//        GenerateReactJS.generateActionReducerIndex(PATH_DESTINATION_REACTJS, arrayListOf())
    }

    @Test
    fun generateReactJSFilesTest(){
        GenerateReactJS.generateReactJSFiles(targetDescriptor = DESCRIPTOR_FILES, targetFolder = PATH_DESTINATION_REACTJS)
    }

    companion object {
        const val PATH_DESTINATION_REACTJS = "/Users/luizsilva/Projects/Research/TechnicalContent/KotlinUtilities/KotlinTests/src/main/kotlin/generator/reactjs"
        const val COMPONENTS = "/src/components"
        const val FILE_NAME_PERSON1 = "./src/test/resources/Descriptors/person1.txt"
        const val FILE_NAME_PERSON2 = "./src/test/resources/Descriptors/person2.txt"
        const val DESCRIPTOR_FILES = "./src/test/resources/Descriptors"
    }
}
