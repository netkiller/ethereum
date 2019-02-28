# NFC 接口

## 数据结果

字段 | 类型 | 描述
---- | --- | ---
uid | String | IPFS Hash 值
uuid | String | 文件名
Size	| String | 文件尺寸

```
neo@MacBook-Pro ~ % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/nfc/json | jq
{
  "id": null,
  "uid": "A98EF",
  "uuid": "98b2b71a-170d-446c-b1f6-d7b26019bff3",
  "organizationId": "32c54f64e42a",
  "createDate": "2018-10-17 14:05:04",
  "updateDate": null
}
```

## 创建信息

方法：POST

地址：/nfc/create

数据：

```json
{
  "uid": "A98EF",
  "uuid": "98b2b71a-170d-446c-b1f6-d7b26019bff3",
  "organizationId": "32c54f64e42a",
}
```

操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
"uid": "A98EF",
  "uuid": "98b2b71a-170d-446c-b1f6-d7b26019bff3",
  "organizationId": "32c54f64e42a",
}
' ${URL}/nfc/create | jq
```

返回结果

```json
{
  "status": true,
  "reason": "添加成功",
  "code": 0,
  "data": {
    "id": "5bc6d1e7bf00272084d507d9",
    "uid": null,
    "uuid": null,
    "organizationId": null,
    "createDate": "2018-10-17 14:08:39",
    "updateDate": null
  }
}
```

## 删除数据

方法：GET

地址：/nfc/remove/{id}

操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/nfc/remove/5bc6d1e7bf00272084d507d9 | jq
```
