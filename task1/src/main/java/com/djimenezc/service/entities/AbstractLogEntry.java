package com.djimenezc.service.entities;

import java.util.Date;

/**
 * Common method of a log entity
 * Created by david on 11/07/2016.
 */
class AbstractLogEntry {

    private Date created;

    public Date getCreatedDate() {
        return created;
    }

    public void setCreatedDate(Date created) {
        this.created = created;
    }

}
