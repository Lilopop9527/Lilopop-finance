import React, {Component} from 'react';
import {Button} from "antd";
import './login.css'
import md5 from "js-md5";
import request from "../../utils/request";
React.Component.prototype.$md5 = md5
class LoginByPhone extends Component {
    constructor() {
        super();
        this.state = {}
    }
    setPhone(e){
        this.setState({
                phone: e.target.value
            }
        )
    }

    setCode(e){
        this.setState({
                code: e
            }
        )
    }
    async login(){
        const msg = await request({
            url: '/auth/auth/login',
            method: 'post',
            params: {
                phone: this.state.phone,
                code: this.state.code
            }
        })
        console.log(msg)
    }
    sendCode(){

    }
    render() {
        return (
            <div>
                <div className='inside'>
                    手机号：<input onChange={e=>{this.setPhone(e)}}/>
                    <br/>
                    <br/>
                    验证码：<input className='input' onChange={e=>{this.setCode(e)}}/>
                    <Button type='primary' onClick={this.sendCode()} className='code'>发送验证码</Button>
                    <br/>
                    <Button type='primary' className='button' onClick={()=>{this.login()}}>登录</Button>
                </div>
            </div>
        );
    }
}

export default LoginByPhone;