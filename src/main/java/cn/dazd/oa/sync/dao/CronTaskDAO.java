package cn.dazd.oa.sync.dao;

import cn.dazd.oa.sync.entity.CronTaskEntity;
import cn.dazd.oa.sync.entity.QCronTaskEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronTaskDAO {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCronTaskEntity qCronTaskEntity = QCronTaskEntity.cronTaskEntity;

    public CronTaskDAO(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 获取全部记录
     *
     * @return 集合
     */
    public List<CronTaskEntity> getAllList() {
        return jpaQueryFactory.selectFrom(qCronTaskEntity).orderBy(qCronTaskEntity.id.desc()).fetch();
    }

    public CronTaskEntity findByTaskClassWithMethodName(String classWithMethodName) {
        return jpaQueryFactory.selectFrom(qCronTaskEntity).where(qCronTaskEntity.taskClassWithMethodName.eq(classWithMethodName)).fetchOne();
    }
}
