import React, {Component} from 'react';
import {Row,Col} from 'antd'
import Left from "./pages/left";
import Header from "./pages/header";
import Footer from "./pages/footer";
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        return (
            <div>
                <Row>
                    <Col span='3' className='nav-left'>
                        <Left/>
                    </Col>
                    <Col span='21' className='main'>
                        <Header/>
                        <Footer/>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Home;