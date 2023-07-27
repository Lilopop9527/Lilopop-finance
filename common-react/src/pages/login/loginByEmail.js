import React, {Component} from 'react';
import {Button} from "antd";
import './login.css'
import md5 from "js-md5";
import request from "../../utils/request";
React.Component.prototype.$md5 = md5
class LoginByEmail extends Component {
    constructor() {
        super();
        this.state = {}
    }
    setEmail(e){
        this.setState({
                email: e.target.value
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
                email: this.state.email,
                password: this.state.password
            }
        })
        console.log(msg)
    }
    render() {
        return (
            <div>
                <div className='inside'>
                    邮 箱：<input onChange={e=>{this.setEmail(e)}}/>
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

export default LoginByEmail;