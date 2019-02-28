# 初级化数据

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')



UUID=$(uuidgen)
organizationId=5bcd8b38432c0010ed6778b1
memberId=5bcd8b0b432c0010ed6778ad

```

## 艺术银行

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"13113668890","password":"123456","role":"Administrator", "captcha":"8888"}' ${URL}/member/create | jq
```

```



curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "organizationId": "5bcd8b38432c0010ed6778b1",
  "memberId": "5bcd8b0b432c0010ed6778ad",
  "uuid": "4AE76C05-35C4-4CF1-AC3C-B9649A95A037",
  "name": "千里江山图",
  "description": "《千里江山图》是北宋王希孟创作的绢本画卷作品，收藏于北京故宫博物院。",
  "type": "书画",
  "location": {
      "type": "Point",
      "coordinates": [
        12.323560,
        12.323456
      ]
    },
  "status": "New",
  "createDate": "2018-09-19 11:18:19",
  "updateDate": null,
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
  ]
}
' ${URL}/assets/create | jq

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "assetsId": "5bced71c432c001c6ea31924",
  "title": "添加信息",
  "message": "信息内容",
  "status": "录入"
}
' ${URL}/history/create | jq

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{
  "assetsId": "5bced71c432c001c6ea31924",
  "memberId": "5bcd8b0b432c0010ed6778ad",
  "icon": "xxx.jpg",
  "name": "张三",
  "remark": "青花瓷，鉴定物品复合年代特征"
}
' ${URL}/certificate/create | jq


URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/assets/get/allinone/5bced71c432c001c6ea31924 | jq

```