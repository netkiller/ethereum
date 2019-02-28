# 资产鉴定

## 获取 Token

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```

## 数据结构

如果你想查看接口的数据结构，可以调用下面接口。

方法：GET

地址：/certificate/json


字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
assetsId | String | 资产ID
memberId | String | 授权人ID
icon | String | 头像
name | String | 授权人姓名
remark | String | 鉴定结论
status	| Enum | Yes 完成鉴定, No 尚未鉴定
createDate | Date | 创建日期
updateDate | Date | 更新日期


操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/certificate/json | jq
```

返回结果

```json
{
  "id": null,
  "assetsId": "00000000000",
  "memberId": "00000000000",
  "icon": "xxx.jpg",
  "name": "张三",
  "remark": "青花瓷，鉴定物品复合年代特征",
  "status": "No",
  "createDate": "2018-10-11 11:22:56",
  "updateDate": null
}
```

## 添加鉴定结论

方法：POST

地址：/certificate/create

数据：

```json
{
  "assetsId": "5bbdb8ee7099aa05e4ddb34d",
  "memberId": "5bbc5d299ea70d08a0b16e04",
  "icon": "xxx.jpg",
  "name": "张三",
  "remark": "青花瓷，鉴定物品复合年代特征"
}
```

操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "assetsId": "5bbdb8ee7099aa05e4ddb34d",
  "memberId": "5bbc5d299ea70d08a0b16e04",
  "icon": "xxx.jpg",
  "name": "张三",
  "remark": "青花瓷，鉴定物品复合年代特征"
}
' ${URL}/certificate/create | jq
```

返回结果

```json
{
  "status": true,
  "reason": "添加鉴定结果成功",
  "code": 0,
  "data": {
    "id": "5bbec4d1fd9557045ad3dac1",
    "assetsId": "5bbdb8ee7099aa05e4ddb34d",
    "memberId": "5bbc5d299ea70d08a0b16e04",
    "name": "张三",
    "remark": "青花瓷，鉴定物品复合年代特征",
    "status": "No",
    "createDate": "2018-10-11 11:34:41",
    "updateDate": null
  }
}
```

## 更新鉴定结论

** 注意，已经上链资产不允许更新 **

方法：POST

地址：/certificate/update

数据：

```json
{
  "id": "5bbec4d1fd9557045ad3dac1",
  "name": "张三",
  "remark": "青花瓷，鉴定物品复合年代特征"
}
```

操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "id": "5bbec4d1fd9557045ad3dac1",
  "name": "李思",
  "remark": "赝品青花瓷，鉴定物品不复合年代特征"
}
' ${URL}/certificate/update | jq
```

返回结果

```json
{
  "status": true,
  "reason": "添加鉴定结果成功",
  "code": 0,
  "data": {
    "id": "5bbec4d1fd9557045ad3dac1",
    "assetsId": "5bbdb8ee7099aa05e4ddb34d",
    "memberId": "5bbc5d299ea70d08a0b16e04",
    "name": "张三",
    "remark": "青花瓷，鉴定物品复合年代特征",
    "status": "No",
    "createDate": "2018-10-11 11:34:41",
    "updateDate": null
  }
}
```

## 删除鉴定结论

方法：GET

地址：/certificate/remove/{id}

操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/certificate/remove/5b8f8b86917d6a2929aae8b8 | jq
```

## 查询鉴定结论

方法：GET

地址：/certificate/get/{id}

操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/certificate/get/5b8f8b86917d6a2929aae8b8 | jq
```

## 查询鉴定结论, 通过资产ID 和 会员ID

方法：GET

地址：/certificate/get/{assetsId}/{memberId}

操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/certificate/get/5b8f8b86917d6a2929aae8b8/a2929aae8b85b8f8b86917d6 | jq
```

## 查询列表 通过 会员 ID

方法：GET

地址：/certificate/list/member/{memberId}

操作演示

## 查询列表 通过资产 ID

方法：GET

地址：/certificate/list/assets/{assetsId}

操作演示


## 查询列表

方法：GET

地址：/certificate/list/assets/{assetsId}/{status}

操作演示

## 查询列表

方法：GET

地址：/certificate/list/member/{memberId}/{status}/{size}/{page}

操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/certificate/list/member/5bcd8b0b432c0010ed6778a0/No/10/1 | jq
```

返回结果

```
{
  "status": true,
  "reason": "获取鉴定结果成功",
  "code": 0,
  "data": [
    {
      "id": "5bc84b4cbf00272967a54c88",
      "organizationId": "5bc6b381cff7a91f5de448b4",
      "memberId": "5bc6b358cff7a91f5de448b0",
      "uuid": "25df1b87-7831-43f6-ade8-eacc8f0d7127",
      "qrcode": null,
      "transactionId": null,
      "nfc": null,
      "name": "阿瑟",
      "description": "在家",
      "type": "青铜器",
      "thumbnail": null,
      "attribute": null,
      "location": null,
      "hypermedia": null,
      "status": null,
      "createDate": "2018-10-18 16:58:52",
      "updateDate": "2018-10-18 16:58:57"
    },
    {
      "id": "5bc84d81bf00272967a54c8a",
      "organizationId": "5bc6b381cff7a91f5de448b4",
      "memberId": "5bc6b358cff7a91f5de448b0",
      "uuid": null,
      "qrcode": null,
      "transactionId": null,
      "nfc": null,
      "name": "你的",
      "description": "不过",
      "type": "青铜器",
      "thumbnail": null,
      "attribute": null,
      "location": null,
      "hypermedia": null,
      "status": "New",
      "createDate": "2018-10-18 17:08:17",
      "updateDate": null
    }
  ]
}

```
