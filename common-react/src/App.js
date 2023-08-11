import React, {Component} from 'react';
import Login from "./pages/login/login";
import Home from "./pages/home/home";
class App extends Component {
    constructor() {
        super();
        this.state = {
            app:<Login tohome={this.toHome.bind(this)}/>
        }
    }

    toHome(){
        this.setState({
            app: <Home tologin={this.toLogin.bind(this)}/>
        })
    }
    toLogin(){
        this.setState({
            app:<Login tohome={this.toHome.bind(this)}/>
        })
    }
    render() {
        return (
            <div>
                {this.state.app}
            </div>
        );
    }
}

export default App;