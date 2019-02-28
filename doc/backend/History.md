# 资产历史记录

## 数据结构

如果你想查看接口的数据结构，可以调用下面接口。

方法：GET

地址：/history/json

字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
assetsId | String | 资产ID
status | String | 状态
title | String | 标题
message | String | 内容
createDate | Date | 创建日期
updateDate | Date | 更新日期

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/history/json
```

返回结果

```
{
  "id": null,
  "assetsId": null,
  "title": "添加信息",
  "message": "信息内容",
  "status": "录入",
  "createDate": "2018-09-20 10:49:06",
  "updateDate": null
}
```

## 添加历史信息

方法：POST

地址：/history/create

数据：


	{
	  "assetsId": "0000000",
	  "title": "添加信息",
	  "message": "信息内容",
	  "status": "录入"
	}

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "assetsId": "0000000",
  "title": "添加信息",
  "message": "信息内容",
  "status": "录入"
}
' ${URL}/history/create | jq
```

返回结果

```json
{
  "status": false,
  "reason": "添加历史事件成功",
  "code": 0,
  "data": {
    "id": "5ba30babdb09117085d088ab",
    "assetsId": "0000000",
    "title": "添加信息",
    "message": "信息内容",
    "status": "录入",
    "createDate": "2018-09-20 10:53:31",
    "updateDate": null
  }
}
```

## 通过资产ID获得历史列表

方法：GET

地址：/history/list/{assetsId}

数据：

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/history/list/0000000 | jq
```

返回结果

```json
{
  "status": true,
  "reason": "获取历史事件成功",
  "code": 0,
  "data": [
    {
      "id": "5ba30babdb09117085d088ab",
      "assetsId": "0000000",
      "title": "添加信息",
      "message": "信息内容",
      "status": "录入",
      "createDate": "2018-09-20 10:53:31",
      "updateDate": null
    }
  ]
}
```

## 获取状态列表

方法：GET

地址：/history/status/list

数据：

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/history/status/list | jq
```

返回结果

```json
{
  "status": true,
  "reason": null,
  "code": 0,
  "data": [
    "录入",
    "变更",
    "收藏",
    "在售",
    "展出"
  ]
}
```


## 删除历史信息

方法：GET

地址：/history/remove/{id}

数据：

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/history/remove/5ba30babdb09117085d088ab | jq
```

返回结果