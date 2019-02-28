# 接口文档

## Demo (Oauth2 + Jwt)

### 设置环境
	URL=http://localhost:8080

### 获取 access_token
	TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

### Demo 接口
	curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/test/hello
	
	{"status":true,"reason":"调用成功","code":0,"data":"Helloworld!!!"}

### jwt token 过期

如何返回 Access token expired 表示 oauth2 认证过期需要重新登录。

{"error":"invalid_token","error_description":"Access token expired: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmxvY2tjaGFpbiJdLCJleHAiOjE1MzUzNDQ4NjAsInVzZXJfbmFtZSI6Im5ldGtpbGxlckBtc24uY29tIiwianRpIjoiNDk1NWE1YWEtNWNhYy00Y2Q0LWI1ZDktYzU0OWE5ZTYzNmFiIiwiY2xpZW50X2lkIjoiYXBpIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.DOuSDHmbiXyqV4q1t1PGSdXIm4aqOcLeCOczH15GaWY”}

## 环境

### 开发环境

	URL=http://localhost:8080

### 测试环境

	URL=http://dev.bitvaluebk.com:8000

### 生产环境

	URL=https://api.bitvaluebk.com

- - -

## 接口认证 Oauth2 + Jwt 

溯源数据存储再  MongoDB 数据库中。MySQL 仅用于 Oauth2 用户认证。

Oauth2 和 Jwt 是互联网开源认证标准，例如腾讯微信公众平台，新浪微博，京东，淘宝…… 都用这种认证方式。

## 了解更多关于 Oauth2 + Jwt

Oauth2: https://oauth.net/2/
Jwt(JSON Web Tokens) : https://jwt.io


## 怎样更合理的使用 Oauth2

Oauth2有过期时间的概念，第一次登录 Oauth2 + Jwt 认证，系统将返回access_token 和  expires_in 。这种方式类似 Session 的 SESSION ID 和 SESSION 过期时间，在 expires_in 时间内无需再次认证。

App开发中，我们可以在App登录或者从后台恢复到前台的时候，登录一次Oauth2。其他时间


## 登录案例演示


登录 Oauth2 获得 access_token，打开 Mac 或者 Xshell 输入 curl 命令。

neo@MacBook-Pro / % curl -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' http://localhost:8080/oauth/token

## 登录成功后返回

{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmxvY2tjaGFpbiJdLCJleHAiOjE1MzY1NTIxMjIsInVzZXJfbmFtZSI6ImJsb2NrY2hhaW4iLCJqdGkiOiIxY2M5ZTQxMy1mZTdjLTQ1ZGQtODFiYS00ZWQ4ZDNlYjcyYjUiLCJjbGllbnRfaWQiOiJhcGkiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.RvvI-q8wfUdtvhDIidScEzN8is8beVzscOCXN5thDoQ","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmxvY2tjaGFpbiJdLCJ1c2VyX25hbWUiOiJibG9ja2NoYWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjFjYzllNDEzLWZlN2MtNDVkZC04MWJhLTRlZDhkM2ViNzJiNSIsImV4cCI6MTUzNjU1MjEyMiwianRpIjoiMjdhM2IxOGQtM2NmNy00ZGQ3LWFmMzgtMGQxZTMzZTA2NjhkIiwiY2xpZW50X2lkIjoiYXBpIn0.N9NFzN-xKYdtOVOlGluK1Vqw7obPRT1ZL9n98sf_E1g","expires_in":3599,"scope":"read write","jti":"1cc9e413-fe7c-45dd-81ba-4ed8d3eb72b5"}


返回json 数据中 access_token 就是我们需要的 jwt 的 Token

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmxvY2tjaGFpbiJdLCJleHAiOjE1MzY1NTIxMjIsInVzZXJfbmFtZSI6ImJsb2NrY2hhaW4iLCJqdGkiOiIxY2M5ZTQxMy1mZTdjLTQ1ZGQtODFiYS00ZWQ4ZDNlYjcyYjUiLCJjbGllbnRfaWQiOiJhcGkiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.RvvI-q8wfUdtvhDIidScEzN8is8beVzscOCXN5thDoQ


## 调用 GET 方式的接口

获取到 access_token 字符串就可以调用接口了。调用 GET 方式的接口，需要设置 HTTP 头 

"Accept: application/json”
"Content-Type: application/json" 
"Authorization: Bearer {access_token 写在这里}”


neo@MacBook-Pro / % curl -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmxvY2tjaGFpbiJdLCJleHAiOjE1MzUzNDQ4NjAsInVzZXJfbmFtZSI6Im5ldGtpbGxlckBtc24uY29tIiwianRpIjoiNDk1NWE1YWEtNWNhYy00Y2Q0LWI1ZDktYzU0OWE5ZTYzNmFiIiwiY2xpZW50X2lkIjoiYXBpIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.DOuSDHmbiXyqV4q1t1PGSdXIm4aqOcLeCOczH15GaWY" -X GET http://localhost:8080/test/hello



## 调用 POST 方式的接口


### HTTP 头设置

"Accept: application/json”
"Content-Type: application/json" 
"Authorization: Bearer {access_token 写在这里}”

### POST 数据设置

注意：请使用raw格式,正确格式如下

错误 aaa=111&bbb=222
正确 {“aaa”:111, “bbb”:222}

curl -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmxvY2tjaGFpbiJdLCJleHAiOjE1MzUxMTg2NDUsInVzZXJfbmFtZSI6Im5ldGtpbGxlckBtc24uY29tIiwianRpIjoiMmM2ZGM4MTktODI5OC00YzgwLTg1NmEtZGYzMDdhZDk5MDM4IiwiY2xpZW50X2lkIjoiYXBpIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.1IEAGzQzhQpc_wE3sDfomKx7iVjHAWFFAiXm9bcbOtY" -X POST -d ‘{“name”:”neo”}’ http://localhost:8080/test/save


### 返回数据的结构说明


{"status":true,"reason":"注册成功","code":0,"data":{"id":"5b8f3b05917d6a265d2b28c9","mobile":"13113668800","password":"a1a8887793acfc199182a649e905daab","role":"Admin","createDate":"2018-09-05T02:10:13.478+0000","updateDate":null}}


status: 布尔型状态，true 表示执行成功， false 表示执行失败

reason: 原因，支持 i18n 国际化
code: 暂未使用，返回 0 
data: 返回数据


测试 Shell 


URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -k -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" -X GET ${URL}/test/hello

