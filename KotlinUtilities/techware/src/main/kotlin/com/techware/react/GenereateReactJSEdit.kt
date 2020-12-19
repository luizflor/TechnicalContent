package com.techware.react

import com.techware.utilities.Type
import java.lang.StringBuilder

class GenerateReactJSEdit {
    companion object {
        fun generatedImport(typeName: String): String {
            val component =
                "import React, { Component } from 'react'\n" +
                        "import { Link } from 'react-router-dom'\n" +
                        "import { connect } from 'react-redux';\n" +
                        "import { Container, Row, Col, Form, Button } from 'react-bootstrap';\n" +
                        "import { getInputChange } from '../../../utilities';\n" +
                        "import dayjs from 'dayjs';\n" +
                        "import numeral from 'numeral';\n" +
                        "import { update${typeName.capitalize()}, add${typeName.capitalize()} } from '../../../actions'\n" +
                        "import { ${typeName.toUpperCase()}_ACTIONS } from '../../../actions/${typeName}_types';\n" +
                        "\n" +
                        "\n" +
                        "export class ${typeName.capitalize()}Edit extends Component {"
            return component
        }

        fun generateConstructor(fieldTypes:List<Type>): String {
            val sb =StringBuilder()
            sb.appendln("    constructor(props) {\n" +
                    "        super(props)\n" +
                    "        this.state = {\n" +
                    "            changing: false,\n" +
                    "            validate: false,")
            fieldTypes.map {
                sb.appendln("            ${it.fieldName}:'',")
            }
            sb.appendln("        }\n" +
                    "    }\n")
            return sb.toString()
        }

        fun generateComponentDidMount(typeName: String, fieldTypes:List<Type>): String {
            val sb =StringBuilder()
            sb.appendln("    componentDidMount() {\n"+
                    "        const { ${typeName} } = this.props;\n" +
                    "        // console.log(${typeName});\n" +
                    "        if (${typeName}) {\n" +
                    "            this.setState({")

            fieldTypes.map {
                sb.appendln("            ${it.fieldName}: ${typeName}.${it.fieldName},")
            }
            sb.appendln("            })\n" +
                    "        }\n" +
                    "    }")

            return sb.toString()
        }

        fun getFieldList(typeList:List<Type>): String {
            val sbFieldsList = StringBuilder()
            typeList.mapIndexed{index, e ->
                if(index < typeList.size-1) {
                    sbFieldsList.append("${e.fieldName}, ")
                } else {
                    sbFieldsList.append("${e.fieldName}")
                }
            }
            return sbFieldsList.toString()
        }

        fun generateComponentHandleSubmit(typeName: String, fieldTypes:List<Type>): String {
            val sb =StringBuilder()
            sb.appendln("    handleInputChange = (eventData) => {\n" +
                    "        this.setState(getInputChange(eventData));\n" +
                    "        this.setState({changing: true});\n" +
                    "    }\n" +
                    "\n" +
                    "    handleSubmit = (event) => {\n" +
                    "        // console.log(event);\n" +
                    "        const form = event.currentTarget;\n" +
                    "        if (form.checkValidity() === false) {\n" +
                    "          event.preventDefault();\n" +
                    "          event.stopPropagation();\n" +
                    "        }\n" +
                    "\n" +
                    "        this.setState({validate: true, changing: false})\n" +
                    "\n" +
                    "        event.preventDefault();")

            val fieldList = getFieldList(fieldTypes)
            sb.appendln("        const { ${fieldList} } = this.state;" )
            sb.appendln("        if ( ${fieldList.replace(","," &&")} ) {")
            sb.appendln("            const new${typeName.capitalize()} = {")
            sb.appendln("               $fieldList")
            sb.appendln("            }")

            sb.appendln("            const {action} = this.props;\n" +
                    "            if(action === ${typeName.toUpperCase()}_ACTIONS.EDIT_${typeName.toUpperCase()}) {\n" +
                    "                this.props.update${typeName.capitalize()}(new${typeName.capitalize()});\n" +
                    "            } else {\n" +
                    "                this.props.add${typeName.capitalize()}(new${typeName.capitalize()});\n" +
                    "            }\n" +
                    "        }\n" +
                    "      };")

            return sb.toString()
        }

        fun generateComponentRender(typeName: String, fieldTypes:List<Type>): String {
            val sb = StringBuilder()
            sb.appendln("    render() {\n" +
                    "        const { ${typeName}} = this.props;\n" +
                    "        if(!${typeName}) return null;")



            sb.appendln("        const {action} = this.props;\n" +
                    "        //  console.log('action', action, this.state.changing )\n" +
                    "        let buttons = <Link className=\"btn btn-success btn-block\" to=\"/\">OK</Link>\n" +
                    "        if (action === ${typeName.toUpperCase()}_ACTIONS.NEW_${typeName.toUpperCase()} ||\n" +
                    "             action === ${typeName.toUpperCase()}_ACTIONS.EDIT_${typeName.toUpperCase()} ||\n" +
                    "             action === ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_SUCCESS ||\n" +
                    "             action === ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_SUCCESS){\n" +
                    "            buttons =\n" +
                    "            <>\n" +
                    "                <Link className=\"btn btn-warning mr-3\" to=\"/\">Cancel</Link>\n" +
                    "                <Button variant=\"success\" type=\"submit\">\n" +
                    "                    Save Changes\n" +
                    "                </Button>\n" +
                    "            </>\n" +
                    "        }\n" +
                    "\n" +
                    "        let readOnly = false;\n" +
                    "        let spanOffset = { span: 3, offset: 6 }\n" +
                    "        let title =\"View\";\n" +
                    "        switch(action) {\n" +
                    "            case ${typeName.toUpperCase()}_ACTIONS.VIEW_${typeName.toUpperCase()}: \n" +
                    "                title = \"View\"; \n" +
                    "                readOnly = true;\n" +
                    "                spanOffset = { span: 3, offset: 6 };\n" +
                    "                break;\n" +
                    "            case ${typeName.toUpperCase()}_ACTIONS.UPDATE_${typeName.toUpperCase()}_SUCCESS:\n" +
                    "            case ${typeName.toUpperCase()}_ACTIONS.EDIT_${typeName.toUpperCase()}:\n" +
                    "                title = \"Edit\"; \n" +
                    "                readOnly = false;\n" +
                    "                spanOffset = { span: 3, offset: 9 };\n" +
                    "\n" +
                    "                break;\n" +
                    "\n" +
                    "            case ${typeName.toUpperCase()}_ACTIONS.ADD_${typeName.toUpperCase()}_SUCCESS:\n" +
                    "            case ${typeName.toUpperCase()}_ACTIONS.NEW_${typeName.toUpperCase()}: \n" +
                    "                title = \"New\"; \n" +
                    "                readOnly = false;\n" +
                    "                spanOffset = { span: 3, offset: 9 };\n" +
                    "\n" +
                    "                break;\n" +
                    "            default: break;\n" +
                    "        }")

            fieldTypes.map {
                when (it.fieldType) {
                    "string"-> {
                        sb.appendln("        const ${it.fieldName} = this.state.changing ?   this.state.${it.fieldName}: this.props.${typeName}.${it.fieldName};")
                    }
                    "date"->{
                        sb.appendln("        const ${it.fieldName} = this.state.changing \n" +
                                "            ? dayjs(this.state.${it.fieldName}).format('YYYY-MM-DD')\n" +
                                "            : dayjs(this.props.${typeName}.${it.fieldName}).format('YYYY-MM-DD')")

                    }
                    "boolean"->{
                        sb.appendln("        const ${it.fieldName} = this.state.changing ?   this.state.${it.fieldName}: this.props.${typeName}.${it.fieldName};")
                    }
                    "instant"->{
                        sb.appendln("        const ${it.fieldName} = this.state.changing \n" +
                                "            ? dayjs(this.state.${it.fieldName}).format('DD/MM/YYYY HH:mm:ss')\n" +
                                "            : dayjs(this.props.${typeName}.${it.fieldName}).format('DD/MM/YYYY HH:mm:ss')")
                    }
                    "double"->{
                        sb.appendln("        const salary =  !readOnly ? this.state.${it.fieldName} : numeral(this.props.${typeName}.${it.fieldName}).format('\$0,0.00');")
                    }
                    else ->{
                        sb.appendln("        const ${it.fieldName} = this.state.changing ?   this.state.${it.fieldName}: this.props.${typeName}.${it.fieldName};")
                    }
                }
            }



            return sb.toString()
        }

        fun generateComponentForm(typeName: String, fieldTypes:List<Type>): String {
            val sb = StringBuilder()
            sb.appendln("        return (\n" +
                    "            <>\n" +
                    "                <Container>\n" +
                    "                    <h1>{title} ${typeName.capitalize()}</h1>\n" +
                    "                    <Form noValidate validated={this.state.validate} onSubmit={this.handleSubmit}>\n" +
                    "                        <Form.Row>")

            fieldTypes.mapIndexed{i,e->
                if(e.fieldName == "id") {
                    sb.appendln("                            <Form.Group as={Col} controlId=\"formGridid\">\n" +
                            "                                <Form.Label className=\"font-weight-bold\">id</Form.Label>\n" +
                            "                                <Form.Control type=\"text\" placeholder=\"id\" name=\"id\" value={id} readOnly plaintext />\n" +
                            "                            </Form.Group>")
                } else  {
                    var controlType = "text"
                    when (e.fieldType) {
                        "boolean"-> controlType = "checkbox"
                        "date" -> controlType="date"
                        else -> controlType="text"
                    }

                    sb.appendln("                            <Form.Group as={Col} controlId=\"formGrid${e.fieldName}\">\n" +
                            "                                <Form.Label className=\"font-weight-bold\">${e.fieldName}</Form.Label>\n" +
                            "                                <Form.Control name=\"${e.fieldName}\" type=\"${controlType}\" placeholder=\"${e.fieldName}\" value={${e.fieldName}} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                            "                                <Form.Control.Feedback type=\"invalid\">\n" +
                            "                                  Please enter ${e.fieldName}.\n" +
                            "                                </Form.Control.Feedback>\n" +
                            "                            </Form.Group>")
                    if (i.rem(3) == 0 ) {
                       sb.append("                        </Form.Row>\n")
                        if(i < fieldTypes.size - 1) {
                            sb.appendln("                        <Form.Row>")
                        } else {}
                    }
                    else { }
                }
            }

            if(fieldTypes.size.rem(3)==0) {
                sb.append("                        </Form.Row>\n")
            }
            sb.appendln("                        <Row>\n" +
                    "                            <Col md={spanOffset} className=\"text-right\">\n" +
                    "                                {buttons}\n" +
                    "                            </Col>\n" +
                    "                        </Row>\n" +
                    "                    </Form>\n" +
                    "                </Container>\n" +
                    "            </>\n" +
                    "        )\n" +
                    "    }\n" +
                    "}\n" +
                    "const mapStateToProps = state => {\n" +
                    "    return {\n" +
                    "        ${typeName}: state.${typeName}Reducer.${typeName},\n" +
                    "        loading: state.${typeName}Reducer.loading,\n" +
                    "        action: state.${typeName}Reducer.action,\n" +
                    "\n" +
                    "}}\n" +
                    "export default connect(mapStateToProps, {update${typeName.capitalize()}, add${typeName.capitalize()}})(${typeName.capitalize()}Edit);\n")



            return sb.toString()
        }
    }
}