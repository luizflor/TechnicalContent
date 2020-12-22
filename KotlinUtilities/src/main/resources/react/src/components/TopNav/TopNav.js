import React, { Component } from 'react'
import {Link} from 'react-router-dom';
import {
    Container,Navbar, Nav, NavDropdown, Form, FormControl, Button
 } from 'react-bootstrap'

export class TopNav extends Component {
    render() {
        return (
            <div>
                <Navbar bg="success" epxand="sm" variant="dark">
                    <Container>
                        <Navbar.Brand>
                            <Link to="/" className='text-white'>Brand</Link>
                        </Navbar.Brand>
                        <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="ml-auto">
                                <Nav.Link href="/"><i className="fas fa-cog"></i> Profile</Nav.Link>
                                <Nav.Link href="/"><i className="fas fa-user"></i> User</Nav.Link>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            </div>
        )
    }
}

export default TopNav
