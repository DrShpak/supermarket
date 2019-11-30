package project_6.products;

import java.util.Date;

@SuppressWarnings("unused")
interface IOverdue {

    default boolean isOverdue(Date endDate) {
        return new Date().compareTo(endDate) <= 0;
    }
}
