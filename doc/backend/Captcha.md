# 验证码接口

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
```

## 发送手机验证码

方法：GET

地址：/captcha/sms/{mobile}

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/13113668890 | jq
{
  "status": true,
  "reason": "手机验证码发送成功",
  "code": 1,
  "data": null
}
```

## 手机号码白名单

白名单手机号码，不会走短信通道，验证码同意 8888

```
neo@MacBook-Pro ~ % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/13113668890 | jq     
{
  "status": true,
  "reason": "手机验证码发送成功",
  "code": 1,
  "data": null
}
neo@MacBook-Pro ~ % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/code/13113668890 | jq
{
  "status": true,
  "reason": "验证码",
  "code": 0,
  "data": "8888"
}
```

## 核对验证码

方法：GET

地址：/captcha/sms/{mobile}/{code}

```
neo@MacBook-Pro ~/workspace/deploy % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/13113668890/1234 | jq

{
  "status": true,
  "reason": "验证码校验成功",
  "code": 1,
  "data": null
}


```

验证码失败

```
{
  "status": false,
  "reason": "验证码校验失败",
  "code": 0,
  "data": null
}
```

## 获取验证码

这个接口只用于开发测试，通过手机号码，取出未过期的验证码

方法：GET

地址：/captcha/sms/code/{mobile}

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/code/13113668890 | jq
{
  "status": false,
  "reason": "验证码",
  "code": 0,
  "data": "2913"
}
```

## 生成测试验证码

当你测试的是需要产生一个验证码，且手机号码是不存在的。可以使用下面接口，生成验证码。

方法：GET

地址：/captcha/test/set/{mobile}

演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/test/13113660000 | jq
```

返回结果, 4281 就是验证码

```
{
  "status": true,
  "reason": "手机测试验证码",
  "code": 1,
  "data": "4281"
}
```
