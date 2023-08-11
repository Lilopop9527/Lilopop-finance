import React, {Component} from 'react';
import {Button,Input,message} from "antd";
import '../login/login.css'
import md5 from "js-md5";
import request from "../../utils/request";
React.Component.prototype.$md5 = md5
class Create extends Component {
    constructor(props) {
        super(props);
        this.state={

        }
    }
    closeModal(msg){
        this.props.close(msg);
    }
    setUserName(e){
        this.setState({
            username: e.target.value
        })
    }
    setEmail(e){
        this.setState({
            email: e.target.value
        })
    }
    setPassword(e){
        const p = md5(e.target.value)
        this.setState({
                password: p
            }
        )
    }
    async saveUser(){
        const close = (msg)=>this.closeModal(msg)
        const username = this.state.username
        const email = this.state.email
        const pattern = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
        if(username == null && email == null){
            message['error']('请输入账号或邮箱')
        }else if(this.state.password == null){
            message['error']('请输入密码')
        }else if (username!=null&&(username.length<8 || username.length>16) || this.state.password.length<6){
            message['error']('账号或密码长度错误，请检查输入')
        }else if(email!=null&&!pattern.test(email)){
            message['error']('邮箱格式不正确，请检查输入')
        }else {
            const msg = await request({
                url: '/auth/user/saveUser',
                method: 'post',
                params: {
                    ...this.state
                }
            }).then(
                function (response) {
                    if (response.data.message === 'success'){
                        message['success']('注册成功')
                        close('createUser')
                    }else {
                        message['error'](response.data.message)
                    }
                }
            ).catch(function (error) {
                message['error']('服务器繁忙，请稍后再试')
            })
        }
    }
    render() {
        return (
            <div>
                账  号:<Input className='input2' placeholder='请输入账号' onChange={e=>{this.setUserName(e)}} />
                <br/>
                <br/>
                邮  箱:<Input className='input2' placeholder='请输入邮箱' onChange={e=>{this.setEmail(e)}}/>
                <br/>
                <br/>
                密  码:<Input.Password className='input2' placeholder='请输入密码' onChange={e=>{this.setPassword(e)}}/>
                <br/>
                <br/>
                <Button type='primary' onClick={()=>this.saveUser()}>注册</Button>
                <Button onClick={()=>this.closeModal('createUser')}>取消</Button>
            </div>
        );
    }
}

export default Create;