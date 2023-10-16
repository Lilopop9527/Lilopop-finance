import React, {Component} from 'react';
import {Button,Input} from "antd";
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
        const pattern = /^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\d{8}$/
        const msg = await request({
            url: '/auth/auth/login',
            method: 'post',
            params: {
                phone: this.state.phone,
                code: this.state.code
            }
        })
    }
    sendCode(){

    }
    render() {
        return (
            <div>
                <div className='inside'>
                    手机号：<Input className='input2' onChange={e=>{this.setPhone(e)}} placeholder='使用手机登录无需注册'/>
                    <br/>
                    <br/>
                    验证码：<Input className='input' onChange={e=>{this.setCode(e)}}/>
                    <Button type='primary' onClick={this.sendCode()} className='code'>发送验证码</Button>
                    <br/>
                    <Button type='primary' className='button' onClick={()=>{this.login()}}>登录</Button>
                </div>
            </div>
        );
    }
}

export default LoginByPhone;