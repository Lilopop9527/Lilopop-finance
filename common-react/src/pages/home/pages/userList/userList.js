import React, {Component} from 'react';
import {connect} from "react-redux";
import {Input, message, Radio, Table, Pagination, Image, Select, Button, Tag, Modal} from "antd";
import request from "../../../../utils/request";
import {minioprev} from "../../../../enum/enums";
import './userList.css'
import {departments,stations} from "../../../../stores/auth/action";
import UserDetail from "./userDetail";
const {Search} = Input
class UserList extends Component {
    constructor(props) {
        super(props);
        this.state={
            type:1,
            pageNum:1,
            pageSize:20,
            list:[],
            columns:[
                {
                    title:'编号',
                    dataIndex:'key',
                    key:'key',
                    fixed:'left',
                    align:'center',
                    width:60
                },
                {
                    title:'用户名',
                    dataIndex:'username',
                    key:'username',
                    fixed:'left',
                    align:'center',
                    width: 200
                },
                {
                    title:'手机号',
                    dataIndex:'phone',
                    key:'phone',
                    align: "center",
                    width: 200
                },
                {
                    title:'邮箱',
                    dataIndex:'email',
                    key:'email',
                    align: "center",
                    width: 200
                },
                {
                    title: '状态',
                    dataIndex: 'deleated',
                    key:'deleated',
                    align: "center",
                    width: 80
                },
                {
                    title:'岗位',
                    dataIndex:'stations',
                    key:'stations',
                    align: 'center',
                    width: 150,
                    render:(_,{stations})=>(
                        <>
                            {stations.map((item,index)=>{
                                return(
                                    <Tag color='geekblue' key={index}>{item.name}</Tag>
                                )
                            })}
                        </>
                    )
                },
                {
                    title:'部门',
                    dataIndex:'depts',
                    key:'depts',
                    align: 'center',
                    width: 150,
                    render:(_,{depts})=>(
                        <>
                            {depts.map((item,index)=>{
                                return(
                                    <Tag color='green' key={index}>{item.name}</Tag>
                                )
                            })}
                        </>
                    )
                },
                {
                    title:'头像',
                    dataIndex:'img',
                    key:'img',
                    align: "center",
                    width: 180,
                    render:(text)=><Image src={text} style={{width:70}}/>
                },
                {
                    title: '操作',
                    key:'操作',
                    align: 'center',
                    width: 100,
                    render:(_,record)=>([
                                <Button onClick={()=>this.toDetail(record.id)}>查看详细</Button>,
                                <Button type='primary'
                                        onClick={()=>this.delUser(record.id,record.deleated)}>
                                    {record.deleated === '启用'?'禁用':'启用'}
                                </Button>,
                                <Modal title='详细信息' open={this.state.showDetail === record.id} footer={[]}
                                       onCancel={()=>{
                                           this.setState({
                                               showDetail:-1
                                           })
                                       }}
                                       cancelText='取消'>
                                    <UserDetail  close = {this.closeModal.bind(this)}
                                                user = {record}/>
                                </Modal>
                            ]
                        )
                }
            ],
            depts:[],
            dv:'',
            stations:[],
            select:[],
            showDetail:-1,
            input:<Search placeholder='请输入搜索内容' style={{width:200}} onChange={(e)=>this.changeSearch(e)}
                          allowClear enterButton
                          onSearch={()=>this.searchList(1,this.state.pageSize)}/>
        }
    }
    componentDidMount() {
        this.searchAll(1,20)
        this.getDepts()
        this.getStations()
    }

    changeType(e){
        const type = e.target.value
        this.setState({
            type:type
        })
        if (type === 5){
            this.setState({
                input:<Select
                    style={{width:200}}
                    options={this.state.depts}
                    onChange={(e)=>this.saveDeptValue(e)}
                />
            })
        }else if (type === 6){
            this.setState({
                input:<Select
                    style={{width:200}}
                    options={this.state.stations}
                    onChange={(e)=>this.saveStationValue(e)}
                />
            })
        }else{
            this.setState({
                input:<Search placeholder='请输入搜索内容' style={{width:200}} onChange={(e)=>this.changeSearch(e)}
                              allowClear enterButton
                              onSearch={()=>this.searchList(1,this.state.pageSize)}/>
            })
        }
    }
    searchList(pageNum,pageSize){
        const local = this

        switch (local.state.type) {
            case 1:
                local.searchAll(pageNum,pageSize)
                break
            case 2:
                local.searchByUsername(pageNum,pageSize)
                break
            case 3:
                local.searchByEmail(pageNum,pageSize)
                break
            case 4:
                local.searchByPhone(pageNum,pageSize)
                break
            case 5:
                local.searchByDept(pageNum,pageSize,this.state.dv)
                break
            case 6:
                local.searchByStation(pageNum,pageSize,this.state.stv)
                break
        }
    }
    async searchAll(pageNum,pageSize){
        const local = this
        let pn = pageNum
        let pz = pageSize
        if (local.state.pageSize !== pageSize){
            local.setState({
                pageSize:pageSize
            })
        }else
            pz = local.state.pageSize
        const msg = await request({
            url:"/auth/l/all",
            method:"get",
            params:{
                pageNum:pn,
                pageSize:pz
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            local.createData(res,pn,pz)
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })

    }

    async searchByUsername(pageNum,pageSize){
        const local = this
        let pn = pageNum
        let pz = pageSize
        if (local.state.pageSize !== pageSize){
            local.setState({
                pageSize:pageSize
            })
        }
        if (pn === 0)pn=1
        const msg = await request({
            url:"/auth/l/ur",
            method:"get",
            params:{
                pageNum:pn,
                pageSize:pz,
                username:local.state.sv
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            local.createData(res,pn,pz)
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async searchByEmail(pageNum,pageSize){
        const local = this
        let pn = pageNum
        let pz = pageSize
        if (local.state.pageSize !== pageSize){
            local.setState({
                pageSize:pageSize
            })
        }
        if (pn === 0)pn=1
        const msg = await request({
            url:"/auth/l/e",
            method:"get",
            params:{
                pageNum:pn,
                pageSize:pz,
                email:local.state.sv
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            local.createData(res,pn,pz)
            // const data = []
            // res.data.data.data.map((item,index)=>{
            //     const url = item.img;
            //     item.img = minioprev+url
            //     const status = item.deleated
            //     item.deleated = status === 0?'启用':'禁用'
            //     data.push({
            //         ...item,
            //         key:(pn-1)*pz+index+1
            //     })
            // })
            // local.setState({
            //     total:res.data.data.total,
            //     list:data,
            //     pageNum:res.data.data.pageNum,
            //     pageSize:res.data.data.pageSize
            // })
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async searchByPhone(pageNum,pageSize){
        const local = this
        const pattern = /^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\d{8}$/
        if (!pattern.test(local.state.sv)){
            message['error']("手机号不合法，请检查输入")
            return
        }
        let pn = pageNum
        let pz = pageSize
        if (local.state.pageSize !== pageSize){
            local.setState({
                pageSize:pageSize
            })
        }
        if (pn === 0)pn=1
        const msg = await request({
            url:"/auth/l/p",
            method:"get",
            params:{
                pageNum:pn,
                pageSize:pz,
                phone:local.state.sv
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            local.createData(res,pn,pz)
            // const data = []
            // res.data.data.data.map((item,index)=>{
            //     const url = item.img;
            //     item.img = minioprev+url
            //     const status = item.deleated
            //     item.deleated = status === 0?'启用':'禁用'
            //     data.push({
            //         ...item,
            //         key:(pn-1)*pz+index+1
            //     })
            // })
            // local.setState({
            //     total:res.data.data.total,
            //     list:data,
            //     pageNum:res.data.data.pageNum
            // })
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async searchByDept(pageNum,pageSize,value){
        const local = this
        let pn = pageNum
        let pz = pageSize
        if (local.state.pageSize !== pageSize){
            local.setState({
                pageSize:pageSize
            })
        }
        if (pn === 0)pn=1
        const msg = await request({
            url:"/auth/l/dept",
            method:"get",
            params:{
                pageNum:pn,
                pageSize:pz,
                deptId:value
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            local.createData(res,pn,pz)
            // const data = []
            // res.data.data.data.map((item,index)=>{
            //     const url = item.img;
            //     item.img = minioprev+url
            //     const status = item.deleated
            //     item.deleated = status === 0?'启用':'禁用'
            //     data.push({
            //         ...item,
            //         key:(pn-1)*pz+index+1
            //     })
            // })
            // local.setState({
            //     total:res.data.data.total,
            //     list:data,
            //     pageNum:res.data.data.pageNum,
            //     pageSize:res.data.data.pageSize
            // })
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async searchByStation(pageNum,pageSize,value){
        const local = this
        let pn = pageNum
        let pz = pageSize
        if (local.state.pageSize !== pageSize){
            local.setState({
                pageSize:pageSize
            })
        }
        if (pn === 0)pn=1
        const msg = await request({
            url:"/auth/l/sta",
            method:"get",
            params:{
                pageNum:pn,
                pageSize:pz,
                staId:value
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            local.createData(res,pn,pz)
            // const data = []
            // res.data.data.data.map((item,index)=>{
            //     const url = item.img;
            //     item.img = minioprev+url
            //     const status = item.deleated
            //     item.deleated = status === 0?'启用':'禁用'
            //     data.push({
            //         ...item,
            //         key:(pn-1)*pz+index+1
            //     })
            // })
            // local.setState({
            //     total:res.data.data.total,
            //     list:data,
            //     pageNum:res.data.data.pageNum,
            //     pageSize:res.data.data.pageSize
            // })
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }

    changeSearch(e) {
        this.setState({
            sv: e.target.value
        })
    }

    async getDepts(){
        const local = this
        if (local.props.department){
            const list = local.props.department
            const data = []
            list.map((item)=>{
                data.push({
                    value:item.id,
                    label:item.name,
                    key:item.id
                })
            })
            local.setState({
                depts:data
            })
            return
        }
        const msg = await request({
            url: '/auth/dept/l',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const temp = []
            res.data.data.map((item,index)=>{
                const t = {
                    value:item.id,
                    label:item.name,
                    key:item.id
                }
                temp.push(t)
            })
            local.setState({
                depts:temp
            })
            local.props.dispatch(departments(res.data.data))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async getStations(){
        const local = this
        if (local.props.station){
            const list = local.props.station
            const data = []
            list.map((item)=>{
                data.push({
                    value:item.id,
                    label:item.name,
                    key:item.id
                })
            })
            local.setState({
                stations:data
            })
            return
        }
        const msg = await request({
            url: '/auth/sta/l',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const temp = []
            res.data.data.map((item,index)=>{
                const t = {
                    value:item.id,
                    label:item.name,
                    key:item.id
                }
                temp.push(t)
            })
            local.setState({
                stations:temp
            })
            local.props.dispatch(stations(res.data.data))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    toDetail(item){
        this.setState({
            showDetail:item
        })
    }
    closeModal(){
        this.toDetail(-1)
    }
    async delUser(id,del){
        const local = this
        const msg = await request({
            url:'/auth/user/del',
            method:'put',
            params:{
                id:id,
                del:del==='启用'?1:0
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const list = local.state.list
            const l = []
            for (let i = 0; i < list.length; i++) {
                const d = res.data.data
                if (list[i].id === d.id){
                    const img = d.img
                    const status = d.deleated
                    const index = list[i].key
                    d.img = minioprev+img
                    d.deleated = status === 0?'启用':'禁用'
                    list[i] = {
                        ...d,
                        key:index
                    }
                }
                l.push(list[i])
            }
            local.setState({
                list:l
            })
            message['success']('修改成功')
        }).catch(function (e) {
            message['error']('修改失败')
        })
    }
    changeSelect(selectedRowKeys){
        this.setState({
            select:selectedRowKeys
        })
    }
    async delAll(){
        const local = this
        let l1 = ''
        let l2 = ''
        const pn = local.state.pageNum
        const pz = local.state.pageSize
        const list = local.state.list
        const select = local.state.select
        for (let i = 0; i < select.length; i++) {
            let t = select[i]
            t = t-(pn-1)*pz-1
            list[t].deleated = list[t].deleated === '启用'?'禁用':'启用'
        }
        for (let i = 0; i < list.length-1; i++) {
            l1 += list[i].id+','
            l2 += (list[i].deleated==='启用'?0:1) +','
        }
        l1+=list[list.length-1].id
        l2+=(list[list.length-1].deleated==='启用'?0:1)
        const msg = await request({
            url:'/auth/user/dAll',
            method:'put',
            params:{
                ids:l1,
                status:l2
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = []
            res.data.data.map((item,index)=>{
                const url = item.img;
                item.img = minioprev+url
                const status = item.deleated
                item.deleated = status === 0?'启用':'禁用'
                data.push({
                    ...item,
                    key:(pn-1)*pz+index+1
                })
            })
            local.setState({
                list:data
            })
            local.setState({
                select:[]
            })
            message['success']('修改完成')
        }).catch(function (err) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    saveDeptValue(e){
        this.setState((state,props)=>{
            return{
                dv:e
            }
        })
        this.searchByDept(1,this.state.pageSize,e)
    }
    saveStationValue(e){
        this.setState((state,props)=>{
            return{
                stv:e
            }
        })
        this.searchByStation(1,this.state.pageSize,e)
    }
    createData(res,pn,pz){
        const data = []
        res.data.data.data.map((item,index)=>{
            const url = item.img;
            item.img = minioprev+url
            const status = item.deleated
            item.deleated = status === 0?'启用':'禁用'
            data.push({
                ...item,
                key:(pn-1)*pz+index+1
            })
        })
        this.setState({
            total:res.data.data.total,
            list:data,
            pageNum:res.data.data.pageNum,
            pageSize:res.data.data.pageSize
        })
    }
    render() {
        const {columns,pageSize,input,select} = this.state
        return (
            <div className='userlist-wrap'>
                <div className='search'>
                    {input}
                    <Radio.Group onChange={(e)=>this.changeType(e)} value={this.state.type} style={{paddingLeft:15}}>
                        <Radio value={1}>全部</Radio>
                        <Radio value={2}>用户名</Radio>
                        <Radio value={3}>邮箱</Radio>
                        <Radio value={4}>手机号</Radio>
                        <Radio value={5}>部门</Radio>
                        <Radio value={6}>岗位</Radio>
                    </Radio.Group>
                </div>
                <Table columns={columns} dataSource={this.state.list} scroll={{x:1500,y:640}}
                       pagination={false}
                       rowSelection={{
                           columnTitle:'',
                           columnWidth:25,
                           fixed:true,
                           selectedRowKeys:select,
                           onChange:this.changeSelect.bind(this)
                       }
                       }
                />
                <Button type='primary' onClick={this.delAll.bind(this)}>批量修改</Button>
                <Pagination className='pagination'
                    total={this.state.total}
                    showSizeChanger
                    showQuickJumper
                    showTotal={(total, range) => {
                        return '共'+this.state.total+'条'
                    }}
                    pageSize={this.state.pageSize}
                    onChange={(page, pageSize) => this.searchList(page,pageSize)}
                />
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    user:state.user,
    department:state.departments,
    station:state.stations
})
export default connect(mapStateToProps)(UserList);