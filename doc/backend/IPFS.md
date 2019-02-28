# 星际文件系统

## 访问IPFS上文件的方法

访问地址：http://47.89.15.169/ipfs/<hash>

访问 IPFS 图片方法，例如

```
	{
      "hash": "QmU6j8R7xnRnzcsaqxsYWAj82EbBA8pVuAh4ZKw3GyecJ5",
      "name": "timg-2.jpeg"
    }
```

通过浏览器打开下面网址，可以看到刚刚上传的图片

	http://47.89.15.169/ipfs/QmU6j8R7xnRnzcsaqxsYWAj82EbBA8pVuAh4ZKw3GyecJ5


## 数据结构

字段 | 类型 | 描述
---- | --- | ---
hash | String | IPFS hash 值
name | String | 文件名
Size	| String | 文件尺寸

## 上传文件一个文件

方法：POST

地址：/upload/ipfs/single

数据：file=/path/to/file

操作演示

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s  -H "Authorization: Bearer ${TOKEN}" -X POST -F "file=@/etc/hosts" ${URL}/upload/ipfs/single

curl -s  -H "Authorization: Bearer ${TOKEN}" -X POST -F "file=@/etc/resolv.conf" ${URL}/upload/ipfs/single

curl -s  -H "Authorization: Bearer ${TOKEN}" -X POST -F "file=@/Users/neo/Downloads/test.mp4" ${URL}/upload/ipfs/single
```
	
返回结果

```json
{"status":true,"reason":"","code":0,"data":{"hash":"Qmd4iuFPhZVap9TbwsUxDq5TJtFs8FBd6ndbH4enmQzRAB","name":"resolv.conf"}}
```

## 上传多个文件（不建议上传视频等大文件） 

方法：POST

地址：/upload/ipfs/group

数据：file=/path/to/file

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s  -H "Authorization: Bearer ${TOKEN}" -X POST -F "file=@timg-2.jpeg" -F "file=@timg-3.jpeg" -F "file=@timg-4.jpeg" ${URL}/upload/ipfs/group | jq

```

返回结果

```
{
  "status": true,
  "reason": null,
  "code": 0,
  "data": [
    {
      "hash": "QmU6j8R7xnRnzcsaqxsYWAj82EbBA8pVuAh4ZKw3GyecJ5",
      "name": "timg-2.jpeg"
    },
    {
      "hash": "QmRTmcAnSUwekB2qAhj5bQU4gUzxA8Rks7rEDrd6nDCgW9",
      "name": "timg-3.jpeg"
    },
    {
      "hash": "QmP31eZSC7sqgy9WhXG695EmawTif8WywKtDKqJZEBwG2q",
      "name": "timg-4.jpeg"
    }
  ]
}
```

## 上传到本地文件系统

由于有些文件不能上传到IPFS，例如身份证图片，这些图片需要保存在我们的服务器上。

方法：POST

地址：/upload/file

数据：file=/path/to/file

```
URL=http://localhost:8080
TOKEN=$(curl -s  -X POST --user 'api:secret' -d 'grant_type=password&username=blockchain&password=123456' ${URL}/oauth/token | sed 's/.*"access_token":"\([^"]*\)".*/\1/g')

curl -s -H "Content-Type: multipart/form-data" -H "Authorization: Bearer ${TOKEN}" -X POST -F "file=@timg.jpg" -F "file=@logo512x512.png" ${URL}/upload/file | jq
```
返回结果

```
{
  "status": true,
  "reason": null,
  "code": 0,
  "data": [
    {
      "filename": "timg.jpg",
      "url": "3d9805b1815749833c9e57faba37d6a509d7762c.jpg",
      "extension": "jpg"
    },
    {
      "filename": "logo512x512.png",
      "url": "52bfbb7d92b3f41da2e4103f1990c054990be863.png",
      "extension": "png"
    }
  ]
}
```

访问文件

```
https://img.bitvaluebk.com/bf99e8a3-3172-4d02-8192-fdcdc370d30c.png
```
