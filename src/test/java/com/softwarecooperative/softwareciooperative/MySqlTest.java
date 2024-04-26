package com.softwarecooperative.softwareciooperative;

import com.softwarecooperative.softwareciooperative.dao.mapper.ClassMapper;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.utils.snowflake.SnowflakeIdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MySqlTest {

    @Autowired
    private ClassMapper classMapper;

    @Test
    public void testInsert() {
        BClass bClass = BClass.builder()
                .classId(SnowflakeIdWorker.nextId())
                .teacherId(114514)
                .teacherName("李田所")
                .courseId(114514)
                .courseName("沼气工程导论")
                .classTerm("1919学年第8学期")
                .build();
        classMapper.insert(bClass);
    }

    @Test
    public void testSelect() {
        System.out.println(classMapper.selectAll());
        BClass clazz = BClass.builder().classId(142543168).build();
        System.out.println(classMapper.selectOne(clazz));
        System.out.println(classMapper.selectByCond(clazz));
    }

    @Test
    public void testUpdate() {
        BClass clazz = BClass.builder()
                .classId(142543168)
                .teacherName("理塘丁真")
                .courseName("抽悦刻五")
                .build();
        classMapper.update(clazz);
    }

    @Test
    public void testDelete() {
        classMapper.delete(142577728);
    }
}
