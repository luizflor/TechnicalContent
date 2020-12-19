import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import { getInputChange } from '../../utilities/utils';

export class LeftSide extends Component {
    constructor(props) {
        super(props)
        this.state = {
            links: ['person','person2'],
            filtered: ""
        }
    }
    handleInputChange = (eventData) => {
        this.setState(getInputChange(eventData));
        this.setState({changing: true});
    }

    render() {
        const filtered = this.state.links.filter(e=>e.indexOf(this.state.filtered) > -1)
        const links = filtered.map((e,i)=>{
            return (
                <li key={i}>
                    <Link  to={`/${e}Table`}>{e}</Link>
                </li>
        )})

        return (
            <div className="ml-5">
                <h1>Navigation</h1>
                <input type="text" onChange={this.handleInputChange} name="filtered" placeholder="select from list below"/>
                <ul>
                {links}
                </ul>
            </div>
        )
    }
}

export default LeftSide
