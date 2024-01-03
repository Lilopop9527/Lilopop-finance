import {useEffect, useRef, useState} from "react";
import ParamsOptionComp from "./ParamsOptionComp";
import {Button} from "antd";
import html2canvas from "html2canvas";


const MaterialEditComp:React.FC = (props)=>{
    const {src,getNewImage} = props
    const [imgSrc,setImageSrc] = useState(src)
    const [imageSize,setImageSize] = useState({width:0,height:0})
    const contentRef = useRef(null)
    const onValuesChange = (changedValues, allValues) => {
        console.log(changedValues, allValues, 11)
        allValues.paramAttribute.forEach((item, index) => {
            item && AddNode(item, index)
        })
    }

    // 新增节点
    const AddNode = ({ content, color, size, left, top }, index) => {
        const contentNode = contentRef.current;
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
        newNode.style.position = 'absolute';

        // 节点不存在新增阶段
        if (!bool) {
            contentNode.appendChild(newNode);
        } else {
            // 节点存在则替换原来的节点
            contentNode.replaceChild(newNode, contentNode.childNodes[index])
        }
    }

    //  删除节点
    const removeNode = (index) => {
        const contentNode = contentRef.current;
        const bool = contentNode?.childNodes[index]
        if (bool) {
            contentNode.removeChild(contentNode.childNodes[index])
        }
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
    }


    useEffect(() => {
        const img = new Image();
        img.src = imgSrc

        img.onload = () => {
            setImageSize({ width: img.width, height: img.height });
            AddNode({
                content: '文字内容',
                color: '#000000',
                size: 24,
                left: img.width / 2 - 48,
                top: img.height / 2 - 24
            }, 0);
        };
    }, []);


    return (
        <>
            <div ref={contentRef} style={{
                background: `url(${imgSrc}) no-repeat`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                width: imageSize.width + 'px',
                height: imageSize.height + 'px',
                marginBottom: '30px',
                marginLeft:'80px'
            }} className="content">

            </div>
            <ParamsOptionComp onValuesChange={onValuesChange} imageSize={imageSize} removeNode={removeNode} />
            <Button type="primary" htmlType="submit" onClick={saveImage} className='btn'>
                生成新的图片
            </Button>
        </>
    )
}
export default MaterialEditComp