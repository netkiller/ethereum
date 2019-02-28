# 杂项

token 请求

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
```

## NFC 手机兼容列表

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/misc/nfc/compatibility | jq
```

返回结果

```
{
  "status": true,
  "reason": "",
  "code": 0,
  "data": [
    "Iphone X, Iphone 8 plus",
    "Iphone 7, Iphone 7 plus",
    "小米2A, 小米3, 小米5",
    "三星 S系列，A系列，Note系列",
    "华为6高配版，荣耀6PLUS， MATE7，荣耀V8，MATE8，MATES",
    "魅族Pro 5",
    "HTC 10 lifestype",
    "酷派锋尚3",
    "Moto X级",
    "Vivo X5 Pro",
    "Oppo Find7"
  ]
}
```

## 艺术品分类标签

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/misc/art/tag | jq

```

返回结果

```
{
  "status": true,
  "reason": "",
  "code": 0,
  "data": [
    "青铜器",
    "书画",
    "玉器",
    "陶瓷",
    "杂项"
  ]
}
```

