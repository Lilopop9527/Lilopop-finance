import React, {Component} from 'react';
import {Button,message} from "antd";
import './login.css'
import md5 from "js-md5";
import request from "../../utils/request";
import {connect} from "react-redux";
import {token,home,userInfo,route,role} from "../../stores/auth/action";

React.Component.prototype.$md5 = md5
class LoginByUsername extends Component {
    constructor(props) {
        super(props);
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
        this.setState({
                password: p
            }
        )
    }
    async login(){
        const p = this.props
        const msg = await request({
            url: '/auth/auth/login',
            method: 'post',
            params: {
                username: this.state.username,
                password: this.state.password
            }
        }).then(function (response) {
            if (response.data.code === 201){
                message["error"](response.data.message)
            }else{
                const {dispatch} = p;
                const user = {
                    id:response.data.data.id,
                    username:response.data.data.username,
                    phone:response.data.data.phone,
                    email:response.data.data.email,
                    img:response.data.data.img
                }
                dispatch(token(response.data.data.token))
                dispatch(userInfo(user))
                dispatch(role(response.data.data.roles))
                dispatch(route(response.data.data.routes))
                dispatch(home(1))
                message['success']('登陆成功')
            }
        }).catch(function (error) {
            message["error"]('登陆失败，请检查账号密码')
        })
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

export default connect()(LoginByUsername);