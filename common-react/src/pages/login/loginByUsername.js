import React, {Component} from 'react';
import {Button} from "antd";
import './login.css'
import md5 from "js-md5";
import request from "../../utils/request";
React.Component.prototype.$md5 = md5
class LoginByUsername extends Component {
    constructor() {
        super();
        this.state = {}
    }
    setUsername(e){
        this.setState({
            username: e.target.value
            }
        )
    }

    setPassword(e){
        const p = md5(e.target.value)
        console.log(p)
        this.setState({
                password: p
            }
        )
    }
    async login(){
        const msg = await request({
            url: '/auth/auth/login',
            method: 'post',
            params: {
                username: this.state.username,
                password: this.state.password
            }
        })
        console.log(msg)
    }
    render() {
        return (
            <div>
                <div className='inside'>
                    账 号：<input onChange={e=>{this.setUsername(e)}}/>
                    <br/>
                    <br/>
                    密 码：<input type='password' onChange={e=>{this.setPassword(e)}}/>
                    <br/>
                    <Button type='primary' className='button' onClick={()=>{this.login()}}>登录</Button>
                </div>
            </div>
        );
    }
}

export default LoginByUsername;