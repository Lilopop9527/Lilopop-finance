import React,{Component}  from 'react';
import {connect} from "react-redux";
import {token} from "../../stores/auth/action";
import {Button,Modal,Radio} from 'antd'
import LoginByUsername from "./loginByUsername";
import LoginByEmail from "./loginByEmail";
import LoginByPhone from "./loginByPhone";
import ForgetPsd from "../users/forgetPsd";
import Create from "../users/create";
import './login.css'

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            login: <LoginByUsername/>,
            forgetPassword: false,
            createUser: false,
            type: 1
        }
    }
    showModal(msg){
        this.setState({
            [msg]: true
        })
    }

    setType(e){
        const v = e.target.value
        switch (v) {
            case 1:
                this.setState({
                    login:<LoginByUsername/>,
                    type: 1
                })
                break;
            case 2:
                this.setState({
                    login:<LoginByEmail/>,
                    type: 2
                })
                break;
            case 3:
                this.setState({
                    login:<LoginByPhone/>,
                    type: 3
                })
                break;
        }
    }

    render() {
        return (
            <div>
                <div className='login'>
                    <h1>欢迎使用XXX管理系统</h1>
                    {this.state.login}
                </div>
                <Button type='link' className="link" onClick={()=>this.showModal('forgetPassword')}>忘记密码？</Button>
                <Modal
                    title='重置密码'
                    open={this.state.forgetPassword}
                    footer={[]}
                    onCancel={()=>{
                        this.setState({
                            forgetPassword: false
                        })
                    }}
                    cancelText='取消'
                >
                    <ForgetPsd/>
                </Modal>
                <br/>
                <Button type='link' className="link" onClick={()=>this.showModal('createUser')}>注册新用户</Button>
                <Modal
                    title='注册用户'
                    open={this.state.createUser}
                    footer={[]}
                    onCancel={()=>{
                        this.setState({
                            createUser: false
                        })
                    }}
                    cancelText='取消'
                >
                    <Create/>
                </Modal>
                <div>
                    <Radio.Group value={this.state.type} onChange={e=>this.setType(e)} className='radio'>
                        <Radio value={1}>账号密码登录</Radio>
                        <Radio value={2}>邮箱密码登录</Radio>
                        <Radio value={3}>手机号登录</Radio>
                    </Radio.Group>
                </div>
            </div>
        )
    }
}

/**
 * connect是一个函数 ，返回一个高阶组件
 * 需要传递两个参数：mapStateToProps，mapDispatchToProps
 */
//用于建立组件和store的state的映射关系
const mapStateToProps = (state)=>{
    return {
        token: state.token
    };
};
const mapDispatchToProps = (dispatch)=>{
    return {
        setToken:()=>{
            dispatch(token("123456"))
        }
    }
}
export default connect(mapStateToProps,mapDispatchToProps)(Login);