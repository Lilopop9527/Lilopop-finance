import {useEffect, useState} from "react";
import {Button, Form, Input, InputNumber, Space} from "antd";
import {MinusCircleOutlined, PlusOutlined} from "@ant-design/icons";


const ParamsOptionComp:React.FC = (props)=>{
    const { onValuesChange, imageSize, removeNode } = props
    const [count, setCount] = useState(0)
    const [form] = Form.useForm();
    useEffect(() => {
        form.resetFields()
    }, [imageSize])

    return <Form form={form} name="dynamic_form_nest_item"
        // 坑
                         initialValues={{
                             paramAttribute: [{
                                 left: imageSize.width / 2 - 48,
                                 top: imageSize.height / 2 - 24,
                                 content: '文字内容'
                             }]
                         }}
                         onValuesChange={onValuesChange} >

        <Form.List name="paramAttribute">
            {(fields, { add, remove, move }) => (
                <>
                    {fields.map(({ key, name, ...restField }, index) => (
                        <Space key={key} style={{ display: 'flex', marginBottom: 0 }} align="baseline">
                            <Form.Item
                                style={{ marginBottom: '10px' }}
                                {...restField}
                                name={[name, 'content']}
                                label="文字内容"
                                rules={[{ required: true, message: '请输入文字内容' }]}
                            >
                                <Input placeholder="文字内容" maxLength={50} />
                            </Form.Item>
                            <Form.Item
                                style={{ marginBottom: '10px' }}
                                {...restField}
                                name={[name, 'color']}
                                label="文字颜色"
                                rules={[{ required: true, message: '请输入文字颜色' }]}
                                initialValue="#000000"
                            >
                                <Input placeholder="文字颜色" type="color" style={{ width: '80px' }} />
                            </Form.Item>
                            <Form.Item
                                style={{ marginBottom: '10px' }}
                                {...restField}
                                name={[name, 'size']}
                                label="文字大小"
                                rules={[{ required: true, message: '请输入文字大小' }]}
                                initialValue="24"
                            >
                                <InputNumber placeholder="文字大小" min={12} value={13} />
                            </Form.Item>
                            <Form.Item
                                style={{ marginBottom: '10px' }}
                                {...restField}
                                name={[name, 'top']}
                                label="上边距"
                                rules={[{ required: true, message: '请输入上边距' }]}
                                initialValue={imageSize.height / 2 - 24}
                            >
                                <InputNumber placeholder="上边距" value={13} />
                            </Form.Item>
                            <Form.Item
                                style={{ marginBottom: '10px' }}
                                {...restField}
                                name={[name, 'left']}
                                label="左边距"
                                rules={[{ required: true, message: '请输入左边距' }]}
                                initialValue={imageSize.width / 2 - 48}
                            >
                                <InputNumber placeholder="左边距" value={13} />
                            </Form.Item>
                            <MinusCircleOutlined onClick={() => {
                                if (count === 0) {
                                    return
                                }
                                remove(name)
                                removeNode(index)
                                setCount(count => count - 1);
                            }} />
                        </Space>
                    ))}
                    <Form.Item>
                        <Button type="dashed" onClick={async () => {
                            try {
                                const values = await form.validateFields()
                                add();
                                setCount(count => count + 1);
                            } catch (errorInfo) {
                                return;
                            }
                        }} block icon={<PlusOutlined />}>添加选项</Button>
                    </Form.Item>
                </>
            )}
        </Form.List>
    </Form>
}
export default ParamsOptionComp