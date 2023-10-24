package com.lgl.qidian;



import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgl.qidian.entity.tobe_writer_contoller.AdjudicationWriterDo;
import com.lgl.qidian.mapper.UserRoleMapper;
import com.lgl.qidian.other.Test1;
import com.lgl.qidian.other.son;
import com.lgl.qidian.service.TestService;
import com.lgl.qidian.util.JwtUtils;
import com.lgl.qidian.util.test;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class QidianApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Test
    public void testRedis(){
        System.out.println(stringRedisTemplate);
        stringRedisTemplate.opsForValue().set("刘广林","");
        String s = stringRedisTemplate.opsForValue().get("刘广林");
        System.out.println(s);
        System.out.println(stringRedisTemplate.delete("55"));
    }

    @Test
    public void testthking(){

    }


    //雪花算法 64位   19byte
    // UUID   32位   36byte
    // JWT生成的密匙长度对比  256位   109byte


    //验证转成父类或接口后，多态的方法内能不能拿到子类声明的变量

    //结论：多态的方法能拿到子类中声明的变量



    //装饰器模式实现

    //验证模板方法不讲子类转为父类时能否用父类方法 成功完成模板设计模式

    @Test
    public void testClassLoader(){
        InputStream fupo = this.getClass().getClassLoader().getResourceAsStream("fupo.jpg");
        System.out.println(fupo);
    }

    @Test
    public void testClass(){
        InputStream fupo = this.getClass().getResourceAsStream("fupo.jpg");
        System.out.println(fupo);
    }

    @Test
    public void testJwt(){
        String token = JwtUtils.generyToken(UUID.randomUUID().toString(), "123456", "key");
        DecodedJWT decodedJWT = JwtUtils.decodeJWT(token, "123456");
        String string = decodedJWT.getClaim("key").toString();
        System.out.println(string);
    }


    @Autowired
    RabbitTemplate rabbitTemplate;
    //测试消息队列的confirm return在主线程结束后，还会不会执行
    //结论，回调是在rabbitmq的线程池中启用一个线程，不用在发送的线程中等待
    @Test
    public void testRabbitmq() throws InterruptedException {
        CachingConnectionFactory connectionFactory = (CachingConnectionFactory)rabbitTemplate.getConnectionFactory();
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("我是comfirm回调方法id为"+ correlationData.getId() + ack);
            }
        });
        rabbitTemplate.send("admin","adjudication",
                new Message("我是消息呀，快开门".getBytes())
                );
        System.out.println("我是主线程");
    }

    @Test
    public void testObjectMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Object[] array = new Object[5];
        array[0] = new AdjudicationWriterDo(45,48, "false");

        System.out.println(objectMapper.writeValueAsString(array));
    }

    @Autowired
//    TestService testService;
    //测试开启事务后，报错会不会被拦截。
    // 如果拦截了，aop的后置报错增强和后置不报错增强执行情况
    // 符合想象，不会拦截
    @Test
    public void testTrancationalAop(){
//        testService.testTranslationAndAop();
    }

    @Autowired
    TestService testService;

    @Test
    public void testJoinPoint(){
//        testService.testTranslationAndAop();
         testService.test();
    }

    //测试可变参数列表在方法中能否用数组方式使用
    @Test
    public void testArgs(){
        testArgs("1");
    }
    public void testArgs(String... args){
        System.out.println(args[0]);
    }

    @Test
    public void charTest(){
        char chinese = '中';
        System.out.println(chinese);
    }

    @Test
    public void nullableTest(){
        useNullable(null);
    }
    void useNullable(@Nullable String name){
        System.out.println(name);
    }

    @Test
    public void stringSplitTest(){
        String str = ",1,5,,";
        String[] strs = str.split(",");
        System.out.println();
    }

    @Test
    public void toArrayTest(){
        List<String> list = new ArrayList<>(2);
        list.add("liang");
        list.add("da");
        String[] s = new String[10];
        s = list.toArray(s);
        System.out.println();
    }

    @Test
    public void subListTest(){
        ArrayList<String> stringList = new ArrayList<>(2);
        stringList.add("liangda");
        stringList.add("guanbao");
        List<String> strings = stringList.subList(0, 2);
        for (String s:strings) {
            stringList.add("我是新加的，会导致子列表并发修改报错");
            System.out.println(s);
        }
        System.out.println();
    }

    @Test
    public void colletionForeachTest(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("simple");
        strings.add("under");
        String[] objectArray = new String[]{"simpel","silpel","under","alfgo"};
        Arrays.sort(objectArray, (o1,o2)->{
            return o1.length() >= o2.length()? 1: -1;
        });
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        Set<Map.Entry<Object, Object>> entries = objectObjectHashMap.entrySet();
        Set<Object> objects = objectObjectHashMap.keySet();
        System.out.println();

//        for (String s : strings){
//            if ("2".equals(s)){
//                strings.remove(s);
//            }
//        }
//        Iterator<String> iterator = strings.iterator();
//        while(iterator.hasNext()){
//            String s = iterator.next();
//            if ("2".equals(s)){
//                strings.remove(s);
//            }
//        }
    }

    class User{
        public int age;
        public User(int age) {
            this.age = age;
        }
        private son so = new son(){
            @Override
            protected void test() {
                super.test();
            }
        };

        }



    @Test
    public void tryText(){
        new User(15);
    }

    static int num = 5;

    @Autowired
    test test;

    @Autowired
    com.lgl.qidian.User user;

    @Test
    public void transactionalTest() throws Exception {
        user.transactional(null);
    }

    /**
     * todo
     * @since
     * @param o
     * @throws Exception
     */
    @Transactional(rollbackFor = {ArithmeticException.class,Exception.class})
    public void transactional(@Nullable Object o) throws Exception{


    }

    @Test
    public void voidReturnTest(){
        System.out.println(Test1.class.getTypeName());
    }

    @Autowired
    UserRoleMapper userRoleMapper;
    @Test
    public void transactionTimeOutTest() throws Exception {

        System.out.println("开始读");
//        Thread thread1  = new Thread(
//                () -> {
//                    test.testUpdate();
//            }
//        );
//        thread1.start();
//        Thread.sleep(1000);
        Thread thread2 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("五秒后子线程结束");
                    }
                }
        );
        thread2.start();
//        Thread.sleep(20000);
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void loggerTest(){
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("test");
        System.out.println(logger.getEffectiveLevel());
        logger.info("这是一次测试");
        System.out.println(passwordEncoder.encode("153945089"));
    }

    @Test
    public void main(){
        List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList("user:add,ROLE_admin");
        list.stream().forEach(grantedAuthority -> {
            System.out.println(grantedAuthority);
        });

    }
}
