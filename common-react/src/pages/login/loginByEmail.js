import React, {Component} from 'react';
import {Button, message} from "antd";
import './login.css'
import md5 from "js-md5";
import request from "../../utils/request";
React.Component.prototype.$md5 = md5
class LoginByEmail extends Component {
    constructor(props) {
        super(props);
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
        this.setState({
                password: p
            }
        )
    }
    toHome(){
        this.props.tohome()
    }
    async login(){
        const home = ()=>this.toHome()
        const msg = await request({
            url: '/auth/auth/login',
            method: 'post',
            params: {
                email: this.state.email,
                password: this.state.password
            }
        }).then(function (response) {
            if (response.data.code === 201){
                message["error"](response.data.message)
            }else{
                window.localStorage.setItem('token',response.data.data.token)
                window.localStorage.setItem('user',response.data.data)
                message['success']('登陆成功')
                home()
            }
        }).catch(function (error) {
            message["error"]('登陆失败，请检查账号密码')
        })

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