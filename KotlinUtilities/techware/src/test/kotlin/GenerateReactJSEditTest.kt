import com.techware.react.GenerateReactJSEdit
import com.techware.utilities.Descriptor
import com.techware.utilities.Type
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class GenerateReactJSEditTest {
    @Test
    fun generatedImportTest() {
        val imports = GenerateReactJSEdit.generatedImport("person")
//        println(imports)
        val expected =
            "import React, { Component } from 'react'\n" +
                    "import { Link } from 'react-router-dom'\n" +
                    "import { connect } from 'react-redux';\n" +
                    "import { Container, Row, Col, Form, Button } from 'react-bootstrap';\n" +
                    "import { getInputChange } from '../../../utilities';\n" +
                    "import dayjs from 'dayjs';\n" +
                    "import numeral from 'numeral';\n" +
                    "import { updatePerson, addPerson } from '../../../actions'\n" +
                    "import { PERSON_ACTIONS } from '../../../actions/person_types';\n" +
                    "\n" +
                    "\n" +
                    "export class PersonEdit extends Component {"
        assertEquals(expected, imports)
    }

    fun getTypeListPerson2(): List<Type> {
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

    fun getTypeListPerson(): List<Type> {
        val descriptorPersonStr="id, int\n" +
                "firstname, string\n" +
                "lastname, string"
        val typeList = Descriptor.Companion.convertDescriptorToTypeList(descriptorPersonStr)
        return typeList
    }

    @Test
    fun generateConstructorTest() {
        val typeList = getTypeListPerson2()

        val constructor = GenerateReactJSEdit.generateConstructor(typeList)
//        println(constructor)
        val expected =
            "    constructor(props) {\n" +
                    "        super(props)\n" +
                    "        this.state = {\n" +
                    "            changing: false,\n" +
                    "            validate: false,\n" +
                    "            id:'',\n" +
                    "            firstname:'',\n" +
                    "            lastname:'',\n" +
                    "            birthdate:'',\n" +
                    "            ismodified:'',\n" +
                    "            timestamp:'',\n" +
                    "            salary:'',\n" +
                    "        }\n" +
                    "    }\n\n"
        assertEquals(expected, constructor)
    }
    @Test
    fun generateComponentDidMountTest() {
        val typeList = getTypeListPerson2()

        val componentDidMount = GenerateReactJSEdit.generateComponentDidMount("person", typeList)
//        println(componentDidMount)
        val expected =
            "    componentDidMount() {\n" +
                    "        const { person } = this.props;\n" +
                    "        // console.log(person);\n" +
                    "        if (person) {\n" +
                    "            this.setState({\n" +
                    "            id: person.id,\n" +
                    "            firstname: person.firstname,\n" +
                    "            lastname: person.lastname,\n" +
                    "            birthdate: person.birthdate,\n" +
                    "            ismodified: person.ismodified,\n" +
                    "            timestamp: person.timestamp,\n" +
                    "            salary: person.salary,\n" +
                    "            })\n" +
                    "        }\n" +
                    "    }\n"
        assertEquals(expected,componentDidMount)

    }

    @Test
    fun getFieldListTest() {
        val typeList = getTypeListPerson2()
        val fieldList = GenerateReactJSEdit.getFieldList(typeList)
//        println(fieldList)
        val expected = "id, firstname, lastname, birthdate, ismodified, timestamp, salary"
        assertEquals(expected,fieldList)
    }

    @Test
    fun generateComponentHandleSubmitTest(){
        val typeList = getTypeListPerson2()
        val submit = GenerateReactJSEdit.generateComponentHandleSubmit("person", typeList)
//        println(submit)
        val expected =
            "    handleInputChange = (eventData) => {\n" +
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
                    "        event.preventDefault();\n" +
                    "        const { id, firstname, lastname, birthdate, ismodified, timestamp, salary } = this.state;\n" +
                    "        if ( id && firstname && lastname && birthdate && ismodified && timestamp && salary ) {\n" +
                    "            const newPerson = {\n" +
                    "               id, firstname, lastname, birthdate, ismodified, timestamp, salary\n" +
                    "            }\n" +
                    "            const {action} = this.props;\n" +
                    "            if(action === PERSON_ACTIONS.EDIT_PERSON) {\n" +
                    "                this.props.updatePerson(newPerson);\n" +
                    "            } else {\n" +
                    "                this.props.addPerson(newPerson);\n" +
                    "            }\n" +
                    "        }\n" +
                    "      };\n"
        assertEquals(expected,submit)
    }

    @Test
    fun generateComponentRenderTest() {
        val typeList = getTypeListPerson2()
        val render = GenerateReactJSEdit.generateComponentRender("person", typeList)
        println(render)

        val expected =
            "    render() {\n" +
                    "        const { person} = this.props;\n" +
                    "        if(!person) return null;\n" +
                    "        const {action} = this.props;\n" +
                    "        //  console.log('action', action, this.state.changing )\n" +
                    "        let buttons = <Link className=\"btn btn-success btn-block\" to=\"/\">OK</Link>\n" +
                    "        if (action === PERSON_ACTIONS.NEW_PERSON ||\n" +
                    "             action === PERSON_ACTIONS.EDIT_PERSON ||\n" +
                    "             action === PERSON_ACTIONS.UPDATE_PERSON_SUCCESS ||\n" +
                    "             action === PERSON_ACTIONS.ADD_PERSON_SUCCESS){\n" +
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
                    "            case PERSON_ACTIONS.VIEW_PERSON: \n" +
                    "                title = \"View\"; \n" +
                    "                readOnly = true;\n" +
                    "                spanOffset = { span: 3, offset: 6 };\n" +
                    "                break;\n" +
                    "            case PERSON_ACTIONS.UPDATE_PERSON_SUCCESS:\n" +
                    "            case PERSON_ACTIONS.EDIT_PERSON:\n" +
                    "                title = \"Edit\"; \n" +
                    "                readOnly = false;\n" +
                    "                spanOffset = { span: 3, offset: 9 };\n" +
                    "\n" +
                    "                break;\n" +
                    "\n" +
                    "            case PERSON_ACTIONS.ADD_PERSON_SUCCESS:\n" +
                    "            case PERSON_ACTIONS.NEW_PERSON: \n" +
                    "                title = \"New\"; \n" +
                    "                readOnly = false;\n" +
                    "                spanOffset = { span: 3, offset: 9 };\n" +
                    "\n" +
                    "                break;\n" +
                    "            default: break;\n" +
                    "        }\n" +
                    "        const id = this.state.changing ?   this.state.person.id: this.props.person.id;\n" +
                    "        const firstname = this.state.changing ?   this.state.person.firstname: this.props.person.firstname;\n" +
                    "        const lastname = this.state.changing ?   this.state.person.lastname: this.props.person.lastname;\n" +
                    "        const birthdate = this.state.changing \n" +
                    "            ? dayjs(this.state.birthdate).format('YYYY-MM-DD')\n" +
                    "            : dayjs(this.props.person.birthdate).format('YYYY-MM-DD')\n" +
                    "        const ismodified = this.state.changing ?   this.state.person.ismodified: this.props.person.ismodified;\n" +
                    "        const timestamp = this.state.changing \n" +
                    "            ? dayjs(this.state.timestamp).format('DD/MM/YYYY HH:mm:ss')\n" +
                    "            : dayjs(this.props.person.timestamp).format('DD/MM/YYYY HH:mm:ss')\n" +
                    "        const salary =  !readOnly ? this.state.salary : numeral(this.props.person.salary).format('\$0,0.00');\n"

        assertEquals(expected, render)

    }
    @Test
    fun generateComponentFormPerson2Test() {
        val typeList = getTypeListPerson2()
        val component = GenerateReactJSEdit.generateComponentForm("person", typeList)
//        println(component)

        val expected =
            "        return (\n" +
                    "            <>\n" +
                    "                <Container>\n" +
                    "                    <h1>{title} Person</h1>\n" +
                    "                    <Form noValidate validated={this.state.validate} onSubmit={this.handleSubmit}>\n" +
                    "                        <Form.Row>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridid\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">id</Form.Label>\n" +
                    "                                <Form.Control type=\"text\" placeholder=\"id\" name=\"id\" value={id} readOnly plaintext />\n" +
                    "                            </Form.Group>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridfirstname\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">firstname</Form.Label>\n" +
                    "                                <Form.Control name=\"firstname\" type=\"text\" placeholder=\"firstname\" value={firstname} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter firstname.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridlastname\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">lastname</Form.Label>\n" +
                    "                                <Form.Control name=\"lastname\" type=\"text\" placeholder=\"lastname\" value={lastname} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter lastname.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridbirthdate\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">birthdate</Form.Label>\n" +
                    "                                <Form.Control name=\"birthdate\" type=\"date\" placeholder=\"birthdate\" value={birthdate} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter birthdate.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                        </Form.Row>\n" +
                    "                        <Form.Row>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridismodified\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">ismodified</Form.Label>\n" +
                    "                                <Form.Control name=\"ismodified\" type=\"checkbox\" placeholder=\"ismodified\" value={ismodified} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter ismodified.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridtimestamp\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">timestamp</Form.Label>\n" +
                    "                                <Form.Control name=\"timestamp\" type=\"text\" placeholder=\"timestamp\" value={timestamp} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter timestamp.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridsalary\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">salary</Form.Label>\n" +
                    "                                <Form.Control name=\"salary\" type=\"text\" placeholder=\"salary\" value={salary} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter salary.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                        </Form.Row>\n" +
                    "                        <Row>\n" +
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
                    "        person: state.personReducer.person,\n" +
                    "        loading: state.personReducer.loading,\n" +
                    "        action: state.personReducer.action,\n" +
                    "\n" +
                    "}}\n" +
                    "export default connect(mapStateToProps, {updatePerson, addPerson})(PersonEdit);\n\n"
        assertEquals(expected,component)
    }
    @Test
    fun generateComponentFormPersonTest() {
        val typeList = getTypeListPerson()
        val component = GenerateReactJSEdit.generateComponentForm("person", typeList)
        println(component)
        val expected =
            "        return (\n" +
                    "            <>\n" +
                    "                <Container>\n" +
                    "                    <h1>{title} Person</h1>\n" +
                    "                    <Form noValidate validated={this.state.validate} onSubmit={this.handleSubmit}>\n" +
                    "                        <Form.Row>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridid\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">id</Form.Label>\n" +
                    "                                <Form.Control type=\"text\" placeholder=\"id\" name=\"id\" value={id} readOnly plaintext />\n" +
                    "                            </Form.Group>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridfirstname\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">firstname</Form.Label>\n" +
                    "                                <Form.Control name=\"firstname\" type=\"text\" placeholder=\"firstname\" value={firstname} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter firstname.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                            <Form.Group as={Col} controlId=\"formGridlastname\">\n" +
                    "                                <Form.Label className=\"font-weight-bold\">lastname</Form.Label>\n" +
                    "                                <Form.Control name=\"lastname\" type=\"text\" placeholder=\"lastname\" value={lastname} onChange={this.handleInputChange} required plaintext={readOnly} readOnly={readOnly} />\n" +
                    "                                <Form.Control.Feedback type=\"invalid\">\n" +
                    "                                  Please enter lastname.\n" +
                    "                                </Form.Control.Feedback>\n" +
                    "                            </Form.Group>\n" +
                    "                        </Form.Row>\n" +
                    "                        <Row>\n" +
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
                    "        person: state.personReducer.person,\n" +
                    "        loading: state.personReducer.loading,\n" +
                    "        action: state.personReducer.action,\n" +
                    "\n" +
                    "}}\n" +
                    "export default connect(mapStateToProps, {updatePerson, addPerson})(PersonEdit);\n\n"
        assertEquals(expected, component)
    }

}