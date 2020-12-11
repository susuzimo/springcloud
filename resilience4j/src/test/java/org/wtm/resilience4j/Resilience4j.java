package org.wtm.resilience4j;



import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;


public class Resilience4j {

    @Test
    public void test1(){
        //获取一个CircuitBreakerRegistry实例，可以调用ofDefaults获取一个CircuitBreakerRegistry实例，也可以自定义属性。
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                //故障率阈值百分比，超过这个阈值，断路器就会打开
                .failureRateThreshold(50)
                //断路器保持打开的时间，在到达设置的时间之后，断路器会进入到 half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //当断路器处于half open 状态时，环形缓冲区的大小
                .ringBufferSizeInHalfOpenState(2)
                //断路器处于关闭状态时，环形缓冲区的大小
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        //断路器的名字
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        CircuitBreaker cb2 = r1.circuitBreaker("javaboy2", config);
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        //调用
        Try<String> result = Try.of(supplier)
                //v 函数返回值
                .map(v -> v + " hello world");
        System.out.println(result.isSuccess()); //函数调用是否成功
        System.out.println(result.get());       //结果
    }


    @Test
    public void test2(){
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                //故障率阈值百分比，超过这个阈值，断路器就会打开
                .failureRateThreshold(50)
                //断路器保持打开的时间，在到达设置的时间之后，断路器会进入到 half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //断路器处于关闭状态时，环形缓冲区的大小
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        //断路器的名字
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        System.out.println(cb1.getState());
        cb1.onError(0,new RuntimeException());

        System.out.println(cb1.getState());
        cb1.onError(0,new RuntimeException());
        System.out.println(cb1.getState());
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        //调用
        Try<String> result = Try.of(supplier)
                //supplier执行成功才会执行map v 函数返回值
                .map(v -> v + " hello world");
        System.out.println(result.isSuccess()); //函数调用是否成功
        System.out.println(result.get());       //结果
    }



    @Test
    public void test3(){
        RateLimiterConfig build = RateLimiterConfig.custom()
                //阈值刷新周期
                .limitRefreshPeriod(Duration.ofMillis(1000))
                //阈值刷新频次 处理请求个数
                .limitForPeriod(2)
                //限流之后冷却时间
                .timeoutDuration(Duration.ofMillis())
                .build();
    }


}
