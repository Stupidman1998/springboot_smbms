#### 登录跳转显示400

```html
<form class="loginForm" th:action="@{/toLogin}"  name="actionForm" id="actionForm"  method="post" >
```



```java
@RequestMapping(value = "/toLogin",method = RequestMethod.POST)
    public String toIndex(@RequestParam("userCode") String userCode,@RequestParam("password") String password, Model model){
        System.out.println("LoginController--->:In toLogin");
        if (!(userCode.isEmpty() && password.isEmpty())){
            System.out.println("LoginController--->:toframe");
            return "frame";
        }
        System.out.println("LoginController--->:ERROR: Return Index");
        return "index";
    }
```

这里的原因是password的参数名不对，前端的参数名是userPassword，修正就好，同时还需要设置“/index”这个请求。



#### MySql用户验证模块版本不一致问题

Unable to load authentication plugin 'caching_sha2_password'.



#### 拦截器不起作用，没进入拦截器

忘记在实现了WebMvcConfigurer的实现类上加上注解@Component

同时要注意排除不拦截的路径，否则就会导致重定向次数过多。

```java
@Component
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("InterceptorConfig--->:In there");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/js/**","/css/**","/images/**","/index","/toLogin","/logout");
    }
}
```



#### 利用@SessionAttributes和SessionStatus控制Session

```java
@Controller
@SessionAttributes("userSession")
public class LoginController {

    @RequestMapping("/logout")
    public String logout(SessionStatus status){
        //消除session
        status.setComplete();
        return "redirect:index";
    }
}
```

#### redirect 目标有三种构建方式

使用 redirect: 前缀url方式构建目标url
使用 RedirectView 类型指定目标, 推荐使用这个
使用 ModelAndView 类型指定目标, ModelAndView 视图名默认是forward, 所以对于redirect, 需要加上 redirect: 前缀

#### Redirect重定向需要有对应的RequestMapping()

```java
@RequestMapping("/toPwdmodify")
public String toPwdModify(Model model){
    System.out.println("in pwd");
    return "redirect:pwdmodify";
}

@RequestMapping("/pwdmodify")
public String pwdModify(){
    return "pwdmodify";
}
```

如果没有@RequestMapping("/pwdmodify")的话，重定向会找不到页面。但是如果没有重定向的话，直接return “pwdmodify”的话，是无需写多一个@RequestMapping("/pwdmodify")的。

#### 前端Ajax调用返回error

此问题一般是返回值有问题。这次是因为请求路径没有在后台写正确，没有区分大小写。

[ajax参数传递与后台接收](https://www.cnblogs.com/ooo0/p/10535278.html)

[fastjson对象，JSON，字符串，map之间的互转](https://www.cnblogs.com/heqiyoujing/p/9840424.html)



#### 前后端之前参数传递问题

```html
<div class="providerView">
            <p><strong>用户编号：</strong><span th:text="#{user.userCode}"></span></p>
            <p><strong>用户名称：</strong><span th:text="#{user.userName}"></span></p>
            <p><strong>用户性别：</strong>
				<span th:if="#{user.gender eq 1}">男</span>
				<span th:if="#{user.gender eq 2}">女</span>
			</p>
            <p><strong>出生日期：</strong><span th:text="#{#dates.format(user.birthday,'yyyy-MM-dd')}"></span></p>
            <p><strong>用户电话：</strong><span th:text="#{user.phone}"></span></p>
            <p><strong>用户地址：</strong><span th:text="#{user.address}"></span></p>
            <p><strong>用户角色：</strong><span th:text="#{user.userRoleName}"></span></p>
			<div class="providerAddBtn">
            	<input type="button" id="back" name="back" value="返回" >
            </div>
        </div>
```

```java
@RequestMapping("/userQuery")
public String userQuery(Model model, @ModelAttribute("userSession")User user){
    System.out.println(user);
    model.addAttribute("user.userCode",user.getUserCode());
    model.addAttribute("user.userName",user.getUserName());
    model.addAttribute("user.gender",user.getGender());
    model.addAttribute("user.birthday",user.getBirthday());
    model.addAttribute("user.phone",user.getPhone());
    model.addAttribute("user.address",user.getAddress());
    model.addAttribute("user.userRoleName",user.getUserRole());
    return "userview";
}
```

前端th:text="${user.userCode} 这样写，会匹配不到字段，需要用#号替代。

**‘$’和‘#’的区别？**

#### 自定义查询注解

[mybatis的注解开发之三种动态sql](https://www.cnblogs.com/guoyafenghome/p/9123442.html)

```java
//自定义查询注解
@SelectProvider(type = generateSql.class,method = "getUserListByParam")
List<User> getUserList(@Param("userName")String userName,@Param("userRole")Integer userRole);

class generateSql{
    public String getUserListByParam(String userName,Integer userRole){
        StringBuilder sql= new StringBuilder("select u.*,r.roleName from smbms_user u,smbms_role r where u.userRole = r.id ");
        if (userName!=null && userName!=" "){
            sql.append("and u.userName like '%#{userName}' ");
        }
        if (userRole!=null && userRole > 0){
            sql.append("and u.userRole = #{userRole}");
        }
        return sql.toString();
    }
```

#### Mybatis报错：Could not set parameters for mapping: ParameterMapping

原因是因为#{ }写在字符串中不能识别，要改写成${ }这种形式

```java
public String getUserListByParam(String userName,Integer userRole){
            StringBuilder sql= new StringBuilder("select u.*,r.roleName from smbms_user u,smbms_role r where u.userRole = r.id ");
            if (userName!=null && userName!=" "){
                sql.append("and u.userName like '%${userName}' ");
            }
            if (userRole!=null && userRole > 0){
                sql.append("and u.userRole = #{userRole}");
            }
            return sql.toString();
        }
```

#### SQL语句起别名

```java
StringBuilder sql= new StringBuilder("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id ");
```

返回的结果集会自动把roleName注入到User对象中的userRoleName属性中。

#### PageHelper没有正确分页

当然，按照官网说的，使用PageHelper方法有可能产生线程安全问题，其实这就是因为pageHelper没有跟在mybatis查询方法之后导致的线程安全。具体可以查看官网。

```java
        //使用分页插件 startPage(当前页数,每页显示条数)
        PageHelper.startPage(pageIndex,8);
        List<User> userList = userService.getUserList(queryUserName, queryUserRole);
```

但是仍然没有正确分页，还是一次性读取全部数据。

#### 前端往后端传实体类的属性，可以在后端用一个实体类来接收

```java
@RequestMapping("/useradd")
public String useradd(@RequestParam("userCode")String userCode,@RequestParam("userName")String userName,
	@RequestParam("userPassword")String userPassword,@RequestParam("gender")Integer gender,
    @RequestParam("birthday")Date birthday,@RequestParam("phone")String phone,
    @RequestParam("address")String address,@RequestParam("userRole")Integer userRole,
    @ModelAttribute("userSession")User userSession){
```

改成：注意，==不需要@RequestParam注解标注==

```java
@RequestMapping("/useradd")
    public String useradd(User user，@ModelAttribute("userSession") User userSession) {
        //还需要从Session中获得当前用户id，和new创建日期。
        user.setCreateBy(userSession.getId());
        user.setCreationDate(new Date());
        userService.userAdd(user);
        return "redirect:userQuery";
    }
```

#### There is no getter for property named 'user' in 'class com.redisdemo.demo.entity.User'

```java
    int updateUser(@Param("user") User user);
```

加上@Param注解

### Field error in object 'userInfo' on field 'birthday'

在实体类的Date类型的属性名上加上@DateTimeFormat(pattern = "yyyy-MM-dd")