# tryhook



## 项目描述

| 实训项目    | 建议人员配置            | 主要功能                                                     | 说明                                                         | 开发资料                                                     |
| :---------- | :---------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 图书服务APP | 前端1人；     后端1人； | 【用户端】     登录注册     推荐（搜索、banner轮播、推荐列表、详情、视频播放、收藏、分类及拨打电话相关功能）     分类（搜索、图书分类）     我的（个人资料、我的收藏、关于我们）     【后台】     登入登出、轮播图管理、用户管理、园长管理、书籍管理、视频管理、二维码管理、系统设置 | 微信搜索乐童记忆小程序；功能及要实现的需求见开发资料说明，该小程序可作为业务及需求参考；     实训项目基于该需求及业务以混合APP形式实现 | 【产品原型及说明】     https://axhub.im/pro/bd604aa27ec1a722     【设计及切图】     https://lanhuapp.com/url/VNJRj-vo14e     【后台示例】     http://ltjy.hooook.com/admin    admin/admin     【代码参考】     遇到需求不清楚的问产品，开发不清楚的问前后端老师；代码可在项目完成后作为比对参考 |



## 后端架构

后端部分，总共分为两大块业务，一部分是用户端，一部分是管理员后台

### 项目分层

| 名称     | 描述                              |
| -------- | --------------------------------- |
| module包 | 模型模块，包含 dao do manager 层  |
| core包   | 业务模块，包含 bo service 层      |
| web包    | web模块，包含controller dto vo 层 |



## 接口文档

### 接口规范

1. 一般的响应必须用Result类封装响应结果，被转化为JSON保存在response实体内：

   ```java
   public class Result<R> {
   
       /**
        * 接口执行成功失败标识
        */
       private boolean success;
   
       /**
        * 结果数据
        */
       private R value;
   
       /**
        * 错误或者提示信息
        */
       private String msg;
     
       /**
        * 响应代码
        */
       private int code;
   }
   ```

   JSON格式：

   ```json
   {
     "success": true,
     "value": {结果值},
     "msg": "",
     "code": -1
   }
   ```

   

2. code，msg规范

   ```java
   SUCCESS(-1, "请求成功！"),
   RESULT_EMPTY(0, "请求结果为空！"),
   PARAM_ERROR(1, "参数有误！"),
   RUNTIME_ERROR(2, "服务器异常！"),
   DATA_CONFLICT(3, "数据冲突！")
   ;
   ```

3. 所有分页请求需要带上page和size参数，page从0开始，size至少为1



4. 上传静态资源的请求格式

   ```html
   <form action="/principal" method="post" enctype="multipart/form-data">
       选择图片：<input type="file" name="license" accept="image/*"/> <br>
       <label>               <!-- 注意name必须同接口描述中参数名一致 -->
           手机号：<input type="text" name="phoneNumber"> 
                             <!-- 注意name必须同接口描述中参数名一致 -->
       </label> <br>
       <input type="submit" value="立刻上传">
   </form>
   ```

5. 获取静态资源的访问流程是：访问特定URL获取到所需静态资源路径，再通过路径获取静态资源



### 接口描述

1. 首页

   | 解释                       | URL                      | 返回值（value值）                                            |
   | -------------------------- | ------------------------ | ------------------------------------------------------------ |
   | 按ID获取轮播图静态资源路径 | /carousel/{id}           | 字符串                                                       |
   | 分页请求推荐图书           | /recommend?page=0&size=1 | {<br/>  "id": 1,<br/>  "name": "demoData",<br/>  "coverPath": "demoData"<br/>} |

2. 图书

   | 解释                         | URL                      | 返回值（value值）                                            |
   | ---------------------------- | ------------------------ | ------------------------------------------------------------ |
   | 按关键字keyword搜索图书      | /search?keyword=demoData | [{<br/>  "id": 1,<br/>  "name": "demoData",<br/>  "coverPath": "demoData",<br/>  "price": 1,<br/>  "author": "demoData",<br/>  "publisher": "demoData",<br/>  "publishTime": "demoData",<br/>  "isbn": "demoData"<br/>}, ...] |
   | 获取所有图书分类             | /category                | [{<br/>  "id": 1,<br/>  "desc": "demoData"<br/>}, ...]       |
   | 获取某个图书分类下的所有图书 | /books?categoryId=1      | [{<br/>  "id": 1,<br/>  "name": "demoData",<br/>  "coverPath": "demoData"<br/>}, ...] |
   | 按照图书id获取详情           | /book/{id}               | {<br/>  "id": 1,<br/>  "name": "demoData",<br/>  "coverPath": "demoData",<br/>  "price": 1,<br/>  "author": "demoData",<br/>  "publisher": "demoData",<br/>  "publishTime": "demoData",<br/>  "isbn": "demoData",<br/>  "introduction": "demoData",<br/>  "videos": {<br/>    "id": 1,<br/>    "bookId": 1,<br/>    "chapterNum": 1,<br/>    "chapterTitle": "demoData",<br/>    "videoPath": "demoData"<br/>  },<br/>  "star": true<br/>} |

3. 我的

   | 解释                         | URL                                                | 返回值（value值）                                            |
   | ---------------------------- | -------------------------------------------------- | ------------------------------------------------------------ |
   | 表单登录                     | POST /login username=&password=                    | 重定向                                                       |
   | 用户登出                     | /logout                                            | 重定向到GET /login?logout                                    |
   | 收藏/取消收藏                | /star?bookId=1                                     | true表示状态变更为收藏，false为未收藏                        |
   | 获取所有收藏图书             | /stars                                             | [{<br/>  "id": 1,<br/>  "name": "demoData",<br/>  "coverPath": "demoData",<br/>  "introduction": "demoData"<br/>}, ...] |
   | 获取某个图书分类下的所有图书 | POST /principal phoneNumber=123&license=file       | true                                                         |
   | 状态查看                     | GET /principal/status                              | 返回integer：NOT(-1, "未认证"), PENDING(0, "认证中"), SUCCESS(1, "已认证"), FAILED(2, "认证失败"); |
   | 获取已经上传的认证信息       | GET /principal                                     | {<br/>  "id": 1,<br/>  "userId": 1,<br/>  "phoneNumber": "demoData",<br/>  "licensePath": "demoData",<br/>  "kindergartenName": "demoData",<br/>  "certificationStatusId": 1<br/>} |
   | 注册用户(头像默认)           | POST /register username=demoData&password=demoData | true                                                         |
   | 上传用户头像                 | POST /avatar avatar=avatar                         | true                                                         |
   | 获取用户信息                 | GET /user                                          | {<br/>  "id": 1,<br/>  "username": "demoData",<br/>  "avatarPath": "demoData"<br/>} |

4. 获取静态资源

URL：/static?dir=demoData



### 园长认证逻辑梳理