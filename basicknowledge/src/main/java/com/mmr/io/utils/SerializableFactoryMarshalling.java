package com.mmr.io.utils;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Description: 借助Marshalling进行序列化传输
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月20日 19:28
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class SerializableFactoryMarshalling {

    /**
     * 创建Jboss Marshalling 解码器MarshallingDecoder
     * @return MarshallingDecoder
     */
    public static MarshallingDecoder marshallingDecoderBuidler(){
        //首先通过marshalling的静态方法获取MarshallerFacotory的实例对象，参数serial标识创建的是java序列化工厂对象
        //此方法由jboss-marshalling-serial 包独家提供
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //和Bootstrap类似，marshalling也需要创建自己的配置对象——MarshallingConfiguration对象
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        //【特别的，选择序列化的版本时，如果程序使用jdk1.5及以上版本时，序列化版本必须也只能填5！】
        configuration.setVersion(5);
        //根据marshallerFactory和MarshallingConfiguration创建provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        //构建Netty的MarshallingDecoder对象，两个参数分别是: provider和单个消息序列化后的最大长度 (这里再次看出，java不提倡发送不限制长度的数据包)
        return new MarshallingDecoder(provider, 1024 * 1024);
    }

    /**
     * 创建Jboss Marshalling 编码器MarshallingEncoder
     * @return MarshallingEncoder
     */
    public static MarshallingEncoder marshallingEncoderBuilder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        //构建Netty的MarshallingEncoder对象，MarshallingEncoder用于实现 将序列化接口的POJO对象序列化成二进制数组
        return new MarshallingEncoder(provider);
    }
}
