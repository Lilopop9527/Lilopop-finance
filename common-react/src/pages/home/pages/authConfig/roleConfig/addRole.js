import React,{useState} from 'react';
import {connect} from "react-redux";
import request from "../../../../../utils/request";
import {Button, Form, Input, message} from "antd";

const AddRole:React.FC = (props)=>{
    const close = ()=>{
        props.close()
    }
    const [form] = Form.useForm()
    const  onFinish = async (values) => {
        const msg = await request({
            url:'/auth/role/save',
            method:'post',
            params:{
                roleName:values.roleName,
                weight:values.weight
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            props.addRole(res.data.data)
            message['success']('添加成功')
            close();
        }).catch(function (e) {
            message['error']('网络错误，请稍后再试')
        })
    }
    return (
        <Form form={form}
              labelCol={{ span: 8 }}
              wrapperCol={{ span: 16 }}
              style={{ maxWidth: 600 }}
              autoComplete="off"
              onFinish={onFinish}
        >
            <Form.Item label='角色名：' name='roleName' rules={[{required:true,message:'请输入角色名'}]}>
                <Input/>
            </Form.Item>
            <Form.Item label='权重：' name='weight' rules={[{required:true,message:'请输入大于0小于10亿的数字'}]}>
                <Input/>
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Button type='primary' htmlType='submit'>
                    保存
                </Button>
                <Button style={{marginLeft:15}} onClick={close}>
                    取消
                </Button>
            </Form.Item>
        </Form>
    )
}
const mapStateToProps = (state)=>({
    token:state.token
})
export default connect(mapStateToProps)(AddRole)