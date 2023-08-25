import React, {Component} from 'react';
import {connect} from "react-redux";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }
    render() {
        return (
            <div>
                {this.props.element}
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    element:state.element
})
export default connect(mapStateToProps)(App);