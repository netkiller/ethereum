# Member 会员接口

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
```

## 注册用户

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"13113668890","password":"123456","role":"User", "captcha":"8888"}' ${URL}/member/create | jq
```