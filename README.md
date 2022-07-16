# 使用步骤

1.引入依赖
```xml
<dependency>
  <groupId>io.github.fengzaiyao</groupId>
  <artifactId>plugin-jwt</artifactId>
  <version>1.0.0</version>
</dependency>
```

2.配置文件
```properties
spring.jwt.plugin.secret=sdvjiopancpowopamacpojpo
spring.jwt.plugin.tokenHead=Bearer
spring.jwt.plugin.signatureAlgorithm=HS512
spring.jwt.plugin.expiration=1800000
spring.jwt.plugin.tokenKey=Authorization
spring.jwt.plugin.refreshExpiration=3600000
spring.jwt.plugin.refreshTokenKey=Refresh-Authorization
```

3.注入拦截器
```java
@Configuration
public class GlobalWebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthTokenUtil jwtAuthTokenUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        UserAuthInterceptor interceptor = new UserAuthInterceptor(jwtAuthTokenUtil);
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
```

4.模拟获取用户信息
```java
@RestController
@RequestMapping("/test")
public class HelloController extends BaseController {

    // 1.只是模拟获取token的过程
    @GetMapping("/generate-access-token")
    public AccessTokenDTO login() {
        LoginAuthUserInfoDTO userInfo = LoginAuthUserInfoDTO.builder()
                .uid(666L)
                .userNo("666")
                .username("1612464562121")
                .nickname("鼻屎拌饭加个蛋")
                .phone("13564546265")
                .email("35453132746@qq.com")
                .build();
        return generateAccessToken(userInfo);
    }

    // 2.获取token信息，记得在请求头中放入token
    @RequireCurrentUser(needUser = false)
    @GetMapping("/get-user-info")
    public LoginAuthUserInfoDTO getUserInfo() {
        System.out.println("HelloController#getUserInfo");
        return UserAuthContextHolder.getCurrentUser().orElse(null);
    }
}
```