# 资产接口

## 获取 Token

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```

## 数据结构

如果你想查看接口的数据结构，可以调用下面接口。

方法：GET

地址：/assets/json


字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
transactionId | String | 区块链哈希值, null 表示没有上链，上链后 05e1c7d40814d050f1d4d32fbf91fec33f32a83df74e11a4071bafaf7f0e7c71
uuid | String | 次产唯一代码
qrcode | String | 二维码
nfc | String | NFC唯一地址UID，用来防止机构使用未授权标签
organizationId | String | 资产隶书组织
memberId | String | 权益持有人
name | String | 名称
description | String | 描述
thumbnail | String | 缩图
type | String | 类型
attribute | Map | **废弃** 暂时保留 属性，类型是Map/Dict，由 key: value 组成，数量不限 暂未使用请传空值
location | Object | 定位信息 {"type": "Point", "coordinates": [12.323560, 12.323456]}   coordinates 数组第一个是 longitude即经度 第二个值是 latitude纬度。 x, y 两个数值请忽略。
hypermedia | Object[] | 资质文件, {"hash":"IPFS Hash 值","name":"文件名","size":"暂未使用"}
status	| Enum | New 新提交信息, Pending 机构审核, Rejected 拒绝, Approved 通过。 默认是 New，机构审批 Pending 艺术银审批后 Approved 同时上链。
createDate | Date | 创建日期
updateDate | Date | 更新日期


操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/json | jq
```

返回结果

```json
{
  "status": true,
  "reason": "创建成功",
  "code": 0,
  "data": {
    "id": null,
    "organizationId": "12",
    "memberId": "1234",
    "uuid": "20f786db-5cbf-4525-9526-1e88c91d0dd0",
    "transactionId": null,
    "nfc": null,
    "qrcode": "20f786db-5cbf-4525-9526-1e88c91d0dd0",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。该作品以长卷形式，立足传统，画面细致入微，烟波浩渺的江河、层峦起伏的群山构成了一幅美妙的江南山水图，渔村野市、水榭亭台、茅庵草舍、水磨长桥等静景穿插捕鱼、驶船、游玩、赶集等动景，动静结合恰到好处。",
    "type": "书画",
    "thumbnail": "http://xxxxx/xxxx.jpg",
    "attribute": {
      "检验结论": "天然翡翠挂件（A）",
      "总质量": "5.64g",
      "形状": "圆环形",
      "贵金属标注": "-----",
      "放大检查": "纤维交织结构"
    },
    "location": {
      "x": 132.32356,
      "y": 12.323456,
      "type": "Point",
      "coordinates": [
        132.32356,
        12.323456
      ]
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "New",
    "createDate": "2018-10-09 16:11:19",
    "updateDate": null
  }
}
```

## 创建资产

方法：POST

地址：/assets/create

** hypermedia 数组第一张图会作为封面 **

数据：

```json

{
  "organizationId": "5b8f4c5d917d6a2730828be3",
  "memberId": "1234",
  "uuid": "1369EF66-AA33-4C40-AC38-40465F44D2DB",
  "nfc": "3D89245",
  "name": "千里江山图",
  "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
  "type": "书画",
  "attribute": {
    "检验结论": "天然翡翠挂件（A）",
    "总质量": "5.64g",
    "形状": "圆环形",
    "贵金属标注": "-----",
    "放大检查": "纤维交织结构"
  },
  "location": {
    "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
  },
  "status": "New",
  "createDate": "2018-09-19 11:18:19",
  "updateDate": null,
  "hypermedia": [
    {
      "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
      "name": "aaa.jpg",
      "size": ""
    },
    {
      "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
      "name": "bbb.jpg",
      "size": ""
    }
  ]
}

```
	
操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "organizationId": "5b8f4c5d917d6a2730828be3",
  "memberId": "1234",
  "uuid": "1369EF66-AA33-4444-ACC8-40445F44D2DF",
  "nfc": "2D0E8FE",
  "name": "千里江山图",
  "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
  "type": "书画",
  "attribute": {
    "检验结论": "天然翡翠挂件（A）",
    "总质量": "5.64g",
    "形状": "圆环形",
    "贵金属标注": "-----",
    "放大检查": "纤维交织结构"
  },
  "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
  "status": "New",
  "createDate": "2018-09-19 11:18:19",
  "updateDate": null,
  "hypermedia": [
    {
      "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
      "name": "aaa.jpg",
      "size": ""
    },
    {
      "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
      "name": "bbb.jpg",
      "size": ""
    }
  ]
}
' ${URL}/assets/create | jq
```

返回结果

```
{
  "status": true,
  "reason": "资产添加成功",
  "code": 0,
  "data": {
    "id": "5bab0d6ec927821a9c02125f",
    "organizationId": "12",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c47-ac38-40465f44d2db",
    "transactionId": null,
    "nfc": "2D0E8F",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "检验结论": "天然翡翠挂件（A）",
      "总质量": "5.64g",
      "形状": "圆环形",
      "贵金属标注": "-----",
      "放大检查": "纤维交织结构"
    },
    "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "New",
    "createDate": "2018-09-26 12:39:10",
    "updateDate": null
  }
}
```

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "organizationId": "5b8f4c5d917d6a2730828be3",
  "name": "千里江山图",
  "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
  "type": "书画"
}
' ${URL}/assets/create | jq
```

## 查询资产

### 通过 UUID 查询

方法：GET

地址：/assets/get/uuid/{uuid}

```
neo@MacBook-Pro ~/workspace/deploy % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/get/uuid/1369ef66-aa33-4c44-ac38-40445f44d2db | jq
{
  "status": true,
  "reason": "获取资产列表成功",
  "code": 0,
  "data": {
    "id": "5badc5d5c9278272e6c7b9c4",
    "organizationId": "5b8f4c5d917d6a2730828be3",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c44-ac38-40445f44d2db",
    "transactionId": "abcd",
    "nfc": "2D0E8FE",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "检验结论": "天然翡翠挂件（A）",
      "总质量": "5.64g",
      "形状": "圆环形",
      "贵金属标注": "-----",
      "放大检查": "纤维交织结构"
    },
    "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "New",
    "createDate": "2018-09-28 14:10:29",
    "updateDate": null
  }
}


```

### 通过 ID 获取资产信息

方法：GET

地址：/assets/get/{id}

```
neo@MacBook-Pro ~/workspace/deploy % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/get/5badc5d5c9278272e6c7b9c4 | jq
{
  "status": true,
  "reason": "获取资产列表成功",
  "code": 0,
  "data": {
    "id": "5badc5d5c9278272e6c7b9c4",
    "organizationId": "5b8f4c5d917d6a2730828be3",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c44-ac38-40445f44d2db",
    "transactionId": "abcd",
    "nfc": "2D0E8FE",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "检验结论": "天然翡翠挂件（A）",
      "总质量": "5.64g",
      "形状": "圆环形",
      "贵金属标注": "-----",
      "放大检查": "纤维交织结构"
    },
    "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "New",
    "createDate": "2018-09-28 14:10:29",
    "updateDate": null
  }
}

```


### 通过 transactionId 查询资产数据


方法：GET

地址：/assets/get/transaction/{transactionId}

操作与演示结果

```

curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/get/transaction/5a61f0d8436c9c5ec06237d48b9a46d3f236c7e1338b55b3841ab2bad1509073 | jq
{
  "status": true,
  "reason": "获取资产列表成功",
  "code": 0,
  "data": {
    "id": "5bab3f98c9278237d6bb4a76",
    "organizationId": "12",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c47-ac38-40445f44d2db",
    "transactionId": "5a61f0d8436c9c5ec06237d48b9a46d3f236c7e1338b55b3841ab2bad1509073",
    "nfc": "2D0E8FO",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "检验结论": "天然翡翠挂件（A）",
      "总质量": "5.64g",
      "形状": "圆环形",
      "贵金属标注": "-----",
      "放大检查": "纤维交织结构"
    },
    "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "Approved",
    "createDate": "2018-09-26 16:13:12",
    "updateDate": "2018-09-26 16:14:03"
  }
}

```

### 通过二维码查询资产数据


方法：GET

地址：/assets/get/qrcode/{qrcode}

操作与演示结果

```
```


### 通过资产数据，含历史信息，鉴定信息，机构信息


方法：GET

地址：/assets/get/allinone/{id}

操作与演示结果

```

curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/get/allinone/5bab3f98c9278237d6bb4a76 | jq


```

返回结果

```json
{
  "status": true,
  "reason": "获取资产列表成功",
  "code": 0,
  "data": {
    "Assets": {
      "id": "5bbabff3e40e5a14ac5485bb",
      "organizationId": "5b8f4c5d917d6a2730828be3",
      "memberId": "1234",
      "uuid": "1369ef66-aa33-4444-acc8-40445f44d2df",
      "transactionId": "",
      "nfc": "2D0E8FE",
      "qrcode": null,
      "name": "千里江山图",
      "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
      "type": "书画",
      "thumbnail": null,
      "attribute": {
        "检验结论": "天然翡翠挂件（A）",
        "总质量": "5.64g",
        "形状": "圆环形",
        "贵金属标注": "-----",
        "放大检查": "纤维交织结构"
      },
      "location": {
        "x": 12.32356,
        "y": 12.323456,
        "type": "Point",
        "coordinates": [
          12.32356,
          12.323456
        ]
      },
      "hypermedia": [
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "aaa.jpg",
          "size": ""
        },
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "bbb.jpg",
          "size": ""
        }
      ],
      "status": "New",
      "createDate": "2018-10-08 10:24:51",
      "updateDate": null
    },
    "History": [
      {
        "id": "5ba30babdb09117085d088ab",
        "assetsId": "5bbabff3e40e5a14ac5485bb",
        "title": "添加信息",
        "message": "信息内容",
        "status": "录入",
        "createDate": "2018-09-20 10:53:31",
        "updateDate": null
      },
      {
        "id": "5badd9d3c9278287f30ceab0",
        "assetsId": "5bbabff3e40e5a14ac5485bb",
        "title": "添加信息",
        "message": "信息内容~我来自远方，像一粒尘土",
        "status": "录入",
        "createDate": "2018-09-28 15:35:47",
        "updateDate": null
      }
    ],
    "Certificate": [
      {
        "id": "5b8f8b3d917d6a2929aae8b7",
        "assetsId": "5bbabff3e40e5a14ac5485bb",
        "memberId": "123",
        "name": "张三",
        "remark": "青花瓷，鉴定物品复合年代特征",
        "status": null,
        "createDate": "2018-09-05 15:52:29",
        "updateDate": null
      }
    ],
    "Organization": {
      "id": "5b8f4c5d917d6a2730828be3",
      "memberId": "5b8f3d7e917d6a265d2b28cb",
      "icon": null,
      "company": null,
      "name": "尚宝轩",
      "telephone": null,
      "type": null,
      "address": "深圳市南山区西丽",
      "industry": null,
      "location": {
        "x": 12.32356,
        "y": 12.323456,
        "type": "Point",
        "coordinates": [
          12.32356,
          12.323456
        ]
      },
      "multimedia": null,
      "status": "New",
      "createDate": "2018-10-08 15:05:15",
      "updateDate": "2018-10-08 15:05:15"
    },
    "Comment": []
  }
}
```

## 更新资产数据


方法：POST

地址：/assets/update

数据：{"id":"5bab0d6ec927821a9c02125f", ...... }

举例：

1. 更新状态  {"id":"5bab0d6ec927821a9c02125f", "status":"Approved" }
1. 更新名称 {"id":"5bab0d6ec927821a9c02125f", "name":"江山多娇" }

操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{"id": "5bab0d6ec927821a9c02125f","organizationId":"12","memberId":"1234","uuid":"1369EF66-AA33-4C47-AC38-40465F44D2DB","nfc":"2D0E8F","name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画","attribute":{"检验结论":"天然翡翠挂件（A）","总质量":"5.64g","形状":"圆环形","贵金属标注":"-----","放大检查":"纤维交织结构"},"location":{"type": "Point","coordinates": [ 12.323560,12.323456]},"status":"New","createDate":"2018-09-19 11:18:19","updateDate":null,"hypermedia":[{"hash":"QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn","name":"aaa.jpg","size":""},{"hash":"QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn","name":"bbb.jpg","size":""}]}
' ${URL}/assets/update | jq
```

返回结果

```
{
  "status": true,
  "reason": "资产更新成功",
  "code": 0,
  "data": {
    "id": "5bab0d6ec927821a9c02125f",
    "organizationId": "12",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c47-ac38-40465f44d2db",
    "transactionId": null,
    "nfc": "2D0E8F",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "检验结论": "天然翡翠挂件（A）",
      "总质量": "5.64g",
      "形状": "圆环形",
      "贵金属标注": "-----",
      "放大检查": "纤维交织结构"
    },
    "location": {
      "longitude": "1125456",
      "latitude": "22256644"
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "New",
    "createDate": "2018-09-26 12:39:10",
    "updateDate": "2018-09-26 12:46:27"
  }
}
```

### 更新图片

hypermedia 数组第一张图会作为封面

方法：POST

地址：/assets/update

数据：

```json
{
	"id":"5bbdb8ee7099aa05e4ddb34d", 
	"hypermedia": [
	 	{"hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn","name": "aaa.jpg"}
     ]
}
```

演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{	
	"id": "5bd6b7490fbbf9439fac7646", 
	"hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg"
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg"
      },
      {
        "hash": "aaaLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg"
      }
    ]

}
' ${URL}/assets/update | jq
```

### 更新二维码或NFC码

方法：POST

地址：/assets/update

数据：

```json
{
	"id":"5bbdb8ee7099aa05e4ddb34d", 
	"qrcode": "10a41bb3-b1ef-48fe-ac0c-b6afe2ddf2cb"
}
```

演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{	
	"id": "5bc05419fd955710cfbe9f16", 
	"qrcode": "10a41bb3-b1ef-48fe-ac0c-b6afe2ddf2cb"

}
' ${URL}/assets/update | jq
```

返回结果

```
{
  "status": true,
  "reason": "资产更新成功",
  "code": 0,
  "data": {
    "id": "5bc05419fd955710cfbe9f16",
    "organizationId": "5bbc65019ea70d08cd5dea60",
    "memberId": "5bbc5d299ea70d08a0b16e04",
    "uuid": null,
    "qrcode": "10a41bb3-b1ef-48fe-ac0c-b6afe2ddf2cb",
    "transactionId": null,
    "nfc": null,
    "name": "我们",
    "description": "我们",
    "type": "青铜器",
    "thumbnail": null,
    "attribute": null,
    "location": null,
    "hypermedia": null,
    "status": "New",
    "createDate": "2018-10-12 15:58:17",
    "updateDate": "2018-10-17 14:46:41"
  }
}
```

### 审批资产状态

审批只需提供 id 和 status

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{"id": "5bab3f98c9278237d6bb4a76", "status":"Approved"}
' ${URL}/assets/update | jq
```

返回结果, 可以看到 "transactionId": "5a61f0d8436c9c5ec06237d48b9a46d3f236c7e1338b55b3841ab2bad1509073"

```
{
  "status": true,
  "reason": "资产更新成功",
  "code": 0,
  "data": {
    "id": "5bab3f98c9278237d6bb4a76",
    "organizationId": "12",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c47-ac38-40445f44d2db",
    "transactionId": "5a61f0d8436c9c5ec06237d48b9a46d3f236c7e1338b55b3841ab2bad1509073",
    "nfc": "2D0E8FO",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "检验结论": "天然翡翠挂件（A）",
      "总质量": "5.64g",
      "形状": "圆环形",
      "贵金属标注": "-----",
      "放大检查": "纤维交织结构"
    },
    "location": {
      "longitude": "1125456",
      "latitude": "22256644"
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "Approved",
    "createDate": "2018-09-26 16:13:12",
    "updateDate": "2018-09-26 16:14:03"
  }
}

```

使用 UUID 从区块链上获取数据 

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/blockchain/assets/1369ef66-aa33-4c47-ac38-40445f44d2db | jq
{
  "status": true,
  "reason": "创建成功",
  "code": 0,
  "data": {
    "id": "5bab3f98c9278237d6bb4a76",
    "organizationId": "12",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c47-ac38-40445f44d2db",
    "transactionId": null,
    "nfc": "2D0E8FO",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "形状": "圆环形",
      "总质量": "5.64g",
      "放大检查": "纤维交织结构",
      "检验结论": "天然翡翠挂件（A）",
      "贵金属标注": "-----"
    },
    "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "Approved",
    "createDate": "2018-09-26 16:13:12",
    "updateDate": null
  }
}
```



## 获取次产列表

### 获取所有资产列表

方法：GET

地址：/assets/list/status/{status}/{size}/{page}

范例：/assets/list/status/Pending/10/1  表示取 10 条，第一页。注意页码从 1 开始

操作与演示结果

```
% curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/list/status/Pending/10/1 | jq
{
  "status": true,
  "reason": "获取资产列表成功",
  "code": 0,
  "data": [
    {
      "id": "5bac52d7c927823c95940c18",
      "organizationId": "5b8f4c5d917d6a2730828be3",
      "memberId": "1234",
      "uuid": "1369ef66-aa33-4c47-ac38-40445f44d2db",
      "transactionId": "",
      "nfc": "2D0E8FO",
      "name": "千里江山图",
      "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
      "type": "书画",
      "attribute": {
        "检验结论": "天然翡翠挂件（A）",
        "总质量": "5.64g",
        "形状": "圆环形",
        "贵金属标注": "-----",
        "放大检查": "纤维交织结构"
      },
      "location": {
        "type": "Point",
	      "coordinates": [
	        12.323560,
	        12.323456
	      ]
      },
      "hypermedia": [
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "aaa.jpg",
          "size": ""
        },
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "bbb.jpg",
          "size": ""
        }
      ],
      "status": "Pending",
      "createDate": "2018-09-27 11:47:35",
      "updateDate": null
    }
  ]
}
```

### 获取机分行构的资产列表

方法：GET

地址：/assets/list/organization/{organizationId}/{size}/{page}

范例：/assets/list/organization/5b8f4c5d917d6a2730828be3/10/1  表示取 10 条，第一页。注意页码从 1 开始

操作与演示结果

```
% curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/list/organization/5b8f4c5d917d6a2730828be3/10/1 | jq
{
  "status": true,
  "reason": "获取资产列表成功",
  "code": 0,
  "data": [
    {
      "id": "5bac52d7c927823c95940c18",
      "organizationId": "5b8f4c5d917d6a2730828be3",
      "memberId": "1234",
      "uuid": "1369ef66-aa33-4c47-ac38-40445f44d2db",
      "transactionId": "",
      "nfc": "2D0E8FO",
      "name": "千里江山图",
      "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
      "type": "书画",
      "attribute": {
        "检验结论": "天然翡翠挂件（A）",
        "总质量": "5.64g",
        "形状": "圆环形",
        "贵金属标注": "-----",
        "放大检查": "纤维交织结构"
      },
      "location": {
        "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
      },
      "hypermedia": [
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "aaa.jpg",
          "size": ""
        },
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "bbb.jpg",
          "size": ""
        }
      ],
      "status": "New",
      "createDate": "2018-09-27 11:47:35",
      "updateDate": null
    }
  ]
}
```

### 获取分行机构特定状态的列表

方法：GET

地址：/assets/list/organization/{organizationId}/{status}/{size}/{page}

参数：status New 新提交信息, Pending 机构审核, Rejected 拒绝, Approved 通过

范例：/assets/list/organization/5b8f4c5d917d6a2730828be3/New/10/1  表示取 10 条，第一页。注意页码从 1 开始

操作与演示结果

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/list/organization/5b8f4c5d917d6a2730828be3/New/10/1 | jq
{
  "status": true,
  "reason": "获取资产列表成功",
  "code": 0,
  "data": [
    {
      "id": "5bac52d7c927823c95940c18",
      "organizationId": "5b8f4c5d917d6a2730828be3",
      "memberId": "1234",
      "uuid": "1369ef66-aa33-4c47-ac38-40445f44d2db",
      "transactionId": "",
      "nfc": "2D0E8FO",
      "name": "千里江山图",
      "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
      "type": "书画",
      "attribute": {
        "检验结论": "天然翡翠挂件（A）",
        "总质量": "5.64g",
        "形状": "圆环形",
        "贵金属标注": "-----",
        "放大检查": "纤维交织结构"
      },
      "location": {
        "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
      },
      "hypermedia": [
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "aaa.jpg",
          "size": ""
        },
        {
          "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
          "name": "bbb.jpg",
          "size": ""
        }
      ],
      "status": "New",
      "createDate": "2018-09-27 11:47:35",
      "updateDate": null
    }
  ]
}
```

## 资产删除

方法：GET

地址：/assets/remove/{id}

数据：

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/remove/5ba30babdb09117085d088ab | jq
```

** 注意，Approved 状态的资产不允许删除。 **
