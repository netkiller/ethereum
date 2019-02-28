# 分行机构接口

## 数据结构

如果你想查看接口的数据结构，可以调用下面接口。

方法：GET

地址：/organization/json


字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
memberId | String | 会员ID，与 member 1:1
company	| String | 分行机构的公司名称
name | String | 联系人
telephone | String | 联系电话
type | String | 资质类别
address | String | 分行机构地址
industry | String | 应用领域
location | Object | 定位信息 {"type": "Point", "coordinates": [12.323560, 12.323456]}   coordinates 数组第一个是 longitude即经度 第二个值是 latitude纬度。
multimedia | Object[] | 资质文件, {"filename":"文件名","url":"路径","extension":"扩展名"}
status	| Enum | Disabled 禁用 (新注册用户是禁用状态), Enabled 启用（艺术银行审批后是启用状态）
createDate | Date | 创建日期
updateDate | Date | 更新日期


操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/organization/json
```

返回结果

```json
{
  "id": null,
  "memberId": "5b8f3d7e917d6a265d2b28cb",
  "company": "XXX 艺术品管理公司",
  "name": "张三",
  "telephone": "0755-11112222",
  "type": "Test",
  "address": "深圳市南山区西丽",
  "industry": "艺术品行业",
  "location": {
    "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
  },
  "multimedia": [
    {
      "filename": "正面图片",
      "url": "/sss/sss.jpg",
      "extension": ".jpg"
    },
    {
      "filename": "侧面图片",
      "url": "/bb/bb.jpg",
      "extension": ".jpg"
    }
  ],
  "status": "Disabled",
  "createDate": "2018-09-17 18:14:56",
  "updateDate": "2018-09-17 18:14:56"
}
```

## 创建分行机构信息

方法：POST

地址：/organization/create

数据：

```json

{
  "memberId": "5b8f3d7e917d6a265d2b28cb",
  "company": "XXX 艺术品管理公司",
  "name": "张三",
  "telephone": "0755-11112222",
  "type": "Test",
  "address": "深圳市南山区西丽",
  "industry": "艺术品行业",
  "location": {
    "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
  },
  "multimedia": [
    {
      "filename": "正面图片",
      "url": "/sss/sss.jpg",
      "extension": ".jpg"
    },
    {
      "filename": "侧面图片",
      "url": "/bb/bb.jpg",
      "extension": ".jpg"
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
	"memberId": "5b8f3d7e917d6a265d2b3888",
    "icon": null,
    "company": "XXX 艺术品管理公司",
    "name": "张三",
    "telephone": "0755-11112222",
    "type": "Test",
    "address": "深圳市南山区西丽",
    "industry": "艺术品行业",
    "location": {
      "x": 12.32356,
      "y": 12.323456,
      "type": "Point",
      "coordinates": [
        12.32356,
        12.323456
      ]
    },
    "multimedia": [
      {
        "filename": "正面图片",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "侧面图片",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ],
    "status": "Disabled",
}
' ${URL}/organization/create | jq
```
	
返回结果

```json
{
  "status": false,
  "reason": "机构信息添加成功",
  "code": 0,
  "data": {
    "id": "5b988b8be203d00b80816436",
    "memberId": "5b8f3d7e917d6a265d2b38cb",
    "company": "XXX 艺术品管理公司",
    "name": "张三",
    "telephone": "0755-11112222",
    "type": "Test",
    "address": "深圳市南山区西丽",
    "industry": "艺术品行业",
    "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
    "multimedia": [
      {
        "filename": "正面图片",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "侧面图片",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ],
    "status": "Disabled",
    "createDate": "2018-09-12 11:44:11",
    "updateDate": null
  }
}
```

## 获得我的机构信息

### 通过 ID 获取信息

方法：GET

地址：/organization/get/{id}


```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/organization/get/5b8f3d7e917d6a265d2b38cb
```

### 通过 会员ID 获取信息

方法：GET

地址：/organization/get/member/{memberId}

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/organization/get/member/5b8f3d7e917d6a265d2b38cb
```

返回结果

```json
{
  "status": true,
  "reason": "机构信息获取成功",
  "code": 0,
  "data": {
    "id": "5b988b8be203d00b80816436",
    "memberId": "5b8f3d7e917d6a265d2b38cb",
    "company": "XXX 艺术品管理公司",
    "name": "张三",
    "telephone": "0755-11112222",
    "type": "Test",
    "address": "深圳市南山区西丽",
    "industry": "艺术品行业",
    "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
    "multimedia": [
      {
        "filename": "正面图片",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "侧面图片",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ],
    "status": "Disabled",
    "createDate": "2018-09-12 11:44:11",
    "updateDate": null
  }
}
```

## 机构列表

### 列出所有机构

方法：GET

地址：/organization/list

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/organization/list
```

### 列出指定状态的机构

方法：GET

地址：/organization/list/{status}/{size}/{page}

演示

```
neo@MacBook-Pro ~ % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/organization/list/Enabled/10/1 | jq
{
  "status": true,
  "reason": "分行机构信息获取成功",
  "code": 0,
  "data": [
    {
      "id": "5bcd8b38432c0010ed6778b1",
      "memberId": "5bcd8b0b432c0010ed6778ad",
      "icon": null,
      "company": "我",
      "name": "我",
      "telephone": "18688939680",
      "type": "你",
      "address": "一本大厦",
      "industry": "书画",
      "location": null,
      "multimedia": [
        {
          "filename": "IMG_2071.JPG",
          "url": "bb434920453683e4d1ffa73ce4d15cd847e5a3bc.JPG",
          "extension": "JPG"
        },
        {
          "filename": "IMG_2072.JPG",
          "url": "6881d8e695c931e2928eee9b85aec6b1912c2d37.JPG",
          "extension": "JPG"
        }
      ],
      "status": "Enabled",
      "createDate": "2018-10-22 16:32:56",
      "updateDate": null
    }
  ]
}
```

返回结果

```
neo@MacBook-Pro ~/workspace/deploy % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/organization/list/Disabled/10/1 | jq
{
  "status": true,
  "reason": "分行机构信息获取成功",
  "code": 0,
  "data": [
    {
      "id": "5bad9027c9278261ad2ee83d",
      "memberId": "",
      "company": "Qin艺术品管理公司",
      "name": "Qin",
      "telephone": "0755-11112222",
      "type": "Test",
      "address": "深圳市南山区西丽",
      "industry": "",
      "location": null,
      "multimedia": [],
      "status": "Disabled",
      "createDate": "2018-09-28 10:21:27",
      "updateDate": null
    },
    {
      "id": "5bad948fc9278266770942dc",
      "memberId": "5bac75c3c9278241099e0efe",
      "company": "Q艺术品管理公司",
      "name": "Qin",
      "telephone": "18688939680",
      "type": "Test",
      "address": "深圳市南山区西丽",
      "industry": "",
      "location": null,
      "multimedia": [],
      "status": "Disabled",
      "createDate": "2018-09-28 10:40:15",
      "updateDate": "2018-09-28 11:28:10"
    }
  ]
}
```

## 更新分行机构信息
### 更新机构信息

方法：POST

地址：/organization/update

数据：{"memberId":"5b8f3d7e917d6a265d2b28cb","company":"XXX 艺术品管理公司","name":"张三","telephone":"0755-11112222","type":"Test","address":"深圳市南山区西丽","industry":"艺术品行业","location":{"longitude":"112'545'6","latitude":"222'566'44"},"multimedia":[{"filename":"正面图片","url":"/sss/sss.jpg","extension":".jpg"},{"filename":"侧面图片","url":"/bb/bb.jpg","extension":".jpg"}]}
	
操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "memberId": "5b8f3d7e917d6a265d2b38cb",
  "company": "XXX 艺术品管理公司",
  "name": "张三",
  "telephone": "0755-11112222",
  "type": "Test",
  "address": "深圳市南山区西丽",
  "industry": "艺术品行业",
  "location": {
    "type": "Point",
    "coordinates": [
      12.32356,
      12.323456
    ]
  },
  "multimedia": [
    {
      "filename": "正面图片",
      "url": "/sss/sss.jpg",
      "extension": ".jpg"
    },
    {
      "filename": "侧面图片",
      "url": "/bb/bb.jpg",
      "extension": ".jpg"
    }
  ]
}
' ${URL}/organization/update
```

### 更新指定数据

方法：POST

地址：/organization/update

数据: {"memberId":"5b8f3d7e917d6a265d2b38cb","status":"Enabled"}

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"memberId":"5b8f3d7e917d6a265d2b38cb","status":"Enabled"}' ${URL}/organization/update
```

返回结果，可以看到 status 已经从 Disabled 变为 Enabled

```json
{"status":true,"reason":"分行机构信息更新成功","code":0,"data":{"id":null,"memberId":"5b8f3d7e917d6a265d2b38cb","company":null,"name":null,"telephone":null,"type":null,"address":null,"industry":null,"location":null,"multimedia":null,"status":"Enabled","createDate":"2018-09-12 12:04:09","updateDate":null}}
```

### 更新定位信息

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{"id":"5b8f4c5d917d6a2730828","memberId":"5b8f3d7e917d6a265d2b3888","location":{"type": "Point","coordinates": [12.323560,12.323456]}}
' ${URL}/organization/update | jq
{
  "status": true,
  "reason": "分行机构信息更新成功",
  "code": 0,
  "data": {
    "id": "5bbb0295e40e5a1c5f931428",
    "memberId": "5b8f3d7e917d6a265d2b3888",
    "icon": null,
    "company": "XXX 艺术品管理公司",
    "name": "张三",
    "telephone": "0755-11112222",
    "type": "Test",
    "address": "深圳市南山区西丽",
    "industry": "艺术品行业",
    "location": {
      "x": 12.32356,
      "y": 12.323456,
      "type": "Point",
      "coordinates": [
        12.32356,
        12.323456
      ]
    },
    "multimedia": [
      {
        "filename": "正面图片",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "侧面图片",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ],
    "status": "Disabled",
    "createDate": "2018-10-08 15:09:09",
    "updateDate": "2018-10-08 15:16:19"
  }
}

```

### 更新图片

更新图片，首先需要调用 /upload/files 接口上传文件

方法：POST

地址：/organization/update

数据: 

```
{
	"id":"5b8f4c5d917d6a2730828",
	"memberId":"5b8f3d7e917d6a265d2b3888",
	"multimedia": [
      {
        "filename": "AAAA",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "BBB",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "CCC",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
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
	"id":"5b8f4c5d917d6a2730828",
	"memberId":"5b8f3d7e917d6a265d2b3888",
	"multimedia": [
      {
        "filename": "AAAA",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "BBB",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "CCC",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ]
}
' ${URL}/organization/update | jq

覆盖演示，下面eee.jpg 会覆盖上面三张图片

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
	"id":"5b8f4c5d917d6a2730828",
	"memberId":"5b8f3d7e917d6a265d2b3888",
	"multimedia": [
      {
        "filename": "EEEE",
        "url": "/eee.jpg",
        "extension": ".jpg"
      }
    ]
}
' ${URL}/organization/update | jq


```

