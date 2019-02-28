# 艺术品信息手机

## 上传图片

```
curl -s -X POST -F "img_file=@/etc/hosts" https://dev.bitvaluebk.com/h5/upload
```

## 上传测试

```
method: 1
money: 0
age: 3
describe: 寒假好假
source: 啊初二初二
name: 减号
tel: 13322224444
area: 北京市 北京市 东城区
picture[]: gather/293a93baa02cb18a840631bac1f9eeb20b7d436f.jpeg
picture[]: gather/be7572e4df527b4389d605766ea65aafcf2d822a.jpg
address: 几号机好了
```

```

curl -s -X POST -d 'name=Tom' https://dev.bitvaluebk.com/h5/save


curl -s -X POST -d 'name=Tom' http://localhost:8080/h5/save 
```

## 浏览数据

```
http://localhost:8080/h5/gather/list
https://dev.bitvaluebk.com/h5/gather/browse
```

## 下载CSV文件

```
http://localhost:8080/h5/gather/download
https://dev.bitvaluebk.com/h5/gather/download
```