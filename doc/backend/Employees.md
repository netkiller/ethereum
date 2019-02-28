# 授权人接口

## 获取 Token

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```

## 数据结构

如果你想查看接口的数据结构，可以调用下面接口。

方法：GET

地址：/employees/json


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
status	| Enum | Disabled 禁用, Enabled 启用
organizationId | String | 隶属于机构
createDate | Date | 创建日期
updateDate | Date | 更新日期


操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/employees/json | jq
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
  "birthday": "2018-10-26 15:47:24",
  "address": "深圳南山区",
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
  "status": "Disabled",
  "createDate": "2018-10-26 15:47:24",
  "updateDate": null,
  "organizationId": "00999"
}
```

## 创建授权人

方法：POST

地址：/employees/create

数据：

```json
{
  "memberId": "000001",
  "organizationId": "00999",
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
  ],
  "status": "Disabled"
}
```

演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d'
{
  "memberId": "000111",
  "organizationId": "00999",
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
'  ${URL}/employees/create | jq
```

返回结果

```
{
  "status": true,
  "reason": "添加成员成功",
  "code": 0,
  "data": {
    "id": "5bd2c8d9e18d9d19aa52708c",
    "memberId": "000111",
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
      "y": 12.24566,
      "type": "Point",
      "coordinates": [
        12.23465,
        12.24566
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
    "createDate": "2018-10-26 15:57:13",
    "updateDate": null,
    "organizationId": "00999"
  }
}
```

## 更新授权人

方法：POST

地址：/employees/update

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d'
{
  "id": "5badf926c927828f8a95d8f8",
  "memberId": "000001",
  "organizationId": "00999",
  "icon": "http://wwww.xxx.com/xxx.jpg",
  "nickname": "送森更温柔",
  "name": "张三",
  "gender": "Male",
  "age": "18",
  "birthday": "2018-09-28 17:45:19",
  "address": "Shenzhen Chain",
  "idCard": "身份证",
  "idNumber": "235364375687234534",
  "location": null,
  "multimedia": null,
  "status": "Disabled"
}
'  ${URL}/employees/update | jq
```

## 查询授权人

### 查询授权人

方法：GET

地址：/employees/get/{id}

演示

```

```

### 通过 会员ID 查询授权人

方法：GET

地址：/employees/get/member{memberId}

演示

```

```

### 通过状态获取授权人列表

方法：GET

地址：/employees/list/status/{status}

演示 /employees/list/status/Disabled

```

neo@MacBook-Pro /tmp/doc % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/employees/list/status/Disabled | jq
{
  "status": true,
  "reason": "获取成员成功",
  "code": 0,
  "data": [
    {
      "id": "5badf926c927828f8a95d8f8",
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
      "location": null,
      "multimedia": null,
      "status": "Disabled",
      "createDate": "2018-09-28 17:49:26",
      "updateDate": null,
      "organizationId": "00999"
    }
  ]
}

```

### 通过分行机构ID查询隶属于分行得授权人

方法：GET

地址：/employees/list/organization/{organizationId}

举例：/employees/list/organization/5b8f4de9917d6a276a2f6188

```
neo@MacBook-Pro /tmp/doc % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/employees/list/organization/5b8f4de9917d6a276a2f6188 | jq
```
操作演示

```
neo@MacBook-Pro ~/workspace/deploy % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/employees/list/organization/5b8f4de9917d6a276a2f6188 | jq
{
  "status": true,
  "reason": "获取成员成功",
  "code": 0,
  "data": [
    {
      "id": "5b922b138cec4724a3f05a36",
      "memberId": "00000",
      "icon": null,
      "nickname": "送森更温柔呵呵",
      "name": "张三",
      "gender": null,
      "age": "18",
      "birthday": "2018-09-07 15:36:36",
      "address": null,
      "idCard": "身份证",
      "idNumber": "235364375687234534",
      "location": null,
      "multimedia": null,
      "status": "Enabled",
      "createDate": "2018-09-07 15:36:36",
      "updateDate": null,
      "organizationId": "5b8f4de9917d6a276a2f6188"
    }
  ]
}
```
