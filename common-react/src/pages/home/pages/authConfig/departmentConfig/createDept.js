import {connect} from "react-redux";
import request from "../../../../../utils/request";
import React,{useEffect, useState} from "react";
import {Button, Form, Input, message, Select} from "antd";

const CreateDept:React.FC = (props)=>{
    const [leaders,setLeaders] = useState([])
    const [principalId,setPrincipalId] = useState(-1)
    const close = ()=>{
        props.close()
    }
    const [form] = Form.useForm()
    useEffect(()=>{
        const list = props.leaders
        const data = []
        data.push({
            value: -1,
            label: ' ',
            key: -1
        })
        list.map((item)=>{
            const t = {
                value:item.id,
                label:item.username,
                key:item.id
            }
            data.push(t)
        })
        setLeaders(data)
    },[])
    const onFinish = async (values)=>{
        const msg = await request({
            url:'/auth/dept/add',
            method:'post',
            params:{
                name:values.name,
                principalId:values.principalId === '未选择'?-1:values.principalId
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            props.addDept(res.data.data)
            message['success']('添加成功')
            close()
        }).catch(function (e) {
            message['error']('网络错误，请稍后再试')
        })
    }

    return (
        <Form form={form}
              labelCol={{span:8}}
              wrapperCol={{ span: 16 }}
              style={{ maxWidth: 600 }}
              autoComplete="off"
              onFinish={onFinish}
        >
            <Form.Item label='部门名称' name='name' rules={[{required:true,message:'请输入名称'}]}>
                <Input/>
            </Form.Item>
            <Form.Item label='负责人' name='principalId' initialValue={-1}>
                <Select defaultValue={-1}  options={leaders} onChange={setPrincipalId}/>
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
    token:state.token,
    leaders:state.leaders
})
export default connect(mapStateToProps)(CreateDept)

