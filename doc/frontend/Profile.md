# User profile 用户资料

## 获取 Token

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```

## 数据结构

如果你想查看接口的数据结构，可以调用下面接口。

方法：GET

地址：/profile/json


字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
memberId | String | 会员ID
icon | String | 头像
nickname | String | 昵称
birthday | Date | 生日
name | String | 姓名
gender | Enum | Male 男 Female 女
age | String | 年龄
address | String | 地址
idCard | String | 证件类型
idNumber | String | 证件号码
location | Object | 定位信息 {"longitude":"经度","latitude":"纬度"}
multimedia | Object[] | 身份证照片等等
status	| Enum | Disabled 禁用, Enabled 启用（默认）
createDate | Date | 创建日期
updateDate | Date | 更新日期


操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/profile/json | jq
```

返回结果

```json
{
  "id": null,
  "memberId": "00000",
  "icon": "http://wwww.xxx.com/xxx.jpg",
  "nickname": "送森更温柔",
  "name": "张三",
  "gender": "Male",
  "age": "18",
  "birthday": "2018-10-30 10:15:46",
  "address": null,
  "idCard": "身份证",
  "idNumber": "235364375687234534",
  "location": {
    "x": 112.23465,
    "y": 12.245676,
    "type": "Point",
    "coordinates": [
      112.23465,
      12.245676
    ]
  },
  "multimedia": [
    {
      "filename": "aaa",
      "url": "/sss/sss.jpg",
      "extension": ".jpg"
    },
    {
      "filename": "bbb",
      "url": "/bb/bb.jpg",
      "extension": ".jpg"
    }
  ],
  "status": "Enabled",
  "createDate": "2018-10-30 10:15:46",
  "updateDate": null
}
```

## 创建用户资料

方法：POST

地址：/profile/create

数据：

```json
{
  "memberId": "000001",
  "icon": "http://wwww.xxx.com/xxx.jpg",
  "nickname": "送森更温柔",
  "name": "张三",
  "gender": "Male",
  "age": "18",
  "birthday": "2018-09-28 17:45:19",
  "address": null,
  "idCard": "身份证",
  "idNumber": "235364375687234534",
  "location": {
    "type": "Point",
    "coordinates": [
      112.23465,
      12.245676
    ]
  },
  "multimedia": [
    {
      "filename": "aaa",
      "url": "/sss/sss.jpg",
      "extension": ".jpg"
    },
    {
      "filename": "bbb",
      "url": "/bb/bb.jpg",
      "extension": ".jpg"
    }
  ]
}
```

演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d'
{
  "memberId": "001111",
  "icon": "http://wwww.xxx.com/xxx.jpg",
  "nickname": "送森更温柔",
  "name": "张三",
  "gender": "Male",
  "age": "18",
  "birthday": "2018-09-28 17:45:19",
  "address": null,
  "idCard": "身份证",
  "idNumber": "235364375687234534",
  "location": {
    "type": "Point",
    "coordinates": [
      12.23465,
      12.24567
    ]
  },
  "multimedia": [
    {
      "filename": "aaa",
      "url": "/sss/sss.jpg",
      "extension": ".jpg"
    },
    {
      "filename": "bbb",
      "url": "/bb/bb.jpg",
      "extension": ".jpg"
    }
  ]
}
'  http://localhost:8080/profile/create | jq
```

返回结果

```
{
  "status": true,
  "reason": "添加成员成功",
  "code": 0,
  "data": {
    "id": "5bd7c0fae18d9d413acd87e8",
    "memberId": "001111",
    "icon": "http://wwww.xxx.com/xxx.jpg",
    "nickname": "送森更温柔",
    "name": "张三",
    "gender": "Male",
    "age": "18",
    "birthday": "2018-09-28 17:45:19",
    "address": null,
    "idCard": "身份证",
    "idNumber": "235364375687234534",
    "location": {
      "x": 12.23465,
      "y": 12.24567,
      "type": "Point",
      "coordinates": [
        12.23465,
        12.24567
      ]
    },
    "multimedia": [
      {
        "filename": "aaa",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "bbb",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ],
    "status": "Enabled",
    "createDate": "2018-10-30 10:24:58",
    "updateDate": null
  }
}
```

## 更新用户资料

方法：POST

地址：/profile/update

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d'
{
  "id": "5bd7c0fae18d9d413acd87e8",
  "memberId": "000001",
  "icon": "http://wwww.xxx.com/xxx.jpg",
  "nickname": "送森更温柔",
  "name": "张三",
  "gender": "Male",
  "age": "18",
  "birthday": "2018-09-28 17:45:19",
  "address": "Shenzhen Chain",
  "idCard": "身份证",
  "idNumber": "235364375687234534",
  "location": {
      "x": 12.23465,
      "y": 12.24567,
      "type": "Point",
      "coordinates": [
        12.23465,
        12.24567
      ]
    },
    "multimedia": [
      {
        "filename": "aaa",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "bbb",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ],
  "status": "Disabled"
}
'  http://localhost:8080/profile/update | jq
```

返回结果

```
{
  "status": true,
  "reason": "更新成员成功",
  "code": 0,
  "data": {
    "id": "5bd7c0fae18d9d413acd87e8",
    "memberId": "001111",
    "icon": "http://wwww.xxx.com/xxx.jpg",
    "nickname": "送森更温柔",
    "name": "张三",
    "gender": "Male",
    "age": "18",
    "birthday": "2018-09-28 17:45:19",
    "address": "Shenzhen Chain",
    "idCard": "身份证",
    "idNumber": "235364375687234534",
    "location": {
      "x": 12.23465,
      "y": 12.24567,
      "type": "Point",
      "coordinates": [
        12.23465,
        12.24567
      ]
    },
    "multimedia": [
      {
        "filename": "aaa",
        "url": "/sss/sss.jpg",
        "extension": ".jpg"
      },
      {
        "filename": "bbb",
        "url": "/bb/bb.jpg",
        "extension": ".jpg"
      }
    ],
    "status": "Disabled",
    "createDate": "2018-10-30 10:24:58",
    "updateDate": "2018-10-30 10:42:32"
  }
}

```

### 查询授权人

方法：GET

地址：/profile/get/{id}

演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/profile/get/5bd7c0fae18d9d413acd87e8 | jq
```