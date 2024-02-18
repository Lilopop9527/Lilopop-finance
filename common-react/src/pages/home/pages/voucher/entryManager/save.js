import request from "../../../../../utils/request";
import {connect} from "react-redux";
import {Button, Form, Input, message, Select} from "antd";
import {useEffect, useState} from "react";
import {secondEntry} from "../../../../../stores/auth/action";
const Save:React.FC = (props)=>{
    const {close} = props
    const [form] = Form.useForm()
    const [second,setSecond] = useState([])
    const [value,setValue] = useState(0)
    useEffect(()=>{
        if(props.second.length === 0){
            getFirst()
        }else{
            const data  = [{
                value:0,
                label:'一级条目'
            }]
            data.push(...createLabel(props.second))
            setSecond(data)
        }
    },[])

    const getFirst = async ()=>{
        const msg = await request({
            url:'/finance/entry/lbyf',
            method:'get',
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            const arr = res.data.data
            const data  = [{
                value:0,
                label:'一级条目'
            }]
            data.push(...createLabel(arr))
            setSecond(data)
            props.dispatch(secondEntry(arr))
        })
    }
    const createLabel = (data)=>{
        const arr = []
        data.map((item)=>{
            arr.push({
                value:item.id,
                label:item.name
            })
        })
        return arr
    }
    const shutDown = ()=>{
        close()
    }
    const submit = async (values)=>{
        const msg = await request({
            url:'/finance/entry/save',
            method:'post',
            params:{
                name:values.name,
                parent:values.parent
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            message['success']('保存成功')
            if (values.parent === 0){
                getFirst()
            }
            shutDown()
        }).catch(function (e) {
            message['error']('保存失败，请稍后再试')
            console.log(e)
        })
    }
    const saveSelectValue = (value)=>{
        setValue(value)
    }
    return (
        <div>
            <Form form={form}
                  labelCol={{span:8}}
                  wrapperCol={{ span: 16 }}
                  style={{ maxWidth: 600 }}
                  autoComplete="off"
                  onFinish={submit}
                  initialValues={{name:'', parent:0}}
            >
                <Form.Item label='条目名称' name='name' rules={[{required:true,message:'请输入名称'}]}>
                    <Input maxLength={4}/>
                </Form.Item>
                <Form.Item label='上级条目' name='parent'>
                    <Select options={second} style={{width:200}} onChange={saveSelectValue}/>
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
const mapStateToProps = (state)=>(
    {
        token:state.token,
        second:state.secondEntry
    }
)
export default connect(mapStateToProps)(Save)