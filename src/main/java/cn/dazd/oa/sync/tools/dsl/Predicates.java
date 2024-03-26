package cn.dazd.oa.sync.tools.dsl;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import lombok.Getter;

import java.util.List;
import java.util.function.Supplier;

@Getter
public class Predicates {
    final List<Predicate> predicates = Lists.newArrayList();

    public static Predicates create() {
        return new Predicates();
    }

    public void add(Supplier<Boolean> supplier, Supplier<Predicate> predicate) {
        if (supplier.get()) {
            predicates.add(predicate.get());
        }
    }
}
