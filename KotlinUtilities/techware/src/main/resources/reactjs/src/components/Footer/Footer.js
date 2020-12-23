import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import "./Footer.css";

export class Footer extends Component {
    render() {
        return (
            <>
                <footer className="fixed-bottom footer" >
                    <Container >
                        <Row>
                            <Col>
                                <p className="lead text-center pt-3">
                                    Copyright &copy;
                               <span id="year" className="pr-2">{new Date().getFullYear()} Demo</span>
                                </p>
                            </Col>
                        </Row>
                    </Container>
                </footer>
            </>
        )
    }
}

export default Footer
