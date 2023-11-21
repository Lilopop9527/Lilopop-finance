import React, {Component} from 'react';
import {Outlet} from "react-router-dom";
class AuthConfig extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        return (
            <div>
                <Outlet/>
            </div>
        );
    }
}

export default AuthConfig;