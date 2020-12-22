package com.techware.react

import com.techware.generate.Type
import java.io.File
import java.lang.StringBuilder

class GenerateReactJSTable {
    companion object {

        fun generateReactJSTable(className: String, targetFolder: String ,descriptorList: List<Type>) {
            // generate table file
            val pathTable = "$targetFolder/${ReactJSConstants.COMPONENTS}/${className.capitalize()}/${className.capitalize()}Table"
            File(pathTable).mkdirs()

            // generate index file
            val indexTableFileName = "$pathTable/index.js"
            val indexTable =GenerateReactJSTable.generateTableIndex(className)
            File(indexTableFileName).writeText(indexTable)

            val sbt = StringBuilder()
            sbt.appendln( GenerateReactJSTable.generateComponentTableHeader(className))
            sbt.appendln(GenerateReactJSTable.generateComponentTableFields(descriptorList))
            sbt.appendln(GenerateReactJSTable.generateComponentTableTrailer(className))

            val table = sbt.toString()
            val tableFileName =  "$pathTable/${className.capitalize()}Table.js"
            File(tableFileName).writeText(table)

            val css = GenerateReactJSTable.generateTableCss()
            val cssFileName ="$pathTable/${className.capitalize()}Table.css"
            File(cssFileName).writeText(css)
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

    }
}