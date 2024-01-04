package com.xht.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/4  14:45
 */

@Configuration
@Slf4j
public class RabbitmqConfig {




    @Autowired
    ConnectionFactory connectionFactory;

    private RabbitTemplate rabbitTemplate;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate();
        return rabbitTemplate;
    }

    /**
    * @Description: 如果配置了json解析器，则程序会走自动确认消费，需重写以下
     * 监听也需要设置Jackson2JsonMessageConverter 才能接收对象
    * @Author: yzd
    * @Date: 2024/1/4-17:22
    */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }


    private void initRabbitTemplate() {

        /**
         * 1、只要消息抵达Broker就ack=true
         * correlationData：当前消息的唯一关联数据(这个是消息的唯一id)
         * ack：消息是否成功收到
         * cause：失败的原因
         */
        //设置确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("抵达broker"+"confirm...correlationData["+correlationData+"]==>ack:["+ack+"]==>cause:["+cause+"]");
        });

        /**
         * 只要消息没有投递给指定的队列，就触发这个失败回调
         * message：投递失败的消息详细信息
         * replyCode：回复的状态码
         * replyText：回复的文本内容
         * exchange：当时这个消息发给哪个交换机
         * routingKey：当时这个消息用哪个路邮键
         */
        rabbitTemplate.setReturnsCallback(returned -> {
            String exchange = returned.getExchange();
            Message message = returned.getMessage();
            String routingKey = returned.getRoutingKey();
            int replyCode = returned.getReplyCode();
            String replyText = returned.getReplyText();
            log.info("消息投递失败"+"Fail Message["+message+"]==>replyCode["+replyCode+"]" +
                    "==>replyText["+replyText+"]==>exchange["+exchange+"]==>routingKey["+routingKey+"]");
        });
    }


    @Autowired
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitAdmin rabbitAdmin(){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

   /********************* Direct ****************************/
    @Bean
    public Queue directQueue(){
        // durable: 是否持久化，默认是false。持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在
        // exclusive: 默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete: 是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // 一般设置一下队列的持久化就好，其余两个就是默认false
        Queue queue = new Queue("directQueue",true );
        // 声明队列，启动时自动创建队列
        rabbitAdmin().declareQueue(queue);
        return queue;
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange", true, false);
    }

    @Bean
    public Binding bindingDirectExchange(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
    }

    /********************* Topic ****************************/

    @Bean
    public Queue topicFirstQueue(){
        Queue queue = new Queue("topicFirstQueue", true);
        rabbitAdmin().declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue topicSecondQueue(){
        Queue queue = new Queue("topicSecondQueue", true);
        rabbitAdmin().declareQueue(queue);
        return queue;
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange",true,false);
    }


    @Bean
    public Binding bindingTopicFirstExchange(){
        return BindingBuilder.bind(topicFirstQueue()).to(topicExchange()).with("topic.first");
    }

    //*匹配一个单词
    //#匹配0个或多个单纯
    @Bean
    public Binding bindingTopicSecondExchange() {
        return BindingBuilder.bind(topicSecondQueue()).to(topicExchange()).with("topic.#");
    }

    /********************* Fanout ****************************/

    @Bean
    public Queue fanoutFirstQueue() {
        Queue queue = new Queue("fanoutFirstQueue", true);
        // 声明队列，启动时自动创建队列
        rabbitAdmin().declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue fanoutSecondQueue() {
        Queue queue = new Queue("fanoutSecondQueue", true);
        // 声明队列，启动时自动创建队列
        rabbitAdmin().declareQueue(queue);
        return queue;
    }

    @Bean
    public FanoutExchange  fanoutExchange(){
        return new FanoutExchange("fanoutExchange",true,false);
    }

    @Bean
    public Binding bindingFanoutFirstExchange() {
        return BindingBuilder.bind(fanoutFirstQueue()).to(fanoutExchange());
    }


    @Bean
    public Binding bindingFanoutSecondExchange() {
        return BindingBuilder.bind(fanoutSecondQueue()).to(fanoutExchange());
    }



}
