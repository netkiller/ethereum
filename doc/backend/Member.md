# 会员接口

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')
```

字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
username | String | 用户名（暂未使用）
mobile	| String | 手机
password | String | 密码
wechat	| String | 微信ID，用于微信登录
role	| String | 角色 User, Organization, Employees , Administrator
 | | User 用户, Organization 机构, Employees 授权人, Administrator 艺术银行


操作演示

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/member/json | jq
```

数据结构

```json
{
  "status": false,
  "reason": "",
  "code": 0,
  "data": {
    "id": null,
    "username": "12322228888",
    "mobile": "12322228888",
    "password": "123456",
    "wechat": "微信ID",
    "role": "Organization",
    "captcha": null,
    "createDate": "2018-10-09 09:32:04",
    "updateDate": null
  }
}
```

## 艺术银行

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"13113668890","password":"123456"}' ${URL}/member/login/password | jq
```

返回结果, 注意艺术银行也是机构，所以会返回 Organization。

```json 

{
  "status": true,
  "reason": "登录成功",
  "code": 0,
  "data": {
    "Member": {
      "id": "5bce94539897c218d2500e54",
      "username": "13113668890",
      "mobile": "13113668890",
      "password": "96e79218965eb72c92a549dd5a330112",
      "wechat": null,
      "role": "Administrator",
      "captcha": null,
      "createDate": "2018-10-23 11:24:03",
      "updateDate": null
    },
    "Wallet": {
      "id": "5bce94549897c218d2500e55",
      "memberId": "5bce94539897c218d2500e54",
      "address": "0xb60d0371d69ade92bf12acba889ba37c69722ee5",
      "privateKey": "twgj8bMFpeFhLMKqHDi2rrDQ3m8UYkd8PFa2qv5TE5tE9NPMp8A2FExFJbXWJkkAX79J8LmhhcU1fxb2NdHVCx1JQeg9xKygYGJMmPa1HTJkZ",
      "mnemonic": "3XgrDXr8tymuxM7tmEh3xphWxooVh1aEeWkFMTCtiLJVZEkudbeKBbT9W3pH9CXHahTkHTqi7hsrCeMkbF9jZdtTG18yfv6ZC1JwrXFsmPLKZZ"
    },
    "Organization": null
  }
}


```

## 用户注册

方法：POST

地址：/member/create

数据：{"mobile":"13113668800","password":"123456","role":"Organization","captcha":"8888"}

### 获取验证码

请参考 Captcha.md 文档

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/13113668890 | jq
```

### 分行机构注册

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"13113668890","password":"123456","role":"Organization", "captcha":"8888"}' ${URL}/member/create | jq
```
	
### 返回结果

字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
username | String | 用户名（暂未使用）
mobile	| String | 手机
password | String | 密码
role	| String | 角色 User, Experts, Organization, Employees, Administrator
 | | User 用户, Organization 机构, Experts 授权人, Administrator 艺术银行

```
{"status":true,"reason":"注册成功","code":0,"data":{"id":"5b97312e8cec47408eb02c47","username":"13113668800","mobile":"13113668800","password":"e10adc3949ba59abbe56e057f20f883e","role":"Organization","createDate":"2018-09-11T03:06:22.937+0000","updateDate":null}}
```

```
neo@MacBook-Pro ~/workspace/deploy % curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"13113668890","password":"123456","role":"Organization", "captcha":"8557"}' ${URL}/member/create | jq
{
  "status": false,
  "reason": "会员已存在",
  "code": 0,
  "data": null
}
```

### 授权人注册

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{"mobile":"13113660000","password":"123456","role":"Experts", "captcha":"4281"}
' ${URL}/member/create
```

## 修改登录信息

### 重置密码

方法：POST

地址：/member/password/reset

数据：{"mobile":"13113668800","password":"123456", "captcha":"8888"}

第一步发送手机验证码

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/13113668890 | jq
```

第二步重置密码

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{"mobile":"13113668890","password":"8888", "captcha":"8888"}
' ${URL}/member/password/reset | jq
```

返回结果

```json
{
  "status": true,
  "reason": "会员密码重置",
  "code": 0,
  "data": null
}
```

### 修改username


方法：POST

地址：/member/update

数据：{"id":"5b97312e8cec47408eb02c47","mobile":"13113668800","password":"123456"}

字段 | 类型 | 描述
---- | --- | ---
id | String | 唯一ID
username | String | 用户名（暂未使用）
mobile	| String | 手机
password | String | 密码
role	| String | 角色 User, Experts, Organization, Employees, Administrator
 | | User 用户, Organization 机构, Experts 授权人, Administrator 艺术银行
	
操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"id":"5b97312e8cec47408eb02c47","username":"neo"}' ${URL}/member/update
```


```
{"status":true,"reason":"更新成功","code":0,"data":{"id":"5b97312e8cec47408eb02c47","username":"neo","mobile":"13113668800","password":"e10adc3949ba59abbe56e057f20f883e","role":"Organization","createDate":"2018-09-11T03:06:22.937+0000","updateDate":"2018-09-11T06:35:09.985+0000"}}
```

### 绑定微信ID

方法：POST

地址：/member/update

数据：{"id":"5b97312e8cec47408eb02c47","wechat":"123456"}

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"id":"5b97312e8cec47408eb02c47","wechat":"123456"}' ${URL}/member/update

```

返回结果

```json
{
  "status": true,
  "reason": "更新成功",
  "code": 0,
  "data": {
    "id": "5b97312e8cec47408eb02c47",
    "username": null,
    "mobile": "13113668890",
    "password": "e10adc3949ba59abbe56e057f20f883e",
    "wechat": "123456",
    "role": "Organization",
    "captcha": null,
    "createDate": "2018-09-11 11:06:22",
    "updateDate": "2018-10-09 09:39:58"
  }
}
```

## 会员登录

### 密码登录

方法：POST

地址：/member/login/password

数据：

{"mobile":"13113668800","password":"123456"}

或者

{"username":"jerry","password":"123456"}


操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"13113668890","password":"123456"}' ${URL}/member/login/password | jq
```

返回结果

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"13113668800","password":"123456"}' ${URL}/member/login/password | jq
{
  "status": true,
  "reason": "登录成功",
  "code": 0,
  "data": [
    {
      "id": "5b97312e8cec47408eb02c47",
      "username": null,
      "mobile": "13113668800",
      "password": "e10adc3949ba59abbe56e057f20f883e",
      "role": "Organization",
      "createDate": "2018-09-11 11:06:22",
      "updateDate": "2018-09-11 14:36:55"
    }
  ]
}
```

表示新注册用户，需要进一步完善资料。否则 Organization 角色，会返回分行机构得信息，Experts 角色，返回授权人信息。

```
neo@MacBook-Pro ~/workspace/deploy % curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mobile":"18688939680","password":"111111"}' ${URL}/member/login/password | jq
{
  "status": true,
  "reason": "登录成功",
  "code": 0,
  "data": [
    {
      "id": "5bac75c3c9278241099e0efe",
      "username": "18688939680",
      "mobile": "18688939680",
      "password": "96e79218965eb72c92a549dd5a330112",
      "role": "Organization",
      "createDate": "2018-09-27 14:16:35",
      "updateDate": null
    },
    {
      "id": "5bad948fc9278266770942dc",
      "memberId": "5bac75c3c9278241099e0efe",
      "company": "Qin艺术品管理公司",
      "name": "Qin",
      "telephone": "0755-11112222",
      "type": "Test",
      "address": "深圳市南山区西丽",
      "industry": "",
      "location": null,
      "multimedia": [],
      "status": "New",
      "createDate": "2018-09-28 10:40:15",
      "updateDate": null
    }
  ]
}
```

data 是 List 数据接口，上面是登录信息，下面是机构信息，或者授权人信息

### 手机短信登录

方法：POST

地址：/member/mobile/{mobile}/{code}

操作演示

第一步发送验证码

```
curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/captcha/sms/13113668890 | jq
```

第二步登录

```
neo@MacBook-Pro ~ % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/member/login/mobile/13113668890/2212 | jq
{
  "status": true,
  "reason": "登录成功",
  "code": 0,
  "data": [
    {
      "id": "5b97312e8cec47408eb02c47",
      "username": null,
      "mobile": "13113668890",
      "password": "e10adc3949ba59abbe56e057f20f883e",
      "role": "Organization",
      "createDate": "2018-09-11 11:06:22",
      "updateDate": "2018-09-11 14:36:55"
    }
  ]
}
```

验证码只能使用一次，如果再次登录会提示过期

```
neo@MacBook-Pro ~ % curl -s -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/member/login/mobile/13113668890/2212 | jq
{
  "status": false,
  "reason": "验证码过期",
  "code": 0,
  "data": null
}
```

### 微信登录

方法：POST

地址：/member/login/wechat

数据：{"wechat":"123456"}


操作演示

```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"wechat":"123456"}' ${URL}/member/login/wechat | jq
```

返回结果

```json
{
  "status": true,
  "reason": "登录成功",
  "code": 0,
  "data": [
    {
      "id": "5b97312e8cec47408eb02c47",
      "username": null,
      "mobile": "13113668890",
      "password": "e10adc3949ba59abbe56e057f20f883e",
      "wechat": "123456",
      "role": "Organization",
      "captcha": null,
      "createDate": "2018-09-11 11:06:22",
      "updateDate": "2018-10-09 09:39:58"
    }
  ]
}
```

### 助记词登录

方法：POST

地址：/member/login/mnemonic

数据：{"mnemonic":"save bulk plug cabbage panic urban extend cannon victory tip mesh gaze"}


操作演示
 
```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '{"mnemonic":"save bulk plug cabbage panic urban extend cannon victory tip mesh gaze"}' ${URL}/member/login/mnemonic | jq
```

返回结果

```json
{
  "status": true,
  "reason": "登录成功",
  "code": 0,
  "data": {
    "Member": {
      "id": "5bbc5d299ea70d08a0b16e04",
      "username": "18688939680",
      "mobile": "18688939680",
      "password": "96e79218965eb72c92a549dd5a330112",
      "wechat": null,
      "role": "Organization",
      "captcha": null,
      "createDate": "2018-10-09 15:47:53",
      "updateDate": null
    },
    "Wallet": {
      "id": "5bbc5d299ea70d08a0b16e05",
      "memberId": "5bbc5d299ea70d08a0b16e04",
      "address": "0x8de4e2ab61fc1e82dca3e50b6351ed4daa6f97ee",
      "privateKey": "2X4xD3gdX7koFtRky3giDzCpZzbaWce2B3cP39kXgqZCRHQ4ST4BiwdWFEehMkZfKm33ChZckiw3BKWeSZBco9ZGJuRYFUh9Z1uGsRgmmkVm6P",
      "mnemonic": "ur577pJKfSN4Ax5pxcjBoeSAvpu8CrkdoTrcDJcKzxUJJ7nj7sP8SxYVaWeG6J6iGcSqpWziZaBuWJBmeoZrdZonDBCk6rXfxqwNkjuRn1JGW"
    },
    "Organization": {
      "id": "5bbc65019ea70d08cd5dea60",
      "memberId": "5bbc5d299ea70d08a0b16e04",
      "icon": null,
      "company": "Qin文艺",
      "name": "Qin",
      "telephone": "18688939680",
      "type": "Qin",
      "address": "广东省深圳市南山区西丽街道茶光路一本大厦507",
      "industry": "珠宝",
      "location": {
        "x": 22.567454,
        "y": 113.949053,
        "type": "Point",
        "coordinates": [
          22.567454,
          113.949053
        ]
      },
      "multimedia": [
        {
          "filename": "IMG_1980.JPG",
          "url": "/tmp/3337928f45237385b15aea1853ed4aac1352fa13.JPG",
          "extension": "JPG"
        },
        {
          "filename": "IMG_1984.JPG",
          "url": "/tmp/1858dee20b3d8eba3d0da4c721e42a52627673ba.JPG",
          "extension": "JPG"
        }
      ],
      "status": "New",
      "createDate": "2018-10-09 16:21:21",
      "updateDate": null
    }
  }
}

```

### 私钥登录

方法：POST

地址：/member/login/privatekey

数据：{"privateKey":"c27288359161d0b71d6c966a6e74f44bb08d69a2ff9695a5f8cc875e10f7589b"}


操作演示
 
```
curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X POST -d '
{"privateKey":"c27288359161d0b71d6c966a6e74f44bb08d69a2ff9695a5f8cc875e10f7589b"}
' ${URL}/member/login/privatekey | jq
```

返回结果

```json
{
  "status": true,
  "reason": "登录成功",
  "code": 0,
  "data": {
    "Member": {
      "id": "5bbc5d299ea70d08a0b16e04",
      "username": "18688939680",
      "mobile": "18688939680",
      "password": "96e79218965eb72c92a549dd5a330112",
      "wechat": null,
      "role": "Organization",
      "captcha": null,
      "createDate": "2018-10-09 15:47:53",
      "updateDate": null
    },
    "Wallet": {
      "id": "5bbc5d299ea70d08a0b16e05",
      "memberId": "5bbc5d299ea70d08a0b16e04",
      "address": "0x8de4e2ab61fc1e82dca3e50b6351ed4daa6f97ee",
      "privateKey": "2X4xD3gdX7koFtRky3giDzCpZzbaWce2B3cP39kXgqZCRHQ4ST4BiwdWFEehMkZfKm33ChZckiw3BKWeSZBco9ZGJuRYFUh9Z1uGsRgmmkVm6P",
      "mnemonic": "ur577pJKfSN4Ax5pxcjBoeSAvpu8CrkdoTrcDJcKzxUJJ7nj7sP8SxYVaWeG6J6iGcSqpWziZaBuWJBmeoZrdZonDBCk6rXfxqwNkjuRn1JGW"
    },
    "Organization": {
      "id": "5bbc65019ea70d08cd5dea60",
      "memberId": "5bbc5d299ea70d08a0b16e04",
      "icon": null,
      "company": "Qin文艺",
      "name": "Qin",
      "telephone": "18688939680",
      "type": "Qin",
      "address": "广东省深圳市南山区西丽街道茶光路一本大厦507",
      "industry": "珠宝",
      "location": {
        "x": 22.567454,
        "y": 113.949053,
        "type": "Point",
        "coordinates": [
          22.567454,
          113.949053
        ]
      },
      "multimedia": [
        {
          "filename": "IMG_1980.JPG",
          "url": "/tmp/3337928f45237385b15aea1853ed4aac1352fa13.JPG",
          "extension": "JPG"
        },
        {
          "filename": "IMG_1984.JPG",
          "url": "/tmp/1858dee20b3d8eba3d0da4c721e42a52627673ba.JPG",
          "extension": "JPG"
        }
      ],
      "status": "New",
      "createDate": "2018-10-09 16:21:21",
      "updateDate": null
    }
  }
}

```


### 令牌登录





