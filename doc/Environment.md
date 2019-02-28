# 环境

## 开发环境

	
功能 | 地址 | 描述
---- | --- | ---
API | http://localhost:8080 | API 接口
IPFS | http://47.89.15.169/ipfs/<Hash> | http://47.89.15.169/ipfs/Qmd4iuFPhZVap9TbwsUxDq5TJtFs8FBd6ndbH4enmQzRAB
	
	
```
```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```
```	

## 开发环境


功能 | 地址 | 描述
---- | --- | ---
API | https://dev.bitvaluebk.com | API 接口
IMG | https://img.bitvaluebk.com/52bfbb7d92b3f41da2e4103f1990c054990be863.png | 资质文件，头像等
IPFS | http://47.89.15.169/ipfs/<Hash> | http://47.89.15.169/ipfs/Qmd4iuFPhZVap9TbwsUxDq5TJtFs8FBd6ndbH4enmQzRAB


```
URL=https://dev.bitvaluebk.com
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```

测试接口是否正常工作

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/test/hello | jq 
{
  "status": true,
  "reason": "调用成功",
  "code": 0,
  "data": "Helloworld!!!"
}
```

## 测试环境

www.goalyee.com

功能 | 地址 | 描述
---- | --- | ---
API | http://api.goalyee.com:8000 | API 接口
IPFS | http://ipfs.goalyee.com/ipfs/<Hash> | http://ipfs.goalyee.com/ipfs/Qmd4iuFPhZVap9TbwsUxDq5TJtFs8FBd6ndbH4enmQzRAB
Image | http://images.goalyee.com/xxxxxx.jpg | 


## 生产环境

功能 | 地址 | 描述
---- | --- | ---
API | http://api.bitvaluebk.com | API 接口
IPFS | http://ipfs.bitvaluebk.com/ipfs/<Hash> | http://ipfs.goalyee.com/ipfs/Qmd4iuFPhZVap9TbwsUxDq5TJtFs8FBd6ndbH4enmQzRAB
Image | http://img.bitvaluebk.com/xxxxxx.jpg |

```
URL=https://api.bitvaluebk.com
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
echo $TOKEN
```
 