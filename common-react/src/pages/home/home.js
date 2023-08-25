import React, {Component} from 'react';
import {Row,Col} from 'antd'
import Left from "./pages/left";
import Header from "./pages/header";
import Footer from "./pages/footer";
import {useRoutes} from 'react-router-dom'
import Menu from '../../routes/menus'
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    toLogin(){
        this.props.tologin()
    }
    render() {
        const Menus = ()=>{
            return useRoutes(Menu)
        }
        return (
            <div>
                <Row>
                    <Col span='3' className='nav-left'>
                        <Left/>
                    </Col>
                    <Col span='21' className='main'>
                        <Header/>
                        <Row className='content'>
                            <Menus/>
                        </Row>
                        <Footer/>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Home;