import {connect} from "react-redux";
import {Button, Form, Input, InputNumber, message, Radio, Select, Space} from "antd";
import {useEffect, useState,useRef} from "react";
import {MinusCircleOutlined, PlusOutlined} from "@ant-design/icons";
import {muban1, mubanprev} from "../../../../../enum/enums";
import html2canvas from "html2canvas";
import request from "../../../../../utils/request";
const Create:React.FC = (props)=>{
    const [form] = Form.useForm()
    const contentRef = useRef(null)
    const ref = useRef(null)
    const [top,setTop] = useState(0)
    const [nodecnt,setNodecnt] = useState(0)
    const [count,setCount] = useState(0)
    const [first,setFirst] = useState([])
    const [second,setSecond] = useState([])
    const [prev,setPrev] = useState([])
    const [last,setLast] = useState([])
    const [money,setMoney] = useState(0)
    const [values,setValues] = useState([{
        content:'出差',
        check:1,
        first:1,
        second:2,
        money:0
    }])
    const [date,setDate] = useState(new Date())
    const [errorData,setErrorData] = useState([])
    const [imageSize,setImageSize] = useState({width:0,height:0})
    const [src,setSrc] = useState(mubanprev+muban1)
    const [local,setLocal] = useState([295,350,405,460,512,568,621])
    const [lefts,setLefts] = useState([120,280,400,510,630,747,767,787,808,828,849,868,889,910,931,952])
    const [widths,setWidths] = useState([160,100,100,100,100,10,10,10,10,10,10,10,10,10,10,10])
    const [numbers,setNumbers] = useState(['零','壹','贰','叁','肆','伍','陆','柒','捌','玖','拾'])

    useEffect(()=>{
        getAllEntry()
        first[0] = prev
        second[0] = last[0]
        setDate(new Date())
        form.resetFields()
        setValues(form.getFieldsValue().paramAttribute)
        let name = (props.detail.firstName&&props.detail.lastName)?props.detail.firstName+props.detail.lastName:
            !props.user.username?'':props.user.username
        const img = new Image()
        img.src = src
        img.onload = ()=>{
            setImageSize({ width: img.width, height: img.height });
            initNode(contentRef,0,name)
        }
    },[top])
    const getAllEntry = async ()=>{
       const msg = await request({
           url:'/finance/entry/la',
           method:'get',
           headers:{
               Auth:props.token
           }
       }).then(function (res) {
           const data = res.data.data
           let a = []
           let b = []
           for (let i = 0; i < data.length; i++) {
               if (data[i].parent === 0){
                   a.push(data[i])
               }
           }
           for (let i = 0; i < a.length; i++) {
               const temp = []
               for (let j = 0; j < data.length; j++) {
                   if (data[j].parent === a[i].id){
                       temp.push(data[j])
                   }
               }
               //console.log(temp)
               b.push(temp)
           }
           createEntry(a,b)
       }).catch(function (e) {
           console.log(e)
       })
        if (top === 0){
            setTop(1)
        }
    }
    const initNode = (mode,m,name)=>{
        AddNode({
            content: `${date.getFullYear()}`,
            color: '#000000',
            size: 20,
            left: 402,
            top: 145+m,
        }, 0,mode,0);
        AddNode({
            content: `${date.getMonth()+1}`,
            color: '#000000',
            size: 20,
            left: 500,
            top: 145+m
        }, 1,mode,0);
        AddNode({
            content: `${date.getDate()}`,
            color: '#000000',
            size: 20,
            left: 580,
            top: 145+m
        }, 2,mode,0);
        AddNode({
            content: name,
            color: '#000000',
            size: 18,
            left: 350,
            top: 662+m,
            width:100
        }, 3,mode,0);
        AddNode({
            content:date.getTime()*3,
            color:'#000000',
            size:18,
            left:820,
            top:145+m
        },4,mode,0);
    }
    const createEntry=(arr1,arr2)=>{
        let a = []
        let b = []
        for (let i = 0; i < arr1.length; i++) {
            a[i] = {label:arr1[i].name,value:arr1[i].id}
            let temp = []
            const t = arr2[i]
            for (let j = 0; j < arr2[i].length; j++) {
               temp.push({label:t[j].name,value:t[j].id})
            }
            b[i] = temp
        }
        setPrev(a)
        setLast(b)
    }
    const valueChange = (changedValues, allValues)=>{
        setValues(allValues.paramAttribute)
        changeNodes(allValues.paramAttribute,contentRef,0)
    }
    const radioChange = (e)=>{

    }
    const AddNode = ({ content, color, size, left, top, width }, index,mode) => {
        const contentNode = mode.current;
        let newNode = document.createElement('div');
        newNode.className = 'node' + index;
        // 此处判断节点是否已经存在
        const bool = contentNode?.childNodes[index]
        if (bool) {
            newNode = contentNode.childNodes[index]
        }
        newNode.textContent = content
        newNode.style.color = color;
        newNode.style.fontSize = size + 'px';
        newNode.style.top = top + 'px';
        newNode.style.left = left + 'px';
        newNode.style.width = width+'px'
        newNode.style.textAlign='center'
        newNode.style.position = 'absolute';

        // 节点不存在新增阶段
        if (!bool) {
            contentNode.appendChild(newNode);
        } else {
            // 节点存在则替换原来的节点
            contentNode.replaceChild(newNode, contentNode.childNodes[index])
        }
    }
    const changeNodes=(items,mode,m)=>{
        let cnt = 5
        for (let i = nodecnt; i > 4; i--) {
            removeNode(i,mode)
        }
        items.forEach((item,index)=>{
            if (!item)return
            let e1 = {
                content:item.content,
                color: '#000000',
                size: 20,
                left:lefts[0],
                top:local[index]+m,
                width:widths[0],
            }
             e1 && AddNode({
                content:item.content,
                color: '#000000',
                size: 20,
                left:lefts[0],
                top:local[index]+m,
                width:widths[0]
            },cnt,mode)
            cnt++
            let o = 0
            let p = 0
            for (let i = 0; i < prev.length; i++) {
                if (prev[i].value === item.first){
                    o = i
                    break
                }
            }
            for (let i = 0; i < last[o].length; i++) {
                if (last[o][i].value === item.second){
                    p = i
                    break
                }
            }
            if (item.check === 1){
                let e2 = {
                    content:first[index][o].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[1],
                    top:local[index]+m,
                    width:widths[1]
                }
                e2&&AddNode({
                    content:first[index][o].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[1],
                    top:local[index]+m,
                    width:widths[1]
                },cnt,mode)
                cnt++
                let e3 = {
                    content:second[index][p].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[2],
                    top:local[index]+m,
                    width:widths[2]
                }
                e3&&AddNode({
                    content:second[index][p].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[2],
                    top:local[index]+m,
                    width:widths[2]
                },cnt,mode)
                cnt++
            }else{
                let e2 = {
                    content:first[index][o].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[3],
                    top:local[index]+m,
                    width:widths[3]
                }
                e2&&AddNode({
                    content:first[index][o].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[3],
                    top:local[index]+m,
                    width:widths[3]
                },cnt,mode)
                cnt++
                let e3 = {
                    content:second[index][p].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[4],
                    top:local[index]+m,
                    width:widths[4]
                }
                e3&&AddNode({
                    content:second[index][p].label,
                    color: '#000000',
                    size: 20,
                    left:lefts[4],
                    top:local[index]+m,
                    width:widths[4]
                },cnt,mode)
                cnt++
            }
            let money = item.money*100
            let n = 15
            money = parseInt(money)
            while (money>0){
                let a = money%10
                let e = {
                    content:numbers[a],
                    color: '#000000',
                    size: 17,
                    left:lefts[n],
                    top:local[index]+m,
                    width:widths[n]
                }
                e&&AddNode({
                    content:numbers[a],
                    color: '#000000',
                    size: 17,
                    left:lefts[n],
                    top:local[index]+m,
                    width:widths[n]
                },cnt,mode)
                money = Math.trunc(money/10)
                cnt++
                n--
            }
        })
        setNodecnt(cnt-1)
    }
    //  删除节点
    const removeNode = (index,mode) => {
        const contentNode = mode.current;
        const bool = contentNode?.childNodes[index]
        if (bool) {
            contentNode.removeChild(contentNode.childNodes[index])
        }
    }
    const firstChange = (e,index)=>{
        let l = 0
        for (let i = 0; i < prev.length; i++) {
            if (prev[i].value === e){
                l = i
                break
            }
        }
        const a = form.getFieldsValue()
        a.paramAttribute[index].second = last[l][0].value
        const data = {...second}
        data[index] = last[l]
        setSecond(data)
    }
    const secondChange = (e,index)=>{

    }
    // 将图片转换成二进制形式
    const base64ToBlob = (code) => {
        let parts = code.split(';base64,')
        let contentType = parts[0].split(':')[1]
        let raw = window.atob(parts[1])
        let rawLength = raw.length
        let uint8Array = new Uint8Array(rawLength)
        for (let i = 0; i < rawLength; i++) {
            uint8Array[i] = raw.charCodeAt(i)
        }
        return new Blob([uint8Array], { type: contentType })
    }
    // 保存图片
    const saveImage = async () => {
        let name = (props.detail.firstName&&props.detail.lastName)?props.detail.firstName+props.detail.lastName:
            !props.user.username?'':props.user.username
        initNode(contentRef,35,name)
        changeNodes(values,contentRef,35)
        const contentNode = contentRef.current;
        const canvas = await html2canvas(contentNode, {
            useCORS: true,
            allowTaint: true,//允许污染
            backgroundColor: '#ffffff',
            // toDataURL: src
        })

        const imgData = canvas.toDataURL('image/png');
        let blob = base64ToBlob(imgData)
        const link = document.createElement('a');
        link.href = imgData;
        // link.href = URL.createObjectURL(blob);
        //getNewImage(link.href)
        // console.log(blob, 11)
        link.download = 'page-image.' + blob.type.split('/')[1];
        link.click();
        const node = contentRef.current
        const length = node.childNodes.length;
        for (let i = length-1; i >= 0; i--) {
            node.removeChild(node.childNodes[i])
        }
        setTop(top=>top+1)
        changeNodes(values,contentRef,0)
    }
    const saveMsg = async ()=>{
        const data = []
        let st = ''
        values.map((item)=>{
            st = st + JSON.stringify(item)+'-'
        })
        st = st.substring(0,st.length-1)
        const vmsgs = {
            createDate:date.getTime(),
            record:props.detail.userId,
            recordName:props.detail.firstName+props.detail.lastName,
            singleId:date.getTime()*3
            //TODO 公司信息添加
        }
        console.log(st,)
        const msg = await request({
            url: '/finance/voucher/save',
            method: 'post',
            params: {
                vouchers:st,
                dto:JSON.stringify(vmsgs)
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            if (!res.data.data){
                res.data.data.map((item)=>{
                    data.push(item)
                })
            }
        }).catch(function (e) {
            message['error']("存储信息失败，请检查输入")
        })
        if (data.length === 0){
            message['success']('保存成功')
            return
        }
        setErrorData(data)
    }
    const submitMsg = async ()=>{
        if (errorData.length !== 0){
            return
        }
        const msg = await request({
            url:'/finance/voucher/submit',
            method:'post',
            params:{
                singleId:date.getTime()*3,
                userId:props.detail.userId,
                username:props.detail.firstName+props.detail.lastName
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            if (res.data.data === "该用户不存在！"){
                message['error']('当前用户非法，请检查信息')
            }else{
                message['success'](res.data.data)
            }
        }).catch(function (e) {
            message['error']('当前凭证编号错误，请检查后提交')
        })
    }
    const reset = ()=>{
        console.log(form.getFieldsValue())
        //setTop(top=>top+1)
    }
    return (
        <div>
            <div ref={contentRef} style={{
                background: `url(${src}) no-repeat`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                width: imageSize.width + 'px',
                height: imageSize.height + 'px',
            }}>
            </div>
            <div style={{height:150,overflow:'auto'}}>
                <Form form={form} name="dynamic_form_nest_item"
                      initialValues={{
                          paramAttribute:[{
                              content:'出差',
                              check:1,
                              first:1,
                              second:2,
                              money:0
                          }]
                      }}
                      onValuesChange={valueChange}
                >
                    <Form.List name='paramAttribute'>
                        {(fields,{add,remove,move})=>(
                            <>
                                {fields.map(({key,name,...restField},index)=>(
                                    <Space key={key} style={{display:'flex',marginBottom:0}} align='baseline'>
                                        <Form.Item
                                            style={{marginBottom:'10px'}}
                                            {...restField}
                                            name={[name,'content']}
                                            label='业务内容'
                                            rules={[{required:true,message:'请输入业务内容'}]}
                                        >
                                            <Input placeholder='' maxLength={8} defaultValue='出差'/>
                                        </Form.Item>
                                        <Form.Item
                                            style={{marginBottom:'10px'}}
                                            {...restField}
                                            name={[name,'check']}
                                            initialValue={1}
                                        >
                                            <Radio.Group onChange={radioChange} value={1}>
                                                <Radio value={1}>借方</Radio>
                                                <Radio value={2}>贷方</Radio>
                                            </Radio.Group>
                                        </Form.Item>
                                        <Form.Item
                                            style={{marginBottom:'10px'}}
                                            {...restField}
                                            name={[name,'first']}
                                            label='一级科目'
                                            initialValue={0}
                                        >
                                            <Select onChange={(e)=>firstChange(e,index)} options={first[index]}/>
                                        </Form.Item>
                                        <Form.Item
                                            style={{marginBottom:'10px'}}
                                            {...restField}
                                            name={[name,'second']}
                                            label='二级科目'
                                            initialValue={0}
                                        >
                                            <Select onChange={(e)=>secondChange(e,index)} options={second[index]} />
                                        </Form.Item>
                                        <Form.Item
                                            style={{marginBottom:'10px'}}
                                            {...restField}
                                            name={[name,'money']}
                                            label='金额'
                                            rules={[{required:true,message:'请输入金额'}]}
                                            initialValue={0}
                                        >
                                            <InputNumber style={{width:180}} max={999999999}/>
                                        </Form.Item>
                                        <MinusCircleOutlined onClick={()=>{
                                            if (count === 0)return
                                            remove(name)
                                            setCount(count=>count-1)
                                        }}/>
                                    </Space>
                                ))}
                                <Form.Item>
                                    <Button type='dashed' onClick={()=>{
                                        try{
                                            if (count === 6){
                                                message['warning']('已添加至上限')
                                                return
                                            }
                                            first[count+1] = prev
                                            second[count+1] = last[0]
                                            //setFirst(a)
                                            //setSecond(b)
                                            add()
                                            setCount(count=>count+1)
                                        }catch (e) {

                                            return
                                        }
                                    }} block icon={<PlusOutlined/>}>添加信息</Button>
                                </Form.Item>
                                <Button type='primary' onClick={saveMsg}>保存信息</Button>
                                <Button style={{marginLeft:10}} type='primary' onClick={submitMsg}>提交审核</Button>
                                <Button style={{marginLeft:10}} type='default' onClick={reset}>重置</Button>
                            </>
                        )}
                    </Form.List>
                </Form>
            </div>
        </div>
    )
}
const mapStateToProps = (state)=>({
    token:state.token,
    detail:state.detail,
    user:state.user
})
export default connect(mapStateToProps)(Create)
