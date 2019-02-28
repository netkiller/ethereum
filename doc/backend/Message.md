# Message

站内消息

## 数据结构

方法：GET

地址：/message/json


字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
memberId | String | 会员ID，与 member 1:1
title	| String | 分行机构的公司名称
message | String | 联系人
status	| Enum | Unread 未读, Read 已读。 
createDate | Date | 创建日期
updateDate | Date | 更新日期

{"id":null,"memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Unread","createDate":"2018-09-12 13:30:26","updateDate":null}

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/message/json
```

返回结果

```
{"id":null,"memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Unread","createDate":"2018-09-12 13:30:26","updateDate":null}
```

## 发送消息

方法：POST

地址：/message/create

数据：{"memberId":"00000","title":"审批通知","message":"你的资产已经审批通过"}
	
操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"memberId":"00000","title":"审批通知","message":"你的资产已经审批通过"}' ${URL}/message/create
```
	
返回结果

```
{"status":true,"reason":"添加成功","code":0,"data":{"id":"5b98a599e203d00c4bce2d63","memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Unread","createDate":"2018-09-12 13:35:21","updateDate":null}}
```


## 更新消息状态

方法：POST

地址：/message/update

数据：{"id":"5b98a599e203d00c4bce2d63","status":"Read"}
	
操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"id":"5b98a599e203d00c4bce2d63","status":"Read"}' ${URL}/message/update
```
	
返回结果

```json
更新前 "status":"Unread", "updateDate":null

{"status":true,"reason":"添加成功","code":0,"data":{"id":"5b98a599e203d00c4bce2d63","memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Unread","createDate":"2018-09-12 13:35:21","updateDate":null}}

更新后 "status":"Read" 同时 "updateDate":"2018-09-12 13:38:29"}

{"status":true,"reason":"更新成功","code":0,"data":{"id":"5b98a599e203d00c4bce2d63","memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Read","createDate":"2018-09-12 13:35:21","updateDate":"2018-09-12 13:38:29"}}
```

## 打开消息

方法：GET

地址：/message/get/{id}

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/message/get/5b98a599e203d00c4bce2d63
```

返回结果

```json
{"status":true,"reason":"获取成功","code":0,"data":{"id":"5b98a599e203d00c4bce2d63","memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Read","createDate":"2018-09-12 13:35:21","updateDate":"2018-09-12 13:41:05"}}
```

## 列出我的消息

方法：GET

地址：/message/list/member/{memberId}/{size}/{page}

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/message/list/00000/10/1
```

返回结果

```json
{"status":true,"reason":"获取成功","code":0,"data":[{"id":"5b98a54fe203d00c3955bb54","memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Unread","createDate":"2018-09-12 13:34:07","updateDate":null},{"id":"5b98a599e203d00c4bce2d63","memberId":"00000","title":"审批通知","message":"你的资产已经审批通过","status":"Read","createDate":"2018-09-12 13:35:21","updateDate":"2018-09-12 13:41:05"}]}
```

## 删除消息

方法：GET

地址：/message/remove/{id}

操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/message/remove/000000000
```
