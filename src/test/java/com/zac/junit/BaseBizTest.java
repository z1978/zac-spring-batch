package com.zac.junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import com.zac.spring_batch.base.BaseBiz;
import com.zac.spring_batch.base.BaseDao;
//ReflectionTestUtils.setField(baseBiz, "baseDao", baseDao); 设置mock对象
@RunWith(MockitoJUnitRunner.class)
public class BaseBizTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
     
    @InjectMocks
    private BaseBiz baseBiz;
    @Mock
    private BaseDao baseDao;
     
    @Spy
    private BaseDao baseDaoSpy;
    @Captor
    private ArgumentCaptor<String> captor;
    @Before
    public void setUp() {
      baseDao = mock(BaseDao.class);
      MockitoAnnotations.initMocks(this);
    }
     
    // 用于返回值设定
    @Test
    public void testGetUser() {
    	logger.info(""+ logger.getName());
    	logger.info(""+ logger.getClass().getName());
    	System.err.println("=====" + logger.getName());
    	System.err.println(logger.getClass().getName());
    	System.err.println(logger.getClass().getSimpleName());

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("userName", "returnMap");
        when(baseDao.getUser("xiaoxiao")).thenReturn(returnMap);
         
        Map<String, Object> user = baseBiz.getUser("xiaoxiao");
        logger.info(user.toString());
         
        verify(baseDao).getUser("xiaoxiao");
    }
     
    @Test
    public void testGetUserAnswer() {
    	logger.info(""+ logger.getName());
    	logger.info(""+ logger.getClass().getName());
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("userName", "returnMap");
         
        String userName = "abc";
        when(baseDao.getUser(userName)).thenAnswer(new Answer<Map<String, Object>>() {
            /**
             * invocation 中方法
             * getArguments()  调用后会以Object数组的方式返回mock方法调用的参数。 
             * getMethod()  返回java.lang.reflect.Method 对象 
             * getMock()  返回mock对象 
             * callRealMethod()  真实方法调用，如果mock的是接口它将会抛出异常 
             */
            public Map<String, Object> answer(InvocationOnMock invocation) throws Throwable {
                Map<String, Object> returnMap = new HashMap<String, Object>();
                returnMap.put("userName", "xiaoxiao");
                Object[] argus = invocation.getArguments();
                if (!argus[0].equals("xiaoxiao")){
                    returnMap = new HashMap<String, Object>();
                    returnMap.put("userName", "returnMap");
                }
                return returnMap;
            }
             
        });
         
        Map<String, Object> user = baseBiz.getUser(userName);
        logger.info(user.toString());
         
        verify(baseDao).getUser(userName);
    }
    // 设置方法调用时产生异常
    @Test(expected=Exception.class)
    public void testGetUserThrowThenThrow() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("userName", "returnMap");
         
        when(baseDao.getUser("xiaoxiao")).thenThrow(new Exception());
     
        Map<String, Object> user = baseBiz.getUser("xiaoxiao");
        logger.info(user.toString());
         
        verify(baseDao).getUser("xiaoxiao");
    }
     
    @Test
    public void testGetUserThrowDoThrow() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("userName", "returnMap");
         
        doThrow(new Exception()).when(baseDao).getUserThrow("xiaoxiao");
        Map<String, Object> user = baseBiz.getUser("xiaoxiao");
        logger.info(user.toString());
         
        verify(baseDao).getUser("xiaoxiao");
    }
    // 主要用于验证
    @Test
    public void testArgumentCaptor() {
        baseBiz.getUser("xiaoxiao");
        baseBiz.getUser("mengmeng");
//       验证参数是否传递正确 ，是否被调用两次
//      captor.capture() 捕获方法参数 
//      captor.getValue() 获取方法参数值，如果方法进行了多次调用，它将返回最后一个参数值 
//      captor.getAllValues() 方法进行多次调用后，返回多个参数值 
        verify(baseDao, times(2)).getUser(captor.capture());
         
        logger.info(captor.getValue());
//      assertEquals("xiaoxiao", captor.getValue());
        assertEquals("mengmeng", captor.getValue());
    }
     
    // 用于参数匹配，无需关心参数值
    @Test
    public void testArgumentMatcher() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("userName", "returnMap");
         
        when(baseDao.getUser(anyString())).thenReturn(returnMap);
        logger.info(baseBiz.getUser("hello").toString());
         
        verify(baseDao).getUser("hello");
        verify(baseDao).getUser(eq("hello"));
    }
    // 用于参数判断是否符合方法需要
    // 自定义参数匹配规则 如果不是XiaoXiao将匹配失败
    @Test
    public void testArgumentMatcherCustom() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("userName", "returnMap");
     
        class XiaoXiaoFalse extends ArgumentMatcher<String> {
            @Override
            public boolean matches(Object argument) {
                return ((String)argument).equals("XiaoXiao");
            } 
        } 
         
        // argThat(Matcher<T> matcher)方法用来应用自定义的规则
        when(baseDao.getUser(argThat(new XiaoXiaoFalse()))).thenReturn(returnMap);
         
        logger.info(baseBiz.getUser("XiaoXiao").toString());
         
        verify(baseDao).getUser(argThat(new XiaoXiaoFalse()));
    }
     
    // spy 真实Object，只stub其中部分的方法，其他方法继续真实的跑。 针对spy【监控】 真实Object 时少用、慎用when 的语法， 尽量用doReturn语法
    @Test
    public void testSpy() {        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("userName", "returnMap");
 
        doReturn(returnMap).when(baseDaoSpy).getUser("xiaojian");
//      when(baseDaoSpy.getUser("xiaojian")).thenReturn(returnMap);
        ReflectionTestUtils.setField(baseBiz, "baseDao", baseDaoSpy);
        Map<String, Object> user = baseBiz.getUser("xiaojian");
        logger.info(user.toString());
        logger.info(baseBiz.getUserCard("xiaojian"));
        verify(baseDaoSpy).getUser("xiaojian");
 
    }
     
}
