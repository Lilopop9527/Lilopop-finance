import React, {Component} from 'react';
import {connect} from "react-redux";
import {muban1, mubanprev} from "./enum/enums";
import MaterialEditComp from "./pages/home/pages/voucher/MaterialEditComp";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
        return (
            <div>
                {/*<MaterialEditComp src = {mubanprev+muban1} getNewImage = ''/>*/}
                {this.props.element}
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    element:state.element
})
export default connect(mapStateToProps)(App);