import React, {Component} from 'react';
import {connect} from "react-redux";
import {delToken,login} from "../../../../stores/auth/action";

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    componentDidMount() {
        const p = this.props
        const token = p.token
        const {dispatch} = p
        if(token === 'empty'||!token||token === ''){
            dispatch(delToken('empty'))
            dispatch(login(1))
        }
    }

    render() {
        return (
            <div>
                <h1>主页组件</h1>
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token
})
export default connect(mapStateToProps)(Home);