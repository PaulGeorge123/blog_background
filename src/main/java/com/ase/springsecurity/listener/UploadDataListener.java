package com.ase.springsecurity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.entity.easyexcel.UploadUserData;
import com.ase.springsecurity.repository.UploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gyhstart
 * @create 2021/2/16 - 20:50
 * @有个很重要的点 UploadDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 **/
public class UploadDataListener extends AnalysisEventListener<UploadUserData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 20;
//    List<UploadUserData> list = new ArrayList<>();
    List<User> list = new ArrayList<>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private UploadRepository uploadRepository;

    public UploadDataListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        uploadRepository = new UploadRepository();
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param uploadRepository
     */
    public UploadDataListener(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(UploadUserData data, AnalysisContext context) {
        User user = new User();
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        user.setNickname(data.getNickname());
        user.setUsername(data.getUsername());
        user.setPassword("1qaz!QAZ");
        user.setRole(data.getRole());
        user.setDatetimes(data.getDatetimes());
        user.setGender(data.getGender());
        user.setLogicRemove(1);
        //list.add(data);
        //LOGGER.info("添加的一条数据:{}", JSON.toJSONString(user));
        list.add(user);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        uploadRepository.save(list);
        LOGGER.info("存储数据库成功！");
    }
}
