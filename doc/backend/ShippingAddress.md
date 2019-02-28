# 地址接口

## 获取 Token

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```

## 数据结构

如果你想查看接口的数据结构，可以调用下面接口。

方法：GET

地址：/shippingaddress/json


字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
memberId | String | 权益持有人
address | String | 地址
name | String | 收货人
phone | String | 联系方式
defaults | Boolean | true 默认地址
createDate | Date | 创建日期
updateDate | Date | 更新日期


操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/shippingaddress/json | jq
```

返回结果

```json
{
  "status": true,
  "reason": "添加地址成功",
  "code": 0,
  "data": {
    "id": null,
    "memberId": "00000000",
    "address": "深圳市南山区西丽 56 号",
    "name": "小明",
    "phone": "1304456224",
    "defaults": true,
    "createDate": "2018-10-19 14:51:07",
    "updateDate": null
  }
}
```

## 添加地址

方法：POST

地址：/shippingaddress/create

数据：

```json
{
    "memberId": "00000000",
    "address": "深圳市南山区西丽 56 号",
    "name": "小芳",
    "phone": "12300009999",
    "defaults": true
}
```
	
** 注意： 当 "defaults": true 时。会重置所有 memberId 下的数据，"defaults": false。 最后一条永远是默认值 **	
	
操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
    "memberId": "00000000",
    "address": "深圳市南山区西丽 56 号",
    "name": "小芳",
    "phone": "12300009999",
    "defaults": true
}
' ${URL}/shippingaddress/create | jq
```

返回结果

```
{
  "status": true,
  "reason": "添加地址成功",
  "code": 0,
  "data": {
    "id": "5bc94d51cff7a9352ed73a24",
    "memberId": "00000000",
    "address": "深圳市南山区西丽 56 号",
    "defaults": true,
    "createDate": "2018-10-19 11:19:45",
    "updateDate": null
  }
}
```

## 查询地址

方法：GET

地址：/shippingaddress/get/{id}

演示

```
neo@MacBook-Pro ~/Downloads % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/shippingaddress/get/5bc94d51cff7a9352ed73a24 | jq

{
  "status": true,
  "reason": "地址获取成功",
  "code": 0,
  "data": {
    "id": "5bc94d51cff7a9352ed73a24",
    "memberId": "00000000",
    "address": "深圳市南山区西丽 56 号",
    "defaults": true,
    "createDate": "2018-10-19 11:19:45",
    "updateDate": null
  }
}
```

## 更新地址

方法：POST

地址：/shippingaddress/update

数据：

```json
{
    "id": "5bc94d51cff7a9352ed73a24",
    "address": "深圳市南山区西丽 56 号",
    "defaults": true
}
```

** 注意： 当 "defaults": true 时。会重置所有 memberId 下的数据 "defaults": false。 只有一条地址是默认值true 其他全是 false **

操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
    "id": "5bc94d51cff7a9352ed73a24",
    "address": "深圳市南山区西丽 56 号 302 室",
    "defaults": false
}
' ${URL}/shippingaddress/update | jq
```

返回结果

```
{
  "status": true,
  "reason": "地址更新成功",
  "code": 0,
  "data": {
    "id": "5bc94d51cff7a9352ed73a24",
    "memberId": "00000000",
    "address": "深圳市南山区西丽 56 号 302 室",
    "defaults": true,
    "createDate": "2018-10-19 11:19:45",
    "updateDate": "2018-10-19 11:31:56"
  }
}
```

## 通过 memberId 查询地址

方法：GET

地址：/shippingaddress/list/member/{memberId}

演示

```
neo@MacBook-Pro ~/Downloads % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/shippingaddress/list/member/00000000 | jq

{
  "status": true,
  "reason": "添加地址成功",
  "code": 0,
  "data": [
    {
      "id": "5bc94c7ccff7a9351c02b542",
      "memberId": "00000000",
      "address": "深圳市南山区西丽 56 号",
      "defaults": true,
      "createDate": null,
      "updateDate": null
    },
    {
      "id": "5bc94d51cff7a9352ed73a24",
      "memberId": "00000000",
      "address": "深圳市南山区西丽 56 号 302 室",
      "defaults": false,
      "createDate": "2018-10-19 11:19:45",
      "updateDate": "2018-10-19 11:32:06"
    }
  ]
}
```

## 删除地址

方法：GET

地址：/shippingaddress/remove/{id}

演示

```

neo@MacBook-Pro ~/Downloads % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/shippingaddress/remove/5bc94c7ccff7a9351c02b542 | jq
{
  "status": true,
  "reason": "地址删除成功",
  "code": 0,
  "data": null
}

```
