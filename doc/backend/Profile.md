# 用户接口

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
age | String | 年龄
address | String | 地址
idCard | String | 证件类型
idNumber | String | 证件号码
location | Object | 定位信息 {"longitude":"经度","latitude":"纬度"}
multimedia | Object[] | 身份证照片等等
status	| Enum | Disabled 禁用, Enable 启用
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
  "birthday": "2018-09-28 17:45:19",
  "address": null,
  "idCard": "身份证",
  "idNumber": "235364375687234534",
  "location": null,
  "multimedia": null,
  "status": "Disabled",
  "createDate": "2018-09-28 17:45:19",
  "updateDate": null
}

```

## 创建用户

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
  "location": null,
  "multimedia": null,
  "status": "Disabled"
}
```

演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d'
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
  "location": null,
  "multimedia": null,
  "status": "Disabled"
}
'  http://localhost:8080/profile/create | jq
```

返回结果

```
{
  "status": false,
  "reason": "添加成员成功",
  "code": 0,
  "data": {
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
    "updateDate": null
  }
}
```

## 更新用户

方法：POST

地址：/profile/update

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d'
{
  "id": "5badf926c927828f8a95d8f8",
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
  "location": null,
  "multimedia": null,
  "status": "Disabled"
}
'  http://localhost:8080/profile/update | jq
```

## 查询用户

### 通过状态获取用户列表

方法：POST

地址：/profile/list/status/{status}

演示 /profile/list/status/Disabled

```

neo@MacBook-Pro /tmp/doc % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/profile/list/status/Disabled | jq
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
      "updateDate": null
    }
  ]
}

```
