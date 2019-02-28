# 资产接口

## 获取 Token

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```

## 区块链诊断接口

存储数据到链上

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/blockchain/set/hello/helloworld | jq
```

从链上获取数据

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/blockchain/get/hello | jq
```


## 查询区块链上的资产

方法：GET

地址：/blockchain/assets/{uuid}

演示：/blockchain/assets/1369ef66-aa33-4c47-ac38-40445f44d2db

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/blockchain/assets/1369ef66-aa33-4c47-ac38-40445f44d2db | jq
{
  "status": true,
  "reason": "创建成功",
  "code": 0,
  "data": {
    "id": "5bab3f98c9278237d6bb4a76",
    "organizationId": "5bab3f98c9278237d6bbsa76",
    "memberId": "1234",
    "uuid": "1369ef66-aa33-4c47-ac38-40445f44d2db",
    "transactionId": "5bab3f98c9278237d6bb4a765bab3f98c9278237d6bb4a76",
    "nfc": "2D0E8FO",
    "name": "千里江山图",
    "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
    "type": "书画",
    "attribute": {
      "形状": "圆环形",
      "总质量": "5.64g",
      "放大检查": "纤维交织结构",
      "检验结论": "天然翡翠挂件（A）",
      "贵金属标注": "-----"
    },
    "location": {
      "longitude": "1125456",
      "latitude": "22256644"
    },
    "hypermedia": [
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "aaa.jpg",
        "size": ""
      },
      {
        "hash": "QmUNLLsPACCz1vLxQVkXqqLX5R1X345qqfHbsf67hvA3Nn",
        "name": "bbb.jpg",
        "size": ""
      }
    ],
    "status": "Approved",
    "createDate": "2018-09-26 16:13:12",
    "updateDate": null
  }
}
```