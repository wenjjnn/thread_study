package com.wjn.threadcode;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<Long> ids = new ArrayList<>();
        List<Long> tempIds = new ArrayList<>();
        tempIds.add(department.getId());
        ids.add(department.getId());
        while (true) {
            for (Long id : tempIds) {
                List<CmsAccountDepartment> departments = selectDepartmentsByParentId(id);
                if (!CollectionUtils.isEmpty(departments)) {
                    tempIds.clear();
                    for (CmsAccountDepartment department : departments) {
                        tempIds.add(department.getId());
                        ids.add(dep.getId());
                    }
                }else {
                    break;
                }
            }
        }
    }
}
