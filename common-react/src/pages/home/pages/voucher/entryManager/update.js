import request from "../../../../../utils/request";
import {connect} from "react-redux";
import {useEffect, useState} from "react";
import {secondEntry} from "../../../../../stores/auth/action";
import {Button, Form, Input, message, Modal, Select} from "antd";
const Update:React.FC = (props)=>{
    const {entry,close} = props
    const {form} = Form.useForm()
    const [second,setSecond] = useState([])
    const [newEntry,setNewEntry] = useState(entry)
    const [value,setValue] = useState(0)
    const [showMsg,setShowMsg] = useState(false)
    useEffect(()=>{
        console.log(entry)
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
            url:'/finance/entry/lbyfa',
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
    const saveSelectValue = (value)=>{
        setValue(value)
    }
    const submit =  (values)=>{
        if (values.parent === entry.id){
            message['info']('不能选择自己作为一级条目')
            return
        }
        setValue(values)
        if (entry.parentId === 0 && values.parent !== 0){
            setShowMsg(true)
        }else {
            update(values)
        }
    }
    const update = async (values)=>{
        console.log(values)
        const msg = await request({
            url:'/finance/entry/update',
            method:'put',
            params:{
                id:entry.id,
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
            setShowMsg(false)
            close()
        }).catch(function (e) {
            message['error']('保存失败，请稍后再试')
            console.log(e)
        })
    }
    const closeMsg = ()=>{
        setShowMsg(false)
    }
    return (
        <div>
            <Form form={form}
                  labelCol={{span:8}}
                  wrapperCol={{ span: 16 }}
                  style={{ maxWidth: 600 }}
                  autoComplete="off"
                  onFinish={submit}
                  initialValues={{name:newEntry.name, parent:newEntry.parentId}}
            >
                <Form.Item label='条目名称' name='name' rules={[{required:true,message:'请输入名称'}]}>
                    <Input maxLength={8}/>
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
            <Modal title='注意信息' open={showMsg} footer={[]}
                   onCancel={()=>{
                       setShowMsg(false)
                   }}
            >
                请注意，您正在将一个一级条目更改为二级条目，如果您希望继续，那么该一级条目下所有二级条目将会暂时失效，直到您将该条目重新变更为一级条目！
                <div style={{textAlign:'center'}}>
                    <Button type='primary' onClick={()=>update({id:entry.id,name:value.name,parent:value.parent})}>继续提交</Button>
                    <Button style={{marginLeft:10}} onClick={closeMsg}>取消</Button>
                </div>
            </Modal>
        </div>
    )
}
const mapStateToProps = (state)=>(
    {
        token:state.token,
        second:state.secondEntry
    }
)
export default connect(mapStateToProps)(Update)