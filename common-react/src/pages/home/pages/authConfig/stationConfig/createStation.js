import {connect} from "react-redux";
import request from "../../../../../utils/request";
import {Button, Form, Input, message} from "antd";
import React from "react";
const CreateStation:React.FC = (props)=>{
    const [form] = Form.useForm()
    const close = ()=>{
        props.close()
    }
    const onFinish = async (values)=>{
        const msg = await request({
            url:'/auth/sta/add',
            method:'post',
            params:{
                name:values.name
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            props.addSta(res.data.data)
            message['success']('添加成功')
            close()
        }).catch(function (e) {
            message['error']('网络错误，请稍后再试')
        })
    }

    return (
        <div>
            <Form form={form}
                  labelCol={{span:8}}
                  wrapperCol={{ span: 16 }}
                  style={{ maxWidth: 600 }}
                  autoComplete="off"
                  onFinish={onFinish}
            >
                <Form.Item label='岗位名称' name='name' rules={[{required:true,message:'请输入名称'}]}>
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
        </div>
    )
}
const mapStateToProps = (state)=>({
    token:state.token
})
export default connect(mapStateToProps)(CreateStation)