import React, {useEffect, useState} from 'react';
import {connect} from "react-redux";
import request from "../../../../../utils/request";
import {Button, message, TreeSelect} from "antd";

const { SHOW_PARENT } = TreeSelect;
const ChangeRoute:React.FC = (props)=>{
    const [select,setSelect] = useState([])
    const id = props.id
    const chi = (item)=>{
        const a = []
        if (item.children){
            item.children.map((item)=>{
                a.push(chi(item))
            })
        }
        return {
            title:item.title,
            value:item.id,
            key:item.id,
            children:a
        }
    }
    const routes = props.routes.map((item,index)=>{
        return chi(item)
    })
    useEffect(()=>{
        getSelect()
        //console.log(routes)
    },[])
    const getSelect= async ()=>{
        const msg = await request({
            url:'/auth/routes/bri',
            method:'get',
            params:{
                id:id
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            const data = []
            if (res.data.data.length>0){
                routes.map((item)=>{
                    dfsSelect(item,res.data.data).map((items)=>{
                        data.push(items)
                    })
                })
            }
            setSelect(data)
        }).catch(function (e) {
        })
    }
    const dfsSelect = (item,selected)=>{
        const data = []
        if (item.children&&item.children.length>0){
            const temp = []
            item.children.map((items)=>{
                const f = dfsSelect(items,selected)
                if (f.length>0){
                    for (let i = 0; i < f.length; i++) {
                        temp.push(f[i])
                    }
                }
            })
            let flag = 0
            if (temp.length === item.children.length){
                for (let i = 0; i < temp.length; i++) {
                    for (let j = 0; j < item.children.length; j++) {
                        if (item.children[j].value === temp[i]){
                            flag++;
                        }
                    }
                }
            }
            if (flag === item.children.length){
                data.push(item.value)
            }else{
               temp.map((items)=>{
                   data.push(items)
               })
            }
        }else{
            for (let i = 0; i < selected.length; i++) {
                if (item.value === selected[i]){
                    data.push(item.value)
                    break
                }
            }
        }
        return data
    }
    const onChange = (newValue)=>{
        setSelect(newValue)
    }
    const dfsChildren = (item,flag)=>{
        const data = []
        if (flag){
            data.push(item.value)
            if (item.children)
                item.children.map((item)=>{
                    dfsChildren(item,flag)
                })
            return data
        }

        for (let i = 0; i < select.length; i++) {
            if (item.value === select[i]){
                flag = true
                break
            }
        }
        if (item.children){
            item.children.map((item)=>{
                const a = dfsChildren(item,flag)
                a.map((item)=>{
                    data.push(item)
                })
            })
        }
        if(data.length>0||flag)
            data.push(item.value)
        return data
    }
    const saveRoutes= async ()=>{
        let data = ''
        routes.map((item)=>{
            const a = dfsChildren(item,false)
            a.map((item)=>{
                data = data + item +','
            })
        })
        data = data.substring(0,data.length-1)
        const msg = await request({
            url:'/auth/routes/crtr',
            method:'put',
            params:{
                rids:data,
                roleId:id
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            const role = props.role
            for (let i = 0; i < role.length; i++) {
                if (role[i].id === id){
                    message["warning"]('当前用户所属角色被修改，建议重新登录')
                    return
                }
            }
            message['success']('修改成功')
        }).catch(function (e) {
            message['error']('修改失败，请稍后再试')
        })
    }
    return (
        <div>
            <TreeSelect treeData={routes}
                        style={{width: 800,marginRight:30}}
                        treeDefaultExpandAll={true}
                        value={select}
                        onChange={onChange}
                        treeCheckable={true}
                        showCheckedStrategy={SHOW_PARENT}
                        placeholder='未选择'
            />
            <Button type='primary' onClick={saveRoutes}>保存</Button>
        </div>
        )
}
const mapStateToProps = (state)=>({
    token:state.token,
    routes:state.routes,
    role:state.role
})
export default connect(mapStateToProps)(ChangeRoute)